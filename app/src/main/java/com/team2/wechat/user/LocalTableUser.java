package com.team2.wechat.user;

/**
 * Created by wjsay on 2017/12/28.
 */

public class LocalTableUser {
    private String id,
            name,
            password,
            gender,
            telephone,
            address,
            birthday,
            headUrl,
            problem,
            ip,
            weixinhao;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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

    public String getIp() {
        return ip;
    }

    public String getWeixinhao() {
        return weixinhao;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
