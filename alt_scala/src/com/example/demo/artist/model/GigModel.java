package com.example.demo.artist.model;


public class GigModel
{
    private int gigId;
    private String artistName;
    private String gigDescription;
    private boolean artistType;

    private double gigPrice;
    private boolean gigStatus;

    private String gigDate;
    private String gigStart;

    private String gigEnd;

    public GigModel()
    {

    }

    public GigModel(int gigId, String artistName, String gigDescription, boolean artistType, Double gigPrice, boolean gigStatus, String gigDate, String gigStart, String gigEnd) {

        this.gigId = gigId;
        this.artistName = artistName;
        this.gigDescription = gigDescription;
        this.artistType = artistType;
        this.gigPrice = gigPrice;
        this.gigStatus = gigStatus;
        this.gigDate = gigDate;
        this.gigStart = gigStart;
        this.gigEnd = gigEnd;
    }

    public GigModel(String artistName, String gigDescription, boolean artistType, double price, boolean status, String gigDate, String gigStart, String gigEnd) {
    }

    public int getGigId() {
        return gigId;
    }

    public void setGigId(int gigId) {
        this.gigId = gigId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGigDescription() {
        return gigDescription;
    }

    public void setGigDescription(String gigDescription) {
        this.gigDescription = gigDescription;
    }

    public boolean isArtistType() {
        return artistType;
    }

    public void setArtistType(boolean artistType) {
        this.artistType = artistType;
    }

    public Double getGigPrice() {
        return gigPrice;
    }

    public void setGigPrice(Double gigPrice) {
        this.gigPrice = gigPrice;
    }

    public boolean isGigStatus() {
        return gigStatus;
    }

    public void setGigStatus(boolean gigStatus) {
        this.gigStatus = gigStatus;
    }

    public String getGigDate() {
        return gigDate;
    }

    public void setGigDate(String gigDate) {
        this.gigDate = gigDate;
    }

    public String getGigStart() {
        return gigStart;
    }

    public void setGigStart(String gigStart) {
        this.gigStart = gigStart;
    }

    public String getGigEnd() {
        return gigEnd;
    }

    public void setGigEnd(String gigEnd) {
        this.gigEnd = gigEnd;
    }

    @Override
    public String toString() {
        return "GigModel{" +
                "gigId=" + gigId +
                ", artistName='" + artistName + '\'' +
                ", gigDescription='" + gigDescription + '\'' +
                ", artistType=" + artistType +
                ", gigPrice=" + gigPrice +
                ", gigStatus=" + gigStatus +
                ", gigDate='" + gigDate + '\'' +
                ", gigStart='" + gigStart + '\'' +
                ", gigEnd='" + gigEnd + '\'' +
                '}';
    }
}
