package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;


public class RestaurentContact implements Serializable {

    private String phone;
    private String email;
    private String address;
    private String latitude;
    private String longitude;
    private String facebook;
    private String yelp;
    private String yellowpages;

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone =phone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email =email;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address=address;
    }

    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {

        this.latitude=latitude;
    }

    public String getLongitude() {

        return longitude;
    }

    public void setLongitude(String longitude) {

        this.longitude=longitude;
    }
    public String getFacebook() {

        return facebook;
    }

    public void setFacebook(String facebook) {

        this.facebook=facebook;
    }
    public String getYelp() {

        return yelp;
    }

    public void setYelp(String yelp) {

        this.yelp=yelp;
    }

    public String getYellowpages() {

        return yellowpages;
    }

    public void setYellowpages(String yellowpages) {

        this.yellowpages=yellowpages;
    }




}
