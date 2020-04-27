package com.example.logicapplication;

public class LoginDetails {
    public String UserName;
    public String PassWord;


    public LoginDetails(String UserName,String PassWord){
        this.UserName= UserName;
        this.PassWord= PassWord;
    }

    @Override
    public String toString() {
        return this.UserName+ this.PassWord;
    }
}
