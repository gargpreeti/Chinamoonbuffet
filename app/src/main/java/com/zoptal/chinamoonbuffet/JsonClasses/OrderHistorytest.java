package com.zoptal.chinamoonbuffet.JsonClasses;


public class OrderHistorytest {

    private String product_id;
    private String product_name;
    private String category_id;
    private String category_name;
    private String price;
    private String qty;
    private String total_amt;
    private String status;
    private String time;
    private String order_type;
    private String product_image;


    public String getProduct_id() {

        return product_id;
    }

    public void setProduct_id(String product_id) {

        this.product_id=product_id;
    }

    public String getProduct_name() {

        return product_name;
    }

    public void setProduct_name(String product_name) {

        this.product_name=product_name;
    }
     public String getCategory_id() {

        return category_id;
    }

    public void setCategory_id(String category_id) {

        this.category_id =category_id;
    }

    public String getCategory_name() {

        return category_name;
    }

    public void setCategory_name(String category_name) {

        this.category_name=category_name;
    }


    public String getPrice() {

        return price;
    }

    public void setPrice(String price) {

        this.price=price;
    }
    public String getQty() {

        return qty;
    }

    public void setQty(String qty) {

        this.qty=qty;
    }
    public  String getTotal_amt(){
        return total_amt;
    }
    public void setTotal_amt(String total_amt) {

        this.total_amt=total_amt;
    }

    public  String getStatus(){
        return status;
    }
    public void setStatus(String status) {

        this.status=status;
    }
    public  String getOrder_type(){
        return order_type;
    }
    public void setOrder_type(String order_type) {

        this.order_type=order_type;
    }
    public  String getTime(){
        return time;
    }
    public void setTime(String time) {

        this.time=time;
    }

    public String getProduct_image() {

        return product_image;
    }

    public void setProduct_image(String product_image) {

        this.product_image=product_image;
    }
}
