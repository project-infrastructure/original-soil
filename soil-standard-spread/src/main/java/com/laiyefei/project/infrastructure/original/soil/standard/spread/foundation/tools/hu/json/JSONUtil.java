package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.hu.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.hu.IHU;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named JSONUtil for do JSONUtil
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class JSONUtil implements IHU {
    public static final String DEFAULT_FAIL = "[Parse failed]";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    private JSONUtil() {
        throw new RuntimeException("can not be an instance.");
    }

    public static final void Marshal(final File file, final Object value) {
        try {
            objectWriter.writeValue(file, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final void Marshal(final OutputStream os, final Object value) {
        try {
            objectWriter.writeValue(os, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final String Marshal(final Object value) {
        try {
            return objectWriter.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final byte[] MarshalBytes(final Object value) {
        try {
            return objectWriter.writeValueAsBytes(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> T Unmarshal(final File file, final Class<T> valueType) {
        try {
            return objectMapper.readValue(file, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> T Unmarshal(final InputStream is, final Class<T> valueType) {
        try {
            return objectMapper.readValue(is, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> T Unmarshal(final String str, final Class<T> valueType) {
        try {
            return objectMapper.readValue(str, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> T Unmarshal(byte[] bytes, final Class<T> valueType) {
        if (JudgeUtil.IsNull(bytes)) {
            bytes = new byte[0];
        }
        try {
            return objectMapper.readValue(bytes, 0, bytes.length, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
