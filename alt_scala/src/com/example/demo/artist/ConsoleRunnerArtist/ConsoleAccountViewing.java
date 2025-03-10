package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountDAO;
import com.example.demo.artist.model.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ConsoleAccountViewing {
    private Scanner scanner;

    public void start(AccountModel account) throws SQLException, ParseException {
        scanner = new Scanner(System.in);

        System.out.println("Viewing Account Information");
        System.out.println(account.toString());
        System.out.println("1. Edit Account Information");
        System.out.println("2. Log out");
        System.out.println("3. Go Back");

        System.out.print("Enter your choice: ");
        String choiceStr = scanner.nextLine();

        try {
            int choice = Integer.parseInt(choiceStr);

            switch (choice) {
                case 1:
                    try {
                        editAccountInfo(account);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    ConsoleAccountCreation consoleAccountCreation = new ConsoleAccountCreation();
                    consoleAccountCreation.start(account);
                    break;
                case 3:
                    ConsoleMainMenu consoleMainMenu = new ConsoleMainMenu();
                    consoleMainMenu.start(account);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }


    private void editAccountInfo(AccountModel account) throws SQLException, IOException, ParseException {

        System.out.println("Editing Account Information");
        System.out.println("You are only able to edit your username and email address");
        System.out.println("1. Edit username");
        System.out.println("2. Edit email Address");
        System.out.println("3. Edit Password");
        System.out.println("4. Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();
        int userId = AccountDAO.findAccountId(account);

        switch (choice) {
            case 1:
                editUsername(userId, account);
                break;
            case 2:
                editEmail(userId, account);
                break;

            case 3:
                editPassword(userId, account);
                break;
            case 4:
                ConsoleMainMenu consoleMainMenu = new ConsoleMainMenu();
                consoleMainMenu.start(account);
                return;
            default:
                System.out.println("Invalid choice. Please try again. ");
                return;
        }
    }


    private void deleteAccount(AccountModel account) throws SQLException {
        System.out.println("Deletion of Account");
        System.out.print("Are you sure you want to delete this account? (y/n): ");
        String decision = scanner.nextLine();
        if (decision.equalsIgnoreCase("y")) {
            try {
                AccountDAO.deleteAccount(account);
                System.out.println("Account deleted successfully.");
                ConsoleAccountCreation consoleAccountCreation = new ConsoleAccountCreation();
                consoleAccountCreation.start(account);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


        } else if (decision.equalsIgnoreCase("n")) {
            System.out.println("Account deletion cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' to confirm deletion or 'n' to cancel.");
            // Optionally, you might want to add additional logic to handle invalid input
        }
    }

    private void editEmail(int userId, AccountModel accountModel) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new email you want to edit (Leave blank to keep current)");
        String newEmail = reader.readLine();
        if(!newEmail.isEmpty()){
            AccountDAO.updateUserEmail(newEmail, userId);
            accountModel.setEmailAddress(newEmail);

        }else {System.out.println("No Changes made to Email");}



    }
    private void editUsername(int userId, AccountModel accountModel) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new username you want to edit (Leave blank to keep current");
        String newUsername = reader.readLine();
        if(!newUsername.isEmpty()){
            AccountDAO.updateUserName(newUsername, userId);
            accountModel.setEmailAddress(newUsername);
        }else {System.out.println("No Changes made to Email");}



    }
    private void editPassword(int userId, AccountModel accountModel) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new password you want to edit (Leave blank to keep current)");
        String newPassword = reader.readLine();
        if(!newPassword.isEmpty()){
            AccountDAO.updatePassword(newPassword, userId);
            accountModel.setEmailAddress(newPassword);
        }else { System.out.println("No Changes made to Email");}



    }
}