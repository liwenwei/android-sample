package com.liwenwei.sortrecyclerview;


import java.util.Date;

public class Country {

    String country;
    boolean pined = false;
    Date pinedTime = new Date();

    public Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isPined() {
        return pined;
    }

    public void setPined(boolean pined) {
        this.pined = pined;
    }

    public Date getPinedTime() {
        return pinedTime;
    }

    public void setPinedTime(Date pinedTime) {
        this.pinedTime = pinedTime;
    }
}
