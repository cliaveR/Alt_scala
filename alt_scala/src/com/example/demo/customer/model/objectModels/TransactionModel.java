package com.example.demo.customer.model.objectModels;

public class TransactionModel
{

    private int transactionId;

    private int userId;

    public String paymentMethod;

    private String email;

    private int ticketAmount;

    private double transactionAmount;
    private String discountCode;

    public TransactionModel(int userId, String paymentMethod, String email, int ticketAmount, double transactionAmount, String discountCode) {
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.email = email;
        this.ticketAmount = ticketAmount;
        this.transactionAmount = transactionAmount;
        this.discountCode = discountCode;
    }

    public TransactionModel(int transactionId, int userId, String paymentMethod, String email, int ticketAmount, double transactionAmount, String discountCode) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.email = email;
        this.ticketAmount = ticketAmount;
        this.transactionAmount = transactionAmount;
        this.discountCode = discountCode;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
