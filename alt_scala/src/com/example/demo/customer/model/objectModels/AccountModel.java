package com.example.demo.customer.model.objectModels;

public class AccountModel
{
   private int userId;
    private boolean userType;
    private String passWord;

    private String userName;

    private String emailAddress;

    private int loyaltyProgress;

    public AccountModel() {
    }

    public AccountModel(String passWord, String userName, String emailAddress) {
        this.passWord = passWord;
        this.userName = userName;
        this.emailAddress = emailAddress;
    }

    public AccountModel(int userId, boolean userType, String passWord, String userName, String emailAddress, int loyaltyProgress) {
        this.userId = userId;
        this.userType = userType;
        this.passWord = passWord;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.loyaltyProgress = loyaltyProgress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "AccountModel{" +  "\n" +
                "UserID: " + userId + "\n" +
                "userType: " + userType + "\n" +
                "Password: " + passWord + "\n" +
                "userName: " + userName + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "Loyalty Progress: " + loyaltyProgress + "\n" +
                '}';
    }
    public String customerToString(){
        return "userName: " + userName + "\n" +
                "Email Address: " + emailAddress + "\n" +
                "Password: " + passWord;
    }
}
