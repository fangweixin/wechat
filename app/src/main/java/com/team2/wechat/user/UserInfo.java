package com.team2.wechat.user;

import android.os.Environment;

/**
 * Created by wjsay on 2017/11/27.
 */

public class UserInfo {
    private String id;
    private String passwd;
    private String name;
    private String gender;
    private String telephone;
    private String address;
    private String birthday;
    private String headUrl;
    private String problem;//用于找回密码的问题
    private String ip;
    private String weixinhao;
    public static String PictruesProfile = "http://139.199.38.177/php/PicturesProfile/";
    public static String userprofile = "userprofile";
    public static String LocalPritruesProfile = Environment.getExternalStorageDirectory() + "/team02weixin/PicturesProfile/";

    //Alter + Enter 或者右键Generate code 生成下面的代码
    public String getIp() {
        return ip;
    }

    public String getWeixinhao() {
        return weixinhao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setWeixinhao(String weixinhao) {
        this.weixinhao = weixinhao;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getGender() {
        return gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public String getProblem() {
        return problem;
    }

}
