package com.example.demo.artist.model;

import com.example.demo.sharedObjects.DbConnect;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShopDAO {
    private static final String DELETE_ITEM_SQL = "DELETE FROM item WHERE itemId = ?";
    private static final String SELECT_ITEM_BY_ID_SQL = "SELECT * FROM item WHERE itemId = ?";
    private static final String SAVE_ITEM_TO_DATABASE_SQL = "INSERT INTO item (itemId, itemName, description, quantity, redeemValue) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ITEM_SQL = "UPDATE item SET itemName = ?, description = ?, quantity = ?, redeemValue = ? WHERE itemId = ?";
    private static String GET_MAX_ITEMID_SQL = "SELECT MAX(itemId) FROM item ";
    private static final String DOES_ITEM_EXIST_SQL = "SELECT 1 FROM item WHERE itemId = ?";
    private static String VIEW_ALL_ITEM_SQL = "select * from item";

    public static void createItem() throws ParseException {
        ShopModel model = new ShopModel();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter item details:");
        System.out.print("Item Name: ");
        String itemName = scanner.nextLine();
        if (itemExistsForItemName(itemName)) {
            System.out.println("An item with the name '" + itemName + "' already exists. Please enter a different name.");
            return;
        }

        System.out.print("Item Description: ");
        String itemDescription = scanner.nextLine();

        System.out.print("Item Quantity: ");
        int quantity = 0;
        while (true) {
            try {
                quantity = scanner.nextInt();
                if (quantity < 0) {
                    throw new IllegalArgumentException("Quantity cannot be negative. Please input a non-negative integer.");
                }
                break;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid quantity (numbers only).");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        System.out.print("Redeem Value: ");
        int redeemValue = 0;
        while (true) {
            try {
                redeemValue = scanner.nextInt();
                if (redeemValue < 0) {
                    throw new IllegalArgumentException("Redeem value cannot be negative. Please input a non-negative integer.");
                }
                break; // Exit the loop if valid positive integer is entered
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a valid redeem value (numbers only).");
                scanner.nextLine(); // Clear the scanner buffer
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());  // Print the specific error message
            }
        }
        int itemId = getMaxAvailableItemID();
        model = new ShopModel(itemId, itemName, itemDescription, quantity, redeemValue);
        saveItemToDatabase(model);
        System.out.println("Item Created");


    }

    private static int getMaxAvailableItemID() {
        int nextAvailableItemId = 0; // Default value if no gigId is found

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Connect to your database
            connection = DbConnect.getConnection();

            // SQL query to get the maximum gigId

            // Prepare the statement
            preparedStatement = connection.prepareStatement(GET_MAX_ITEMID_SQL);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // If result set contains a row
            if (resultSet.next()) {
                // Get the maximum gigId
                int maxItemId = resultSet.getInt(1);

                // Increment it to get the next available gigId
                nextAvailableItemId = maxItemId + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nextAvailableItemId;
    }

    public static boolean doesItemExist(int itemId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DOES_ITEM_EXIST_SQL)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean updateItem(int itemId, ShopModel model) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_SQL)) {
            preparedStatement.setString(1, model.getItemName());
            preparedStatement.setString(2, model.getDescription());
            preparedStatement.setInt(3, model.getQuantity());
            preparedStatement.setInt(4, model.getRedeemValue());
            preparedStatement.setInt(5, itemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private static void saveItemToDatabase(ShopModel model) throws ParseException {
        try {
            // Load the MySQL JDBC driver and establish connection
            Connection connection = DbConnect.getConnection();

            // Prepare the insert query
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_ITEM_TO_DATABASE_SQL);
            preparedStatement.setInt(1, model.getItemId());
            preparedStatement.setString(2, model.getItemName());
            preparedStatement.setString(3, model.getDescription());
            preparedStatement.setInt(4, model.getQuantity());
            preparedStatement.setInt(5, model.getRedeemValue());

            // Execute the insert query
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean itemExistsForItemName(String itemName) {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM item WHERE itemName = ?")) {
            preparedStatement.setString(1, itemName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if a gig exists for the artist on the specified date
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false by default or in case of any errors
    }

    public static ShopModel getItemById(int itemId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_ID_SQL)) {
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ShopModel shop = new ShopModel();
                shop.setItemId(resultSet.getInt("itemId"));
                shop.setItemName(resultSet.getString("itemName"));
                shop.setDescription(resultSet.getString("description"));
                shop.setQuantity(resultSet.getInt("quantity"));
                shop.setRedeemValue(resultSet.getInt("redeemValue"));
                return shop;
            }
        }
        return null;
    }


    public static boolean deleteItem(int itemId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM_SQL)) {
            preparedStatement.setInt(1, itemId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static List<ShopModel> viewAllItems() throws SQLException {
        Connection connection = DbConnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(VIEW_ALL_ITEM_SQL);

        ArrayList<ShopModel> arrayList = new ArrayList<>();
        boolean itemsFound = false; // Variable to track if any gigs are found

        while (resultSet.next()) {
            itemsFound = true; // Set to true if at least one gig is found

            ShopModel model = new ShopModel();
            int itemId = resultSet.getInt("itemId");
            String itemName = resultSet.getString("itemName");
            String description = resultSet.getString("description");
            int quantity = resultSet.getInt("quantity");
            int redeemValue = resultSet.getInt("redeemValue");

            model.setItemId(itemId);
            model.setItemName(itemName);
            model.setDescription(description);
            model.setQuantity(quantity);
            model.setRedeemValue(redeemValue);


            arrayList.add(model);
        }
        if (!itemsFound) {
            System.out.println("No gigs found.");
        } else {
            for (ShopModel model : arrayList) {
                System.out.println("Item ID: " + model.getItemId());
                System.out.println("Item Name: " + model.getItemName());
                System.out.println("Description: " + model.getDescription());
                System.out.println("Quantity: " + model.getQuantity());
                System.out.println("Redeem Value: " + model.getRedeemValue());
                System.out.println();
            }
        }

        return null;

    }
}

