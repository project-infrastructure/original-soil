package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.util.IUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.EncodeType;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-05-18 16:32
 * @Desc : 加解密工具类
 * @Version : v2.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class CryptionUtil implements IUtil {
    public static final String RSA = "RSA";
    private final static int KEY_SIZE = 1024;

    @Getter
    public static class KeyPairBase64Wrapper {
        private final KeyPair keyPair;
        private final String pub;
        private final String pri;

        private KeyPairBase64Wrapper(KeyPair keyPair) {
            final Base64.Encoder encoder = Base64.getEncoder();
            final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            this.keyPair = keyPair;
            this.pub = encoder.encodeToString(publicKey.getEncoded());
            this.pri = encoder.encodeToString(privateKey.getEncoded());
        }
    }

    private CryptionUtil() {
        throw new RuntimeException("sorry, it can not be an instance");
    }

    @SneakyThrows
    public static final KeyPairBase64Wrapper gainKeyPair() {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        return new KeyPairBase64Wrapper(keyPairGenerator.generateKeyPair());
    }

    @SneakyThrows
    public static final String enCrypt(final String initString, final String pub) {
        final byte[] decoded = Base64.getDecoder().decode(pub);
        final RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(decoded));
        final Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(initString.getBytes(EncodeType.UTF8)));
    }

    @SneakyThrows
    public static final String deCrypt(final String codeString, final String pri) {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] inputByte = decoder.decode(codeString);
        final byte[] decoded = decoder.decode(pri);
        final RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        final Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }
}
