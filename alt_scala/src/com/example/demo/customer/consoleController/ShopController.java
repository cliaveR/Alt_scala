package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.classModels.AccountProcess;
import com.example.demo.customer.model.classModels.ShopProcess;
import com.example.demo.customer.model.objectModels.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShopController
{
    public ShopController(AccountModel accountModel) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sundayag Shop Page");
        System.out.println("Can only redeem one item");
        int shopPoints = AccountProcess.returnShopPoints(accountModel);

        ShopProcess.showItems();
        System.out.println("This is your shop Points: " + shopPoints);

        System.out.println("Please enter the itemId that you want to redeem");
        int itemId = Integer.parseInt(reader.readLine());

        int itemRedeemValue = ShopProcess.getRedeemValueItem(itemId);

        if(shopPoints >= itemRedeemValue){
            System.out.println("You have redeemed: ");
            ShopProcess.reduceQuantity(itemId);
            AccountProcess.reduceUserShopPoints(accountModel,itemRedeemValue);
            ShopProcess.createItemRecord(accountModel,itemRedeemValue,itemId);
        }else {
            System.out.println("You don't have enough points to redeem that item");
        }
        //TODO CREATE THE AUTO DECREASE of shop points





    }
}
