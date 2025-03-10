package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.classModels.AccountProcess;
import com.example.demo.customer.model.objectModels.AccountModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class ViewAccount {
    public void start(AccountModel accountModel) throws SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean run = true; // Flag to control the loop
        while (run) { // Start the while loop
            try {
                System.out.println("=====================================");
                System.out.println("Sundayag Customer Profile Page");
                System.out.println(accountModel.customerToString());
                int accountId = AccountProcess.findAccountId(accountModel);

                System.out.println("[1] Edit username account");
                System.out.println("[2] Edit password account");
                System.out.println("[3] Go back");
                System.out.print("Enter choice: ");

                int choice = Integer.parseInt(reader.readLine());
                switch (choice) {
                    case 1:
                        editUsername(accountId, accountModel);
                        break;
                    case 2:
                        editPassword(accountId, accountModel);
                        break;
                    case 3:
                        run = false; // Exit the loop if the user chooses option 4
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void editUsername(int accountId, AccountModel accountModel) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new Username you want to edit or leave blank to keep current:");
        String username = reader.readLine();
        if (!username.isEmpty()) {
            AccountProcess.editCustomerUsername(username, accountId);
            // Update accountModel with new username
            accountModel.setUserName(username);
            System.out.println("Username updated successfully!");
        } else {
            System.out.println("No changes made to the username.");
        }
    }

    private void editEmail(int accountId, AccountModel accountModel) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new email you want to edit or leave blank to keep current:");
        String email = reader.readLine();
        if (!email.isEmpty()) {
            AccountProcess.editCustomerEmail(email, accountId);
            // Update accountModel with new email
            accountModel.setEmailAddress(email);
            System.out.println("Email updated successfully!");
        } else {
            System.out.println("No changes made to the email.");
        }
    }

    private void editPassword(int accountId, AccountModel accountModel) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the new password you want to edit or leave blank to keep current:");
        String password = reader.readLine();
        if (!password.isEmpty()) {
            AccountProcess.editCustomerPassword(password, accountId);
            // Update accountModel with new password
            accountModel.setPassWord(password);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("No changes made to the password.");
        }
    }
}