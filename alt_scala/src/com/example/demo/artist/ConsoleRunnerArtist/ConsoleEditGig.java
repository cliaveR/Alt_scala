package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.*;
import com.example.demo.artist.model.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.example.demo.artist.model.GigDAO.validateDate;
import static com.example.demo.artist.model.GigDAO.validateTime;

public class ConsoleEditGig {
    private static AccountModel accountModel = new AccountModel();

    public static void main(String[] args) {
        try {
            start();
        } catch (SQLException e) {
            // Handling SQL exceptions by printing the error message
            System.err.println("SQL Exception occurred: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the gigId you want to modify: ");
            int gigId;

            if (scanner.hasNextInt()) {
                gigId = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (!GigDAO.doesGigExist(gigId)) {
                System.out.println("Gig with ID " + gigId + " does not exist.");
                continue; // Restart loop to get valid gig ID
            }

            GigModel gig = GigDAO.getGigById(gigId);


            int fieldChoice;
            do {
                // Display current gig details
                System.out.println("Current Gig Details:");
                System.out.println("1. Gig Description: " + gig.getGigDescription());
                System.out.println("2. Gig Date: " + gig.getGigDate());
                System.out.println("3. Gig Start: " + gig.getGigStart());
                System.out.println("4. Gig End: " + gig.getGigEnd());
                System.out.println("=======================================================");
                System.out.println("5. Delete gig");
                System.out.println("6. Exit edit gig");
                System.out.println("=======================================================");
                System.out.print("Enter the number of the field you want to modify: ");
                fieldChoice = readIntInput(scanner);
            } while (fieldChoice < 1 || fieldChoice > 6);

                switch (fieldChoice) {
                    case 1:
                        System.out.print("Enter new gig description: ");
                        scanner.nextLine();
                        String newDescription = scanner.nextLine();
                        gig.setGigDescription(newDescription);
                        break;
                    case 2:
                        boolean validDate = false;
                        String newDate = null;
                        while (!validDate) {
                            try {
                                System.out.print("Enter new Gig Date (YYYY-MM-DD): ");
                                newDate = scanner.next();
                                gig.setGigDate(newDate);
                                validateDate(newDate);  // Call method to validate date format
                                validDate = true;
                            } catch (InvalidDateFormatException e) {
                                System.err.println("Error: Invalid date format. Please enter in YYYY-MM-DD format.");
                            }
                        }
                        gig.setGigDate(newDate);
                        System.out.println("The gigDate has been replaced.");
                        break;
                    case 3:
                        boolean validTime = false;
                        String newStartTime = null;
                        while (!validTime) {
                            try {
                                System.out.print("Enter new Gig Start Time (HH:MM:SS): ");
                                newStartTime = scanner.next();
                                gig.setGigStart(newStartTime);
                                validateTime(newStartTime);  // Call method to validate time format
                                validTime = true;
                            } catch (InvalidTimeFormatException e) {
                                System.err.println("Error: Invalid time format. Please enter in HH:MM:SS format.");
                            }
                        }
                        gig.setGigStart(newStartTime);
                        System.out.println("The gigScheduleStart has been replaced.");
                        break;
                    case 4:
                        boolean validEndTime = false;
                        String newEndTime = null;
                        while (!validEndTime) {
                            try {
                                System.out.print("Enter new Gig End Time (HH:MM:SS): ");
                                newEndTime = scanner.next();
                                gig.setGigEnd(newEndTime);
                                validateTime(newEndTime);  // Call method to validate time format
                                validEndTime = true;
                            } catch (InvalidTimeFormatException e) {
                                System.err.println("Error: Invalid time format. Please enter in HH:MM:SS format.");
                            }
                        }
                        gig.setGigEnd(newEndTime);
                        System.out.println("The gigScheduleEnd has been replaced.");
                        break;
                    case 5:
                        GigDAO.deleteGig(gigId);
                        System.out.println("Gig deleted successfully.");
                        return; // Exit the method after deletion
                    case 6:
                        ConsoleMainMenu consoleMainMenu = new ConsoleMainMenu();
                        consoleMainMenu.start(accountModel);
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Restart loop to get valid field choice
                }

                // Update the gig in the database
                GigDAO.updateGig(gigId, gig);
                System.out.println("Gig details updated successfully.");
        }
    }

    public static int readIntInput(Scanner scanner) {
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