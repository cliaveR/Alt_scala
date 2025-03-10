package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountDAO;
import com.example.demo.artist.model.AccountModel;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ConsoleAccountCreation {
    public void start(AccountModel accountModel) throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to Gig!");
            System.out.println("1. Register a New Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = ConsoleMainMenu.readChoice(scanner);

            switch (choice) {
                case 1:
                    registerNewAccount(accountModel);
                    break;
                case 2:
                    login(accountModel);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.exit(0);
    }

    //TODO Error it could not connect to jdbc
    private void registerNewAccount(AccountModel accountModel) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter user type \n[1] for regular user \n[2] for admin \n[0] go back");
                System.out.print("Enter user type: ");
                String userChoice = scanner.nextLine();
                int userType = 0;
                if (userChoice.equals("1")) {
                    userType = 0; // Regular user
                } else if (userChoice.equals("2")) {
                    userType = 1; // Admin
                } else if (userChoice.equals("0")) {
                    return; // Go back
                } else {
                    System.out.println("Invalid input. Please enter 1 for regular user, 2 for admin, and 0 if you want to go back.");
                    continue;
                }


                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                if (AccountDAO.doesUsernameExist(username)) {
                    System.out.println("Username already exists.");
                    continue;
                }
                System.out.print("Enter email address: ");
                String emailAddress = scanner.nextLine();
                if (AccountDAO.doesEmailExist(emailAddress)) {
                    System.out.println("Email address already exists.");
                    continue;
                }
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                int shopPoints = 0;
                accountModel.setUserType(userType);
                accountModel.setUsername(username);
                accountModel.setEmailAddress(emailAddress);
                accountModel.setPassword(password);
                accountModel.setShopPoints(shopPoints);
                AccountDAO.registerNewAccount(accountModel);// Register new account
                System.out.println("Account registered successfully!");
                break;
            } catch (SQLException e) {
                System.out.println("Error");
                e.printStackTrace();

            }


        }

    }

    private void login(AccountModel accountModel) throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Login");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Check if the account exists in the database
            if (AccountDAO.doesAccountExist(username, password)) {
                System.out.println("Logged in");
                accountModel.setUsername(username);
                accountModel.setPassword(password);
                ConsoleMainMenu consoleMainMenu = new ConsoleMainMenu(accountModel);
                consoleMainMenu.start(accountModel);
                exit = true;
            } else {
                System.out.println("Login failed. " + username + " does not exist.");
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Do you want to try again? (yes/no): ");
                    String tryAgain = scanner.nextLine();
                    if (tryAgain.equalsIgnoreCase("yes") || tryAgain.equalsIgnoreCase("no")) {
                        validInput = true;
                        exit = tryAgain.equalsIgnoreCase("no");
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                }
            }
        }
    }
}