package com.cmcc.medicalcare.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.cmcc.medicalcare.exception.CoderException;

/**
 * @preserve public
 * @author wuchao
 * 
 */
public class DES {

	/**
	 * ALGORITHM
	 */
    private static final String ALGORITHM = "DES";

    /**
     * CIPHER
     */
    private static final String CIPHER = "DES";

    /**
     * DEFAULT_KEY
     */
    private String DEFAULT_KEY = "kdjm2016";
    // Des加密解密的密匙

    /**
     * iv
     */
    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * legalChars
     */
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            .toCharArray();

    /**
     * 指定密钥构造方法
     * 
     */
    public DES() {
    }

    /**
     * 指定密钥构造方法
     * 
     * @param strKey
     *            指定的密钥
     */
    public DES(String strKey) {
        DEFAULT_KEY = strKey;
    }

    /**
     * data[]
     * 
     * @param data
     * @return
     */
    public static String encode(byte[] data) {

        int start = 0;
        int len = data.length;
        StringBuilder buf = new StringBuilder(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = (((data[i]) & 0x0ff) << 16) | (((data[i + 1]) & 0x0ff) << 8) | ((data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = (((data[i]) & 0x0ff) << 16) | (((data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = ((data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    /**
     * parseByte2HexStr
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {

    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * decode
     * @param s
     * @return
     */
    public static byte[] decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            // System.err.println("Error while decoding BASE64: " +
            // ex.toString());
        }
        return decodedBytes;
    }

    /**
     * decode
     * @param s
     * @param os
     * @throws IOException
     */
    private static void decode(String s, OutputStream os) throws IOException {

        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }

    /**
     * decode
     * @param c
     * @return
     */
    private static int decode(char c) {

        if (c >= 'A' && c <= 'Z')
            return (c) - 65;
        else if (c >= 'a' && c <= 'z')
            return (c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return (c) - 48 + 26 + 26;
        else
            switch (c) {
            case '+':
                return 62;
            case '/':
                return 63;
            case '=':
                return 0;
            default:
                throw new RuntimeException("unexpected code: " + c);
            }
    }

    /**
     * encrypt
     * @param strIn
     * @param key
     * @param encoding
     * @return
     * @throws Exception
     */
    public String encrypt(String strIn, String key, String encoding) throws Exception {

        return encryptDES(strIn, key, encoding);
    }

    /**
     * decrypt
     * @param strIn
     * @param key
     * @param encoding
     * @return
     * @throws Exception
     */
    public String decrypt(String strIn, String key, String encoding) throws Exception {

        return decryptDES(strIn, key, encoding);
    }

    /**
     * encryptDES
     * @param encryptString
     * @param encryptKey
     * @param encoding
     * @return
     * @throws Exception
     */
    private String encryptDES(String encryptString, String encryptKey, String encoding) throws Exception {

        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes(encoding));
        
        System.out.println(bytes2HexString(encryptedData));
        
        return Base64.encode(encryptedData);
    }

    /**
     * decryptDES
     * @param decryptString
     * @param decryptKey
     * @param encoding
     * @return
     * @throws Exception
     */
    private String decryptDES(String decryptString, String decryptKey, String encoding) throws Exception {

        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData, encoding);
    }

    /** 使用BASE64时的字符集编码. */
    public static final String CHARSET = "UTF-8";

    /**
     * 先DEC加密，再用Base64转换 。JDK默认： ECB 加密模式和 PKCS#5 填充方式.
     * 
     * @param input
     *            the input
     * @return the string
     * @throws Exception
     *             the exception
     */
    public String encryptDESBeas64(String input) throws CoderException {
        try {
            return encryptDES(input, DEFAULT_KEY, CHARSET);
        } catch (Exception e) {
            throw new CoderException(e);
        }
    }

    /**
     * 先用Base64转换,再DEC解密 。JDK默认： ECB 加密模式和 PKCS#5 填充方式.
     * 
     * @param input
     *            the input
     * @return the string
     * @throws Exception
     *             the exception
     */
    public String decryptBeas64DES(String input) throws CoderException {
        try {
            return decryptDES(input, DEFAULT_KEY, CHARSET);
        } catch (Exception e) {
            throw new CoderException(e);
        }
    }
    
    /**
     * bytes2HexString
     * @param b
     * @return
     */
    public static String bytes2HexString(byte[] b) {  
        StringBuilder result = new StringBuilder();  
        String hex;  
        for (int i = 0; i < b.length; i++) {  
            hex = Integer.toHexString(b[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            result.append(hex.toUpperCase());  
        }  
        return result.toString();  
    }
    
    public String encryptDESBeas64(Object object){
    	if (object != null&& object.toString().trim().length() > 0) {
    		try {
				object = encryptDESBeas64(object.toString().trim());
			} catch (CoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			object = "";
		}
    	return object.toString();
    }
    
    
    /**
     * MAIN
     * @param args
     */
    public static void main(String args[]){
//    	String phone = "15710739360";
//    	System.out.println(phone.substring(3));
//    	DES des = new DES("10739360");//15710739360
//    	try {
//    		String account ="39900378945";
//    		account = des.encryptDESBeas64(account);
////    		account = des.encode(account.getBytes());
//			System.out.println("account密文="+account);
//			account = des.decryptBeas64DES(account);
//			System.out.println("account明文="+account);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	String phone1 = "13727226986";
    	phone1 = phone1.substring(3);
    	System.out.println("phone1:"+phone1);
		DES des1 = new DES(phone1);
    	String pwdString;
		try {
			pwdString = des1.decryptBeas64DES("bfdm48BqjVg=");
			System.out.println("pwdString:"+pwdString);
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//238259
    }
}
