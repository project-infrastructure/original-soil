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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.aid.binding.parser.ParserCache;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.pojo.co.Constant;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.service.BaseService;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.BeanUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util.Validator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : Spring上下文帮助类
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
@Lazy(false)
public class ContextHelper implements ApplicationContextAware, IAid {
    private static final Logger log = LoggerFactory.getLogger(ContextHelper.class);

    /***
     * ApplicationContext上下文
     */
    private static ApplicationContext APPLICATION_CONTEXT = null;

    /**
     * Entity-对应的Service缓存
     */
    private static Map<String, IService> ENTITY_SERVICE_CACHE = new ConcurrentHashMap<>();
    /**
     * Entity-对应的BaseService缓存
     */
    private static Map<String, BaseService> ENTITY_BASE_SERVICE_CACHE = new ConcurrentHashMap<>();
    /**
     * 存储主键字段非id的Entity
     */
    private static Map<String, String> PK_NID_ENTITY_CACHE = new ConcurrentHashMap<>();
    /**
     * 数据库类型
     */
    private static String DATABASE_TYPE = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
        ENTITY_SERVICE_CACHE.clear();
        ENTITY_BASE_SERVICE_CACHE.clear();
        PK_NID_ENTITY_CACHE.clear();
    }

    /***
     * 获取ApplicationContext上下文
     */
    public static ApplicationContext getApplicationContext() {
        if (APPLICATION_CONTEXT == null) {
            APPLICATION_CONTEXT = ContextLoader.getCurrentWebApplicationContext();
        }
        if (APPLICATION_CONTEXT == null) {
            log.warn("无法获取ApplicationContext，请在Spring初始化之后调用!");
        }
        return APPLICATION_CONTEXT;
    }

    /***
     * 根据beanId获取Bean实例
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        return getApplicationContext().getBean(beanId);
    }

    /***
     * 获取指定类型的单个Bean实例
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            log.debug("无法找到 bean: {}", clazz.getSimpleName());
            return null;
        }
    }

    /***
     * 获取指定类型的全部实现类
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(Class<T> type) {
        Map<String, T> map = getApplicationContext().getBeansOfType(type);
        if (Validator.isEmpty(map)) {
            return null;
        }
        List<T> beanList = new ArrayList<>();
        beanList.addAll(map.values());
        return beanList;
    }

    /***
     * 根据注解获取beans
     * @param annotationType
     * @return
     */
    public static List<Object> getBeansByAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> map = getApplicationContext().getBeansWithAnnotation(annotationType);
        if (Validator.isEmpty(map)) {
            return null;
        }
        List<Object> beanList = new ArrayList<>();
        beanList.addAll(map.values());
        return beanList;
    }

    /**
     * 根据Entity获取对应的Service (已废弃，请调用getBaseServiceByEntity)
     *
     * @param entity
     * @return
     */
    @Deprecated
    public static IService getServiceByEntity(Class entity) {
        return getIServiceByEntity(entity);
    }

    /**
     * 根据Entity获取对应的IService实现
     *
     * @param entity
     * @return
     */
    @Deprecated
    public static IService getIServiceByEntity(Class entity) {
        if (ENTITY_SERVICE_CACHE.isEmpty()) {
            Map<String, IService> serviceMap = getApplicationContext().getBeansOfType(IService.class);
            if (Validator.notEmpty(serviceMap)) {
                for (Map.Entry<String, IService> entry : serviceMap.entrySet()) {
                    Class entityClass = BeanUtil.getGenericityClass(entry.getValue(), 1);
                    if (entityClass != null) {
                        ENTITY_SERVICE_CACHE.put(entityClass.getName(), entry.getValue());
                    }
                }
            }
        }
        IService iService = ENTITY_SERVICE_CACHE.get(entity.getName());
        if (iService == null) {
            log.error("未能识别到Entity: " + entity.getName() + " 的IService实现！");
        }
        return iService;
    }

    /**
     * 根据Entity获取对应的BaseService实现
     *
     * @param entity
     * @return
     */
    public static BaseService getBaseServiceByEntity(Class entity) {
        if (ENTITY_BASE_SERVICE_CACHE.isEmpty()) {
            Map<String, BaseService> serviceMap = getApplicationContext().getBeansOfType(BaseService.class);
            if (Validator.notEmpty(serviceMap)) {
                for (Map.Entry<String, BaseService> entry : serviceMap.entrySet()) {
                    Class entityClass = BeanUtil.getGenericityClass(entry.getValue(), 1);
                    if (entityClass != null) {
                        ENTITY_BASE_SERVICE_CACHE.put(entityClass.getName(), entry.getValue());
                    }
                }
            }
        }
        BaseService baseService = ENTITY_BASE_SERVICE_CACHE.get(entity.getName());
        if (baseService == null) {
            log.info("未能识别到Entity: " + entity.getName() + " 的Service实现！");
        }
        return baseService;
    }

    /**
     * 根据Entity获取对应的BaseMapper实现
     *
     * @param entityClass
     * @return
     */
    public static BaseMapper getBaseMapperByEntity(Class entityClass) {
        return ParserCache.getMapperInstance(entityClass);
    }

    /**
     * 获取Entity主键
     *
     * @return
     */
    public static String getPrimaryKey(Class entity) {
        if (!PK_NID_ENTITY_CACHE.containsKey(entity.getName())) {
            String pk = Constant.FieldName.id.name();
            List<Field> fields = BeanUtil.extractAllFields(entity);
            if (Validator.notEmpty(fields)) {
                for (Field fld : fields) {
                    TableId tableId = fld.getAnnotation(TableId.class);
                    if (tableId == null) {
                        continue;
                    }
                    TableField tableField = fld.getAnnotation(TableField.class);
                    if (tableField != null && tableField.exist() == false) {
                        continue;
                    }
                    pk = fld.getName();
                    break;
                }
            }
            PK_NID_ENTITY_CACHE.put(entity.getName(), pk);
        }
        return PK_NID_ENTITY_CACHE.get(entity.getName());
    }

    /***
     * 获取JdbcUrl
     * @return
     */
    public static String getJdbcUrl() {
        Environment environment = getApplicationContext().getEnvironment();
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        if (jdbcUrl == null) {
            jdbcUrl = environment.getProperty("spring.datasource.druid.url");
        }
        if (jdbcUrl == null) {
            String master = environment.getProperty("spring.datasource.dynamic.primary");
            jdbcUrl = environment.getProperty("spring.datasource.dynamic.datasource." + master + ".url");
        }
        return jdbcUrl;
    }

    /**
     * 获取数据库类型
     *
     * @return
     */
    public static String getDatabaseType() {
        if (DATABASE_TYPE != null) {
            return DATABASE_TYPE;
        }
        String jdbcUrl = getJdbcUrl();
        if (jdbcUrl != null) {
            DbType dbType = JdbcUtils.getDbType(jdbcUrl);
            DATABASE_TYPE = dbType.getDb();
            if (DATABASE_TYPE.startsWith(DbType.SQL_SERVER.getDb())) {
                DATABASE_TYPE = DbType.SQL_SERVER.getDb();
            }
        } else {
            SqlSessionFactory sqlSessionFactory = getBean(SqlSessionFactory.class);
            if (sqlSessionFactory != null) {
                DATABASE_TYPE = sqlSessionFactory.getConfiguration().getDatabaseId();
            }
        }
        if (DATABASE_TYPE == null) {
            log.warn("无法识别数据库类型，请检查数据源配置:spring.datasource.url等");
        }
        return DATABASE_TYPE;
    }

}