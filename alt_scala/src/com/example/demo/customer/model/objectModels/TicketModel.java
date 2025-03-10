package com.example.demo.customer.model.objectModels;

public class    TicketModel
{
    private int ticketId;
    private int ticketNumber;
    private int gigId;
    private int ticketUsage;
    private int transactionId;

    private int userId;

    public TicketModel(int ticketId, int ticketNumber, int gigId, int ticketUsage, int transactionId, int userId) {
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.gigId = gigId;
        this.ticketUsage = ticketUsage;
        this.transactionId = transactionId;
        this.userId = userId;
    }

    public TicketModel() {
    }

    public TicketModel(int ticketId, int ticketNumber, int gigId, int ticketUsage, int transactionId) {
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.gigId = gigId;
        this.ticketUsage = ticketUsage;
        this.transactionId = transactionId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getGigId() {
        return gigId;
    }

    public int getTicketUsage() {
        return ticketUsage;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setGigId(int gigId) {
        this.gigId = gigId;
    }

    public void setTicketUsage(int ticketUsage) {
        this.ticketUsage = ticketUsage;
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

    @Override
    public String toString() {
        return "TicketModel{" +
                "ticketId=" + ticketId +
                ", ticketNumber=" + ticketNumber +
                ", gigId=" + gigId +
                ", ticketUsage=" + ticketUsage +
                ", transactionId=" + transactionId +
                ", userId=" + userId +
                '}';
    }


    public String toStringWithUserId() {
        return "TicketModel{" +
                "ticketId=" + ticketId +
                ", ticketNumber=" + ticketNumber +
                ", gigId=" + gigId +
                ", ticketUsage=" + ticketUsage +
                ", transactionId=" + transactionId +
                ", userId=" + userId +
                '}';
    }
}
