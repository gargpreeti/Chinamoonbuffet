package com.zoptal.chinamoonbuffet.JsonClasses;

/**
 * Created by zotal.102 on 09/12/16.
 */
public class Earncouponcode {

    private String id;
    private String user_id;
    private String title;
    private String discount;
    private String code;
    private String description;
    private String status;
    private String earn_on;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getUser_id() {

        return user_id;
    }

    public void setUser_id(String user_id) {

        this.user_id =user_id;
    }




    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title =title;
    }

    public String getDiscount() {

        return discount;
    }

    public void setDiscount(String discount) {

        this.discount=discount;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code=code;
    }


    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description=description;
    }
    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status=status;
    }
    public  String getEarn_on(){
        return earn_on;
    }
    public void setEarn_on(String earn_on) {

        this.earn_on=earn_on;
    }
}
