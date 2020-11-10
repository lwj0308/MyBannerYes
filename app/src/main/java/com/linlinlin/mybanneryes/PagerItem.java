package com.linlinlin.mybanneryes;

public class PagerItem {
    private String title;
    private int picResID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicResID() {
        return picResID;
    }

    public void setPicResID(int picResID) {
        this.picResID = picResID;
    }

    public PagerItem(String title, int picResID) {
        this.title = title;
        this.picResID = picResID;
    }
}

