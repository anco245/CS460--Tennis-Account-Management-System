package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSignIn() throws IOException {
        App.setRoot("signIn");
    }
}
