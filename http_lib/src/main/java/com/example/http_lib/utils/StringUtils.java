package com.example.http_lib.utils;

import android.text.Editable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import com.google.common.collect.Range;
//import com.qiniu.android.dns.util.Hex;

/**
 * Created by xiaochan on 2017/6/26.
 */

public class StringUtils {

    /**
     * 是空则为true, 非空则为false
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断editText获取的文本是否为空
     *
     * @param inputText 输入内容
     * @return true(空)/false(非空)
     */
    public static boolean inputIsNull(Editable inputText) {
        return TextUtils.isEmpty(inputText) || TextUtils.isEmpty(inputText.toString().trim());
    }

    /**
     * 将积分转换为字符串，超过一百万的保留整数部分，超过一万的保留1位小数
     *
     * @param score 积分
     * @return 转换后的信息
     */
    public static String formatScore(long score) {
        if (score / 10000000 > 0) {
            return score / 1000000 + "千万";
        } else if (score / 1000000 > 0) {
            return score / 1000000 + "百万";
        } else if (score / 10000 > 0) {
            return String.format("%.1f", ((double) score) / 10000) + "万";
        } else {
            return String.valueOf(score);
        }
    }

    public static String getCountDownString(long time) {
        SimpleDateFormat format = new SimpleDateFormat("dd天HH小时后截止");
        return format.format(new Date(time));
    }

