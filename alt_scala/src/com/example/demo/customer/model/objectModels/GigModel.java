package com.example.demo.customer.model.objectModels;

import java.sql.Date;
import java.sql.Time;

public class GigModel
{
    private int gigId;
    private String artistName;
    private String gigDescription;
    private boolean artistType;

    private Double gigPrice;
    private boolean gigStatus;

    private Date gigDate;
    private Time gigStart;

    private Time gigEnd;

    public GigModel(int gigId, String artistName, String gigDescription, boolean artistType, Double gigPrice, boolean gigStatus, Date gigDate, Time gigStart, Time gigEnd) {
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

    public GigModel()
    {

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

    public Date getGigDate() {
        return gigDate;
    }

    public void setGigDate(Date gigDate) {
        this.gigDate = gigDate;
    }

    public Time getGigStart() {
        return gigStart;
    }

    public void setGigStart(Time gigStart) {
        this.gigStart = gigStart;
    }

    public Time getGigEnd() {
        return gigEnd;
    }

    public void setGigEnd(Time gigEnd) {
        this.gigEnd = gigEnd;
    }
}
