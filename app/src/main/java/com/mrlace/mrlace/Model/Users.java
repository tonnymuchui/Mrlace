package com.mrlace.mrlace.Model;

public class Users {

private String password, phoneNumber, userName, image, address;

public Users() {

    }

    public Users(String password, String phoneNumber, String userName, String image, String address) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.image = image;
        this.address = address;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
