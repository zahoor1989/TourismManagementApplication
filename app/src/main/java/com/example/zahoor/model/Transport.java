package com.example.zahoor.model;

public class Transport {
    private int id;
    private int product_id;
    private String type;
    private String vehicleName;
    private String pickup;
    private String drop;
    private int price;

    //default constructor
    public Transport(){ }

    //parameterised constructor
    public Transport(int id, int pid, String type, String vName, String pickup, String drop, int price){
        this.id = id;
        this.product_id = pid;
        this.type = type;
        this.vehicleName = vName;
        this.pickup = pickup;
        this.drop = drop;
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
