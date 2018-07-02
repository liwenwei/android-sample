package com.liwenwei.myrxjava2;

/**
 * Created by v-wenwli on 6/25/2018.
 */

public class VIP {
    private int userId;
    private Boolean vip;

    public VIP(int userId, Boolean vip) {
        this.userId = userId;
        this.vip = vip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
