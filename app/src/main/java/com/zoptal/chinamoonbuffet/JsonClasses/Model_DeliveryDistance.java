package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;


public class Model_DeliveryDistance implements Serializable {

    private String image;
    private String name;
    private String email;
    private String address;
    private String zip_code;
    private String phone;
    private String distance;
    private String delivery_fee;
    private String userlat;
    private String userlong;
    private String hotel_latitude;
    private String hotel_longitude;


    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image=image;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name=name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email=email;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address=address;
    }

    public String getZip_code() {

        return zip_code;
    }

    public void setZip_code(String zip_code) {

        this.zip_code=zip_code;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone=phone;
    }
    public String getDistance() {

        return distance;
    }

    public void setDistance(String distance) {

        this.distance=distance;
    }
    public String getDelivery_fee() {

        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {

        this.delivery_fee=delivery_fee;
    }
    public String getUserlat() {

        return userlat;
    }

    public void setUserlat(String userlat) {

        this.userlat=userlat;
    }
    public String getUserlong() {

        return userlong;
    }

    public void setUserlong(String userlong) {

        this.userlong=userlong;
    }
    public String getHotel_latitude() {

        return hotel_latitude;
    }

    public void setHotel_latitude(String hotel_latitude) {

        this.hotel_latitude=hotel_latitude;
    }

    public String getHotel_longitude() {

        return hotel_longitude;
    }

    public void setHotel_longitude(String hotel_longitude) {

        this.hotel_longitude=hotel_longitude;
    }
}
