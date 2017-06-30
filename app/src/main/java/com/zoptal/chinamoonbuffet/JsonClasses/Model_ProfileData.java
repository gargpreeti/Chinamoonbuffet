package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_ProfileData implements Serializable {

    private String id;
    private String access_token;
    private String email;
    private String name;
    private String image;
    private String address;
    private String zipcode;
    private String phone;



    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getAccess_token() {

        return access_token;
    }

    public void setAccess_token(String access_token) {

        this.access_token =access_token;
    }
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image= image;
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
