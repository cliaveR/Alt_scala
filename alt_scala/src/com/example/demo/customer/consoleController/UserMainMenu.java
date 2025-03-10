package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.objectModels.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class UserMainMenu {
    //book a gig depending on name;
    //buying a gig depending on payment
    //show current gigs for editing or deleting ;

    private AccountModel account;

    public UserMainMenu(AccountModel account){
        this.account = account;
    }

    public UserMainMenu(){}

    public void start(AccountModel accountModel) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit){
            System.out.println("=====================================");
            System.out.println("Welcome to Sundayag (press 1-4 for usage)");

            System.out.println("1. Show available gigs");
            System.out.println("2. Open Accounts");
            System.out.println("3. Show gig reservation tickets");
            System.out.println("4. Show redeem shop");
            System.out.println("5. Log out");
            int userchoice = readChoice(reader);

            switch (userchoice) {
                case 1:
                    UserGigController userGigController = new UserGigController();
                    userGigController.start(accountModel);
                    break;
                case 2:
                    ViewAccount viewAccount = new ViewAccount();
                    viewAccount.start(accountModel);
                    break;
                case 3:
                    WatchGig viewingGig = new WatchGig(accountModel);
                    break;
                case 4:
                    ShopController shopController = new ShopController(accountModel);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static int readChoice(BufferedReader reader) {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = Integer.parseInt(reader.readLine());
                validInput = true;
            } catch (NumberFormatException | IOException e) {
                System.out.print("Invalid input. Please enter a number: ");
                // Consume the invalid input
            }
        }

        return choice;
    }
}
