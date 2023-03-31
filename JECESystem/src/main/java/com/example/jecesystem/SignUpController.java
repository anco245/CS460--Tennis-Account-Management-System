package com.example.jecesystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class SignUpController
{
    @FXML
    private void switchToLogin() throws IOException
    {
        App.setRoot("login");
    }
}
