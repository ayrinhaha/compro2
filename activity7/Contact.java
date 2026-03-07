package com.phonebook.models;

public class Contact {
    
    // priv fieldzzz
    private String name;
    private String phoneNumber;
    private String email;


    //constructor
    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    //getter
    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    public String toCsvString(){
        return name + "," + phoneNumber + "," + email;
    }



    
}
