package com.example.demo.customer.model.objectModels;

public class LoyaltyCardModel
{
    private int discountId;

    private int gigCatalogIdId;

    private String discountCode;

    public LoyaltyCardModel() {
    }

    public LoyaltyCardModel(int discountId, int gigCatalogIdId, String discountCode) {
        this.discountId = discountId;
        this.gigCatalogIdId = gigCatalogIdId;
        this.discountCode = discountCode;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getGigCatalogIdId() {
        return gigCatalogIdId;
    }

    public void setGigCatalogIdId(int gigCatalogIdId) {
        this.gigCatalogIdId = gigCatalogIdId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
