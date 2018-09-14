package com.example.lancer.MovieMusic.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.lancer.MovieMusic.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lancer on 2018/6/28.
 */

public class VideoUtils {
    public List<VideoBean> getVideoList(Context context) {
        List<VideoBean> lists = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            ToastUtils.showShort(context,"error");
        }else {
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    VideoBean videoBean = new VideoBean();
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                    long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                    videoBean.setDuration(duration);
                    videoBean.setSize(size);
                    videoBean.setPath(path);
                    videoBean.setTitle(title);

                    lists.add(videoBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return lists;
    }
}
