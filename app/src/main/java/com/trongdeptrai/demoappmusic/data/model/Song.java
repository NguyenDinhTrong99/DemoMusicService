package com.trongdeptrai.demoappmusic.data.model;

public class Song {
    private String mTitle;
    private int mResource;

    public Song(String title, int resource) {
        mTitle = title;
        mResource = resource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getResource() {
        return mResource;
    }

    public void setResource(int resource) {
        mResource = resource;
    }
}
