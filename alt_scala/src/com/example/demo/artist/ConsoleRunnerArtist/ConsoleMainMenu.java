package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountModel;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMainMenu {
    private Scanner scanner = new Scanner(System.in);
    private AccountModel account;

    public ConsoleMainMenu(AccountModel account) {
        this.account = account;
    }

    public ConsoleMainMenu() {
    }

    public void start(AccountModel account) throws SQLException, ParseException {
        boolean exit = false;
        while (!exit) {
            System.out.println("--------------------------------");
            System.out.println("Welcome to Gig Main Menu");
            System.out.println("1. View gigs");
            System.out.println("2. Create gigs");
            System.out.println("3. View Account");
            System.out.println("4. Shop Items");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = readChoice(scanner);
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    ConsoleShowGigs consoleShowGigs = new ConsoleShowGigs();
                    consoleShowGigs.start();
                    break;
                case 2:
                    ConsoleGigCreation consoleGigCreation = new ConsoleGigCreation();
                    consoleGigCreation.start();
                    break;
                case 3:
                    ConsoleAccountViewing consoleAccountViewing = new ConsoleAccountViewing();
                    consoleAccountViewing.start(account);
                    break;
                case 4:
                    ConsoleShopMenu consoleShopMenu = new ConsoleShopMenu();
                    consoleShopMenu.start();
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static int readChoice(Scanner scanner) {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return choice;
    }
}