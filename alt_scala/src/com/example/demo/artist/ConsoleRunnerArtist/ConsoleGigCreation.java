package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountModel;
import com.example.demo.artist.model.GigDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleGigCreation {
    AccountModel accountModel;

    public static void main(String[] args) {
        try {
            ConsoleGigCreation.start();//for debugging purposes
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Create gig");
            System.out.println("2. Go back");
            System.out.print("Enter your choice: ");
            int choice = ConsoleMainMenu.readChoice(scanner);

            switch (choice) {
                case 1:
                    GigDAO.createGig();
                    break;
                case 2:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
