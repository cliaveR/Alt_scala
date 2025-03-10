package com.example.demo.customer.model.classModels;

import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.sharedObjects.DbConnect;

import java.sql.*;

public class ShopProcess
{
    private static final Connection connection = DbConnect.getConnection();
    public static void main(String[] args) {
    showItems();
    }

    public static void showItems(){
        String sqlQuery = "select * from item where quantity > 1 ";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet =  statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                int itemId = resultSet.getInt("itemId");
            String itemName = resultSet.getString("itemName");
            String description = resultSet.getString("description");
            int quantity = resultSet.getInt("quantity");
            int redeemValue = resultSet.getInt("redeemValue");


                System.out.println("itemId: "+ itemId+
                        "\nitem: "+ itemName+
                        "\ndescription: "+ description+
                        "\nquantity: " + quantity+
                        "\nredeem points: " + redeemValue+ "\n---------------------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getQuantityItem(int itemId){
        String sqlQuery = "SELECT quantity FROM transaction WHERE itemId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.getInt("quantity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reduceQuantity(int itemId){
        try {
            String sql = "UPDATE item SET quantity = quantity - 1 Where itemId =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, itemId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("item quantity reduce");
                } else {
                    System.out.println("Failed to reduce quantity.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getRedeemValueItem(int itemId){
        String sqlQuery = "SELECT redeemValue FROM item WHERE itemId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt("redeemValue");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createItemRecord(AccountModel accountModel, int totalRedeemValue, int itemID){

        String sql = "INSERT INTO itemRecord (redeemDate, totalRedeemValue,userID,itemId) VALUES (NOW(),?,?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int userId = AccountProcess.findAccountId(accountModel);
            preparedStatement.setInt(1, totalRedeemValue);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, itemID);
            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Redeeming created successfully!");
            } else {
                System.out.println("Failed to create redeem.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