    public static String getTimeFordate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(new Date(time));
    }
    public static String getTimeForMd(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM.dd");
        return format.format(new Date(time));
    }


    public static String timeForDate(Long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date(time));
    }

    public static String timeForDetailDate(Long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(new Date(time));
    }

    public static String formatYMD(Long timeStamp) {
        if (timeStamp == null) {
            return "刚刚";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(timeStamp));
    }


    public static String formatTime(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd号HH:mm");
        return format.format(new Date(timeStamp));
    }

    /**
     * 检测输入的昵称是否要求
     *
     * @param nickName
     * @return
     */
    public static boolean checkNick(String nickName) {
//        if (nickName.length() == 11){
//            if (nickName.startsWith("1") && nickName.indexOf("****") == 3){
//                return true;
//            }
//        }
        String regex = "[a-zA-Z0-9\\u4E00-\\u9FA5]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nickName);
        return matcher.matches();
    }

    public static boolean checkWeightAndHeight(String content) {
        try {
            double aDouble = Double.parseDouble(content);
            return aDouble > 0 && aDouble <= 300;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkSchoolAndMajor(String content) {

        if (content.length() > 20) {
            return false;
        }
        String regex = "[a-zA-Z0-9\\u4E00-\\u9FA5]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
//        String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        if (password.length() < 6 || password.length() > 18) {
            return false;
        }
        String regex = "[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String presData(Long time) {
        Long day = time / (24 * 60 * 60 * 1000);
        Long homur = (time - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        Long m = (time - day * (24 * 60 * 60 * 1000) - homur * (60 * 60 * 1000)) / (60 * 1000);
        Long s = (time - day * (24 * 60 * 60 * 1000) - homur * (60 * 60 * 1000) - m * (60 * 1000)) / 1000;
        if (day == 0) {
            if (homur != 0) {
                return homur + "小时后截止";
            } else {
                if (m != 0) {
                    return m + "分钟后截止";
                } else {
                    return "1分钟后截止";
                }
            }
        } else {
            return day + "天" + homur + "小时后截止";
        }
    }

    public static String getData(Long time) {
        Long day = time / (24 * 60 * 60 * 1000);
        Long homur = (time - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        Long m = (time - day * (24 * 60 * 60 * 1000) - homur * (60 * 60 * 1000)) / (60 * 1000);
        Long s = (time - day * (24 * 60 * 60 * 1000) - homur * (60 * 60 * 1000) - m * (60 * 1000)) / 1000;

        if (day == 0) {
            if (homur != 0) {
                return homur + "小时前";
            } else {
                if (m != 0) {
                    return m + "分钟前";
                } else {
                    return "刚刚";
                }
            }
        } else {
            return day + "天" + homur + "小时前";
        }

    }

    public static String parseDynamicTime(long timeStamp) {
        long period = System.currentTimeMillis() - timeStamp;
        Long day = period / (24 * 60 * 60 * 1000);
        Long hour = (period - day * 24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        Long m = (period - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000)) / (60 * 1000);
        Long s = (period - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000) - m * (60 * 1000)) / 1000;

        if (day == 0) {
            if (hour != 0) {
                return hour + "小时前";
            } else {
                if (m != 0) {
                    return m + "分钟前";
                } else {
                    return "刚刚";
                }
            }
        } else {
            return formatYMD(timeStamp);
        }
    }


//    static Range<Character> utf84ByteRange = Range.closed("\u1F601".charAt(0), "\u1F64F".charAt(0));

    //把utf-8了字节编码的字符去掉
    //参见: http://cenalulu.github.io/linux/character-encoding/
    // 所谓Emoji就是一种在Unicode位于\u1F601-\u1F64F区段的字符
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static String replaceUtf84byte(String str) {
//        if (TextUtils.isEmpty(str)) return "";
//
//        char[] chars = str.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            if (Character.isSurrogate(chars[i])) { //参见: http://stackoverflow.com/questions/14981109/checking-utf-8-data-type-3-byte-or-4-byte-unicode
//                chars[i] = ' '; //替换成空格
//            } else if (utf84ByteRange.contains(chars[i])) {
//                chars[i] = ' '; //替换成空格
//            }
//        }
//        return new String(chars);
//    }


    public static boolean shouldNotEncrypt(String requestUrl) {
//        return !requestUrl.contains("login");
        if (TextUtils.isEmpty(requestUrl)) {
            return false;
        } else {
            return requestUrl.contains("login") || requestUrl.contains("getVersion") ||
                    requestUrl.contains("forgetpwd") || requestUrl.contains("registered");
        }
    }


    /**
     * 重写toString方法，处理了空指针问题</br> (默认如果对象为null则替换成"")
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
     * 将字符串过滤处理成正常的Double值
     */
    public static Double toDouble(String value) {
        int startIndex = 48;
        int endIndex = 57;
        boolean hasDian = false; // 已经验证出了小数点
        boolean hasNum = false; // 已经验证出了数字
        StringBuffer sb = new StringBuffer(value);
        for (int i = 0; i < value.length(); i++) {
            char ch = sb.charAt(i);
            // 处理小数点
            if (hasNum && !hasDian && ch == '.') {
                hasDian = true;
                continue;
            }
            // 处理数字
            if (ch >= startIndex && ch <= endIndex) {
                hasNum = true;
                continue;
            }
            // 排除掉其他字符
            sb.setCharAt(i, ' ');
        }
        try {
            return Double.parseDouble(sb.toString().replace(" ", ""));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串过滤处理成正常的Double值
     */
    public static Integer toInt(String value) {
        int startIndex = 48;
        int endIndex = 57;
        StringBuffer sb = new StringBuffer(value);
        for (int i = 0; i < value.length(); i++) {
            char ch = sb.charAt(i);
            // 排除掉其他字符
            if (ch < startIndex || ch > endIndex) {
                sb.setCharAt(i, ' ');
            }
        }
        try {
            return Integer.parseInt(sb.toString().replace(" ", ""));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 校验对象是不是为null或者内容是""
     */
    public static boolean isEmpty(Object obj) {
        return obj == null || obj.toString().trim().equals("");
    }

    /**
     * 将base64字符串处理成String字节<br/>
     *
     * @param str base64的字符串
     * @return 原字节数据
     * @throws IOException
     */
    public static byte[] base64ToByte(String str) throws IOException {
        try {
            if (str == null) {
                return null;
            }
            return new BASE64Decoder().decodeBuffer(str);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * 将base64字符串处理成String<br/>
     * (用默认的String编码集)
     *
     * @param str base64的字符串
     * @return 可显示的字符串
     * @throws IOException
     */
    public static String base64ToString(String str) {
        try {
            if (str == null) {
                return null;
            }
            return new String(base64ToByte(str), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * 将base64字符串处理成String<br/>
     * (用默认的String编码集)
     *
     * @param str     base64的字符串
     * @param charset 编码格式(UTF-8/GBK)
     * @return 可显示的字符串
     * @throws IOException
     */
    public static String base64ToString(String str, String charset) {
        try {
            if (str == null) {
                return null;
            }
            return new String(base64ToByte(str), charset);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * 将字节数据处理成base64字符串<br/>
     *
     * @param bts 字节数据
     * @return base64编码后的字符串(用于传输)
     * @throws IOException
     */
    public static String toBase64(byte[] bts) throws IOException {
        if (bts == null || bts.length == 0) {
            return null;
        }
        return new BASE64Encoder().encode(bts);
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
        byte[] bts = oldStr.getBytes();
        return new BASE64Encoder().encode(bts);
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
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * 下面这个函数用于将字节数组换成成16进制的字符串
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
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
            throw new RuntimeException(e.getLocalizedMessage());
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
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static String toSha1(String sourceData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("sha1");
            digest.update(sourceData.getBytes());
            return byteArrayToHex(digest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
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
            throw new RuntimeException("将字符串转换成MD5时出现异常,异常信息：error =\t" + e.getLocalizedMessage() + "\r\n"
                    + "将要加密的字符串 str =\t" + sourceData);
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
                c[i] = '\u3000'; // 中文空格
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
     * 将字符串转换为unicode编码
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
     * convertStreamToString(InputStream转换成String)
     * InputStream转换成String
     *
     * @return String
     * @throws Exception
     * @throws
     * @since 1.0.0
     */
    public static String convertStreamToString(InputStream is, String charset) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new String(sb.toString().getBytes(), charset);
    }

    /**
     * mapToUrl
     * map 转换成Url字符串
     *
     * @return void
     * @throws
     * @since 1.0.0
     */
    public static String mapToUrl(Map<String, ?> map, boolean isEncode) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key).toString();

            if (isEncode) {
                value = StringUtils.toUrlStr(value);
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * mapToUrl
     * map 转换成Url字符串 url不做urlencode处理
     *
     * @return void
     * @throws
     * @since 1.0.0
     */
    public static String mapToUrl(Map<String, ?> map) {
        return mapToUrl(map, false);
    }

    /**
     * urlToMap
     * Url 字符串 转换成map
     *
     * @return void
     * @throws
     * @since 1.0.0
     */
    public static Map<String, String> urlToMap(String url, boolean isEncode) {
        Map<String, String> returnMap = new HashMap<String, String>();

        String[] urlStringArr = url.split("&");

        for (String urlString : urlStringArr) {
            String[] tmpUrlString = urlString.split("=");
            String key = tmpUrlString[0];
            String value = tmpUrlString[1];

            if (isEncode) {
                value = StringUtils.urlStrToString(value);
            }

            returnMap.put(key, value);
        }

        return returnMap;
    }

    /**
     * urlToMap
     * Url 字符串 转换成map , 不做urldecode
     *
     * @return void
     * @throws
     * @since 1.0.0
     */
    public static Map<String, String> urlToMap(String url) {
        return urlToMap(url, false);
    }

    /**
     * getMapFromXML
     * 解析XML为map
     *
     * @return void
     * @throws
     * @since 1.0.0
     */

    public static String toStringUtf_8(String str) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

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
//        	byte[] btText = base64ToByte(text);
//        	KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        	kgen.init(128, new SecureRandom(charset==null?token.getBytes():token.getBytes(charset)));
//        	SecretKey secretKey = kgen.generateKey();
//        	byte[] enCodeFormat = secretKey.getEncoded();
//        	SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//        	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
//        	cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//        	byte[] bts = cipher.doFinal(btText);
            text = text.replace("\r", "").replace("\n", "");
            String[] datas = text.split("  ");
            String result = "";
            for (String data : datas) {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(token.getBytes(), "AES"));
                byte[] bts = cipher.doFinal(base64ToByte(data));
                result += charset == null ? new String(bts) : new String(bts, charset);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    /** AES解密
     * @param text        要加密的数据
     * @param token        约定密串
     * @return 解密后的原文
     */
//    public static String AESToString(String text, String token) {
//        return AESToString(text, token, null);
//    }


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
            return StringUtils.toBase64(all.toByteArray());
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
        if (StringUtils.isEmpty(val)) {
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
            byte[] bts = StringUtils.base64ToByte(val);
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
            return StringUtils.toBase64(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 系统压缩(返回base64后的压缩数据)
     */
    public static String compress(String val, String... valCharset) {
        if (StringUtils.isEmpty(val)) {
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
            if (StringUtils.isEmpty(val)) {
                return val;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(StringUtils.base64ToByte(val));
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


}
