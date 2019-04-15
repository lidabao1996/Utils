package com.sophia.utils;

import java.security.MessageDigest;

public class MD5Util {  
      
    // MD5加密。32位的。  
    public static String md5ToSecret(String source) {  
        MessageDigest md5 = null;  
        try {  
            md5 = MessageDigest.getInstance("MD5");  
        } catch (Exception e) {  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = source.toCharArray();  //一起jquery,17jquery 
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
  
        byte[] md5Bytes = md5.digest(byteArray);  
  
        StringBuffer hexValue = new StringBuffer();  
  
        for (int i = 0; i < md5Bytes.length; i++) {  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
  
    // 可逆的加密算法  
    public static String md5Encrypt(String inStr) {  
        // String s = new String(inStr);  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++) {  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
    }  
  
    // 加密后解密  
    public static String md5Decrypt(String inStr) { // 17jquery.com 
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++) {  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String k = new String(a);  
        return k;  
    }  
  
     
    public static void main(String[] args) {  
        String s = "Hello";  
        System.out.println("原字符串："  + s);  
        System.out.println("MD5加密之后："  + md5ToSecret(s));  
        System.out.println("再加密："  + md5Encrypt(md5ToSecret(s)));//  一起jquery,17jquery 
        System.out.println("解密之后："  + md5Decrypt(md5Encrypt(md5ToSecret(s))));
        
        System.out.println(md5Encrypt(s));
        System.out.println("解密之后："  + md5Decrypt(md5Encrypt(s)));  
    }  
} 