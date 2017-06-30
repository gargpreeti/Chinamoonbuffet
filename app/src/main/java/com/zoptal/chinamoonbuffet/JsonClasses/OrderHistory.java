package com.zoptal.chinamoonbuffet.JsonClasses;


public class OrderHistory {

    private String order_id;
    private String txn_id;
    private String grand_total;
    private String order_type;
    private String order_status;
    private String time;


    public String getOrder_id() {

        return order_id;
    }

    public void setOrder_id(String order_id) {

        this.order_id = order_id;

    }

    public String getTxn_id() {

        return txn_id;
    }

    public void setTxn_id(String txn_id) {

        this.txn_id = txn_id;
    }

    public String getGrand_total() {

        return grand_total;
    }

    public void setGrand_total(String grand_total) {

        this.grand_total = grand_total;
    }

    public String getOrder_status() {

        return order_status;
    }

    public void setOrder_status(String order_status) {

        this.order_status = order_status;
    }


    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {

        this.order_type = order_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

}
