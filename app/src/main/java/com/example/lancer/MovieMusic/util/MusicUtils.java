package com.example.lancer.MovieMusic.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.lancer.MovieMusic.R;
import com.example.lancer.MovieMusic.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lancer on 2018/7/5.
 */

public class MusicUtils {
    public List<MusicBean> getMusicList(Context context) {
        List<MusicBean> lists = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               MusicBean info = new MusicBean();
                info.setArtist(cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)));
                info.setName((cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE))));
                info.setDuration(cursor.getInt(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION)));
                info.setAblumid(cursor.getInt(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID)));
                info.setPath(cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA)));
                //  info.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
                lists.add(info);
            }
        }

        return lists;
    }

    /**
     * 获得音乐专辑图片
     * @param album_id  专辑id
     * @param context
     * @return  专辑图片
     */
    public static Bitmap getAlbumArt(int album_id, Context context) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
            bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.user);
        }
        return bm;
    }
}
