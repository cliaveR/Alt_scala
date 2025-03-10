package com.example.demo.customer.model.classModels;

import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.customer.model.objectModels.TicketModel;
import com.example.demo.customer.model.objectModels.TransactionModel;
import com.example.demo.sharedObjects.DbConnect;

import java.sql.*;
import java.util.ArrayList;

public class TicketProcessor {

    private static Connection connection = DbConnect.getConnection();

    public TicketProcessor() {

    }

    public static void createTicket(TransactionModel transactionModel, int gigiD, int ticketAmount) {
        for (int i = 0; i < ticketAmount; i++) {
            String sql = "INSERT INTO ticket ( ticketNumber, gigId, ticketUsage,transactionId,expDate) " +
                    "VALUES ( ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, generateUniqueTicketNumber());
                preparedStatement.setInt(2, gigiD);
                preparedStatement.setInt(3, 1);
                preparedStatement.setInt(4, transactionModel.getTransactionId());
                preparedStatement.setDate(5, GigUserOperations.findGigTime(gigiD));
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("TICKET created successfully!");
                } else {
                    System.out.println("Failed to create ticket.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void incrementUserShopPoints(int userId) {
        boolean success = AccountProcess.findIfAccountIdExists(userId);
        if (success) {
            String sql = "UPDATE user SET shopPoints = shopPoints + 1 Where userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully!");
                } else {
                    System.out.println("Failed to update account.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void backToZeroUserShopPoints(int userId) {
        boolean success = AccountProcess.findIfAccountIdExists(userId);
        if (success) {
            String sql = "UPDATE user SET shopPoints = 0 Where userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,userId);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Account updated successfully!");
                } else {
                    System.out.println("Failed to update account.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void decrementTicketAmount(int ticketNumber) {
        String sql = "UPDATE ticket SET ticketUsage = ? -1 WHERE ticketNumber =?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, findTicketUsingTicketNumber(ticketNumber));

            preparedStatement.setInt(2, ticketNumber);
            int rowsInserted = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findTicketUsingTicketNumber(int ticketNumber) {
        String sql = "SELECT * FROM ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ticketNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ticketUsage");
            } else {
                throw new RuntimeException("Ticket number not found: " + ticketNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int generateUniqueTicketId() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateTicketId(); // Assuming you have a method to generate gig IDs
            idExists = findIfTicketIdExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }

    private static int generateTicketId() {
        int min = 100000;
        int max = 999999;

        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static boolean findIfTicketIdExists(int ticketId) {
        String sqlQuery = "SELECT * FROM ticket WHERE ticketId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, ticketId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int generateUniqueTicketNumber() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateTicketNumber(); // Assuming you have a method to generate gig IDs
            idExists = findIfTicketNumberExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }

    private static int generateTicketNumber() {
        int min = 1000000;
        int max = 9999999;

        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static boolean findIfTicketNumberExists(int ticketNumber) {
        String sqlQuery = "SELECT * FROM ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, ticketNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean deleteTicket(int ticketNumber) {
        String sql = "DELETE FROM ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ticketNumber);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Ticket deleted successfully!");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fetchAllticketGigOfUser(AccountModel accountModel) {
        String sql = "SELECT ticket.*, transaction.userId, gig.gigId, gig.artistName, gig.gigScheduleEnd, gig.gigScheduleStart, gig.gigDate " +
                "FROM transaction " +
                "INNER JOIN  ticket ON ticket.transactionId = transaction.transactionId " +
                "LEFT JOIN gig ON ticket.gigId = gig.gigId " +  // Added a space here
                "WHERE transaction.userId = ?";

        int userId = AccountProcess.findAccountId(accountModel);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (!(resultSet.getInt("ticketUsage") == 0)) {

                    String artistName = resultSet.getString("artistName");
                    Time gigScheduleStart = resultSet.getTime("gigScheduleStart");
                    Time gigScheduleEnd = resultSet.getTime("gigScheduleEnd");
                    Date gigDate = resultSet.getDate("gigDate");
                    int ticketNumber = resultSet.getInt("ticketNumber");
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println("Artist Name: " + artistName);
                    System.out.println("Gig schedule: " + gigDate + " " + "start: " + gigScheduleStart + " end: " + gigScheduleEnd);
                    System.out.println("TicketNumber: " + ticketNumber);
                    System.out.println("-------------------------------------------------------------------------------");
                    System.out.println(" ");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean ifThereIsATicketNumber(int ticketNumber) {
        String sql = "SELECT * from ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ticketNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findGigIdBasedOnTicketNumber(int ticketNumber) {
        String sql = "SELECT * from ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ticketNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("gigId");
            } else {
                throw new RuntimeException("Ticket number not found: " + ticketNumber);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfTheTicketUsageIsZero(int ticketNumber) {
        String sql = "SELECT ticketUsage From ticket WHERE ticketNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ticketNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                while (resultSet.next()){
                    int usage = resultSet.getInt("ticketUsage");
                    if(usage == 0){
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}