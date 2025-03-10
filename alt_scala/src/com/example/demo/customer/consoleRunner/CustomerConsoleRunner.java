package com.example.demo.customer.consoleRunner;

import com.example.demo.customer.consoleController.AccountCreation;
import com.example.demo.customer.model.objectModels.AccountModel;

import java.sql.SQLException;
public class CustomerConsoleRunner {
    public static void main(String[] args) throws SQLException{
        AccountCreation accountCreation = new AccountCreation();
        accountCreation.start(new AccountModel());
    }
}