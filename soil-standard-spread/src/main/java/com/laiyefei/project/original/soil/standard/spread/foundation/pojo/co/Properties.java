package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.ICo;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named Properties for do Properties
 * @Version : v1.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public enum Properties implements ICo {
    JavaVersion("java.version", "Java运行时环境版本"),
    JavaVendor("java.vendor", "Java运行时环境供应商"),
    JavaVendorUrl("java.vendor.url", "Java供应商的 URL"),
    JavaHome("java.home", "Java安装目录"),
    JavaVmSpecificationVersion("java.vm.specification.version", "Java虚拟机规范版本"),
    JavaVmSpecificationVendor("java.vm.specification.vendor", "Java虚拟机规范供应商"),
    JavaVmSpecificationName("java.vm.specification.name", "Java虚拟机规范名称"),
    JavaVmVersion("java.vm.version", "Java虚拟机实现版本"),
    JavaVmVendor("java.vm.vendor", "Java虚拟机实现供应商"),
    JavaVmName("java.vm.name", "Java虚拟机实现名称"),
    JavaSpecificationVersion("java.specification.version", "Java运行时环境规范版本"),
    JavaSpecificationVendor("java.specification.vendor", "Java运行时环境规范供应商"),
    JavaSpecificationName("java.specification.name", "Java运行时环境规范名称"),
    JavaClassVersion("java.class.version", "Java类格式版本号"),
    JavaClassPath("java.class.path", "Java类路径"),
    JavaLibraryPath("java.library.path", "加载库时搜索的路径列表"),
    JavaIOTmpdir("java.io.tmpdir", "默认的临时文件路径"),
    JavaCompiler("java.compiler", "要使用的 JIT 编译器的名称"),
    JavaExtDirs("java.ext.dirs", "一个或多个扩展目录的路径"),
    OsName("os.name", "操作系统的名称"),
    OsArch("os.arch", "操作系统的架构"),
    OsVersion("os.version", "操作系统的版本"),
    FileSeparator("file.separator", "文件分隔符（在 UNIX 系统中是“/”）"),
    PathSeparator("path.separator", "路径分隔符（在 UNIX 系统中是“:”）"),
    LineSeparator("line.separator", "行分隔符（在 UNIX 系统中是“/n”）"),
    UserName("user.name", "用户的账户名称"),
    UserHome("user.home", "用户的主目录"),
    UserDir("user.dir", "用户的当前工作目录"),
    ;
    public static final String getProperty(Properties item) {
        return System.getProperty(item.getCode());
    }

    private final String code;
    private final String description;

    Properties(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
