package com.zoptal.chinamoonbuffet.JsonClasses;

import java.util.ArrayList;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_EarnCoupon {

    private ArrayList<Earncouponcode> al_earncoupon = new ArrayList<Earncouponcode>();


    public ArrayList<Earncouponcode> getAl_earncoupon() {
        return al_earncoupon;
    }

    public void setAl_coupondata(ArrayList<Earncouponcode> al_earncoupon) {
        this.al_earncoupon= al_earncoupon;
    }

}
