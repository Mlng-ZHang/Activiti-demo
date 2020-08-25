package com.zm.demo.utils;

import io.jsonwebtoken.impl.Base64Codec;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Name: RsaUtils
 * @Author: zhangming
 * @Date 2020/8/12 14:34
 * @Description:
 */
public class RsaUtils {

    public static final String ALGORITHM_RSA = "RSA";

    /**
     * 生成RSA秘钥对
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 获取公钥(Base64编码)
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair){
        return Base64Codec.BASE64.encode(keyPair.getPublic().getEncoded());
    }

    /**
     * 获取私钥(Base64编码)
     * @param keyPair
     * @return
     */
    public static String getPrivateKey(KeyPair keyPair){
        return Base64Codec.BASE64.encode(keyPair.getPrivate().getEncoded());
    }

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * @param priStr
     * @return
     * @throws Exception
     */
    public static PrivateKey parseStringPrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64Codec.BASE64.decode(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * @param priStr
     * @return
     * @throws Exception
     */
    public static PublicKey parseStringPublicKey(String priStr) throws Exception{
        byte[] keyBytes = Base64Codec.BASE64.decode(priStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 公钥加密
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String publicEncrypt(String content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64Codec.BASE64.encode(cipher.doFinal(content.getBytes()));
    }

    /**
     * 私钥解密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String privateDecrypt(String content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64Codec.BASE64.decode(content)));
    }

}
