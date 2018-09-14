package com.example.lancer.MovieMusic.util;

import android.content.Context;
import android.net.TrafficStats;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lancer on 2018/6/28.
 */

public class MyUtils {
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static long lastTotalRxBytes = 0;
    public static long lastTimeStamp = 0;

    /**
     * @param size(SD卡中电影的大小)
     * @return 格式化电影大小方法
     */
    public static String formatSize(long size) {
        if (size / (1024 * 1024) > 1024) {
            return (decimalFormat.format(size / 1024.0 / 1024.0 / 1024.0)) + " GB";
        } else if (size / 1024 > 1024)
            return (decimalFormat.format(size / 1024.0 / 1024.0)) + " MB";
        else if (size > 1024)
            return (decimalFormat.format(size / 1024.0)) + " KB";
        else
            return size + " B";
    }

    /**
     * @param time
     * @return 格式化时间方法
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 时间格式化
     *
     * @param oldTime
     * @return
     */
    public static String parseTime(int oldTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");// 时间格式
        String newTime = sdf.format(new Date(oldTime));
        return newTime;
    }

    /**
     * 判断传入的uri是本地视频还是网络视频
     *
     * @param uri 传入的视频播放地址
     * @return
     */
    public static boolean isNetUri(String uri) {
        boolean result = false;
        if (uri.toLowerCase().startsWith("http") || uri.toLowerCase().startsWith("rtp") || uri.toLowerCase().startsWith("mms")) {
            result = true;
        }
        return result;
    }

    /**
     * 获得手机当前的网速
     *
     * @param context
     * @return 返回的是网速
     */
    public static String getNetSpeed(Context context) {

        long nowTotalRxBytes =
                TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        String speedStr = String.valueOf(speed) + " kb / s ";
        return speedStr;

    }


}
