package com.PingShoping.bean;

public class UserInfo {
    private String user_id;
    private String utel;// 用户电话
    private String umail;// 用户邮箱
    private String pwd;//-- 用户密码

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUtel(String utel) {
        this.utel = utel;
    }

    public void setUmail(String umail) {
        this.umail = umail;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUtel() {
        return utel;
    }

    public String getUmail() {
        return umail;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPhoto() {
        return photo;
    }

    public Integer getStatus() {
        return status;
    }

    private String photo;//  -- 个人图像
    private Integer status;//

    public UserInfo() {
        super();
    }

    public UserInfo(String user_id, String utel, String umail, String pwd, String photo, Integer status) {
        this.user_id = user_id;
        this.utel = utel;
        this.umail = umail;
        this.pwd = pwd;
        this.photo = photo;
        this.status = status;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
