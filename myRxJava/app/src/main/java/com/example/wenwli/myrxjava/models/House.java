package com.example.wenwli.myrxjava.models;

public class House {

    private double price;//单价 xxxx元/平米
    private double area;//面积
    private String communityName;//小区名
    private String desc;//房源描述

    public House(double price, double area, String communityName, String desc) {

        this.price = price;
        this.area = area;
        this.communityName = communityName;
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
