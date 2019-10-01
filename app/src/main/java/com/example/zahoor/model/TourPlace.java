package com.example.zahoor.model;
import com.example.zahoor.sql.TouristPlaceDatabase;

import java.util.List;

public class TourPlace {
    private int id;
    private String name;
    private String add;
    private String desc;
    private int price;
    private String image;
    private String video;
    private String lati;
    private String longi;

    public TourPlace(){}

    public TourPlace(int id,String name, String add, String desc, int price, String image, String video,String lati, String longi ){
        this.id = id;
        this.name = name;
        this.add= add;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.video = video;
        this.lati = lati;
        this.longi = longi;

    }
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getVideo(){
        return video;
    }
    public void setVideo(String video){
        this.video = video;
    }
    public String getLati(){
        return lati;
    }
    public void setLati(String lati){
        this.lati = lati;
    }
    public String getLongi(){
        return longi;
    }
    public void setLongi(String longi){
        this.longi = longi;
    }


}

