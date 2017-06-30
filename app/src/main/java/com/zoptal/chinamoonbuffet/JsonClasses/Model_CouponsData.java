package com.zoptal.chinamoonbuffet.JsonClasses;

import java.util.ArrayList;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_CouponsData {

    private ArrayList<CouponData> al_coupondata = new ArrayList<CouponData>();


    public ArrayList<CouponData> getAl_coupondata() {
        return al_coupondata;
    }

    public void setAl_coupondata(ArrayList<CouponData> al_coupondata) {
        this.al_coupondata= al_coupondata;
    }

}
