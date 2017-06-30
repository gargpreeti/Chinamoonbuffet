package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;

/**
 * Created by zotal.102 on 09/12/16.
 */
public class MenuData implements Serializable {

    private String id;
    private String title;
    private String category;
    private String categoryname;
    private String image;
    private String description;
    private String price;
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

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category=category;
    }

    public String getCategoryname() {

        return categoryname;
    }

    public void setCategoryname(String categoryname) {

        this.categoryname=categoryname;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image=image;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description=description;
    }
    public String getPosted() {

        return posted;
    }

    public void setPosted(String posted) {

        this.posted=posted;
    }
    public  String getPrice(){
        return price;
    }
    public void setPrice(String price) {

        this.price=price;
    }
}
