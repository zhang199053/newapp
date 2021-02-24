package com.yhj.ViewUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yhj.Utils.DateTime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Huang Mingfei on 2017/9/20 0020.
 */


@SuppressWarnings({"ALL", "AlibabaAvoidUseTimer"})
public class DialogUtils {

    /**
     * @方法说明:
     * @方法名称:makeAuthenticator
     * @param loginId
     * @param loginPassword
     * @param timeStamp
     * @return
     * @返回�byte[]
     */
    public static byte[] makeAuthenticator(String loginId,
                                           String loginPassword, int timeStamp) {
        java.security.MessageDigest digest = null;
        byte[] result = null;
        StringBuffer sb = new StringBuffer(64);
        String tmp = String.valueOf(timeStamp);
        String timeStampStr = tmp.length() == 9 ? "0" + tmp : tmp;
        sb.append(loginId).append("\0\0\0\0\0\0\0\0\0").append(loginPassword)
                .append(timeStampStr);
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(sb.toString().getBytes());
            result = digest.digest();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @方法说明:Test whether is Mobile phone number
     * @方法名称:isMobileNO
     * @param mobiles
     * @return
     * @返回�boolean
     */
    public static boolean isMobileNO(String mobiles) {
        if (isNotEmpty(mobiles)) {
            Pattern p = Pattern
                    .compile("^(1[0-9])\\d{9}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }
    }

    /**
     * @方法说明:验证字符串为� * @方法名称:isNotEmpty
     * @param str
     * @return
     * @返回�Boolean
     */
    public static Boolean isNotEmpty(String str) {
        return null != str && !"".equals(str.trim());
    }

    /**
     * @方法说明:read the file
     * @方法名称:readFile
     * @param path
     * @return
     * @返回�byte[]
     */
    public static byte[] readFile(String path) {
        FileInputStream fis = null;
        File file = new File(path);
        try {
            fis = new FileInputStream(file);
            byte[] bb = new byte[fis.available()];
            fis.read(bb);
            return bb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @方法说明:存文� * @方法名称:writeFile
     * @param data
     * @param fileName
     * @返回�void
     */
    public static void writeFile(byte[] data, String fileName) {

        try {

            FileOutputStream output = new FileOutputStream(fileName);
            output.write(data);
            output.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * @方法说明:Test whether the SD card presence
     * @方法名称:hasSdcard
     * @return
     * @返回�boolean
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @方法说明:get the file's path
     * @方法名称:getFilePath
     * @return
     * @返回�String
     */
    public static String getFilePath() {
        if (hasSdcard()) {
            return sdPath;

        } else {
            return dataPath;
        }
    }

    /* To create file's path */
    public final static String sdPath = Environment
            .getExternalStorageDirectory().toString();
    public final static String dataPath = "/data/data/com.lyn.loginUI/";
    public final static String projectResoucePath = "/WanKeXing/";
    public final static String projectImageResoucePath = "/Wankexing/";

    /**
     * @方法说明:To create the file by the path and filename
     * @方法名称:creatFilePath
     * @param Path
     * @param filename
     * @return
     * @返回�File
     */
    public static File creatFilePath(String Path, String filename) {

        File path = new File(Path);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path.getAbsolutePath() + File.separator + filename);
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @方法说明:To create file folder
     * @方法名称:creatFilePath
     * @param Path
     * @返回�void
     */
    public static void creatFilePath(String Path) {

        File path = new File(Path);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * @方法说明:创建文件及目� * @方法名称:creatFile
     * @param path
     * @return
     * @返回�File
     */
    public static File creatFile(String path) {
        File file = new File(path);
        try {
            int lastIndex = path.lastIndexOf(File.separator);
            if (lastIndex != -1) {
                String md = path.substring(0, lastIndex);
                File mdFile = new File(md);
                if (!mdFile.exists()) {
                    mdFile.mkdirs();
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @方法说明:加载布局文件
     * @方法名称:LoadXmlView
     * @param con
     * @param resouce_Id
     * @return
     * @返回�View
     */
    public static View LoadXmlView(Context con, int resouce_Id) {
        LayoutInflater flat = LayoutInflater.from(con);
        View view = flat.inflate(resouce_Id, null);
        return view;
    }

    /**
     * @方法说明:remove all child's view
     * @方法名称:removeAllView
     * @param view
     * @返回�void
     */
    public static void removeAllView(LinearLayout view) {
        int count = view.getChildCount();
        for (int i = 0; i < count; i++) {
            view.removeViewAt(0);
        }
    }

    /**
     * @方法说明:Get a picture of bytes after compression flow
     * @方法名称:getImage
     * @param srcPath
     * @return
     * @返回�byte[]
     */
    public static byte[] getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        // if(newOpts.outWidth<=0||newOpts.outHeight<=0){
        //
        // left_return new byte[]{0};
        // }
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置
        float hh = 800f;// 这里设置高度00f
        float ww = 480f;// 这里设置宽度80f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即
        int be = 1;// be=1表示不缩
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Bitmap createbt = matrixBitmap(bitmap, srcPath);
        return compressImage(createbt);// 压缩好比例大小后再进行质量压
    }

    /**
     * @方法说明:图片质量压缩
     * @方法名称:compressImage
     * @param image
     * @return
     * @返回�byte[]
     */
    public static byte[] compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这00表示不压缩，把压缩后的数据存放到baos
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大00kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos
            options -= 10;// 每次都减0
            if (options == 0) {
                break;
            }
        }
        byte[] b = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * @方法说明:再度压缩，图片大�0kb,就继续压� * @方法名称:uploadFileComp
     * @param image
     * @param file
     * @返回�void
     */
    public static void uploadFileComp(Bitmap image, File file) {
        if (image == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            int options = 90;
            while (stream.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大00kb,大于继续压缩
                stream.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, stream);// 这里压缩options%，把压缩后的数据存放到baos
                // options
                // -=
                // 10;//
                // 每次都减0
                if (options == 0) {
                    break;
                }
            }
            byte[] bytes = stream.toByteArray();
            fos.write(bytes);
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);// 把压缩后的数据baos存放到ByteArrayInputStream
            // image
            // =
            // BitmapFactory.decodeStream(isBm,
            // null,
            // null);//
            // 把ByteArrayInputStream数据生成图片
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap uploadCompressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这00表示不压缩，把压缩后的数据存放到baos
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大00kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos
            options -= 10;// 每次都减0
            if (options == 0) {
                break;
            }
        }
        byte[] b = baos.toByteArray();
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @方法说明:获取sd卡剩余容� * @方法名称:getSDFreeSize
     * @return
     * @返回�long
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大
        return freeBlocks * blockSize; // 单位Byte
        // left_return (freeBlocks * blockSize)/1024; //单位KB
        // left_return (freeBlocks * blockSize); // 单位MB
        // left_return (freeBlocks * blockSize)/1024; //单位KB
        // left_return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * @方法说明:获取SD卡总容� * @方法名称:getSDAllSize
     * @return
     * @返回�long
     */
    public static long getSDAllSize() {
        // 取得SD卡文件路
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大
        return allBlocks * blockSize; // 单位Byte
        // left_return (allBlocks * blockSize)/1024; //单位KB
        // left_return (allBlocks * blockSize)/1024; // 单位MB
    }

    /**
     * @方法说明:创建位图的缩略图
     * @方法名称:decodeBitmap
     * @param picPhotoPath
     * @param sw
     * @param sh
     * @param isBreviary
     * @return
     * @返回�Bitmap
     */
    public static Bitmap decodeBitmap(String picPhotoPath, float sw, float sh,
                                      boolean isBreviary) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(picPhotoPath, options);

        options.inJustDecodeBounds = false;
        if (sw == 0 || sh == 0) {
            options.inSampleSize = 1;
        } else {
            float souceSize = options.outWidth >= options.outHeight ? options.outWidth
                    : options.outHeight;
            if (souceSize <= 0) {
                return null;
            }
            float scaleSize = sw >= sh ? sw : sh;

            int scale = (int) (souceSize / scaleSize);

            if (scale <= 0) {
                scale = 1;
            }
            options.inSampleSize = scale;
        }
        // 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来
        Bitmap bitmapResult = null;
        try {
            bitmapResult = BitmapFactory.decodeFile(picPhotoPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapResult;

    }

    /**
     * @方法说明:从资源包中创建位图缩略图
     * @方法名称:decodeBitmap
     * @param context
     * @param resouceId
     * @param sw
     * @param sh
     * @return
     * @返回�Bitmap
     */
    public static Bitmap decodeBitmap(Context context, int resouceId, float sw,
                                      float sh) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resouceId, options);
        options.inJustDecodeBounds = false;
        int scale;
        if (sw == 0 || sh == 0) {
            scale = 1;
        } else {
            float souceSize = options.outWidth >= options.outHeight ? options.outWidth
                    : options.outHeight;
            float scaleSize = sw >= sh ? sw : sh;
            scale = (int) (souceSize / scaleSize);
            if (scale <= 0) {
                scale = 1;
            }
        }
        options.inSampleSize = scale;
        // 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                resouceId, options);
        if (sw > 0 && sh > 0) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, (int) sw,
                    (int) sh, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * @方法说明:获得图片的旋转角� * @方法名称:getExifOrientation
     * @param filepath
     * @return
     * @返回�int
     */
    private static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            // Log.e("test", "cannot read exif", ex);
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * @方法说明:还原图片的角� * @方法名称:matrixBitmap
     * @param bitmap
     * @param path
     * @return
     * @返回�Bitmap
     */
    public static Bitmap matrixBitmap(Bitmap bitmap, String path) {
        if (bitmap == null) {
            return null;
        }
        int angle = getExifOrientation(path);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (angle != 0) { // 如果照片出现旋转 那么 就更改旋转度
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        }
        return bitmap;
    }

    /**
     * @方法说明:图片压缩的方� * @方法名称:matrixresuBitmap
     * @param bitmap
     *            bitmap图片
     * @param fixedw
     *            宽度压缩比例
     * @param fixedh
     *            高度压缩比例
     * @return
     * @返回�Bitmap 压缩后的图片
     */
    public static Bitmap matrixresuBitmap(Bitmap bitmap, float fixedw,
                                          float fixedh) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scalew = fixedw / w;
        float scaleh = fixedh / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scalew, scaleh);// 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                false);
        return resizeBitmap;
    }

    public static boolean play(File file, boolean isEmpty) {
        // Get the file we want to playback.
        if (!file.exists()) {
            return false;
        }
        int musicLength = (int) (file.length() / 2);
        short[] music = new short[musicLength];

        try {
            // Create a DataInputStream to read the audio data back from the
            // saved file.
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // Read the file into the music array.
            int i = 0;
            if (isEmpty) {
                return dis.available() > 0;
            } else {
                while (dis.available() > 0) {
                    music[i] = dis.readShort();
                    i++;
                }
            }

            // Close the input streams.
            dis.close();

            // Create a new AudioTrack object using the same parameters as the
            // AudioRecord
            // object used to create the file.
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, musicLength * 2,
                    AudioTrack.MODE_STREAM);

            // Start playback
            audioTrack.play();

            // Write the music buffer to the AudioTrack object
            audioTrack.write(music, 0, musicLength);

            audioTrack.stop();

        } catch (Throwable t) {
            Log.e("AudioTrack", "Playback Failed");
        }
        return true;
    }

    public static int chatState = 0;// 聊天类型
    public static final int PERSONALCHAT = 0;
    public static final int ACTCHAT = 1;
    public static final int INTERESTCHAT = 2;

    /* 改变聊天类型 */
    public static void changeChaType(int state) {
        chatState = state;
    }

    /**
     * @方法说明:获得XML字符
     * @方法名称:getXmlStr
     * @param con
     * @param rid
     * @return
     * @返回�String
     */
    public static String getXmlStr(Context con, int rid) {
        return con.getString(rid);
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * @方法说明:活动map中下标指定的
     * @方法名称:getMapIndexObject
     * @param data
     * @param index
     * @return
     * @返回�Object
     */
    public static Object getMapIndexObject(Map data, int index) {
        int count = 0;
        Iterator it = data.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (count == index) {
                Object ob = data.get(key);
                return ob;
            }
            count++;

        }
        return null;

    }

    /**
     * @方法说明:获得一个map的指定下标的key� * @方法名称:getMapIndexObjectAndKey
     * @param data
     * @param index
     * @return
     * @返回�Object[]
     */
    public static Object[] getMapIndexObjectAndKey(Map data, int index) {
        int count = 0;
        Iterator it = data.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (count == index) {
                Object ob = data.get(key);
                return new Object[] { key, ob };
            }
            count++;

        }
        return null;
    }

    /**
     * @方法说明:根据路径删除指定的目录或文件，无论存在与� * @方法名称:DeleteFolder
     * @param sPath
     * @return
     * @返回�boolean
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存
        if (!file.exists()) { // 不存在返false
            return flag;
        } else {
            // 判断是否为文
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * @方法说明:删除单个� * @方法名称:deleteFile
     * @param sPath
     * @return
     * @返回�boolean
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * @方法说明:删除目录（文件夹）以及目录下的文� * @方法名称:deleteDirectory
     * @param sPath
     * @return
     * @返回�boolean
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文包括子目
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } // 删除子目
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        return dirFile.delete();
    }

    /**
     * @方法说明:判断一个map的key值是否存
     * @方法名称:isKey
     * @param data
     * @param key
     * @return
     * @返回�boolean
     */
    public static boolean isKey(Map data, String key) {
        Iterator it = data.keySet().iterator();
        while (it.hasNext()) {
            String mapKey = (String) it.next();
            if (mapKey.equals(key)) {
                return true;
            }

        }
        return false;
    }

    /**
     * @方法说明:获得一个map对象的数
     * @方法名称:getMapObjectNum
     * @param data
     * @return
     * @返回�int
     */
    public static int getMapObjectNum(Map data) {
        int count = 0;
        Iterator it = data.keySet().iterator();

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;

    }

    /**
     * @方法说明:获得当前系统的时� * @方法名称:getCutimeIsShow
     * @return
     * @返回�String
     */
    public static String getCutimeIsShow() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * @方法说明:下载数据� * @方法名称:writeFile
     * @param showname
     * @param bin
     * @返回�void
     */
    public static synchronized void writeFile(String showname, byte[] bin) {
        File file = DialogUtils.creatFilePath(DialogUtils.getFilePath()
                + projectResoucePath, showname);
        DialogUtils.writeFile(bin, file.getAbsolutePath());
    }

    /**
     * @方法说明:获得指定格式的日� * @方法名称:getDate
     * @param parten
     * @param date
     * @return
     * @返回�String
     */
    public static String getDate(String parten, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(parten);
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }

    /**
     * @方法说明:获得相册图片的路� * @方法名称:getPicPath
     * @param data
     * @param ac
     * @return
     * @返回�String
     */
    public static String getPicPath(Intent data, Activity ac) {

        Uri originalUri = data.getData();

        String[] proj = { MediaStore.Images.Media.DATA };

        if (originalUri != null && proj != null) {
            Cursor cursor = ac.getContentResolver().query(originalUri, null,
                    null, null, null);
            if (cursor == null) {
                String path = originalUri.getPath();
                if (!DialogUtils.isEmpty(path)) {

                    String type = ".jpg";
                    String type1 = ".png";
                    if (path.endsWith(type) || path.endsWith(type1)) {

                        return path;
                    } else {

                        return "";
                    }
                } else {
                    return "";
                }
            } else
                // 将光标移至开，这个很重要，不小心很容易引起越
            {
                cursor.moveToFirst();
            }
            // 按我个人理解 这个是获得用户选择的图片的索引
            int column_index = cursor.getColumnIndex(proj[0]);

            // 最后根据索引值获取图片路
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else {
            Bundle bundle = data.getExtras();
            String path = "";
            if (bundle != null) {
                Bitmap photo = (Bitmap) bundle.get("data");
                try {
                    File file = DialogUtils.creatFilePath(DialogUtils.getFilePath()
                            + DialogUtils.projectResoucePath, DialogUtils.getCutimeIsShow()
                            + ".jpg");
                    path = file.getAbsolutePath();
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(path, false));
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return path;
        }
    }

    /**
     * @方法说明:使用系统当前日期加以调整作为照片的名� * @方法名称:getPhotoFileName
     * @return
     * @返回�String
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static void albumCorp(Activity act, int index) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        act.startActivityForResult(intent, index);
    }

    public static void picSelect(Activity ac, int respIndex) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        ac.startActivityForResult(intent, respIndex);

    }

    /**
     * @方法说明:此方法没有被使用� * @方法名称:photoPic
     * @param ac
     * @param respIndex
     * @返回�void
     */
    public static void photoPic(Activity ac, int respIndex) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        ac.startActivityForResult(intent, respIndex);
    }

    /**
     * @方法说明:照相机功� * @方法名称:photoPic
     * @param ac
     * @param file
     * @param respIndex
     * @返回�void
     */
    public static void photoPic(Activity ac, File file, int respIndex) {
        // 跳转至相机界
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 指定相机拍照后相片的存储位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        ac.startActivityForResult(intent, respIndex);
    }

    /**
     * @方法说明:
     * @方法名称:photoCut
     * @param ac
     * @param tempFile
     * @param respIndex
     * @返回�void
     */
    public static void photoCut(Activity ac, File tempFile, int respIndex) {
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        ac.startActivityForResult(cameraintent, respIndex);
    }

    public static String getStartsWith(String souceStr, String startStr,
                                       String str) {
        int index = souceStr.indexOf(startStr);
        if (index != -1) {
            String leftStr = souceStr.substring(0, index);
            String right = souceStr.substring(index + startStr.length());
            return leftStr + str + getStartsWith(right, startStr, str);
        } else {
            return souceStr;
        }
    }

    /**
     * @方法说明:插图图片
     * @方法名称:insetImage
     * @param et
     * @param imageName
     * @param image
     * @返回�void
     */
    public static void insetImage(EditText et, String imageName, Bitmap image) {
        Editable eb = et.getEditableText();
        SpannableString ss = new SpannableString("[" + imageName + "]");
        // 定义插入图片
        Drawable drawable = new BitmapDrawable(image);
        ss.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), 0, ("["
                + imageName + "]").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(2, 0, drawable.getIntrinsicWidth() + 20,
                drawable.getIntrinsicHeight() + 20);
        // 获得光标所在位�
        int qqPosition = et.getSelectionStart();
        // 插入图片
        eb.insert(qqPosition, "\n");
        eb.insert(qqPosition, ss);
        eb.insert(qqPosition, "\n");
    }

    /*
     * @param sourceStr
     *
     * @left_return
     *
     * @icon_back�String
     */
    public static String parserDiscussContext(String sourceStr) {
        String parrer = "[\\[\\]]";
        String[] ss = sourceStr.split(parrer);
        String endStr = "";
        for (int i = 0; i < ss.length; i++) {
            String indexStr = ss[i];
            if (indexStr.startsWith("/")) {

                endStr += "[/img]";
            } else {
                endStr += indexStr;
            }
        }
        return endStr;
    }

    /**
     * @方法说明:测量一个控件的大小
     * @方法名称:getViewSize
     * @param view
     * @return
     * @返回�int[]
     */
    public static int[] getViewSize(View view) {

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);
        int vw = view.getMeasuredWidth();
        int vh = view.getMeasuredHeight();
        return new int[] { vw, vh };
    }

    public static void setViewisvible(View view, Boolean is) {
        if (is) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setViewVisibilityAgainst(View view, boolean is) {
        int visible = view.getVisibility();
        switch (visible) {
            case View.GONE:
            case View.INVISIBLE:
                view.setVisibility(View.VISIBLE);
                break;

            case View.VISIBLE:
                if (is) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }

                break;
        }
    }

    /**
     * @方法说明:计算图片的大� * @方法名称:calculatePicBig
     * @param path
     * @return
     * @返回�int
     */
    public static int calculatePicBig(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int[] size = DialogUtils.getBitmapWorH(path);
        int w = options.outWidth;
        int h = options.outHeight;
        int sizeByte = 4;
        /**
         * 这一段代码不理解
         */
        if (options.inPreferredConfig == Bitmap.Config.ALPHA_8) {
            sizeByte = 1;
        } else if (options.inPreferredConfig == Bitmap.Config.ARGB_4444) {
            sizeByte = 2;

        } else if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
            sizeByte = 4;

        } else if (options.inPreferredConfig == Bitmap.Config.RGB_565) {
            sizeByte = 2;
        }
        return w * h * sizeByte;
    }

    /**
     * @方法说明:获取图片的宽和高
     * @方法名称:getBitmapWorH
     * @param path
     * @return
     * @返回�int[]
     */
    public static int[] getBitmapWorH(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int w = options.outWidth;
        int h = options.outHeight;
        return new int[] { w, h };
    }

    /**
     * @方法说明:获取图片的宽和高
     * @方法名称:getDrawbleWorH
     * @param context
     * @param resId
     * @return
     * @返回�int[]
     */
    public static int[] getDrawbleWorH(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resId);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        return new int[] { w, h };
    }

    private static int y, m, d;
    private static DatePickerDialog showdata;

    /**
     * @方法说明:显示日期
     * @方法名称:showData
     * @param con
     * @param c
     * @param isTime
     * @param showText
     * @返回�void
     */
    public static void showData(Context con, Calendar c, final boolean isTime,
                                final TextView showText) {
        showdata = new DatePickerDialog(con,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int month, int dayOfMonth) {
                        y = year;
                        m = month + 1;
                        d = dayOfMonth;
                        String sm = String.valueOf(m);
                        String sd = String.valueOf(d);
                        if (m < 0) {
                            sm = 0 + String.valueOf(m);
                        }
                        if (d < 0) {
                            sd = 0 + String.valueOf(d);
                        }
                        if (!isTime) {
                            showText.setText(y + "-" + sm + "-" + sd);
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)) {

        };
        showdata.setCancelable(false);
        showdata.show();
    }

    /**
     * @方法说明:关闭时间选择� * @方法名称:closeDataSelect
     * @返回�void
     */
    public static void closeDataSelect() {
        if (showdata != null) {
            showdata.dismiss();
        }
        if (showtime != null) {
            showtime.dismiss();
        }
    }

    private static int h, f;
    private static TimePickerDialog showtime;

    /**
     * @方法说明:show出一个时间对话框，可供用户选择
     * @方法名称:showTime
     * @param con
     * @param c
     * @param showText
     * @param state
     * @param starTime
     * @返回�void
     */
    public static void showTime(final Context con, Calendar c,
                                final TextView showText, final int state, final String starTime) {

        showtime = new TimePickerDialog(con,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timerPicker,
                                          int hourOfDay, int minute) {
                        h = hourOfDay;
                        f = minute;
                        String hf = String.valueOf(h);
                        if (h < 10) {
                            hf = 0 + String.valueOf(h);
                        }
                        String sf = String.valueOf(f);
                        if (f < 10) {
                            sf = 0 + String.valueOf(f);
                        }
                        DateTime dt = new DateTime();
                        String allTime = y + "-" + m + "-" + d + " " + hf + ":"
                                + sf;
                        String showTime = y + "-" + m + "-" + d + " " + hf
                                + ":" + sf;
                        switch (state) {
                            case 0:
                                if (dt.getActLongTime(allTime) >= System
                                        .currentTimeMillis()
                                        && !DateTime
                                        .getInstance()
                                        .judgeData(
                                                1,
                                                DateTime.HOUR,
                                                new Date(),
                                                DateTime.getInstance()
                                                        .formatString(
                                                                allTime,
                                                                DateTime.DATE_PATTERN_8))) {
                                    showText.setText(allTime);
                                } else {
                                    Toast.makeText(con, "开始时间至少在当前时间1小时内",
                                            Toast.LENGTH_SHORT);
                                    showText.setText("");
                                }
                                break;

                            case 1:
                                if (DateTime.getInstance().getActLongTime(allTime) <= DateTime
                                        .getInstance().getActLongTime(starTime)
                                        || DateTime
                                        .getInstance()
                                        .judgeData(
                                                2,
                                                DateTime.HOUR,
                                                DateTime.getInstance()
                                                        .formatString(
                                                                starTime,
                                                                DateTime.DATE_PATTERN_8),
                                                DateTime.getInstance()
                                                        .formatString(
                                                                allTime,
                                                                DateTime.DATE_PATTERN_8))) {
                                    Toast.makeText(con, "结束时间至少是开始时间的两小时之内",
                                            Toast.LENGTH_SHORT);
                                } else {

                                    showText.setText(allTime);
                                }

                                break;
                            case 2:
                                if (DateTime.getInstance().getActLongTime(allTime) <= System
                                        .currentTimeMillis()
                                        || DateTime.getInstance().getActLongTime(
                                        allTime) >= DateTime.getInstance()
                                        .getActLongTime(starTime)
                                        || DateTime
                                        .getInstance()
                                        .judgeData(
                                                0,
                                                DateTime.HOUR,
                                                DateTime.getInstance()
                                                        .formatString(
                                                                allTime,
                                                                DateTime.DATE_PATTERN_8),
                                                DateTime.getInstance()
                                                        .formatString(
                                                                starTime,
                                                                DateTime.DATE_PATTERN_8))) {
                                    // Tools.showPrompt(con, "结束报名时间应该在活动发布后以及开始时间之,
                                    // Tools.TOASHOW);
                                } else {

                                    showText.setText(showTime);
                                }

                                break;
                        }

                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true) {

        }; // 是否为二十四 showtime.setCancelable(false);
        showtime.show();
    }

    /**
     * @方法说明:时间选择� * @方法名称:selecData
     * @param context
     * @param showText
     * @param isTime
     * @param state
     * @param starTime
     * @返回�void
     */
    public static void selecData(Context context, TextView showText,
                                 boolean isTime, int state, String starTime) {
        Calendar c = Calendar.getInstance();
        if (isTime) {
            showTime(context, c, showText, state, starTime);
            showData(context, c, isTime, showText);
        } else {
            showData(context, c, isTime, showText);
        }
    }

    /**
     * @方法说明:关闭时间选择� * @方法名称:closeSystemDialog
     * @param dialog
     * @param isClose
     * @返回�void
     */
    public static void closeSystemDialog(DialogInterface dialog, boolean isClose) {
        Field field;
        try {
            field = dialog.getClass().getSuperclass().getDeclaredField(

                    "mShowing");
            field.setAccessible(true);
            // 将mShowing变量设为false，表示对话框已关 field.set(dialog, isClose);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @方法说明:加载一组布局文件
     * @方法名称:loadViewArray
     * @param context
     * @param layoutR
     * @return
     * @返回�ArrayList<View>
     */
    public static ArrayList<View> loadViewArray(Context context, int[] layoutR) {
        ArrayList<View> list = new ArrayList<View>();
        for (int i = 0; i < layoutR.length; i++) {
            View view = DialogUtils.LoadXmlView(context, layoutR[i]);
            list.add(view);
        }
        return list;
    }

    /**
     * @方法说明:移除数组中的所有元� * @方法名称:removeAll
     * @param list
     * @返回�void
     */
    public static void removeAll(ArrayList list) {
        int count = list.size();
        for (int i = 0; i < count; i++) {
            list.remove(0);
        }
    }

    /**
     * @方法说明:获得listView的高� * @方法名称:setListViewHeightBasedOnChildren
     * @param listView
     * @返回�void
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        BaseAdapter adpter = (BaseAdapter) listView.getAdapter();
        if (adpter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = adpter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = adpter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (adpter.getCount() - 1));

        listView.setLayoutParams(params);
    }

    /**
     * @方法说明:获取gridView的高� * @方法名称:setListViewHeightBasedOnChildren
     * @param gridView
     * @param cols
     * @param hozHight
     * @param height
     * @返回�void
     */
    public static void setListViewHeightBasedOnChildren(GridView gridView,
                                                        int cols, int hozHight, int height) {
        // 获取GridView对应的Adapter
        BaseAdapter adpter = (BaseAdapter) gridView.getAdapter();
        if (adpter == null) {
            return;
        }
        // 每列放多少个item
        int cls = adpter.getCount() % cols == 0 ? adpter.getCount() / cols
                : adpter.getCount() / cols + 1;

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = cls * height + hozHight * (cls - 1);

        gridView.setLayoutParams(params);

    }

    /**
     * @方法说明:字符串转换成整数
     * @方法名称:toInt
     * @param str
     * @param defValue
     * @return
     * @返回�int
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * @方法说明:对象转整� * @方法名称:toInt
     * @param obj
     * @return
     * @返回�int
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    /**
     * @方法说明:判断字符串是否为� * @方法名称:isEmpty
     * @param str
     * @return
     * @返回�boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }

    /**
     * @方法说明:判断str是否为数� * @方法名称:isNumeric
     * @param str
     * @return
     * @返回�boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * @方法说明:判断一个文件是否存� * @方法名称:fileIsExists
     * @param filePath
     * @return
     * @返回�boolean
     */
    public boolean fileIsExists(String filePath) {
        File f = new File(filePath);
        return f.exists();
    }

    public static Bitmap getRoundGray(Bitmap bt, int pix, boolean isGray) {
        int width = bt.getWidth();
        int height = bt.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawColor(0x0);
        if (pix > 0) {
            Rect rect = new Rect(0, 0, bt.getWidth(), bt.getHeight());
            RectF rectf = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawRoundRect(rectf, pix, pix, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        if (isGray) {
            paint.setAlpha(0x60);
        }
        canvas.drawBitmap(bt, 0, 0, paint);

        if (bt.isRecycled()) {
            bt.recycle();
        }
        return output;
    }

    /**
     * @方法说明:判断触摸点是否在控件� * @方法名称:isEvenAboveView
     * @param v
     * @param event
     * @return
     * @返回�boolean
     */
    public static boolean isEvenAboveView(View v, MotionEvent event) {
        if (v != null) {
            int[] leftTop = { 0, 0 };
            v.getLocationOnScreen(leftTop);
            int left = leftTop[0];

            int top = leftTop[1];
            int bottom = leftTop[1] + v.getHeight();
            int right = leftTop[0] + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事 left_return true;
            }
        }
        return false;
    }

    /**
     * @方法说明:实现文本复制功能
     * @方法名称:copy
     * @param content
     * @param context
     * @返回�void
     */
    public static void copy(SpannableString content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
    }

    private static int verifyCount;
    private static Button verifyButton = null;
    private static String verifyContent = "向我发送验证码";

    /**
     * @说明: 验证码发送检� * @名称:handler
     * @类型:Handler
     */
    static Handler handler = null;
    @SuppressWarnings("AlibabaAvoidUseTimer")
    static Timer timer = null;
    private static boolean isCheckMessage = false;

    public static void startTimeMessage(int second) {
        verifyCount = second;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (verifyCount > 0) {
                    verifyCount--;
                    setPlaySound(true);
                    setMessageState(true);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        verifyCount = 0;
                        setMessageState(false);
                        setPlaySound(false);
                    }
                });
            }
        }).start();

    }

