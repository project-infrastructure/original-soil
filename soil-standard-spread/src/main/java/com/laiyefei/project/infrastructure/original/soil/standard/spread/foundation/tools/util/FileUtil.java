package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.Feature;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.Properties;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-03-01 09:05
 * @Desc : this is class named FileUtil for do FileUtil
 * @Version : v2.0.0.20200301
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class FileUtil implements IUtil {

    private FileUtil() {
        throw new RuntimeException("sorry, util is can no be an instance.");
    }

// TODO.. dump 前端模板

//    public static final void dumpWithParamToTmpDir(final String tmpPath,
//                                                   final VelocityContext content) {
//        final String filePath = FileUtil.BuildTempPath(CMD.Node.BuildFrontEnd.getPath());
//        final File file = new File(filePath);
//        if (!file.exists()) {
//            file.getParentFile().mkdirs();
//        } else {
//            file.delete();
//        }
//        try{
//
//            if (!file.createNewFile()) {
//                throw new RuntimeException("error: can not create file with path ".concat(filePath));
//            }
//            final Template tpl = Velocity.getTemplate(tmpPath, EncodeType.UTF8);
//            try (final StringWriter sw = new StringWriter()) {
//                tpl.merge(content, sw);
//                try (final PrintWriter printWriter = new PrintWriter(new FileWriter(file, true), true)) {
//                    printWriter.println(sw.toString());
//                }
//            }
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }


    public static final void dumpToTmpDir(final String classPath, final String... group) {
        if (JudgeUtil.IsNull(classPath)) {
            throw new RuntimeException("sorry, the classpath is can not be null.");
        }
        try {
            try (final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classPath)) {
                final byte[] buffer = new byte[is.available()];
                is.read(buffer);
                final String tmpPath = BuildTempPath(classPath, group);
                final File file = new File(tmpPath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                try (final OutputStream os = new FileOutputStream(file)) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static final void dumpToTempDirWithValue(final String value,
                                                    final String fileName,
                                                    final String... group) {
        final String tmpPath = BuildTempPath(fileName, group);
        final File file = new File(tmpPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            try (final OutputStream os = new FileOutputStream(file)) {
                os.write(value.getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String BuildTempPath(final String classPath, final String... group) {
        String groupInfo = Feature.TempDirPrefix.getCode();
        if (JudgeUtil.IsNotEmpty(group)) {
            groupInfo = StringUtil.DOT.concat(ProjectUtil.FormatProjectCode(String.join(StringUtil.EMPTY, Arrays.asList(group))));
        }
        return Properties.getProperty(Properties.JavaIOTmpdir)
                .concat(groupInfo)
                .concat("/")
                .concat(classPath).replaceAll("\\/+", "/");
    }

    public static final void CheckAndMakeFolder(final String path) {
        if (JudgeUtil.IsNull(path)) {
            return;
        }
        final File file = new File(path);
        if (file.exists()) {
            return;
        }
        if (!file.mkdirs()) {
            throw new RuntimeException("error: file.mkdirs()");
        }
    }

    public static final String[] GainRelativePathsBy(final String basePath) {
        if (StringUtil.IsTrimEmpty(basePath)) {
            throw new RuntimeException("error: the  basePath is can not be empty.");
        }
        final File file = new File(basePath);
        if (!file.exists()) {
            throw new RuntimeException("error: can not be find the file of ".concat(basePath));
        }
        if (file.isFile()) {
            return new String[]{basePath};
        }
        final List<String> paths = new ArrayList<>();
        recurrenceFindPaths(paths, basePath, file);
        return paths.toArray(new String[0]);
    }

    public static final String[] GainRelativePathsBy(final String basePath,
                                                     final File file,
                                                     final String... pathContains) {
        if (StringUtil.IsTrimEmpty(basePath)) {
            throw new RuntimeException("error: the  basePath is can not be empty.");
        }
        if (!file.exists()) {
            throw new RuntimeException("error: can not be find the file of ".concat(basePath));
        }
        if (file.isFile()) {
            return new String[]{basePath};
        }
        final List<String> paths = new ArrayList<>();
        recurrenceFindPaths(paths, basePath, file, pathContains);
        return paths.toArray(new String[0]);
    }

    private static final void recurrenceFindPaths(final List<String> paths,
                                                  final String preFixPath,
                                                  final File file,
                                                  final String... pathContains) {
        if (JudgeUtil.IsEmpty(new Object[]{paths, preFixPath, file})) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            if (JudgeUtil.IsEmpty(pathContains)) {
                paths.add(preFixPath);
                return;
            }
            if (StringUtil.IsContainsIgnoreCase(preFixPath, pathContains)) {
                paths.add(preFixPath);
            }
            return;
        }
        final File[] files = file.listFiles();
        if (files.length <= 0) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            recurrenceFindPaths(paths,
                    preFixPath.concat("/").concat(files[i].getName()),
                    files[i], pathContains);
        }
    }

    public static final String GainFileNameByPath(final String path, final String... joins) {
        final StringBuilder sb = new StringBuilder();
        if (JudgeUtil.IsNotEmpty(joins)) {
            for (String join : joins) {
                sb.append(join);
            }
        }
        final String[] paths = path.split("/");
        return paths[paths.length - 1].concat(sb.toString());
    }


    public static final void writeToFileWithValue(String filePath,
                                                  String value) {
        final File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        } else {
            file.delete();
        }
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException("error: can not create file with path ".concat(filePath));
            }
            try (final PrintWriter printWriter = new PrintWriter(new FileWriter(file, true), true)) {
                printWriter.println(value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final void writeToFileFromPath(String filePath,
                                                 String srcPath) {
        final File file = new File(srcPath);
        if (!file.exists()) {
            return;
        }
        writeToFileWithValue(filePath, loadValueByPath(srcPath));
    }

    public static final String loadValueByPath(String filePath) {

        final File file = new File(filePath);
        final StringBuilder sb = new StringBuilder();
        if (!file.exists()) {
            return sb.toString();
        }
        try {

            try (final FileInputStream fis = new FileInputStream(file)) {
                try (final BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
