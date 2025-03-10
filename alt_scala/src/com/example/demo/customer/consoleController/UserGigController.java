package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.classModels.GigUserOperations;
import com.example.demo.customer.model.classModels.TicketProcessor;
import com.example.demo.customer.model.objectModels.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class UserGigController {
    public void start(AccountModel accountModel) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean run = true; // Set up a flag to control the loop
        while (run) { // Start the while loop
            try {
                GigUserOperations.showAllGig();
                System.out.println("=====================================");
                System.out.println("Sundayag Gig Ticket Page");
                System.out.println("[1] Buy a gig ticket ");
                System.out.println("[2] Delete a gig ticket");
                System.out.println("[3] Go back to main menu");
                System.out.print("Enter a number: ");
                int userChoice = UserMainMenu.readChoice(reader);

                if (userChoice == 1) {
                    System.out.println("Please enter the gigId of the gig you want to buy");
                    int gigId = 0;
                    boolean validInput = false;
                    while (!validInput) {
                        try {
                            gigId = Integer.parseInt(reader.readLine());
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer gigId: ");
                        }
                    }
                    boolean gigFound = false;
                    while (!gigFound) {
                        gigFound = GigUserOperations.findIfThereIsAGig(gigId);
                        if (!gigFound) {
                            System.out.println("There was no gig with that gigId. Please enter a valid gigId: ");
                            gigId = Integer.parseInt(reader.readLine());
                        }
                    }
                    GigUserOperations.buyGig(gigId, accountModel);
                } else if (userChoice == 2) {
                    boolean dRun = true;
                    while (dRun){
                        System.out.println("[List of bought tickets] ");
                        System.out.println("====================================================================================");
                        TicketProcessor.fetchAllticketGigOfUser(accountModel);
                        System.out.println("Please enter the ticket Number of your ticket to be deleted (enter 0 to exit): ");
                        int ticketNumber = 0;
                        boolean validInput = false;
                        while (!validInput) {
                            try {
                                ticketNumber = Integer.parseInt(reader.readLine());
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer ticket number: ");
                            }
                            if (ticketNumber <= 0){
                                dRun = false;
                            }
                            boolean success = TicketProcessor.deleteTicket(ticketNumber);
                            if (success) {
                                System.out.println("Ticket number " + ticketNumber + " has been deleted: ");
                            } else {
                                System.out.println("Deletion of the ticket is cancelled because there was no " +
                                        "ticket number found .");
                            }
                        }
                    }
                } else if (userChoice == 3) {
                    run = false; // Exit the loop if the user chooses option 3
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void showGigs()
    {
        System.out.println("Viewing gigs...");
    }
}