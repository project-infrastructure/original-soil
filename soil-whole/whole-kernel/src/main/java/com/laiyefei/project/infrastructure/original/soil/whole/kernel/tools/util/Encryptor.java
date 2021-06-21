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
package com.laiyefei.project.infrastructure.original.soil.whole.kernel.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 加解密工具类 （提供AES加解密）
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class Encryptor implements IUtil {
    private static final Logger log = LoggerFactory.getLogger(Encryptor.class);

    /**
     * 算法
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5PADDING";

    private static final String KEY_FILL = "abcdefghijklmnop";

    /**
     * 加密Cipher缓存
     */
    private static Map<String, Cipher> encryptorMap = new ConcurrentHashMap<>();
    /**
     * 解密Cipher缓存
     */
    private static Map<String, Cipher> decryptorMap = new ConcurrentHashMap<>();

    /**
     * 加密字符串（可指定加密密钥）
     *
     * @param input 待加密文本
     * @param key   密钥（可选）
     * @return
     * @throws Exception
     */
    public static String encrypt(String input, String... key) {
        String seedKey = Validator.notEmpty(key) ? key[0] : getDefaultKey();
        try {
            Cipher cipher = getEncryptor(seedKey);
            byte[] enBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (Exception e) {
            log.error("加密出错:" + input, e);
            return input;
        }
    }

    /**
     * 解密字符串
     *
     * @param input 待解密文本
     * @param key   加密key（可选）
     * @return
     * @throws Exception
     */
    public static String decrypt(String input, String... key) {
        if (Validator.isEmpty(input)) {
            return input;
        }
        String seedKey = Validator.notEmpty(key) ? key[0] : getDefaultKey();
        try {
            Cipher cipher = getDecryptor(seedKey);
            byte[] deBytes = Base64.getDecoder().decode(input.getBytes());
            return new String(cipher.doFinal(deBytes));
        } catch (Exception e) {
            log.error("解密出错:" + input, e);
            return input;
        }
    }

    /***
     * 获取指定key的加密器
     * @param key 加密密钥
     * @return
     * @throws Exception
     */
    private static Cipher getEncryptor(String key) throws Exception {
        byte[] keyBytes = getKey(key);
        Cipher encryptor = encryptorMap.get(new String(keyBytes));
        if (encryptor == null) {
            SecretKeySpec skeyspec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            encryptor = Cipher.getInstance(CIPHER_ALGORITHM);
            encryptor.init(Cipher.ENCRYPT_MODE, skeyspec);
            // 放入缓存
            encryptorMap.put(key, encryptor);
        }
        return encryptor;
    }

    /***
     * 获取指定key的解密器
     * @param key 解密密钥
     * @return
     * @throws Exception
     */
    private static Cipher getDecryptor(String key) throws Exception {
        byte[] keyBytes = getKey(key);
        Cipher decryptor = encryptorMap.get(new String(keyBytes));
        if (decryptor == null) {
            SecretKeySpec skeyspec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            decryptor = Cipher.getInstance(CIPHER_ALGORITHM);
            decryptor.init(Cipher.DECRYPT_MODE, skeyspec);
            // 放入缓存
            decryptorMap.put(key, decryptor);
        }
        return decryptor;
    }

    /***
     * 获取key，如非16位则调整为16位
     * @param seed
     * @return
     */
    private static byte[] getKey(String seed) {
        if (Validator.isEmpty(seed)) {
            seed = getDefaultKey();
        }
        if (seed.length() < 16) {
            seed = seed + StringUtil.cut(KEY_FILL, 16 - seed.length());
        } else if (seed.length() > 16) {
            seed = StringUtil.cut(seed, 16);
        }
        return seed.getBytes();
    }

    /**
     * 默认加密seed（可通过配置文件）
     */
    private static String getDefaultKey() {
        String defaultKey = SystemConfig.getProperty("kernel.encryptor.seed");
        return Validator.notEmpty(defaultKey) ? defaultKey : "kernel";
    }
}