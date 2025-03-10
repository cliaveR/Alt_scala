package com.example.demo.artist.model;

public class ShopModel
{
    private int itemId;
    private String itemName;
    private String description;
    private int quantity;
    private int redeemValue;
    public ShopModel(int itemId, String itemName, String description, int quantity, int redeemValue){
        this.itemId=itemId;
        this.itemName=itemName;
        this.description=description;
        this.quantity=quantity;
        this.redeemValue=redeemValue;
    }

    public ShopModel() {

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedeemValue() {
        return redeemValue;
    }

    public void setRedeemValue(int redeemValue) {
        this.redeemValue = redeemValue;
    }


}
