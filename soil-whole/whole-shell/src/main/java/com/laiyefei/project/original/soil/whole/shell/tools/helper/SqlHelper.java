/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.laiyefei.project.original.soil.whole.shell.tools.helper;

import com.baomidou.mybatisplus.annotation.DbType;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.helper.IHelper;
import com.laiyefei.project.original.soil.whole.kernel.aid.ContextHelper;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.SqlExecutor;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.StringUtil;
import com.laiyefei.project.original.soil.whole.kernel.tools.util.Validator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-10-02 18:09
 * @Desc : SQL帮助类
 * @Version : v1.0.0.20201002
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class SqlHelper implements IHelper {
    private static final Logger log = LoggerFactory.getLogger(SqlHelper.class);

    // 数据字典SQL
    private static final String DICTIONARY_SQL = "SELECT id FROM ${SCHEMA}.dictionary WHERE id=0";
    private static final String MYBATIS_PLUS_SCHEMA_CONFIG = "mybatis-plus.global-config.db-config.schema";
    private static String CURRENT_SCHEMA = null;
    private static Environment environment;

    /**
     * 初始化
     *
     * @param env
     */
    public static void init(Environment env)  {
        environment = env;
    }

    /***
     * 初始化安装SQL
     * @return
     */
    public static void initBootstrapSql(Class inst, Environment environment, String module) {
        init(environment);
        String dbType = getDbType();
        String sqlPath = "META-INF/sql/init-" + module + "-" + dbType + ".sql";
        extractAndExecuteSqls(inst, sqlPath);
    }

    /**
     * 检查是否dictionary表已存在
     *
     * @return
     */
    public static boolean checkIsDictionaryTableExists() {
        return checkIsTableExists(DICTIONARY_SQL);
    }

    /**
     * 检查SQL文件是否已经执行过
     *
     * @param sqlStatement
     * @return
     */
    public static boolean checkIsTableExists(String sqlStatement) {
        sqlStatement = buildPureSqlStatement(sqlStatement);
        return SqlExecutor.validateQuery(sqlStatement);
    }

    /***
     * 从SQL文件读出的行内容中 提取SQL语句并执行
     * @param sqlPath
     * @return
     */
    public static boolean extractAndExecuteSqls(Class inst, String sqlPath) {
        List<String> sqlFileReadLines = readLinesFromResource(inst, sqlPath);
        if (Validator.isEmpty(sqlFileReadLines)) {
            return false;
        }
        // 解析SQL
        List<String> sqlStatementList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String line : sqlFileReadLines) {
            if (line.contains("--")) {
                line = line.substring(0, line.indexOf("--"));
            }
            sb.append(" ");
            if (line.contains(";")) {
                // 语句结束
                sb.append(line.substring(0, line.indexOf(";")));
                String cleanSql = buildPureSqlStatement(sb.toString());
                sqlStatementList.add(cleanSql);
                sb.setLength(0);
                if (line.indexOf(";") < line.length() - 1) {
                    String leftSql = line.substring(line.indexOf(";") + 1);
                    if (Validator.notEmpty(leftSql)) {
                        sb.append(leftSql);
                    }
                }
            } else if (Validator.notEmpty(line)) {
                sb.append(line);
            }
        }
        if (sb.length() > 0) {
            String cleanSql = buildPureSqlStatement(sb.toString());
            sqlStatementList.add(cleanSql);
            sb.setLength(0);
        }
        // 返回解析后的SQL语句
        return executeMultipleUpdateSqls(sqlStatementList);
    }

    /**
     * 构建纯净可执行的SQL语句: 去除注释，替换变量
     *
     * @param sqlStatement
     * @return
     */
    protected static String buildPureSqlStatement(String sqlStatement) {
        sqlStatement = clearComments(sqlStatement);
        // 替换sqlStatement中的变量，如{SCHEMA}
        if (sqlStatement.contains("${SCHEMA}")) {
            if (getDbType().equals(DbType.SQL_SERVER.getDb())) {
                sqlStatement = StringUtil.replace(sqlStatement, "${SCHEMA}", getSqlServerCurrentSchema());
            } else if (getDbType().equals(DbType.ORACLE.getDb())) {
                sqlStatement = StringUtil.replace(sqlStatement, "${SCHEMA}", getOracleCurrentSchema());
            } else {
                sqlStatement = StringUtil.replace(sqlStatement, "${SCHEMA}.", "");
            }
        }
        return sqlStatement;
    }

    /***
     * 执行多条批量更新SQL
     * @param sqlStatementList
     * @return
     */
    public static boolean executeMultipleUpdateSqls(List<String> sqlStatementList) {
        if (Validator.isEmpty(sqlStatementList)) {
            return false;
        }
        for (String sqlStatement : sqlStatementList) {
            try {
                boolean success = SqlExecutor.executeUpdate(sqlStatement, null);
                if (success) {
                    log.info("初始化SQL执行完成: " + StringUtil.substring(sqlStatement, 0, 60) + "...");
                }
            } catch (Exception e) {
                log.error("初始化SQL执行异常，请检查或手动执行。SQL => " + sqlStatement, e);
            }
        }
        return true;
    }

    /***
     * 获取
     * @param inst
     * @return
     */
    protected static List<String> readLinesFromResource(Class inst, String sqlPath) {
        List<String> lines = null;
        try {
            InputStream is = inst.getClassLoader().getResourceAsStream(sqlPath);
            lines = IOUtils.readLines(is, "UTF-8");
        } catch (FileNotFoundException fe) {
            log.warn("暂未发现数据库SQL: " + sqlPath + "， 请参考其他数据库定义DDL手动初始化。");
        } catch (Exception e) {
            log.warn("读取SQL文件异常: " + sqlPath, e);
        }
        return lines;
    }

    /***
     * 剔除SQL中的注释，提取可执行的实际SQL
     * @param inputSql
     * @return
     */
    private static String clearComments(String inputSql) {
        String[] sqlRows = inputSql.split("\\n");
        List<String> cleanSql = new ArrayList();
        for (String row : sqlRows) {
            // 去除行注释
            if (row.contains("--")) {
                row = row.substring(0, row.indexOf("--"));
            }
            if (Validator.notEmpty(row.trim())) {
                cleanSql.add(row);
            }
        }
        inputSql = StringUtil.join(cleanSql, " ");

        // 去除多行注释
        inputSql = removeMultipleLineComments(inputSql);
        // 去除换行
        return inputSql.replaceAll("\r|\n", " ");
    }

    /***
     * 去除多行注释
     * @param inputSql
     * @return
     */
    private static String removeMultipleLineComments(String inputSql) {
        if (inputSql.contains("*/*")) {
            //忽略此情况，避免死循环
            return inputSql;
        }
        if (inputSql.contains("/*") && inputSql.contains("*/")) {
            inputSql = inputSql.substring(0, inputSql.lastIndexOf("/*")) + inputSql.substring(inputSql.indexOf("*/") + 2, inputSql.length());
        }
        if (inputSql.contains("/*") && inputSql.contains("*/")) {
            return removeMultipleLineComments(inputSql);
        }
        return inputSql;
    }

    //SQL Server查询当前schema
    public static final String SQL_DEFAULT_SCHEMA = "SELECT DISTINCT default_schema_name FROM sys.database_principals where default_schema_name is not null AND name!='guest'";

    /**
     * 查询SqlServer当前schema
     *
     * @return
     */
    public static String getSqlServerCurrentSchema() {
        if (CURRENT_SCHEMA == null) {
            Object firstValue = queryFirstValue(SQL_DEFAULT_SCHEMA, "default_schema_name");
            if (firstValue != null) {
                CURRENT_SCHEMA = (String) firstValue;
            }
            if (CURRENT_SCHEMA == null) {
                CURRENT_SCHEMA = environment.getProperty(MYBATIS_PLUS_SCHEMA_CONFIG);
            }
            // dbo schema兜底
            if (CURRENT_SCHEMA == null) {
                CURRENT_SCHEMA = "dbo";
            }
        }
        return CURRENT_SCHEMA;
    }

    /**
     * 获取当前schema，oracle默认schema=当前user
     *
     * @return
     */
    public static String getOracleCurrentSchema() {
        if (CURRENT_SCHEMA == null) {
            // 先查找配置中是否存在指定
            String alterSessionSql = environment.getProperty("spring.datasource.hikari.connection-init-sql");
            if (Validator.notEmpty(alterSessionSql) && StringUtil.containsIgnoreCase(alterSessionSql, " current_schema=")) {
                CURRENT_SCHEMA = StringUtil.substringAfterLast(alterSessionSql, "=");
            }
            if (CURRENT_SCHEMA == null) {
                CURRENT_SCHEMA = environment.getProperty(MYBATIS_PLUS_SCHEMA_CONFIG);
            }
            if (CURRENT_SCHEMA == null) {
                // 然后默认为当前用户名大写
                String username = environment.getProperty("spring.datasource.username");
                if (username != null) {
                    CURRENT_SCHEMA = username.toUpperCase();
                }
            }
        }
        return CURRENT_SCHEMA;
    }


    /**
     * 查询SQL返回第一项
     *
     * @return
     */
    public static Object queryFirstValue(String sql, String key) {
        try {
            List<Map<String, Object>> mapList = SqlExecutor.executeQuery(sql, null);
            if (Validator.notEmpty(mapList)) {
                for (Map<String, Object> mapElement : mapList) {
                    if (mapElement.get(key) != null) {
                        return mapElement.get(key);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取SqlServer默认Schema异常: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取数据库类型
     *
     * @return
     */
    public static String getDbType() {
        return ContextHelper.getDatabaseType();
    }
}