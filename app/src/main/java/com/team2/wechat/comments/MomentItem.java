package com.team2.wechat.comments;

import android.graphics.Bitmap;

import com.mysql.jdbc.StringUtils;

/**
 * Created by FEI on 2017/12/30.
 * 保存用户的头像，用户名，文章内容，文章配图
 */

public class MomentItem {
    private String userName;  //好友名
    private Bitmap userAvatar; //头像
    private String momentText; //内容
    private Bitmap momentImage; //配图
    private String momentTopName; //我的名字
    private Bitmap momentTopImage; //背景
    private Bitmap momentTopMe; //我的头像

    public MomentItem() {}

    MomentItem(String userName, String momentText, Bitmap userAvatar, Bitmap momentImage, Bitmap momentTopImage, Bitmap momentTopMe){
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.momentImage = momentImage;
        this.momentText = momentText;
        this.momentTopImage = momentTopImage;
        this.momentTopMe = momentTopMe;
    }

    public String getMomentTopName() {
        return momentTopName;
    }

    Bitmap getUserAvatar() {
        return userAvatar;
    }

    Bitmap getMomentImage() {
        return momentImage;
    }

    String getMomentText() {
        return momentText;
    }

    String getUserName() {
        return userName;
    }

    Bitmap getMomentTopImage() {
        return momentTopImage;
    }

    Bitmap getMomentTopMe() {
        return momentTopMe;
    }

    public void setMomentTopName(String momentTopName) {
        this.momentTopName = momentTopName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(Bitmap userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setMomentText(String momentText) {
        this.momentText = momentText;
    }

    public void setMomentImage(Bitmap momentImage) {
        this.momentImage = momentImage;
    }

    public void setMomentTopImage(Bitmap momentTopImage) {
        this.momentTopImage = momentTopImage;
    }

    public void setMomentTopMe(Bitmap momentTopMe) {
        this.momentTopMe = momentTopMe;
    }
}
