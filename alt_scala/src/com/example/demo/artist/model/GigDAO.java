package com.example.demo.artist.model;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import com.example.demo.sharedObjects.DbConnect;

public class GigDAO {
    private static final String SELECT_GIG_BY_ID_SQL = "SELECT * FROM gig WHERE gigId = ?";
    private static final String UPDATE_GIG_SQL = "UPDATE gig SET gigDescription = ?, gigDate = ?, gigScheduleStart = ?, gigScheduleEnd = ? WHERE gigId = ?";
    private static final String DELETE_GIG_SQL = "DELETE FROM gig WHERE gigId = ?";
    private static final String DOES_GIG_EXIST_SQL = "SELECT 1 FROM gig WHERE gigId = ?";
    private static final String VIEW_ALL_GIG_SQL = "select * from gig";
    private static final String SAVE_GIG_TO_DATABASE_SQL = "INSERT INTO gig (gigId, artistName, gigDescription, artistType, gigPrice, gigStatus, gigDate, gigScheduleStart, gigScheduleEnd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_MAX_GIGID_SQL = "SELECT MAX(gigId) FROM gig";

    public static boolean doesGigExist(int gigId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DOES_GIG_EXIST_SQL)) {
            preparedStatement.setInt(1, gigId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If there's a result, the gig exists; otherwise, it doesn't.
        }
    }


    public static List<GigModel> viewAllGig() throws SQLException {
        Connection connection = DbConnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(VIEW_ALL_GIG_SQL);

        ArrayList<GigModel> arrayList = new ArrayList<>();
        boolean gigsFound = false; // Variable to track if any gigs are found

        while (resultSet.next()) {
            gigsFound = true; // Set to true if at least one gig is found

            GigModel gigModel = new GigModel();
            int gigId = resultSet.getInt("gigId");
            String artistName = resultSet.getString("artistName");
            String gigDescription = resultSet.getString("gigDescription");
            boolean gigStatus = resultSet.getInt("gigStatus") != 0;
            String date = resultSet.getString("gigDate");
            String gigStart = resultSet.getString("gigScheduleStart");
            String gigEnd = resultSet.getString("gigScheduleEnd");

            gigModel.setGigId(gigId);
            gigModel.setArtistName(artistName);
            gigModel.setGigDescription(gigDescription);
            gigModel.setGigStatus(gigStatus);
            gigModel.setGigDate(date);
            gigModel.setGigStart(gigStart);
            gigModel.setGigEnd(gigEnd);

            arrayList.add(gigModel);
        }

        if (!gigsFound) {
            System.out.println("No gigs found.");
        } else {
            for (GigModel model : arrayList) {
                System.out.println("Gig ID: " + model.getGigId());
                System.out.println("Artist Name: " + model.getArtistName());
                System.out.println("Gig Description: " + model.getGigDescription());
                System.out.println("Gig Status: " + (model.isGigStatus() ? "Active" : "Inactive"));
                System.out.println("Gig Date: " + model.getGigDate());
                System.out.println("Gig Start time: " + model.getGigStart());
                System.out.println("Gig End time: " + model.getGigEnd());
                System.out.println();
            }
        }

        return null;
    }

    public static GigModel getGigById(int gigId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GIG_BY_ID_SQL)) {
            preparedStatement.setInt(1, gigId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                GigModel gig = new GigModel();
                gig.setGigId(resultSet.getInt("gigId"));
                gig.setArtistName(resultSet.getString("artistName"));
                gig.setGigDescription(resultSet.getString("gigDescription"));
                gig.setGigDate(resultSet.getString("gigDate"));
                gig.setGigStart(resultSet.getString("gigScheduleStart"));
                gig.setGigEnd(resultSet.getString("gigScheduleEnd"));
                return gig;
            }
        }
        return null;
    }

    public static boolean updateGig(int gigId, GigModel gig) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GIG_SQL)) {
            preparedStatement.setString(1, gig.getGigDescription());
            preparedStatement.setString(2, gig.getGigDate());
            preparedStatement.setString(3, gig.getGigStart());
            preparedStatement.setString(4, gig.getGigEnd());
            preparedStatement.setInt(5, gigId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static boolean deleteGig(int gigId) throws SQLException {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GIG_SQL)) {
            preparedStatement.setInt(1, gigId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static void createGig() {
        GigModel gig = new GigModel();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter gig details:");
        System.out.print("Artist Name: ");
        String artistName = scanner.nextLine();


        System.out.print("Gig Description: ");
        String gigDescription = scanner.nextLine();
        System.out.print("Enter 0 if the artist is a solo singer or 1 if in a band:");
        boolean artistType = false; // Default value assuming solo singer
        int numChoice;

        while (true) {
            try {
                numChoice = scanner.nextInt();
                if (numChoice == 0 || numChoice == 1) {
                    artistType = numChoice == 1;
                    break; // Exit loop if input is valid
                } else {
                    System.out.println("Please enter either 0 or 1.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter either 0 or 1.");
                scanner.nextLine(); // Clear buffer
            }
        }
        System.out.print("Gig Price: ");
        double gigPrice = 0;
        while (true) {
            try {
                gigPrice = scanner.nextDouble();
                if (gigPrice < 0) { //for handling negative numbers
                    throw new IllegalArgumentException("Gig price cannot be negative Please input a positive number");
                }


                break; // Exit the loop if valid positive number is entered
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price (numbers only).");
                scanner.nextLine(); // Clear the scanner buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());  // Print the specific error message
            }
        }

        boolean validDate = false;
        String gigDate = null;
        boolean gigStatus = false;
        while (!validDate) {
            try {

                System.out.print("Gig Date (YYYY-MM-DD): ");
                gigDate = scanner.next();
                validateDate(gigDate);  // Call method to validate date format
                LocalDate gigDates = LocalDate.parse(gigDate);
                gigStatus = determineGigStatus(gigDates);


                if (gigExistsForArtistOnDate(artistName, gigDate)) {
                    System.out.println("This artist already has a gig scheduled for " + gigDate + "., Select a different date.");
                    return; // Exit the method if the artist already has a gig on the specified date
                }


                validDate = true;
            } catch (InvalidDateFormatException e) {
                System.err.println("Error: Invalid date format. Please enter in YYYY-MM-DD format.");
            } catch (InputMismatchException e) {
                System.err.println("Error: Invalid input. Please enter a valid date.");
                scanner.nextLine(); // Clear the scanner buffer to avoid infinite loop
            }
        }

        boolean validStartTime = false;
        String gigStart = "";
        while (!validStartTime) {
            try {
                System.out.print("Gig Start Time (HH:MM:SS): ");
                gigStart = scanner.next();
                validateTime(gigStart);// Call method to validate time format

                validStartTime = true;

            } catch (InvalidTimeFormatException e) {
                System.err.println("Error: Invalid time format. Please enter in HH:MM:SS format.");
            } catch (InputMismatchException e) {
                System.err.println("Error: Invalid input. Please enter a valid time.");
                scanner.nextLine(); // Clear the scanner buffer
            }
        }

        boolean validEndTime = false;
        String gigEnd = "";
        while (!validEndTime) {
            try {
                System.out.print("Gig End Time (HH:MM:SS): ");
                gigEnd = scanner.next();
                validateTime(gigEnd);  // Reuse validateTime for consistency
                validEndTime = true;
            } catch (InvalidTimeFormatException e) {
                System.err.println("Error: Invalid time format. Please enter in HH:MM:SS format.");
            } catch (InputMismatchException e) {
                System.err.println("Error: Invalid input. Please enter a valid time.");
                scanner.nextLine(); // Clear the scanner buffer
            }
        }

        // Process valid date, start time, and end time here (assuming they are stored in variables)
        System.out.println("Gig details successfully created!");
        int gigId = getRandomAvailableGigId();
        //Creates a new gigId
        GigModel gigModel = new GigModel(gigId, artistName, gigDescription, artistType, gigPrice, gigStatus, gigDate, gigStart, gigEnd);
        try {
            saveGigToDatabase(gigModel);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Gig details inserted successfully with gigId = " + gigId + ".");
    }


    private static void saveGigToDatabase(GigModel gigModel) throws ParseException {
        try {
            // Load the MySQL JDBC driver and establish connection
            Connection connection = DbConnect.getConnection();

            // Prepare the insert query
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_GIG_TO_DATABASE_SQL);


            preparedStatement.setInt(1, gigModel.getGigId());
            preparedStatement.setString(2, gigModel.getArtistName());
            preparedStatement.setString(3, gigModel.getGigDescription());
            preparedStatement.setBoolean(4, gigModel.isArtistType());
            preparedStatement.setDouble(5, gigModel.getGigPrice());
            preparedStatement.setBoolean(6, gigModel.isGigStatus());
            preparedStatement.setDate(7, Date.valueOf(gigModel.getGigDate()));
            preparedStatement.setTime(8, Time.valueOf(gigModel.getGigStart()));
            preparedStatement.setTime(9, Time.valueOf(gigModel.getGigEnd()));
            // Execute the insert query
            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the next available gigId
    public static int getNextAvailableGigId() {
        int nextAvailableGigId = 0; // Default value if no gigId is found

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Connect to your database
            connection = DbConnect.getConnection();

            // SQL query to get the maximum gigId

            // Prepare the statement
            preparedStatement = connection.prepareStatement(GET_MAX_GIGID_SQL);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // If result set contains a row
            if (resultSet.next()) {
                // Get the maximum gigId
                int maxGigId = resultSet.getInt(1);

                // Increment it to get the next available gigId
                nextAvailableGigId = maxGigId + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nextAvailableGigId;
    }

    public static void validateDate(String date) throws InvalidDateFormatException {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidDateFormatException();
        }
    }

    public static void validateTime(String time) throws InvalidTimeFormatException {
        if (!time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new InvalidTimeFormatException();
        }
    }

    private static boolean gigExistsForArtistOnDate(String artistName, String gigDate) {
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM gig WHERE artistName = ? AND gigDate = ?")) {
            preparedStatement.setString(1, artistName);
            preparedStatement.setString(2, gigDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if a gig exists for the artist on the specified date
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false by default or in case of any errors
    }

    private static boolean determineGigStatus(LocalDate gigDate) {
        LocalDate today = LocalDate.now();
        if (gigDate.isEqual(today)) {
            return true; // Gig date is today, set gig status to active (1)
        } else if (gigDate.isAfter(today)) {
            return false; // Gig date is in the future, set gig status to inactive (0)
        } else {
            // Gig date is in the past, handle this case based on your requirement
            // For now, assuming past gigs are also considered inactive
            return false;
        }
    }

    public static int getRandomAvailableGigId() {
        int min = 100000;
        int max = 999999;

        int finalGigId = (int) (Math.random() * (max - min + 1) + min);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Connect to your database
            connection = DbConnect.getConnection();

            // Check if the random gigId is already taken
            preparedStatement = connection.prepareStatement(DOES_GIG_EXIST_SQL);
            preparedStatement.setInt(1, finalGigId);
            resultSet = preparedStatement.executeQuery();
            // If the result set contains a row
            if (resultSet.next()) {
                // Generate a new random gigId
                return finalGigId;
            }
            // If we reach here, the random gigId is available
            return finalGigId;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // In case of any errors, return a negative value
        return -1;
    }
}
