package com.example.demo.artist.model;

import com.example.demo.sharedObjects.DbConnect;

import java.sql.*;

public class AccountDAO {
    private static String UPDATE_ACCOUNT_SQL = "UPDATE user SET username = ?, emailAddress = ? WHERE userID = ?";
    private static String REGISTER_ACCOUNT_SQL = "INSERT INTO user (userID, userType, password, username, emailAddress, loyaltyProgress, shopPoints) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static String DOES_ACCOUNT_EXIST_SQL = "SELECT COUNT(*) AS count FROM user WHERE username = ? AND password = ?";
    private static String GET_INFO_BY_USERNAME_AND_PASSWORD_SQL = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static String NEXT_USER_ID_SQL = "SELECT MAX(userID) FROM user";
    private static String DELETE_USER_SQL = "DELETE FROM user WHERE userID = ?";


    public static void updateAccountInfo(AccountModel updatedAccount) {
        String query = UPDATE_ACCOUNT_SQL;
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedAccount.getUsername());
            preparedStatement.setString(2, updatedAccount.getEmailAddress());
            preparedStatement.setInt(3, updatedAccount.getUserID()); // Assuming userID is the primary key
            preparedStatement.executeUpdate();
            System.out.println("Username and email address updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to register a new account
    public static void registerNewAccount(AccountModel account) {
        String query = REGISTER_ACCOUNT_SQL;
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int userID = getNextUserID();
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, account.getUserType());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setString(4, account.getUsername());
            preparedStatement.setString(5, account.getEmailAddress());
            preparedStatement.setInt(6, 0);// Initial loyaltyProgress set to 0
            preparedStatement.setInt(7, account.getShopPoints());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if an account exists based on username and password
    public static boolean doesAccountExist(String username, String password) {
        String query = DOES_ACCOUNT_EXIST_SQL;
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //     Method to retrieve user info by username
    public static AccountModel getUserInfoByUsernameAndPassword(String username, String password) {
        String query = GET_INFO_BY_USERNAME_AND_PASSWORD_SQL;
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new AccountModel(
                            resultSet.getInt("userID"),
                            resultSet.getInt("userType"),
                            resultSet.getString("password"),
                            resultSet.getString("username"),
                            resultSet.getString("emailAddress"),
                            resultSet.getInt("loyaltyProgress"),
                            resultSet.getInt("shopPoints")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getNextUserID() {
        // Query the database to get the maximum userID
        int maxUserID = 0; // Initialize with a default value
        try (Connection connection = DbConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(NEXT_USER_ID_SQL)) {
            if (resultSet.next()) {
                maxUserID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Increment the maximum userID to get the next unique value
        return maxUserID + 1;
    }

    public static void deleteAccount(AccountModel accountModel) throws SQLException {
        String query = DELETE_USER_SQL;
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountModel.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Deletion Error");
            e.printStackTrace();
        }


    }

    public static boolean doesUsernameExist(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;

        }
    }

    public static boolean doesEmailExist(String emailAddress) throws SQLException {

        String query = "SELECT COUNT(*) FROM user WHERE emailAddress = ?";

        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, emailAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    public static void updateUserName(String username, int userId) {
        try {
            Connection connection = DbConnect.getConnection();
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

    public static void updatePassword(String password, int userId) {
        try {
            Connection connection = DbConnect.getConnection();

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

    public static void updateUserEmail(String email, int userId) {
        try {
            Connection connection = DbConnect.getConnection();
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
    public static int findAccountId(AccountModel accountModel) throws SQLException {
        Connection connection = DbConnect.getConnection();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, accountModel.getUsername());
            preparedStatement.setString(2, accountModel.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                if (accountModel.getUsername().equalsIgnoreCase(username) && accountModel.getPassword().equalsIgnoreCase(password)) {

                    return resultSet.getInt("userId");
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}