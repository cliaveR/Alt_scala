package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountModel;
import com.example.demo.artist.model.GigDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ConsoleShowGigs {
    private static Scanner scanner = new Scanner(System.in);
    AccountModel accountModel;

    public static void main(String[] args) throws SQLException, ParseException {
        ConsoleShowGigs consoleShowGigs = new ConsoleShowGigs();
        consoleShowGigs.start();
    }

    public void start() throws SQLException, ParseException {
        GigDAO.viewAllGig();

        boolean exit = false;
        while (!exit) {
            System.out.println("---------------------------------");
            System.out.println("1. Edit gig");
            System.out.println("2. Go back");
            System.out.print("Enter Choice: ");
            int choice = ConsoleMainMenu.readChoice(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ConsoleEditGig consoleEditGig = new ConsoleEditGig();
                    consoleEditGig.start();
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
