package com.example.custom.model;

/**
 * Created by Administrator on 2018/1/31.
 */

public class User {
    private int portrait;
    private String name;
    private String desc;

    public User(int portrait, String name, String desc) {
        this.portrait = portrait;
        this.name = name;
        this.desc = desc;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
