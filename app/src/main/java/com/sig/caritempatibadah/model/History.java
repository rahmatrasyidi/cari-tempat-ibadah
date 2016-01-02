package com.sig.caritempatibadah.model;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */
public class History {
    private int id;
    private String place;
    private String testimoni;
    private String address;

    public History(){}

    public History(String place, String address, String testimoni) {
        this.address = address;
        this.place = place;
        this.testimoni = testimoni;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTestimoni() {
        return testimoni;
    }

    public void setTestimoni(String testimoni) {
        this.testimoni = testimoni;
    }
}
