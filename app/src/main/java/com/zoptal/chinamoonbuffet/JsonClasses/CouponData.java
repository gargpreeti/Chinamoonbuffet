package com.zoptal.chinamoonbuffet.JsonClasses;

/**
 * Created by zotal.102 on 09/12/16.
 */
public class CouponData  {

    private String id;
    private String title;
    private String image;
    private String discount;
    private String code;
    private String description;
    private String status;
    private String posted;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title =title;
    }
    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image=image;
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
    public  String getPosted(){
        return posted;
    }
    public void setPosted(String posted) {

        this.posted=posted;
    }
}
