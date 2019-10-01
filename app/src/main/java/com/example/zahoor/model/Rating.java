package com.example.zahoor.model;

public class Rating {
    private int id;
    private int customer_id;
    private int rating;
    private int product_id;
    private String createdAt;

    //created default constructor
    public Rating(){

    }
    //Parameterised constructor
    public Rating(int id, int cusId, int rat, int proId, String createdAt){
        this.id = id;
        this.customer_id = cusId;
        this.rating = rat;
        this.product_id = proId;
        this.createdAt = createdAt;
    }
    //getter and setter methods

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setCustomer_id(int cId){
        this.customer_id=cId;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setRat(int rat){
        this.rating = rat;
    }
    public int getRat(){
        return rating;
    }
    public void setProduct_id(int prodId){
        this.product_id = prodId;
    }
    public int getProduct_id(){
        return product_id;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return createdAt;
    }

}
