package com.team2.wechat.contactlist;

import android.net.Uri;
import android.view.View;

/**
 * Created by wjsay on 2017/12/29.
 */

public class AddressItem {
    private int titleVisible;
    private String titleName;
    private Uri imageUri;
    private String name;
    private String userid;

    public AddressItem(int titleVisible, String tilteName, Uri imageUri, String name) {
        this.titleVisible = titleVisible;
        this.titleName = tilteName;
        this.imageUri = imageUri;
        this.name = name;
    }
    public AddressItem(int titleVisible, String tilteName, Uri imageUri, String name, String id) {
        this.titleVisible = titleVisible;
        this.titleName = tilteName;
        this.imageUri = imageUri;
        this.name = name;
        this.userid = id;
    }

    public AddressItem() {}

    public int getTitleVisible() {
        return titleVisible;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getUserid() {
        return userid;
    }

    public void setTitleVisible(int titleVisible) {
        this.titleVisible = titleVisible;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
