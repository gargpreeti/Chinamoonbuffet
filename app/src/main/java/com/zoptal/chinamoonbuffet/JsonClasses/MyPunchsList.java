package com.zoptal.chinamoonbuffet.JsonClasses;


public class MyPunchsList {

    private String id;
    private String relation;
    private String title;
    private String discount;
    private String description;
    private String status;
    private String punchdate;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id =id;
    }

    public String getRelation() {

        return relation;
    }

    public void setRelation(String relation) {

        this.relation=relation;
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
    public  String getPunchdate(){
        return punchdate;
    }
    public void setPunchdate(String punchdate) {

        this.punchdate=punchdate;
    }
}
