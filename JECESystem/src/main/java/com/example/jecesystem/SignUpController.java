package com.example.jecesystem;

import java.io.IOException;

public class SignUpController
{
    @FXML
    private void switchToLogin() throws IOException
    {
        App.setRoot("login");
    }
}
