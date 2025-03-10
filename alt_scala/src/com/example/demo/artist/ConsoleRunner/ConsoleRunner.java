package com.example.demo.artist.ConsoleRunner;

import com.example.demo.artist.ConsoleRunnerArtist.ConsoleAccountCreation;
import com.example.demo.artist.model.AccountModel;

import java.sql.SQLException;
import java.text.ParseException;

public class ConsoleRunner {
    public static void main(String[] args) throws SQLException, ParseException {
        ConsoleAccountCreation consoleAccountCreation = new ConsoleAccountCreation();
        consoleAccountCreation.start(new AccountModel());
    }
}
