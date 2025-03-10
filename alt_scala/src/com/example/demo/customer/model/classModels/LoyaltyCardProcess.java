package com.example.demo.customer.model.classModels;


import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.sharedObjects.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class LoyaltyCardProcess
{
    private static Connection connection = DbConnect.getConnection();



    public static int countForDiscountCode(AccountModel accountModel)
    {
        String sql = "SELECT userId, COUNT(watchStatus) AS watchCount FROM gigcatalog WHERE userId = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
              int userId = AccountProcess.findAccountId(accountModel);

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int watchCount = resultSet.getInt("watchCount");
                return watchCount;
            }else {
                throw new RuntimeException("you have not watch any gig");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createDiscountCode(AccountModel accountModel)
    {

        String sql = "INSERT INTO loyaltyCard(discountID, discountCode, userId) VALUES(?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int userId = AccountProcess.findAccountId(accountModel);
            preparedStatement.setInt(1, generateUniqueGigCatalogId());
            preparedStatement.setString(2, discountCodeGenerator());
            preparedStatement.setInt(3, userId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
               return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String retrieveDiscountCode(AccountModel accountModel)
    {
        String sql = "SELECT * FROM loyaltyCard WHERE userId = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int userId = AccountProcess.findAccountId(accountModel);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getString("discountCode");
            } else {
                throw new RuntimeException("No discount retrieveCode");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUserDiscountCode(int userId, String discountCode)
    {
        String sql = "SELECT userId, discountCode FROM loyaltyCard WHERE userId = ? AND discountCode = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,discountCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String discountCodeGenerator()
    {
        int length = 7;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    public static int generateUniqueGigCatalogId() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateLoyaltyCardID(); // Assuming you have a method to generate gig IDs
            idExists = findIfLoyaltyExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }
    private static int generateLoyaltyCardID()
    {
        int min = 100000; int max = 999999;

        return (int)(Math.random()*(max - min+1) +min);
    }

    public static boolean findIfLoyaltyExists(int loyaltyCardId) {
        String sqlQuery = "SELECT * FROM loyaltyCard WHERE discountID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, loyaltyCardId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
