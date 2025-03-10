package com.example.demo.artist.model;

public class AccountModel {

private int userID;
private int userType;
private String password;
private String username;
private String emailAddress;
private int loyaltyProgress;
private int shopPoints;

    public AccountModel()
    {

    }
    public AccountModel(int userId, int userType, String password, String username, String emailAddress, int loyaltyProgress, int shopPoints){
        this.userID = userId;
        this.userType = userType;
        this.password = password;
        this.username = username;
        this.emailAddress = emailAddress;
        this.loyaltyProgress = loyaltyProgress;
        this.shopPoints = shopPoints;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getLoyaltyProgress() {
        return loyaltyProgress;
    }

    public void setLoyaltyProgress(int loyaltyProgress) {
        this.loyaltyProgress = loyaltyProgress;
    }


    @Override
    public String toString() {
        return "AccountModel{" +
                "userID=" + userID +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", loyaltyProgress=" + loyaltyProgress +
                ", shopPoints=" + shopPoints +
                '}';
    }

    public int getShopPoints() {
        return shopPoints;
    }

    public void setShopPoints(int shopPoints) {
        this.shopPoints = shopPoints;
    }
}




