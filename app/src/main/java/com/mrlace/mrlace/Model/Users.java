package com.mrlace.mrlace.Model;

public class Users {

private String password, phoneNumber, userName;

public Users() {

    }

    public Users(String password, String phoneNumber, String userName) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
