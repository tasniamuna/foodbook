package com.example.shabab.foodbook.Databse;

/**
 * Created by shabab on 8/7/17.
 */

public class Rsturant {
     String name,location;
   // String location;
     String email;
     String description;
     String openhour,closehour;
    String contact;
    double envrrating,servrating;
     double users ;
    //private Menu menu;

    public Rsturant() {
    }

    public Rsturant(String name, String location,String email) {
        this.name = name;
        this.location = location;
        this.email=email;
    }

    public Rsturant(String name, String location, String email, String description, String openhour, String closehour,String contact) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.description = description;
        this.openhour = openhour;
        this.closehour = closehour;
        this.contact=contact;
        envrrating =0;
        servrating=0;
        users=0;
    }

    public double getServrating() {
        return servrating;
    }

    public String getOpenhour() {

        return openhour;
    }

    public String getLocation() {

        return location;
    }

    public double getEnvrrating() {

        return envrrating;
    }

    public String getDescription() {

        return description;
    }

    public String getClosehour() {
        return closehour;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}
