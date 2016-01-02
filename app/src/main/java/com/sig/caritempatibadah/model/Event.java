package com.sig.caritempatibadah.model;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */
public class Event {
    private int id;
    private String name;
    private String place;
    private String address;
    private String description;
    private String time;

    public Event(){}

    public Event(String name, String address, String place, String description, String time) {
        this.address = address;
        this.name = name;
        this.place = place;
        this.description = description;
        this.time = time;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
