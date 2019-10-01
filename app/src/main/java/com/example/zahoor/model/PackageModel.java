package com.example.zahoor.model;

public class PackageModel {
    private int id;
    private String pName;
    private String pDetail;
    private String image;
    private int pPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return pName;
    }

    public void setName(String name) {
        this.pName = name;
    }

    public String getDesc() {
        return pDetail;
    }

    public void setDesc(String detail) {
        this.pDetail = detail;
    }

    public String getImage(){
        return image;
    }
    public void setImage(String img){
        this.image = img;
    }
    public void setPrice(int price){
        this.pPrice =price;
    }

    public int getPrice() {
        return pPrice;
    }
    //constructors
    public PackageModel(){

    }
    public PackageModel(int id,String pName,String pDetail,String image, int pPrice){
        this.id=id;
        this.pName=pName;
        this.pDetail=pDetail;
        this.image = image;
        this.pPrice=pPrice;
    }

}
