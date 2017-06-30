package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;

/**
 * Created by zotal.102 on 08/12/16.
 */
public class Model_Categories implements Serializable {

    private String id;
    private String title;



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

}
