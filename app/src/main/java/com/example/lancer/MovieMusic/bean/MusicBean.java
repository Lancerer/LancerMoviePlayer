package com.example.lancer.MovieMusic.bean;

import java.io.Serializable;

/**
 * Created by Lancer on 2018/7/5.
 */

public class MusicBean implements Serializable {
    private int id;
    private String ablum;
    private String name;
    private String path;
    private long size;
    private long duration;
    private String artist;
    private int ablumid;

    public int getAblumid() {
        return ablumid;
    }

    public void setAblumid(int ablumid) {
        this.ablumid = ablumid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAblum() {
        return ablum;
    }

    public void setAblum(String ablum) {
        this.ablum = ablum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "id=" + id +
                ", ablum='" + ablum + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", artist='" + artist + '\'' +
                ", ablumid=" + ablumid +
                '}';
    }
}
