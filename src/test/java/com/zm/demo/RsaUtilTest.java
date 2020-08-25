package com.zm.demo;

import com.zm.demo.constants.CommonConstants;
import com.zm.demo.utils.RsaUtils;
import org.junit.Test;

import java.security.KeyPair;

public class RsaUtilTest {

    @Test
    public void testRsa() throws Exception{
        // 生成rsa
        KeyPair keyPair = RsaUtils.getKeyPair();
        // 获取公私钥
        String publicKey = RsaUtils.getPublicKey(keyPair);
        String privateKey = RsaUtils.getPrivateKey(keyPair);
        System.out.println("public: " + publicKey);
        System.out.println("private: " + privateKey);
        // 需要加密的信息
        String msg = "12345678";
        // 前端先rsa加密，再转base64
        String password = RsaUtils.publicEncrypt(msg, RsaUtils.parseStringPublicKey(CommonConstants.PUBLIC_KEY));
        System.out.println(password);
        // 服务端解密
        System.out.println(RsaUtils.privateDecrypt(password, RsaUtils.parseStringPrivateKey(CommonConstants.PRIVATE_KEY)));
    }
}
