package com.forum.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by apple on 2016/2/17.
 */
public class PasswordUtil {
    public static String _salt = "!@#ZDVT*()QWER$%^BNM";
    public static String _key = "G#$%^1234MasWQlZ";
    public static String _keyForReset="@%VCode^$13phone";

    public static String genHashBySHA(String _input,String _salt) {
        String password = _input + _salt;
        byte[] key =null;
        try {
            key = password.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = md.digest(key);

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            hexString.append(Integer.toHexString(0xFF & hash[i]));
        }

        return hexString.toString();
    }
    public static String GenHashBySHA256(String _input,String _salt) {
        MessageDigest objSHA = null;
        String password = _input + _salt;

        try {
            objSHA = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        objSHA.reset();
        byte[] bytSHA=null;
        try {
            bytSHA = objSHA.digest(password.getBytes("UTF-8"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytSHA.length; i++) {
        	
        	//hexString.append(Integer.toHexString(0xFF & bytSHA[i]));
        	
        	//8-12 by Will, 根据网上的信息，此处如果Integer.toHexString(0xFF & bytSHA[i])产生的字符串只有两位的话，需要在前面补零，否则在某种情况，此处产生的编码是不合适的。
        	if (Integer.toHexString(0xFF & bytSHA[i]).length() == 1) {
        		hexString.append("0" + Integer.toHexString(0xFF & bytSHA[i])) ;
        	} else {
        		 hexString.append(Integer.toHexString(0xFF & bytSHA[i]));
        	}
        }
        return hexString.toString();

    }
    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new BASE64Encoder().encode(bytes);
}
    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
//    public static String GenHMACSHA256(String _input, String _salt) {
//        String secret = _salt ?: "secret";
//        String message = _input ?: "Message";
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
//        sha256_HMAC.init(secret_key);
//        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
//
//        return hash;
//    }
}
