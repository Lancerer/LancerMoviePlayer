package com.example.lancer.notepad.bean;

import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by Lancer on 2018/6/28.
 */

public class VideoBean implements Serializable {
    private int id;
    private String album;
    private String title;
    private String path;
    private long size;
    private long duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", album='" + album + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                '}';
    }
}
