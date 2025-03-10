package com.example.demo.customer.model.classModels;

import com.example.demo.customer.model.objectModels.AccountModel;
import com.example.demo.customer.model.objectModels.GigModel;
import com.example.demo.customer.model.objectModels.TransactionModel;
import com.example.demo.sharedObjects.DbConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class GigUserOperations
{

    private final GigModel gigModel = new GigModel();

    private static BufferedReader reader;

    private static final Connection connection = DbConnect.getConnection();
    public GigUserOperations()
    {

    }



    public static void showAllGig()
    {
        try {
            String sqlQuery = "select * from gig WHERE gigId = 387041";
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(sqlQuery);


            ArrayList<GigModel> arrayList = new ArrayList<>();
            if(!checkIfThereIsAnyGig())
            {
                System.out.println("There are no gigs");
            }else {
                while (resultSet.next()) {
                    GigModel gigModel = new GigModel();
                    int gigId = Integer.parseInt(resultSet.getString("gigId"));
                    String artistName = resultSet.getString("artistName");
                    String gigDescription = resultSet.getString("gigDescription");
                    int artistType = Integer.parseInt(resultSet.getString("artistType"));
                    boolean type = returnBooleanValue(artistType);
                    double gigPrice = Double.parseDouble(resultSet.getString("gigPrice"));
                    int gigStatus = Integer.parseInt(resultSet.getString("gigStatus"));
                    boolean status = returnBooleanValue(gigStatus);
                    Date date = resultSet.getDate("gigDate");
                    Time gigStart = resultSet.getTime("gigScheduleStart");
                    Time gigEnd = resultSet.getTime("gigScheduleEnd");
                    gigModel.setGigId(gigId);
                    gigModel.setArtistName(artistName);
                    gigModel.setGigDescription(gigDescription);
                    gigModel.setArtistType(type);
                    gigModel.setGigPrice(gigPrice);
                    gigModel.setGigStatus(status);
                    gigModel.setGigDate(date);
                    gigModel.setGigStart(gigStart);
                    gigModel.setGigEnd(gigEnd);
                    arrayList.add(gigModel);
                }
                System.out.println("Gigs \n");
                for (GigModel model : arrayList) {
                    System.out.println(
                            "Artist: " + model.getArtistName() +
                                    "Gig Id: " + model.getGigId()+
                                    "\nDescription: " + model.getGigDescription() +
                                    "\nPrice: " + model.getGigPrice() +
                                    "\nDate: " + model.getGigDate() +
                                    "\nStart Time:  " + model.getGigStart() +
                                    "\nEnd Time: " + model.getGigEnd());
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfThereIsAnyGig()
    {

            String sqlQuery = "select * from gig";
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                boolean success =resultSet.next();


                return success;

            }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static int findGig(int gigId)
    {
        String sqlQuery = "select * from gig WHERE gigId= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setInt(1, gigId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getInt("gigId");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean findIfThereIsAGig(int gigId) {
        String sql = "SELECT * FROM gig WHERE gigId = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gigId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if there is a gig with the provided gigId
            } catch (Exception e)
            {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Date findGigTime(int gigId)
    {
        String sqlQuery = "select * from gig WHERE gigId= ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setInt(1, gigId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return resultSet.getDate("gigDate");
            }
            else {
                throw new RuntimeException("There was no gigScheduleFound");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * this method will search for a specific gig after which it will store this gig in the
     * @param
     */
    public static void buyGig(int gigId, AccountModel accountModel)
    {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            int userId = AccountProcess.findAccountId(accountModel);
            int ticketAmount = 0;
            boolean validTicketAmount = false;
            while (!validTicketAmount) {
                try {
                    System.out.println("Please enter amount of tickets you want: ");
                    ticketAmount = Integer.parseInt(reader.readLine());
                    if (ticketAmount > 0) {
                        validTicketAmount = true;
                    } else
                        System.out.println("Invalid number. Please enter any amount greater than 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ticket amount format. Please enter a valid number.");
                }
            }

            int paymentMethod = -1;
            boolean validPaymentMethod = false;
            while (!validPaymentMethod) {
                System.out.println("Please enter the paymentMethod Gcash(0) - OnlineBank = 1: ");
                try {
                    paymentMethod = Integer.parseInt(reader.readLine());
                    if (paymentMethod == 0 || paymentMethod == 1) {
                        validPaymentMethod = true;
                    } else {
                        System.out.println("Invalid payment method. Please enter either 0 or 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid payment method format. Please enter either 0 or 1.");
                }
            }

            String methodValue = returnBankPayment(paymentMethod);


            String discountCode = "";
            boolean validDiscountCode = false;
            while (!validDiscountCode) {
                System.out.println("Enter your Discount if available (optional): ");
                discountCode = reader.readLine();
                if (discountCode.length() == 7 || discountCode.isEmpty()) {
                    validDiscountCode = true;
                } else {
                    System.out.println("Discount code must be 7 characters long. Please try again.");
                }
            }

            if (discountCode.isEmpty()) {
                System.out.println("No discount code entered. Proceeding without discount.");
            } else {
                System.out.println("Discount code entered: " + discountCode);
            }

            boolean discountCodeFound = LoyaltyCardProcess.checkUserDiscountCode(userId,discountCode.trim());
            if (discountCodeFound) {
                // Continue with the program
                System.out.println("Discount code found.");
            } else {
                // Display a message indicating no discount code was found
                System.out.println("No discount code found.");
            }

            System.out.println("This is the price of your bought tickets: ");
            int getGigIdPrice = findGig(gigId);
            double gigPrice = getGigPrice(getGigIdPrice);
            if(discountCodeFound){
                gigPrice =gigPrice*.80;
            }
            System.out.print((gigPrice * ticketAmount)+"\n");


            boolean correctPriceEntered = false;

            String email = AccountProcess.findUserEmail(accountModel);
          boolean success =  findIfThereIsAGig(gigId);
          if(success)
          {
              //Changed the transactionModel
              TransactionModel transactionModel = new TransactionModel(generateUniqueTransactionId(),userId,methodValue,email,ticketAmount, gigPrice,discountCode);
              transactionCreation(transactionModel, gigId, accountModel);
          }else
          {
              System.out.println("There was no gig with that name");
          }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static double getGigPrice(int gigId){
        String sql = "Select gigPrice From gig where gigId = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, gigId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("gigPrice");
                } else {
                    return 0.0;
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkIfThePriceMatch(double price, int gigId) {
        String sql = "SELECT gigPrice FROM gig WHERE gigId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, gigId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double gigPrice = resultSet.getDouble("gigPrice");
                    return price >= gigPrice;
                } else {
                    return false; // No gig found for the given name
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String returnBankPayment(int paymentMethod)
    {
        String value = "";
        if (paymentMethod == 0)
        {
            value = "Gcash";
            return value.trim();
        }
        else{
            value = " OnlineBank ";
            return  value.trim();
        }
    }



    private static void transactionCreation(TransactionModel transactionModel , int gigId, AccountModel accountModel) throws SQLException {

        String sql = "INSERT INTO transaction (transactionId, userId, paymentMethod,emailAddress,ticketAmount, transactionAmount,discountCode) VALUES (?,?,?,?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transactionModel.getTransactionId());
            preparedStatement.setInt(2, transactionModel.getUserId());
            preparedStatement.setString(3, transactionModel.getPaymentMethod());
            preparedStatement.setString(4, transactionModel.getEmail());
            preparedStatement.setInt(5, transactionModel.getTicketAmount());
            preparedStatement.setDouble(6, transactionModel.getTransactionAmount());
            preparedStatement.setString(7, transactionModel.getDiscountCode());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction created successfully!");
                 gigId = findGig(gigId);
                TicketProcessor.createTicket(transactionModel,gigId, transactionModel.getTicketAmount() );
            } else {
                System.out.println("Failed to create transaction.");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void watchGig(AccountModel accountModel, int ticketNumber)
    {
        String sql = "INSERT INTO gigcatalog(gigCatalogId, gigId, userId, watchStatus, watchThreshold) VALUES(?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            int gigId = TicketProcessor.findGigIdBasedOnTicketNumber(ticketNumber);
            int userId = AccountProcess.findAccountId(accountModel);

            preparedStatement.setInt(1, generateUniqueGigCatalogId());

            preparedStatement.setInt(2, gigId);

            preparedStatement.setInt(3, userId);

            preparedStatement.setBoolean(4, true);

            Time time = Time.valueOf("00:40:00");
            preparedStatement.setTime(5, time);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("you have watched the Gig!");
                TicketProcessor.incrementUserShopPoints(userId);
            } else {
                System.out.println("Was not able to watch the gig");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean returnBooleanValue(int value)
    {
        if(value == 0)
        {
            return false;
        }
        return true;
    }

    public static int generateUniqueTransactionId() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateTransactionId(); // Assuming you have a method to generate gig IDs
            idExists = findIfTransactionIdExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }
    private static int generateTransactionId()
    {
        int min = 100000; int max = 999999;

        return (int)(Math.random()*(max - min+1) +min);
    }

    public static boolean findIfTransactionIdExists(int transactionId) {
        String sqlQuery = "SELECT * FROM transaction WHERE transactionId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, transactionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int generateUniqueGigCatalogId() {
        int generatedId;
        boolean idExists;
        do {
            generatedId = generateGigCatalogId(); // Assuming you have a method to generate gig IDs
            idExists = findIfGigCatalogIdExists(generatedId); // Method to check if the ID already exists
        } while (idExists);
        return generatedId;
    }
    public static int generateGigCatalogId()
    {
        int min = 10000; int max = 99999;

        return (int)(Math.random()*(max - min+1) +min);
    }
    public static boolean findIfGigCatalogIdExists(int gigCatalogId) {
        String sqlQuery = "SELECT * FROM gigcatalog WHERE gigCatalogId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, gigCatalogId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the gigId already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
