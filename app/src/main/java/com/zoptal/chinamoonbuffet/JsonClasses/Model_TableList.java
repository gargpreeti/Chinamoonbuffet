package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;


public class Model_TableList implements Serializable {

    private String id;
    private String title;
    private String image;
    private String description;
    private String booked;



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

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description=description;
    }

    public String getBooked() {

        return booked;
    }

    public void setBooked(String booked) {

        this.booked=booked;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image=image;
    }


}
