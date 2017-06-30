package com.zoptal.chinamoonbuffet.JsonClasses;

import java.io.Serializable;

/**
 * Created by zotal.102 on 09/12/16.
 */
public class Model_MyCart implements Serializable {

    private String id;
    private String title;
    private String category;
    private String categoryname;
    private String price;
    private String qty;
    private  String total_amt;
    private String image;
    private String posted;
    private String product_id;
    private String  total_qty;
    private String t_amt;
    private String uniq;

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

    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price=price;
    }
    public String getPosted() {

        return posted;
    }

    public void setPosted(String posted) {

        this.posted=posted;
    }

    public String getQty() {

        return qty;
    }

    public void setQty(String qty) {

        this.qty=qty;
    }

    public String getTotal_amt() {

        return total_amt;
    }

    public void setTotal_amt(String total_amt) {

        this.total_amt=total_amt;
    }

    public String getProduct_id() {

        return product_id;
    }

    public void setProduct_id(String product_id) {

        this.product_id=product_id;
    }

    public String getTotal_qty() {

        return total_qty;
    }

    public void setTotal_qty(String total_qty) {

        this.total_qty=total_qty;
    }

    public String getT_amt() {

        return t_amt;
    }

    public void setT_amt(String t_amt) {

        this.t_amt=t_amt;
    }

    public String getUniq() {

        return uniq;
    }

    public void setUniq(String uniq) {

        this.uniq=uniq;
    }

}
