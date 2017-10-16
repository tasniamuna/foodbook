package com.example.shabab.foodbook.Databse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shabab on 8/8/17.
 */

public class MENU implements  Serializable{
    String items;
    String resname;
    String offer;
    String menuname;
    String category;
    double rating;
    int user;
    int price;
    public MENU() {

        items="";
        resname="";
        offer="";
        menuname="";
        category="";
        user = 0 ;
        rating = 0;
        price=0;
    }
    public MENU(String items, String resname, String offer, String menuname, int price) {
        this.items = items;
        this.resname = resname;
        this.offer = offer;
        this.menuname = menuname;
        this.price = price;
        user = 0 ;
        rating = 0;
    }
    public MENU(String items, String resname, String offer, String menuname, int price,String category) {
        this.items = items;
        this.resname = resname;
        this.offer = offer;
        this.menuname = menuname;
        this.price = price;
        this.category = category;
        user = 0 ;
        rating = 0;
    }

    public String getItems() {
        return items;
    }

    public String getMenuname() {
        return menuname;
    }

    public String getOffer() {
        return offer;
    }

    public int getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getResname() {
        return resname;
    }

    public int getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }
}
