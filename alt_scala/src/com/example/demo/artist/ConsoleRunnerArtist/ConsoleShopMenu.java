package com.example.demo.artist.ConsoleRunnerArtist;

import com.example.demo.artist.model.AccountModel;
import com.example.demo.artist.model.GigDAO;
import com.example.demo.artist.model.ShopDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import com.example.demo.artist.model.ShopModel;

public class ConsoleShopMenu {
    public static void main(String[] args) {
        try {
            ConsoleShopMenu.start();//for debugging purposes
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() throws SQLException, ParseException {
        AccountModel model = new AccountModel();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Create Item");
            System.out.println("2. View Items");
            System.out.println("3. Go Back");
            System.out.print("Enter your choice: ");
            int choice = ConsoleMainMenu.readChoice(scanner);

            switch (choice) {
                case 1:
                    ShopDAO.createItem();
                    break;
                case 2:
                    ViewItem();
                    break;
                case 3:
                    ConsoleMainMenu consoleMainMenu = new ConsoleMainMenu();
                    consoleMainMenu.start(model);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public static void ViewItem() throws SQLException {
        ShopDAO.viewAllItems();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter the itemId you want to modify: ");
                int itemId;

                if (scanner.hasNextInt()) {
                    itemId = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                try {
                    if (!ShopDAO.doesItemExist(itemId)) {
                        System.out.println("Item with ID " + itemId + " does not exist.");
                        continue; // Restart loop to get valid item ID
                    }

                    ShopModel item = ShopDAO.getItemById(itemId);

                    int fieldChoice;
                    do {
                        // Display current item details
                        System.out.println("Current Item Details:");
                        System.out.println("1. Item Name: " + item.getItemName());
                        System.out.println("2. Item Description: " + item.getDescription());
                        System.out.println("3. Item Quantity: " + item.getQuantity());
                        System.out.println("4. Redeem Value: " + item.getRedeemValue());
                        System.out.println("=======================================================");
                        System.out.println("5. Delete item");
                        System.out.println("6. Exit modify item");
                        System.out.println("=======================================================");
                        System.out.print("Enter the number of the field you want to modify: ");
                        fieldChoice = scanner.nextInt();
                    } while (fieldChoice < 1 || fieldChoice > 6);

                    switch (fieldChoice) {
                        case 1:
                            System.out.print("Enter new item name: ");
                            scanner.nextLine(); // Consume newline
                            String newName = scanner.nextLine();
                            item.setItemName(newName);
                            break;
                        case 2:
                            System.out.print("Enter new item description: ");
                            scanner.nextLine(); // Consume newline
                            String newDescription = scanner.nextLine();
                            item.setDescription(newDescription);
                            break;
                        case 3:
                            System.out.print("Enter new item quantity: ");
                            int newQuantity = scanner.nextInt();
                            item.setQuantity(newQuantity);
                            break;
                        case 4:
                            System.out.print("Enter new redeem value: ");
                            int newRedeemValue = scanner.nextInt();
                            item.setRedeemValue(newRedeemValue);
                            break;
                        case 5:
                            ShopDAO.deleteItem(itemId);
                            System.out.println("Item deleted successfully.");
                            return; // Exit the method after deletion
                        case 6:
                            // You can implement logic to return to the main menu here
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            continue; // Restart loop to get valid field choice
                    }

                    // Update the item in the database
                    ShopDAO.updateItem(itemId, item);
                    System.out.println("Item details updated successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