    private static boolean isplaySound = false;

    public static boolean getPlaySound() {
        return isplaySound;
    }

    public static void setPlaySound(boolean isPSound) {
        isplaySound = isPSound;
    }

    public static boolean getMessageState() {
        return isCheckMessage;
    }

    public static void setMessageState(boolean iso) {
        isCheckMessage = iso;
    }

    /**
     * @方法说明:重置音效状� * @方法名称:stopMessageVerfy
     * @返回�void
     */
    public static void stopMessageVerfy() {
        verifyCount = 0;
        setMessageState(false);
        setPlaySound(false);
    }

    /**
     * @方法说明:停止验证检� * @方法名称:stopVerfy
     * @返回�void
     */
    // public static void stopVerfy() {
    // if (verifyButton != null) {
    // verifyCount = 0;
    // verifyButton.setTag("true");
    // verifyButton.setText(verifyContent);
    // verifyButton.setBackgroundResource(R.drawable.normal_btn);
    // }
    // }

    /**
     * @方法说明:根据手机的分辨率从dp的单位转成px(像素)
     * @方法名称:diptopx
     * @param context
     * @param dpValue
     * @return
     * @返回�int
     */
    public static int diptopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @方法说明:根据手机的分辨率从px(像素)转换成dp
     * @方法名称:pxtodip
     * @param context
     * @param pxValue
     * @return
     * @返回�int
     */
    public static int pxtodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @方法说明:带单选按钮的Dialog
     * @方法名称:selectcondition
     * @param title
     * @param array
     * @param text
     * @param con
     * @返回�void
     */
    public static void selectcondition(String title, final String[] array,
                                       final TextView text, Context con) {
        AlertDialog.Builder malertdialog = new AlertDialog.Builder(con);
        malertdialog.setTitle(title);
        malertdialog.setIcon(android.R.drawable.ic_dialog_info);
        malertdialog.setItems(array, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                text.setText(array[whichButton]);
                dialog.dismiss();
            }
        }).create().show();

    }

    /**
     * @方法说明:保存参数，此方法没有被使� * @方法名称:savaValue
     * @param tag1
     * @param tag2
     * @param value
     * @param context
     * @返回�void
     */
    public static void savaValue(String tag1, String tag2, Object value,
                                 Context context) {
        SharedPreferences sp = context.getSharedPreferences(tag1 + "value",
                Context.MODE_PRIVATE);
        if (value instanceof Boolean) {
            sp.edit().putBoolean(tag1 + "value" + tag2, (Boolean) value)
                    .commit();

        } else if (value instanceof Integer || value instanceof Byte) {
            sp.edit().putInt(tag1 + "value" + tag2, (Integer) value).commit();

        } else if (value instanceof Long) {
            sp.edit().putLong(tag1 + "value" + tag2, (Long) value).commit();
        } else if (value instanceof Float) {
            sp.edit().putFloat(tag1 + "value" + tag2, (Float) value).commit();

        } else if (value instanceof String) {
            sp.edit().putString(tag1 + "value" + tag2, (String) value).commit();

        }

    }

    /**
     * @方法说明:返回共享首选项中的数据，此方法没有被使� * @方法名称:getValue
     * @param tag1
     * @param tag2
     * @param T
     * @param context
     * @param defaultValue
     * @return
     * @返回�Object
     */
    public static Object getValue(String tag1, String tag2, Class<?> T,
                                  Context context, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(tag1 + "value",
                Context.MODE_PRIVATE);
        if (T == Boolean.class) {
            return sp.getBoolean(tag1 + "value" + tag2, (Boolean) defaultValue);

        } else if (T == Integer.class || T == Byte.class) {
            return sp.getInt(tag1 + "value" + tag2, (Integer) defaultValue);
        } else if (T == Long.class) {
            return sp.getLong(tag1 + "value" + tag2, (Long) defaultValue);
        } else if (T == Float.class) {
            return sp.getFloat(tag1 + "value" + tag2, (Float) defaultValue);
        } else if (T == String.class) {
            return sp.getString(tag1 + "value" + tag2, (String) defaultValue);
        }
        return null;
    }

    /**
     * @方法说明:计算分享内容的字数，一个汉字两个英文字母，一个中文标点两个英文标�
     *                                         注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     * @方法名称:calculateLength
     * @param c
     * @return
     * @返回�long
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * @方法说明:限制EditText的输入字� * @方法名称:limitEdit
     * @param editText
     * @param length
     * @返回�void
     */
    public static void limitEdit(final Context context,
                                 final EditText editText, final int length) {
        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private boolean isEdit = true;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isEdit == false) {
                    Toast.makeText(context, "输入的字数超过限制", Toast.LENGTH_SHORT)
                            .show();
                    Editable etext = editText.getText();
                    int pos = etext.length();
                    Selection.setSelection(etext, pos);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (DialogUtils.calculateLength(temp) > length) {
                    isEdit = false;
                    Toast.makeText(context, "输入的字数超过限制", Toast.LENGTH_SHORT)
                            .show();
                    s.delete(temp.length() - 1, temp.length());
                    editText.setText(s);
                }
            }
        });
    }

    /**
     * @方法说明:刷新剩余输入字数最大值新浪微博是140个字，人人网200个字
     * @方法名称:setLeftCount
     * @param mTextView
     * @param num
     * @param mEditText
     * @返回�void
     */
    public static void setLeftCount(TextView mTextView, int num,
                                    EditText mEditText) {
        mTextView.setText(String.valueOf((num - getInputCount(mEditText))));
    }

    /**
     * @方法说明:获取用户输入的分享内容字� * @方法名称:getInputCount
     * @param mEditText
     * @return
     * @返回�long
     */
    private static long getInputCount(EditText mEditText) {
        return calculateLength(mEditText.getText().toString());
    }

    /**
     * @方法说明:截取字符� * @方法名称:stringSubStringfInfo
     * @param str
     * @param minNum
     * @param maxNum
     * @return
     * @返回�String
     */
    public static String stringSubStringfInfo(String str, int minNum, int maxNum) {
        if (DialogUtils.isEmpty(str)) {
            return null;
        }
        String s = str.substring(minNum, maxNum);
        return s;
    }

    /**
     * @方法说明:判断sd卡存� * @方法名称:hasSDCard
     * @return
     * @返回�boolean
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
            // /data/data/
        }
    }

	/*
	 * public static void countdown(int second, final Button button, final
	 * String content) { verifyButton = button; verifyCount = second;
	 * button.setTag("false"); button.setBackgroundColor(R.color.gray); new
	 * Thread(new Runnable() {
	 *
	 * @Override public void run() { while (verifyCount > 0) {
	 *
	 * new Handler(Looper.getMainLooper()).post(new Runnable() {
	 *
	 * @Override public void run() { button.setText(String.valueOf(verifyCount)
	 * + "); // button.setOnClickListener(null); } }); verifyCount--; try {
	 * Thread.sleep(1000); } catch (InterruptedException e) {
	 * e.printStackTrace(); }
	 *
	 * } new Handler(Looper.getMainLooper()).post(new Runnable() {
	 *
	 * @Override public void run() { verifyCount = 0; button.setTag("true");
	 * button.setText(content);
	 * button.setBackgroundResource(R.drawable.my_btn_blue_backgroup); } });
	 *
	 * } }).start();
	 *
	 * }
	 */

    public static View.OnFocusChangeListener onFocusAutoClearHintListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };

    private static long lastClickTime;

    /**
     * @方法说明:防止控件被重复点击，如果点击间隔时间小于指定时间就点击无� * @方法名称:isFastDoubleClick
     * @param times
     * @return
     * @返回�boolean
     */
    public static boolean isFastDoubleClick(long times) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < times) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * @方法说明:获取当前客户端版本信息versionName + versionCode
     * @方法名称:getCurrentVersion
     * @param mContext
     * @return
     * @返回�String
     */
    public static String getCurrentVersion(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return info.versionName.trim() + String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }

    public static String getVersionName(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return info.versionName.trim();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }

    public static String getVersionCode(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }

    /**
     * @方法说明:获取文件的名� * @方法名称:getFileName
     * @param filePath
     * @return
     * @返回�String
     */
    public static String getFileName(String filePath) {
        if (isEmpty(filePath)) {
            return "";
        }
        int index0 = filePath.lastIndexOf(".");
        if (index0 != -1) {
            filePath = filePath.substring(0, index0);
        }
        filePath = filePath.replaceAll(":", "");
        filePath = filePath.replaceAll(File.separator, "");
        return filePath;
    }

    /**
     * @方法说明:
     * @方法名称:getDisPlayMetrics
     * @param context
     * @return
     * @返回�DisplayMetrics
     */
    public static DisplayMetrics getDisPlayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        return metric;
    }

    /**
     * @方法说明:获取屏幕的宽度（像素� * @方法名称:getScreenWidth
     * @param context
     * @return
     * @返回�int
     */
    public static int getScreenWidth(Context context) {
        int width = getDisPlayMetrics(context).widthPixels;
        return width;
    }

    /**
     * @方法说明:获取屏幕的高� * @方法名称:getScreenHeight
     * @param context
     * @return
     * @返回�int
     */
    public static int getScreenHeight(Context context) {
        int height = getDisPlayMetrics(context).heightPixels;
        return height;
    }

    /**
     * @方法说明:屏幕密度(0.75 / 1.0 / 1.5)
     * @方法名称:getDensity
     * @param context
     * @return
     * @返回�float
     */
    public static float getDensity(Context context) {
        float density = getDisPlayMetrics(context).density;
        return density;
    }

    /**
     * @方法说明:屏幕密度DPI(120 / 160 / 240)
     * @方法名称:getDensityDpi
     * @param context
     * @return
     * @返回�int
     */
    public static int getDensityDpi(Context context) {
        int densityDpi = getDisPlayMetrics(context).densityDpi;
        return densityDpi;
    }

    /**
     * @方法说明:半角转换为全� * @方法名称:ToDBC
     * @param input
     * @return
     * @返回�String
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * @方法说明:去除特殊字符或将所有中文标号替换为英文标号
     * @方法名称:stringFilter
     * @param str
     * @return
     * @返回值:String
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}

