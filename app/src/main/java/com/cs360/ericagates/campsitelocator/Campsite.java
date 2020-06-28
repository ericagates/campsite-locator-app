package com.cs360.ericagates.campsitelocator;

public class Campsite {
    //instance variables
    private int var_id;
    private String var_name;
    private String var_address;
    private String var_city_state;
    private String var_zip;
    private String var_feature;
    private String var_phone;
    private String var_details;

    //empty constructor
    public Campsite(){
    }

    //constructor with all variables
    public Campsite (int id,String name, String address, String city_state, String zip, String feature, String phone, String details){
        this.var_id = id;
        this.var_name = name;
        this.var_address = address;
        this.var_city_state = city_state;
        this.var_zip = zip;
        this.var_feature = feature;
        this.var_phone = phone;
        this.var_details = details;
    }

    //constructor without id
    public Campsite (String name, String address, String city_state, String zip, String feature, String phone, String details){
        this.var_name = name;
        this.var_address = address;
        this.var_city_state = city_state;
        this.var_zip = zip;
        this.var_phone = phone;
        this.var_feature = feature;
        this.var_details = details;
    }

    //constructor with name only
    public Campsite (String name){
        this.var_name = name;
    }


    // setters (mutators)
    public void setID(int id) {
        this.var_id = id;
    }
    public void setName(String name) {
        this.var_name = name;
    }
    public void setAddress(String address) {
        this.var_address = address;
    }
    public void setCityState(String city_state) {
        this.var_city_state = city_state;
    }
    public void setZip(String zip) {
        this.var_zip = zip;
    }
    public void setFeature(String feature) {
        this.var_feature = feature;
    }
    public void setPhone(String phone) {
        this.var_phone = phone;
    }
    public void setDetails(String details) {
        this.var_details = details;
    }

    // getters (accessors)
    public int getID() {
        return this.var_id;
    }
    public String getName() {
        return this.var_name;
    }
    public String getAddress() {
        return this.var_address;
    }
    public String getCityState() {
        return this.var_city_state;
    }
    public String getZip() {
        return this.var_zip;
    }
    public String getFeature() { return this.var_feature;}
    public String getPhone() {
        return this.var_phone ;
    }
    public String getDetails() {
        return this.var_details;
    }

}
