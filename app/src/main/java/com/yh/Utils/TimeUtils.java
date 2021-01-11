package com.yh.Utils;


import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Huang Mingfei on 2017/10/24 0024.
 */

public class TimeUtils {

    public static String getLeftTime(long ms) {

        int ss = 1;
        int mi = ss * 60;
        int hh = mi * 60;

        int hour = (int) (ms / hh);
        int minute = (int) ((ms - hour * hh) / mi);
        int second = (int) ((ms - hour * hh - minute * mi) / ss);

        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        if (!"00".equals(strHour)) {
            return strHour + "时" + strMinute + "分" + strSecond + "秒";
        } else if (!"00".equals(strMinute) && "00".equals(strHour)) {
            return strMinute + "分" + strSecond + "秒";
        }
        return strSecond + "秒";
    }

    /**
     * 将毫秒转换为hh-mm-ss
     *
     * @param time
     * @return
     */
    //时间处理
    public static String getTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(time);
        return hms;
    }

    public static String conversionTime(String time) {

        try {
            long l = Long.parseLong(time);
            int minute = (int) (l / 60);
            int seconds = (int) (l % 60);
            return minute + "分" + seconds + "秒";
        }catch (Exception e){
            return 0 + "分" + 0 + "秒";
        }

    }
}
