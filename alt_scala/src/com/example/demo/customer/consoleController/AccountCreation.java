package com.example.demo.customer.consoleController;

import com.example.demo.customer.model.classModels.AccountProcess;
import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.sharedObjects.DbConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountCreation {
    private static Connection connection = DbConnect.getConnection();

    public void start(AccountModel accountModel) throws SQLException {
        BufferedReader reader;
        boolean run = true; // Change to true to enter the loop

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            while (run) { // Changed condition to loop until explicitly stopped
                System.out.println("=====================================");
                System.out.println("Welcome to Sundayag");
                System.out.print("(0)Login or (1)SignUp: ");

                String choiceStr = reader.readLine();
                if (!isValidInput(choiceStr)) {
                    System.out.println("Invalid input. Please enter either 0 for Login or 1 for SignUp.");
                    continue; // Restart the loop to prompt for valid input
                }

                int checkForLogOrSign = Integer.parseInt(choiceStr);
                if (checkForLogOrSign == 1) {
                    signUp(accountModel);
                } else if (checkForLogOrSign == 0) {
                    login();
                }

                // After signing up or logging in, ask the user if they want to continue
                boolean validContinueInput = false;
                while (!validContinueInput) {
                    System.out.println("Do you want to continue? (yes/no)");
                    String continueChoice = reader.readLine().toLowerCase();
                    if (continueChoice.equals("yes") || continueChoice.equals("no")) {
                        validContinueInput = true;
                        run = continueChoice.equals("yes"); // Set run based on user input
                    } else {
                        System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            closeResources();
        }

        System.out.println("Thank you for using our service. Goodbye!");
        System.exit(0);
    }

    public static boolean isValidInput(String input) {
        return input.equals("0") || input.equals("1");
    }

    public static void signUp(AccountModel accountModel) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=====================================");
        System.out.println("Sign Up Page");

        System.out.println("Username: ");
        String userName = reader.readLine();

        boolean validEmail = false;
        String emailAddress = "";
        while (!validEmail) {
            System.out.println("Email: ");
            emailAddress = reader.readLine();

            // Validate email format
            if (!emailAddress.endsWith("@gmail.com")) {
                System.out.println("Invalid email address. Please enter an email address ending with '@gmail.com'.");
            } else {
                validEmail = true;
            }
        }
        System.out.println("Password:");
        String passWord = reader.readLine();

        boolean isDuplicate = AccountProcess.isDuplicateAccount(userName, emailAddress);
        if (isDuplicate) {
            System.out.println("Account with the same username or email already exists. Please choose a different username or email.");
            return; // Exit the signUp method
        }
        boolean validUserTypeInput = false;
        boolean userType = false;
        while (!validUserTypeInput) {
            System.out.println("Enter userType: Artist(1) or customer(0) ");
            String userTypeInput = reader.readLine();
            if (!isValidInput(userTypeInput)) {
                System.out.println("Invalid input. Please enter either 0 for Customer or 1 for Artist.");
            } else {
                userType = AccountProcess.getUserType(Integer.parseInt(userTypeInput));
                validUserTypeInput = true;
            }
        }
        System.out.println("=====================================");

        int userId = AccountProcess.generateUserId();
        accountModel.setUserId(userId);
        accountModel.setUserName(userName);
        accountModel.setEmailAddress(emailAddress);
        accountModel.setPassWord(passWord);
        accountModel.setUserType(userType);

        boolean successful = AccountProcess.accountCreation(accountModel);
        if (successful) {
            System.out.println("Account creation successful.");
        } else {
            System.out.println("The account creation was not successful.");
        }
    }

    public static void login() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=====================================");
        System.out.println("Login Page");

        System.out.print("Username: ");
        String userName = getInput(reader);

        System.out.print("Email: ");
        String emailAddress = getInput(reader);

        System.out.print("Password:");
        String passWord = getInput(reader);

        AccountModel accountLogin = new AccountModel(passWord, userName, emailAddress);
        boolean successful = AccountProcess.loginAccount(accountLogin);
        if (successful) {
            System.out.println("Logged in");
            UserMainMenu userMainMenu = new UserMainMenu();
            userMainMenu.start(accountLogin);
        } else {
            System.out.println("Login failed. Invalid input/s.");
        }
    }

    // Method to get input from the user and handle empty input
    private static String getInput(BufferedReader reader) {
        String input = null;
        try {
            input = reader.readLine().trim(); // trim() to remove leading/trailing whitespaces
            if (isEmpty(input)) {
                System.out.println("Input cannot be empty. Please enter a valid value:");
                return getInput(reader); // Recursive call to getInput() until a non-empty input is provided
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception according to your application's logic
        }
        return input;
    }

    // Method to check if a string is empty
    private static boolean isEmpty(String str) {
        return str.isEmpty();
    }
    public static void closeResources() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}