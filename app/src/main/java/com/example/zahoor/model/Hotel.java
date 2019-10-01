package com.example.zahoor.model;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String desc;
    private String service;
    private String pricePerNight;
    private String images;
    private String lati;
    private String longi;

    //default and parameterised constructor
    public Hotel(){}
    public Hotel(String name, String address, String phone, String desc, String service, String pricePerNight, String images) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.desc = desc;
        this.service = service;
        this.pricePerNight = pricePerNight;
        this.images = images;
    }
    //getters and setters method

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(String pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setLati(String lati){ this.lati = lati;}

    public String getLati(){return lati;}

    public void setLongi(String longi){ this.longi = longi;}

    public String getLongi(){ return longi; }
}
