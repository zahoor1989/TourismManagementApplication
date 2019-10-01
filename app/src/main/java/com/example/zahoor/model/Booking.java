package com.example.zahoor.model;

public class Booking {
    private int id;
    private int product_id;
    private int customer_id;
    private int quantity;
    private int total;
    private String type;
    private String payStatus;
    private String createdAt;

    // default and parameterised constructor
    public Booking(){ }
    //parameterised constructor
    public Booking(int id, int product_id, int customer_id,int quantity, int total, String type,String payStatus, String createdAt){
        this.id= id;
        this.product_id = product_id;
        this.customer_id = customer_id;
        this.quantity = quantity;
        this.total = total;
        this.type = type;
        this.payStatus = payStatus;
        this.createdAt = createdAt;
    }
    //getter and setter methods
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setProduct_id(int id){
        this.product_id = id;
    }
    public int getProduct_id(){
        return product_id;
    }
    public void setCustomer_id(int cid){
        this.customer_id = cid;
    }
    public int getCustomer_id(){
        return customer_id;
    }
    public void setQuantity(int q){
        this.quantity = q;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return total;
    }
    public String getType(){ return type;}
    public void setType(String type){this.type = type; }
    public void setPayStatus(String status){
        this.payStatus = status;
    }
    public String getPayStatus(){
        return payStatus;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return createdAt;
    }

}
