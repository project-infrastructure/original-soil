package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co.EncodeType;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-08 12:52
 * @Desc : this is class named ZipUtil.
 * @Version : v1.0.0.20200408
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class ZipUtil implements IUtil {
    private ZipUtil() {
        throw new RuntimeException("sorry, can not be an instance.");
    }

    public static final byte[] pack(final String[] paths, final String... replacePreFixs) {
        if (JudgeUtil.IsEmpty(paths)) {
            throw new RuntimeException("sorry，paths of pack is can not be empty");
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            try (final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
                for (final String path : paths) {
                    final ByteArrayOutputStream pathBOS = new ByteArrayOutputStream();
                    final InputStream is = ZipUtil.class.getClassLoader().getResourceAsStream(path);
                    if (JudgeUtil.IsNull(is)) {
                        throw new RuntimeException("error: can not find ".concat(path));
                    }
                    int index;
                    while (-1 < (index = is.read())) {
                        pathBOS.write(index);
                    }
                    String zipInnerPath = path;
                    if (JudgeUtil.IsNotEmpty(replacePreFixs)) {
                        for (String replacePreFix : replacePreFixs) {
                            zipInnerPath = zipInnerPath.replaceAll((replacePreFix.concat("/")).replaceAll("\\/+", "/"), StringUtil.EMPTY);
                        }
                    }
                    zipOutputStream.putNextEntry(new ZipEntry(zipInnerPath));
                    IOUtils.write(pathBOS.toString(), zipOutputStream, EncodeType.UTF8);
                }
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public static final byte[] pack(final String path, final String... values) {
        if (JudgeUtil.IsEmpty(values)) {
            return pack(new String[]{path});
        }
        if (StringUtil.IsTrimEmpty(path)) {
            throw new RuntimeException("error: the path is can not be empty.");
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            try (final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
                zipOutputStream.putNextEntry(new ZipEntry(path));
                for (final String value : values) {
                    IOUtils.write(value, zipOutputStream, EncodeType.UTF8);
                }
                zipOutputStream.closeEntry();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public static final String BuildPathWith(final String rootPath,
                                             final String path,
                                             final String... replacePrefix) {
        if (StringUtil.IsTrimEmpty(rootPath)) {
            throw new RuntimeException("error: basePackagePath is null");
        }
        if (StringUtil.IsTrimEmpty(path)) {
            throw new RuntimeException("error: the path is can not be empty.");
        }
        String newPath = path;
        for (String prefix : replacePrefix) {
            newPath = newPath.replaceAll(prefix, StringUtil.EMPTY);
        }
        return rootPath
                .concat("/")
                .concat(newPath)
                .replaceAll("\\/+", "/");
    }

    public static final String BuildPathBy(final String rootPath,
                                           final String targetPath) {
        if (StringUtil.IsTrimEmpty(rootPath)) {
            throw new RuntimeException("error: basePackagePath is null");
        }
        if (JudgeUtil.IsNull(targetPath)) {
            throw new RuntimeException("error: targetPathAboutRoot is null");
        }
        return rootPath
                .concat("/")
                .concat(targetPath)
                .replaceAll("\\/+", "/");
    }
}
