package com.cs360.ericagates.campsitelocator;

public class User {
    //instance variables
    private int var_id;
    private String var_username;
    private String var_password;

    //empty constructor
    public User(){
    }

    //constructor with all variables
    public User (int id, String username, String password){
        this.var_id = id;
        this.var_username = username;
        this.var_password = password;

    }

    //constructor without id
    public User (String username, String password){
        this.var_username = username;
        this.var_password = password;
    }

    // setters (mutators)
    public void setID(int id) {
        this.var_id = id;
    }
    public void setUsername(String name) {
        this.var_username = name;
    }
    public void setPassword(String password) {
        this.var_password = password;
    }


    // getters (accessors)
    public int getID() {
        return var_id;
    }
    public String getUsername() {
        return var_username;
    }
    public String getPassword() {
        return var_password;
    }

}
