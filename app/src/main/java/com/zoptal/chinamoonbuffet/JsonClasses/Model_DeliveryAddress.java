package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_DeliveryAddress implements Serializable {


    private String email;
    private String name;
    private String address;
    private String zipcode;
    private String phone;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;
    }
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode=zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }
}
