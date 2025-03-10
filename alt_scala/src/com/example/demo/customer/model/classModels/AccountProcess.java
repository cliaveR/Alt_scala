package com.example.demo.customer.model.classModels;

import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.sharedObjects.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountProcess
{
    private static Connection connection = DbConnect.getConnection();

    public static boolean isDuplicateAccount(String username, String email) {
        String sql = "SELECT COUNT(*) AS count FROM user WHERE username = ? OR emailAddress = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Returns true if a duplicate account is found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // Return false if no duplicate account is found
    }
    public static boolean accountCreation(AccountModel accountModel) throws SQLException {
        String sql = "INSERT INTO user (userId,username, emailAddress, password, userType,loyaltyProgress) VALUES (?,?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountModel.getUserId());
            preparedStatement.setString(2, accountModel.getUserName());
            preparedStatement.setString(3, accountModel.getEmailAddress());
            preparedStatement.setString(4, accountModel.getPassWord());
            preparedStatement.setBoolean(5, accountModel.isUserType());
            preparedStatement.setInt(6, 0);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean loginAccount(AccountModel accountModel)
    {
        boolean accountFound = true;

        String sql = "SELECT * FROM user WHERE username = ? AND password = ? AND emailAddress = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            preparedStatement.setString(1, accountModel.getUserName());
            preparedStatement.setString(2, accountModel.getPassWord());
            preparedStatement.setString(3, accountModel.getEmailAddress());
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next())
            {
                String username = resultSet.getString("username");
                String email = resultSet.getString("emailAddress");
                String password = resultSet.getString("password");

                if(accountModel.getEmailAddress().equalsIgnoreCase(email)&& accountModel.getUserName().equalsIgnoreCase(username) && accountModel.getPassWord().equalsIgnoreCase(password))
                {
                    return accountFound;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static int generateUniqueUserId() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateUserId(); // Assuming you have a method to generate gig IDs
            idExists = findIfAccountIdExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }
    public static int generateUserId()
    {
        int min = 1000; int max = 9999;

        return (int)(Math.random()*(max - min+1) +min);
    }
    public static boolean findIfAccountIdExists(int accountId) {
        String sqlQuery = "SELECT * FROM user WHERE userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int findAccountId(AccountModel accountModel)
    {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accountModel.getUserName());
            preparedStatement.setString(2, accountModel.getPassWord());
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                if (accountModel.getUserName().equalsIgnoreCase(username) && accountModel.getPassWord().equalsIgnoreCase(password)) {

                    return resultSet.getInt("userId");
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editCustomerUsername(String username, int userId) {
        try {

            String sql = "UPDATE user SET username = ? WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully!");
                } else {
                    System.out.println("Failed to update account.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editCustomerEmail(String email, int userId) {
        try {

            String sql = "UPDATE user SET emailAddress = ? WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully!");
                } else {
                    System.out.println("Failed to update account.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editCustomerPassword(String password, int userId) {
        try {

            String sql = "UPDATE user SET password = ? WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, password);
                preparedStatement.setInt(2, userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully!");
                } else {
                    System.out.println("Failed to update account.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean getUserType(int type) {
        return type == 1;
    }

    public static boolean checkAndAddCodeIntoUser(int loyaltyProgress, int accountId) {
        String sql = "UPDATE user SET loyaltyProgress = ? WHERE userID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, loyaltyProgress);
            preparedStatement.setInt(2, accountId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating loyalty progress: " + e.getMessage()); // Add this line for debugging
            throw new RuntimeException(e);
        }
    }


    public static int retrieveLoyaltyProgress(AccountModel accountModel)
    {
        String sql = "Select loyaltyProgress From user Where userId = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            int accountId = findAccountId(accountModel);
            preparedStatement.setInt(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getInt("loyaltyProgress");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

  public static String findUserEmail(AccountModel accountModel){
        String username = accountModel.getUserName();
        String email = accountModel.getEmailAddress();

        String sql = "Select emailAddress From user where username = ? AND emailAddress = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getString("emailAddress");
            }else {
                return "";
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
  }

    public static void reduceUserShopPoints(AccountModel accountModel, int redeemValue){
        try {
            String sql = "UPDATE user SET shopPoints = shopPoints - ? Where userID =?";
            int userId = AccountProcess.findAccountId(accountModel);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, redeemValue);
                preparedStatement.setInt(2, userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("user shop points reduce");
                } else {
                    System.out.println("Failed to reduce shop points.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int returnShopPoints(AccountModel accountModel){
        String sql = "SELECT shopPoints FROM user WHERE userId =?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            int userId= findAccountId(accountModel);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getInt("shopPoints");
            }else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
