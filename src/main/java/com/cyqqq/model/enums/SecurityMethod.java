package com.cyqqq.model.enums;

/**
 * 解密 / 加密
 */
public enum SecurityMethod {

    NULL,
    AES,
    RSA,
    DES,
    DES3,
    SHA1,
    MD5
    ;

    public static SecurityMethod getByCode(String method){
        try {
            return SecurityMethod.valueOf(method);
        }catch (Exception e){
            return NULL;
        }
    }
}
