package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.helper;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.helper.IHelper;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-09 09:05
 * @Desc : this is class named FileHelper for do FileHelper
 * @Version : v1.0.0.20200409
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class FileHelper implements IHelper {

    private final String folderPath;

    public FileHelper(String folderPath) {
        this.folderPath = folderPath;
    }

    public final Set<String> getPathContainer(final String... pathContains) throws IOException {
        //路径容器
        final Set<String> pathsContainer = new LinkedHashSet<>();
        // 是否循环迭代
        final boolean recursive = true;
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        final Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(this.folderPath);
        // 循环迭代下去
        while (dirs.hasMoreElements()) {
            // 获取下一个元素
            final URL url = dirs.nextElement();
            // 得到协议的名称
            final String protocol = url.getProtocol();
            // 如果是以文件的形式保存在服务器上
            switch (protocol) {
                case "file":
                    //加深打印
                    System.err.println("file类型的扫描:" + this.folderPath);
                    // 获取包的物理路径
                    final String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddFileInFolderByFile(
                            filePath,
                            recursive,
                            this.folderPath,
                            pathsContainer,
                            pathContains);
                    break;
                case "jar":
                    // 如果是jar包文件
                    // 定义一个JarFile
                    //加深打印
                    System.err.println("jar类型的扫描");
                    final JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    // 获取jar
                    // 从此jar包 得到一个枚举类
                    final Enumeration<JarEntry> entries = jar.entries();
                    // 同样的进行循环迭代
                    while (entries.hasMoreElements()) {
                        // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                        final JarEntry entry = entries.nextElement();
                        String name = entry.getName();

                        if (JudgeUtil.IsNotEmpty(pathContains)) {
                            if (!StringUtil.IsContainsIgnoreCase(name, pathContains)) {
                                continue;
                            }
                        }

                        // 如果是以/开头的
                        if (name.charAt(0) == '/') {
                            // 获取后面的字符串
                            name = name.substring(1);
                        }
                        // 如果前半部分和定义的包名相同
                        if (name.startsWith(this.folderPath)) {
                            // 如果可以迭代下去
                            if (0 <= name.lastIndexOf('/') || recursive) {
                                // 而且不是目录
                                if (!entry.isDirectory()) {
                                    // 添加到pathsContainer
                                    pathsContainer.add(name);
                                }
                            }
                        }
                    }
                    break;
                default:
                    throw new RuntimeException("no support this protocol.");
            }
        }
        return pathsContainer;
    }

    private final void findAndAddFileInFolderByFile(
            final String folderPath,
            final boolean recursive,
            final String thePath,
            final Set<String> pathsContainer,
            final String... pathsContains) {
        // 获取此包的目录 建立一个File
        final File dir = new File(folderPath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists()) {
            return;
        }
        if (!dir.isDirectory()) {
            if (JudgeUtil.IsNotEmpty(pathsContains)) {
                if (!StringUtil.IsContainsIgnoreCase(thePath, pathsContains)) {
                    return;
                }
            }
            //否则加入文件名
            pathsContainer.add(thePath);
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        final File[] dirfiles = dir.listFiles(file -> (recursive));
        // 循环所有文件
        for (final File file : dirfiles) {
            findAndAddFileInFolderByFile(
                    file.getAbsolutePath(),
                    recursive,
                    thePath.concat("/").concat(file.getName()),
                    pathsContainer,
                    pathsContains);
        }
    }

    public static final FileHelper BuildBy(final String folderPath) {
        return new FileHelper(folderPath);
    }
}
