package com.team2.wechat.listener;

/**
 * Created by wjsay on 2017/12/23.
 */

public class MyMenuItem {
    private String id;
    private int icon;
    private String text;

    public MyMenuItem() {}

    public MyMenuItem(String text) {
        this.text = text;
    }

    public MyMenuItem(int iconId, String text) {
        this.icon = iconId;
        this.text = text;
    }

    public MyMenuItem(String id, int iconId, String text) {
        this.id = id;
        this.icon = iconId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;

    }
    public void setIcon(int iconId) {
        this.icon = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

