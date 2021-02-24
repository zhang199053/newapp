package com.yhj.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Huang Mingfei on 2017/9/30 0030.
 */

public class MD5Utils {

    /*
     * 获取当前程序的版本号
	 */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    //获取版本号(内部识别号)
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public MD5Utils() {
    }

    /**
     * MD5
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //left_return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public final static String MD5(String source) {
        // 用于加密的字符
        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            // 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = source.getBytes("UTF-8");
            // 获得指定摘要算法的 MessageDigest对象，此处为MD5
            // MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
            // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // System.out.println(mdInst);
            // MD5 Message Digest from SUN, <initialized>

            // MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);
            // System.out.println(mdInst);
            // MD5 Message Digest from SUN, <in m_progress_horizontal>

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
            // System.out.println(md);

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            // System.out.println(j);
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }

            // 返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * UTF-8字符编码。
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    //hex字符数组
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将字节数组转换为Hex字符数组。
     *
     * @param data 字节数组
     * @return Hex字符数组
     */
    public static char[] encodeHex(final byte[] data) {
        final int len;
        if (data != null && (len = data.length) > 0) {
            final char[] out = new char[len << 1];
            for (int i = 0, j = 0; i < len; i++) {
                out[j++] = digits[(0xF0 & data[i]) >>> 4];
                out[j++] = digits[(0x0F & data[i])];
            }
            return out;
        }
        return null;
    }

    /**
     * 将字节数组转换为Hex字符串。
     *
     * @param data 字节数组。
     * @return Hex字符串。
     */
    public static String encodeHexString(final byte[] data) {
        return new String(encodeHex(data));
    }

    private static int toDigit(final char ch, final int index) throws Exception {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * 将Hex字符串转换为字节数组。
     *
     * @param data Hex字符数组。
     * @return 字节数组。
     */
    public static byte[] decodeHex(final char[] data) throws Exception {
        final int len;
        if (data != null && (len = data.length) > 0) {
            if ((len & 0x01) != 0) {
                throw new Exception("odd number of characters");
            }
            final byte[] out = new byte[len >> 1];
            for (int i = 0, j = 0; j < len; i++) {
                int f = toDigit(data[j], j) << 4;
                j++;
                f = f | toDigit(data[j], j);
                j++;
                out[i] = (byte) (f & 0xFF);
            }
            return out;
        }
        return null;
    }

    /**
     * 字节数组md5加密
     *
     * @param data 明文字节数组
     * @return md5加密后的字节数组
     */
    public static byte[] md5(final byte[] data) {
        if (data != null && data.length > 0) {
            try {
                return MessageDigest.getInstance("MD5").digest(data);
            } catch (final NoSuchAlgorithmException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return null;
    }

    /**
     * 将字符串md5加密后转换为Hex.
     *
     * @param data 明文字符串。
     * @return hex字符串。
     */
    public static String md5Hex(String data) {
        if (!org.apache.commons.lang3.StringUtils.isBlank(data)) {
            return encodeHexString(md5(data.getBytes(DEFAULT_CHARSET)));
        }
        return null;
    }

    /**
     * 将数据进行Base64编码。
     *
     * @param data 明文数据。
     * @return Base64字符串。
     */
    public static String base64EncodeToString(String data) {
        if (!org.apache.commons.lang3.StringUtils.isBlank(data)) {
            final byte[] arrays = data.getBytes(DEFAULT_CHARSET);
            return Base64.encodeToString(arrays, Base64.DEFAULT);
        }
        return null;
    }

    /**
     * 将Base64进行解码。
     *
     * @param base64 base64字符串。
     * @return 解密后的明文。
     */
    public static String base64DecodeToString(String base64) {
        if (!org.apache.commons.lang3.StringUtils.isBlank(base64)) {
            try {
                final byte[] data = base64.getBytes(DEFAULT_CHARSET);
                return new String(Base64.decode(data, Base64.DEFAULT));
            } catch (Exception e) {
                Log.e("Hex", "base64DecodeToString: base64[]解密错误=>" + e, e);
            }
        }
        return null;
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
