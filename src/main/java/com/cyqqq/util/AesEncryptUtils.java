package com.cyqqq.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/30 16:10
 * @Version :
 */
public class AesEncryptUtils {
    //可配置到Constant中，并读取配置文件注入
    private static final String KEY = "abcdef0123456789";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS7Padding";

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        return Base64.encodeBase64String(b);
    }

    /**
     * 解密
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }


    public static void main(String[] args) throws Exception {
        String content = "Adjust";
        System.out.println("加密前：" + content);

//        String encrypt = encrypt(content, KEY);
        String encrypt = "6BHk6HHOiSSU9jS587O8MlV4dY2WKQ3EZXyFp+qwHdkDPuJLmO5+hhw9DaZiK0QNb/di8uDRps9ouvJNUx4ambrv6Emeewgb4/N84wzHzCV6+AgZJ2LqlGrgbaIvevdGHS4f+i893mo7VoSAavagV7KSvjj5W8rfzPYBw/Jr843r346Q/EVs2g9/2ZLokvEtzQftbOig5NDYZFS+TVVz5zBv8WAmT4HSW5lraIOnbFP/qeNqzuAQN6Hb7/2OJeeW";
        System.out.println("加密后：" + encrypt);

        String decrypt = decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }

}
