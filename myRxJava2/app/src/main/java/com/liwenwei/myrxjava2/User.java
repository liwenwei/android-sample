package com.liwenwei.myrxjava2;

/**
 * Created by v-wenwli on 6/25/2018.
 */

public class User {
    private int userId;
    private String userName;
    private int age;
    private Boolean vip;

    public User(int userId, String userName, int age, Boolean vip) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
        this.vip = vip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
