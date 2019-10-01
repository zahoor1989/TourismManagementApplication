package com.example.zahoor.model;

public class Ticket {
    private int id;
    private String name;
    private String desc;
    private String price;
    private String tDate;
    private String validity;

    //default and parameterised
    public Ticket(){}
    public Ticket(int id, String name, String desc, String price, String tDate, String validity) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.tDate = tDate;
        this.validity = validity;
    }
    //getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
