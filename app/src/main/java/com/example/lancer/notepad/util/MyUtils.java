package com.example.lancer.notepad.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lancer on 2018/6/28.
 */

public class MyUtils {
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /**
     * @param size(SD卡中电影的大小)
     * @return
     * 格式化电影大小方法
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
     * @return
     * 格式化时间方法
     */
    public static String formatTime(long time){
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

}
