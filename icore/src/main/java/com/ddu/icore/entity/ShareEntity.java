package com.ddu.icore.entity;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class ShareEntity {

    private String name;
    private int resId;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
