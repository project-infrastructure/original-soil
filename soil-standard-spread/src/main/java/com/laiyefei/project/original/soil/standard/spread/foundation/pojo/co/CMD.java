package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named CMD for do enum CMD
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class CMD implements ICo {
    ;

    public enum MYSQL implements ICo {
        init("init:mysql", "初始化创建数据库", "$init/cmd/mysql/init.bat"),
        sql("exec:sql", "执行sql语句", "$init/cmd/mysql/run.sql");
        private final String code;
        private final String description;
        private final String path;

        MYSQL(String code, String description, String path) {
            this.code = code;
            this.description = description;
            this.path = path;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public String getPath() {
            return path;
        }
    }

    public enum Node implements ICo {
        BuildFrontEnd("build:front", "构建前端项目", "$init/cmd/node/front-end.build.bat");
        public static final String projectPath = "projectPath";

        private final String code;
        private final String description;
        private final String path;

        Node(String code, String description, String path) {
            this.code = code;
            this.description = description;
            this.path = path;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public String getPath() {
            return path;
        }
    }

    public static final String buildCommand(final String cmdPath) {
        return "cmd /c ".concat(cmdPath);
    }
}
