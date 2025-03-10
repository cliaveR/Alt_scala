package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.classModels.AccountProcess;
import com.example.demo.customer.model.classModels.GigUserOperations;
import com.example.demo.customer.model.classModels.LoyaltyCardProcess;
import com.example.demo.customer.model.classModels.TicketProcessor;
import com.example.demo.customer.model.objectModels.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WatchGig {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public WatchGig(AccountModel accountModel) {
        boolean run = true; // Flag to control the loop
        while (run) { // Start the while loop
            try {
                System.out.println("====================================================================================");
                System.out.println("These are your tickets that you have:");
                TicketProcessor.fetchAllticketGigOfUser(accountModel);
                System.out.println("Please Enter the ticketNumber you want to use (enter 0 to exit):");
                int ticketNumber = Integer.parseInt(reader.readLine().trim());

                if (ticketNumber == 0) {
                    run = false; // Exit the loop if the user chooses to exit
                } else if(TicketProcessor.checkIfTheTicketUsageIsZero(ticketNumber)){
                    System.out.println("ticketNumber has already been used");
                }
                else {
                    boolean ifThereIsATicket = TicketProcessor.ifThereIsATicketNumber(ticketNumber);
                    if (!ifThereIsATicket) {
                        throw new TicketNotFoundException("Ticket not found!"); // Throw an exception if the ticket is not found
                    }

                    GigUserOperations.watchGig(accountModel, ticketNumber);
                    int countDiscountCode = LoyaltyCardProcess.countForDiscountCode(accountModel);
                    int accountId = AccountProcess.findAccountId(accountModel);
                    boolean successAccount = AccountProcess.checkAndAddCodeIntoUser(countDiscountCode, accountId);

                    if (successAccount) {
                        int userDiscountCode = AccountProcess.retrieveLoyaltyProgress(accountModel);
                        TicketProcessor.decrementTicketAmount(ticketNumber);
                        if (userDiscountCode % 10 == 0) {
                            boolean success = LoyaltyCardProcess.createDiscountCode(accountModel);
                            if (success) {
                                System.out.println("Congratulations! You have watched 30 gigs in total and are now eligible for a discount code:");
                                String discountCode = LoyaltyCardProcess.retrieveDiscountCode(accountModel);
                                System.out.println("Your discount code: " + discountCode);
                            }
                        } else {
                            System.out.println(" ");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ticket number (an integer).");
            } catch (TicketNotFoundException e) {
                System.out.println("Invalid ticket number. Please enter a valid ticket number.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private class TicketNotFoundException extends Exception {
        public TicketNotFoundException(String s) {
        }
    }
}
