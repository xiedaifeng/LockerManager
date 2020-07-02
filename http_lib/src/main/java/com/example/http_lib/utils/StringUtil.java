package com.example.http_lib.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 字符串处理类
 */
@SuppressWarnings("restriction")
public class StringUtil {

    /**
     * 重写toString方法，处理了空指针问题</br>
     * (默认如果对象为null则替换成"")
     *
     * @param obj String类型的Object对象
     * @return 转换后的字符串
     */
    public static String toString(Object obj) {
        return toString(obj, "");
    }

    /**
     * 重写toString方法，处理了空指针问题
     *
     * @param obj          String类型的Object对象
     * @param defaultValue 如果obj是null，则以defaultValue的值返回
     * @return 转换后的字符串
     */
    public static String toString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }

    /**
     * 校验对象是不是为null或者内容是""
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (ObjectUtil.isBaseClass(obj)) {
            return obj.toString().equals("");
        }
        return ObjectUtil.getNotNullFields(obj).isEmpty();
    }

    /**
     * 将base64字符串处理成String字节<br/>
     *
     * @param str base64的字符串
     * @throws IOException
     * @return 原字节数据
     */
    public static byte[] base64ToByte(String str) {
        try {
            if (str == null) {
                return null;
            }
            return new BASE64Decoder().decodeBuffer(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将base64字符串处理成String<br/>
     * (用默认的String编码集)
     *
     * @param str base64的字符串
     * @throws IOException
     * @return 可显示的字符串
     */
    public static String base64ToString(String str) {
        try {
            if (str == null) {
                return null;
            }
            return new String(base64ToByte(str), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将base64字符串处理成String<br/>
     * (用默认的String编码集)
     *
     * @param str     base64的字符串
     * @param charset 编码格式(UTF-8/GBK)
     * @throws IOException
     * @return 可显示的字符串
     */
    public static String base64ToString(String str, String charset) {
        try {
            if (str == null) {
                return null;
            }
            return new String(base64ToByte(str), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数据处理成base64字符串<br/>
     *
     * @param bts 字节数据
     * @throws IOException
     * @return base64编码后的字符串(用于传输)
     */
    public static String toBase64(byte[] bts) {
        if (bts == null || bts.length == 0) {
            return null;
        }
        String base64Str = new BASE64Encoder().encode(bts);
        base64Str = base64Str.replace("\r", "").replace("\n", "");
        return base64Str;
    }

    /**
     * 将String处理成base64字符串<br/>
     * (用默认的String编码集)
     *
     * @param oldStr 原字符串
     * @return base64编码后的字符串(用于传输)
     * @throws
     */
    public static String toBase64(String oldStr) {
        if (oldStr == null) {
            return null;
        }
        try {
            byte[] bts = oldStr.getBytes("UTF-8");
            String base64Str = new BASE64Encoder().encode(bts);
            base64Str = base64Str.replace("\r", "").replace("\n", "");
            return base64Str;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将String处理成base64字符串<br/>
     * (用默认的String编码集)
     *
     * @param oldStr 原字符串
     * @return base64编码后的字符串(用于传输)
     * @throws UnsupportedEncodingException
     */
    public static String toBase64(String oldStr, String charset) {
        try {
            if (oldStr == null) {
                return null;
            }
            byte[] bts = oldStr.getBytes(charset);
            return new BASE64Encoder().encode(bts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下面这个函数用于将字节数组换成成16进制的字符串
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 将byte转换为MD5
     */
    public static String toMD5(byte[] sourceData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(sourceData);
            return byteArrayToHex(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为MD5
     */
    public static String toMD5(String sourceData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(sourceData.getBytes());
            return byteArrayToHex(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换为MD5
     *
     * @throws UnsupportedEncodingException
     */
    public static String toMD5(String sourceData, String sourceCharset) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(sourceData.getBytes(sourceCharset));
            return byteArrayToHex(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换为全角
     *
     * @param input 需要转换的字符串
     * @return 全角字符串.
     */
    public static String toFullString(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            // 空格单独处理, 其余的偏移量为65248
            if (c[i] == ' ') {
                c[i] = '\u3000';    // 中文空格
            } else if (c[i] < 128) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 转换为半角
     *
     * @param input 需要转换的字符串
     * @return 半角字符串
     */
    public static String toHalfString(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            // 是否是中文空格， 单独处理
            if (c[i] == '\u3000') {
                c[i] = ' ';
            }
            // 校验是否字符值是否在此数值之间
            else if (c[i] > 65248 && c[i] < (128 + 65248)) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 将字符串转换为unicode编码
     *
     * @param input 要转换的字符串(主要是包含中文的字符串)
     * @return 转换后的unicode编码
     */
    public static String toUnicode(String input) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < input.length(); i++) {
            // 取出每一个字符
            char c = input.charAt(i);
            String hexStr = Integer.toHexString(c);
            while (hexStr.length() < 4) {
                hexStr = "0" + hexStr;
            }
            // 转换为unicode
            unicode.append("\\u" + hexStr);
        }
        return unicode.toString();
    }

    /**
     * 将unicode编码还原为字符串
     *
     * @param input unicode编码的字符串
     * @return 原始字符串
     */
    public static String unicodeToString(String input) {
        StringBuffer string = new StringBuffer();
        String[] hex = input.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 将字符串转换为url参数形式(中文和特殊字符会以%xx表示)
     */
    public static String toUrlStr(String input) {
        return toUrlStr(input, "UTF-8");
    }

    /**
     * 将字符串转换为url参数形式(中文和特殊字符会以%xx表示)
     */
    public static String toUrlStr(String input, String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将url参数形式的字符串转换为原始字符串(中文和特殊字符会以%xx表示)
     */
    public static String urlStrToString(String input) {
        return urlStrToString(input, "UTF-8");
    }

    /**
     * 将url参数形式的字符串转换为原始字符串(中文和特殊字符会以%xx表示)
     */
    public static String urlStrToString(String input, String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES加密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原文的编码集
     * @return 加密后的密文
     */
    public static String toDES(String text, String token, String charset) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            byte[] btToken = charset == null ? token.getBytes() : token.getBytes(charset);
            byte[] btText = charset == null ? text.getBytes() : text.getBytes(charset);
            DESKeySpec desKeySpec = new DESKeySpec(btToken);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(btToken);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bts = cipher.doFinal(btText);
            return toBase64(bts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES加密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 加密后的密文
     */
    public static String toDES(String text, String token) {
        return toDES(text, token, null);
    }

    /**
     * DES解密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原文的编码集
     * @return 原文字符串
     */
    public static String DESToString(String text, String token, String charset) {
        try {
            byte[] bytesrc = base64ToByte(text);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            byte[] btToken = charset == null ? token.getBytes() : token.getBytes(charset);
            DESKeySpec desKeySpec = new DESKeySpec(btToken);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(btToken);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return charset == null ? new String(retByte) : new String(retByte, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DES解密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 原文字符串
     */
    public static String DESToString(String text, String token) {
        return DESToString(text, token, null);
    }

    /**
     * AES加密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原数据字符集
     * @return 加密后的密文
     */
//	public static String toAESForSplit(String text, String token, String charset) {
//		try {
//			byte[] data = charset==null?text.getBytes():text.getBytes(charset);
//			int maxDataSize = 127;
//			String result = "";
//			//转换为字符数组
//			char chs[] = text.toCharArray();
//
//			int size = 0;
//			int lastIndex = 0;
//			int emojiCharacterNum = 0;
//			for(int i=0; i<text.length(); i++) {
//				int length = charset==null?String.valueOf(chs[i]).getBytes().length:String.valueOf(chs[i]).getBytes(charset).length;
//				//判断是否是特殊字符，表情符号
//				if(isEmojiCharacter(chs[i])){
//					length += 1;
//					++emojiCharacterNum;
//				}
//				//加上最后一个字符的字节长度大于127，i--,把该字符放到下一轮加密，防止把中文拆分加密
//				if(size + length > maxDataSize) {
//					i--;
//					//有表情字符，并且表情数为偶数
//					if(emojiCharacterNum > 0 && emojiCharacterNum % 2 == 0){
//						i--;
//						size -= length;
//					}
//				} else if(i == text.length()-1) {
//					size += length;
//				} else {
//					size += length;
//					continue;
//				}
//				byte[] onceBts = Arrays.copyOfRange(data, lastIndex, lastIndex+size);
//				KeyGenerator kgen = KeyGenerator.getInstance("AES");
//				kgen.init(128);
//				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
//				byte[] tempBts = cipher.doFinal(onceBts);
//				result += toBase64(tempBts) + "_";
//				lastIndex += size;
//				size = 0;
//				emojiCharacterNum = 0;
//			}
//			return result.trim().replace("\r", "").replace("\n", "");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//    public static String toAESForSplit(String text, String token, String charset) {
//        try {
//            byte[] data = charset == null ? text.getBytes() : text.getBytes(charset);
//            int maxDataSize = 128;
//            String result = "";
//            //转换为字符数组
//            char chs[] = text.toCharArray();
//
////            int number = data.length / 128;
////
////            for (int i = 0; i < number; i++) {
////                KeyGenerator kgen = KeyGenerator.getInstance("AES");
////                kgen.init(128);
////                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
////                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
////                byte[] bytes = Arrays.copyOfRange(data, maxDataSize * i, maxDataSize * (i + 1));
////                byte[] tempBts = cipher.doFinal(bytes);
////                result += toBase64(tempBts) + "_";
////            }
////
////            if (data.length % maxDataSize > 0) {
////                KeyGenerator kgen = KeyGenerator.getInstance("AES");
////                kgen.init(128);
////                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
////                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
////                byte[] bytes = cipher.doFinal(Arrays.copyOfRange(data, maxDataSize * number, data.length));
////                byte[] tempBts = cipher.doFinal(bytes);
////                result += toBase64(tempBts)+ "_";
////            }
//
////
//            int size = 0;
//            int lastIndex = 0;
//            int emojiCharacterNum = 0;
//            for (int i = 0; i < text.length(); i++) {
//                int length = charset == null ? String.valueOf(chs[i]).getBytes().length : String.valueOf(chs[i]).getBytes(charset).length;
//                //判断是否是特殊字符，表情符号
////				if(isEmojiCharacter(chs[i])){
////					length += 1;
////					++emojiCharacterNum;
////				}
//                //加上最后一个字符的字节长度大于127，i--,把该字符放到下一轮加密，防止把中文拆分加密
//                if (size + length > maxDataSize) {
//                    i--;
//                    //最后截取的字符为表情字符，并且表情数为偶数
////					if(length == 2 && emojiCharacterNum % 2 == 0){
////						i--;
////						size -= 2;
////					}
//
//                } else if (i == text.length() - 1) {
//                    size += length;
//                } else {
//                    size += length;
//                    continue;
//                }
//                byte[] onceBts = Arrays.copyOfRange(data, lastIndex, lastIndex + size);
////				byte[] onceBts = null;
////				if(i == text.length()-1){
////					onceBts = Arrays.copyOfRange(data, lastIndex, data.length);
////				}else{
////					onceBts = Arrays.copyOfRange(data, lastIndex, lastIndex+size);
////				}
//				KeyGenerator kgen = KeyGenerator.getInstance("AES");
//				kgen.init(128);
//				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
//				byte[] tempBts = cipher.doFinal(onceBts);
//				result += toBase64(tempBts) + "_";
//                lastIndex += size;
//                size = 0;
////				emojiCharacterNum = 0;
//            }
//            return result.trim().replace("\r", "").replace("\n", "");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static String toAESForSplit(String text, String token, String charset) {
        try {
            byte[] data = charset == null ? text.getBytes() : text.getBytes(charset);
            String result = "";
            byte[] onceBts =  null;
            int splitNum = (int) Math.ceil(data.length / (128 * 1.0f));
            for(int i = 0; i < splitNum; i++){
                if(i == splitNum - 1){
                    onceBts = Arrays.copyOfRange(data, i * 128, data.length );
                } else {
                    onceBts = Arrays.copyOfRange(data, i * 128, (i + 1) * 128 );
                }
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
                byte[] tempBts = cipher.doFinal(onceBts);
                result += toBase64(tempBts) + "_";
            }
            return result.trim().replace("\r", "").replace("\n", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * AES加密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原数据字符集
     * @return 加密后的密文
     */
    public static String toAES(String text, String token, String charset) {
        try {
//    		KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//    		kgen.init(128, new SecureRandom(charset==null?token.getBytes():token.getBytes(charset)));  
//    		SecretKey secretKey = kgen.generateKey();  
//    		byte[] enCodeFormat = secretKey.getEncoded();  
//    		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
//    		Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
//    		byte[] btText = charset==null?text.getBytes():text.getBytes(charset);  
//    		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
//    		byte[] bts = cipher.doFinal(btText);

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
            byte[] tempBts = cipher.doFinal(charset == null ? text.getBytes() : text.getBytes(charset));
            return toBase64(tempBts).replace("\r", "").replace("\n", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES加密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 加密后的密文
     */
    public static String toAESForSplit(String text, String token) {
        return toAESForSplit(text, token, null);
    }

    /**
     * AES加密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 加密后的密文
     */
    public static String toAES(String text, String token) {
        return toAES(text, token, null);
    }

    /**
     * AES解密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原数据字符集
     * @return 解密后的原文
     */
    public static String AESToString(String text, String token, String charset) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
            byte[] bts = cipher.doFinal(base64ToByte(text));
            return charset == null ? new String(bts) : new String(bts, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** AES解密 
     * @param text        要加密的数据
     * @param token        约定密串
     * @param charset    原数据字符集
     * @return 解密后的原文
     */
//    public static String AESToStringForSplit(String text, String token, String charset) {
//    	try {
////        	byte[] btText = base64ToByte(text);
////        	KeyGenerator kgen = KeyGenerator.getInstance("AES");
////        	kgen.init(128, new SecureRandom(charset==null?token.getBytes():token.getBytes(charset)));
////        	SecretKey secretKey = kgen.generateKey();
////        	byte[] enCodeFormat = secretKey.getEncoded();
////        	SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
////        	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
////        	cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
////        	byte[] bts = cipher.doFinal(btText);
//    		text = text.replace("\r", "").replace("\n", "");
//    		String[] datas = text.split("_");
//    		String result = "";
//    		for(String data : datas) {
//    			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        		kgen.init(128);
//        		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
//                byte[] bts = cipher.doFinal(base64ToByte(data));
//                result += charset==null?new String(bts):new String(bts, charset);
//    		}
//    		return result;
//        } catch (Exception e) {
//        	throw new RuntimeException(e);
//        }
//    }

    /**
     * AES解密
     *
     * @param text    要加密的数据
     * @param token   约定密串
     * @param charset 原数据字符集
     * @return 解密后的原文
     */
    public static String AESToStringForSplit(String text, String token, String charset) {
        try {
            text = text.replace("\r", "").replace("\n", "");
            String[] datas = text.split("_");
            String result = "";
            byte[] target = null;
            for (String data : datas) {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
                byte[] bts = cipher.doFinal(base64ToByte(data));
                target = addAll(target, bts);
            }
            result = charset == null ? new String(target) : new String(target, charset);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }

    /**
     * AES解密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 解密后的原文
     */
    public static String AESToStringForSplit(String text, String token) {
        return AESToStringForSplit(text, token, null);
    }

    /**
     * AES解密
     *
     * @param text  要加密的数据
     * @param token 约定密串
     * @return 解密后的原文
     */
    public static String AESToString(String text, String token) {
        return AESToString(text, token, null);
    }


    /**
     * 自定义二级压缩(最终显示为base64后的串)
     *
     * @return 压缩后的字符串
     */
    public static String compress2(byte[] bts) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte now = 0;
            int count = 0;
            for (int i = 0; i < bts.length; i++) {
                if (count == 0) {
                    now = bts[i];
                    count++;
                    continue;
                }
                if (now == bts[i] && count < 0xFF) {
                    count++;
                } else {
                    bout.write(count);
                    bout.write(now);
                    count = 0;
                    // 重新本次循环
                    i--;
                }
            }
            // 补全最后一次
            bout.write(count);
            bout.write(now);
            count = 0;
            bout.flush();

            // 第二次处理
            bts = bout.toByteArray();
            ByteArrayOutputStream head = new ByteArrayOutputStream();
            ByteArrayOutputStream body = new ByteArrayOutputStream();
            for (int i = 0; i < bts.length; i += 2) {
                if (count == 0) {
                    now = bts[i];
                    count = 1;
                    body.write(bts[i + 1]);
                    continue;
                }
                if (now == bts[i] && count < 0xFF) {
                    count++;
                    body.write(bts[i + 1]);
                } else {
                    head.write(count);
                    head.write(now);
                    count = 0;
                    // 重新本次循环
                    i -= 2;
                }
            }
            // 补全最后一次
            head.write(count);
            head.write(now);
            count = 0;
            head.flush();
            body.flush();
            // 计算压缩头大小
            int size = head.size();
            // 合并
            ByteArrayOutputStream all = new ByteArrayOutputStream();
            all.write(size >> 24);
            all.write(size >> 16);
            all.write(size >> 8);
            all.write(size);
            all.write(head.toByteArray());
            all.write(body.toByteArray());
            all.flush();
            return StringUtil.toBase64(all.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 自定义二级压缩(最终显示为base64后的串)
     *
     * @param val 原始字符串
     * @return 压缩后的字符串
     */
    public static String compress2(String val, String... valCharset) {
        if (StringUtil.isEmpty(val)) {
            return val;
        }
        try {
            return compress2(valCharset != null && valCharset.length > 0 ? val.getBytes(valCharset[0]) : val.getBytes());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 自定义二级解压缩
     *
     * @param val base64后的压缩数据
     * @return 解压后的原数据
     */
    public static String unCompress2(String val, String... oldCharset) {
        try {
            byte[] bts = StringUtil.base64ToByte(val);
            int size = (bts[0] << 24) + (bts[1] << 16) + (bts[2] << 8) + bts[3];
            ByteArrayOutputStream body = new ByteArrayOutputStream();
            // 首次解压
            int count = 4 + size;
            for (int i = 4; i < 4 + size; i += 2) {
                int k = bts[i];
                byte bt = bts[i + 1];
                for (int j = 0; j < k; j++) {
                    body.write(bt);
                    body.write(bts[count]);
                    count++;
                }
            }
            body.flush();
            bts = body.toByteArray();
            // 二次解压
            ByteArrayOutputStream all = new ByteArrayOutputStream();
            for (int i = 0; i < bts.length; i += 2) {
                for (int j = 0; j < bts[i]; j++) {
                    all.write(bts[i + 1]);
                }
            }
            all.flush();
            bts = all.toByteArray();
            return oldCharset != null && oldCharset.length > 0 ? new String(bts, oldCharset[0]) : new String(bts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 系统压缩(返回base64后的压缩数据)
     */
    public static String compress(byte[] bts) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(bts);
            gzip.close();
            return StringUtil.toBase64(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 系统压缩(返回base64后的压缩数据)
     */
    public static String compress(String val, String... valCharset) {
        if (StringUtil.isEmpty(val)) {
            return val;
        }
        try {
            return compress(valCharset != null && valCharset.length > 0 ? val.getBytes(valCharset[0]) : val.getBytes());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 系统解压缩
     */
    public static String unCompress(String val, String... oldCharset) {
        try {
            if (StringUtil.isEmpty(val)) {
                return val;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(StringUtil.base64ToByte(val));
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;
            while ((n = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return oldCharset != null && oldCharset.length > 0 ? new String(out.toByteArray(), oldCharset[0]) : new String(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String listToString(List<String> list){
        StringBuffer str2 = new StringBuffer();
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            str2.append(string);
            if(iterator.hasNext()){
                str2.append(",");
            }
        }
        return str2.toString();
    }
}
