package com.di.gbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

import static com.di.gbl.connection.Connect.conexionSql;
import static com.di.gbl.connection.Connect.showAlert;

public class LoginController {
    @FXML
    private TextField nombre;
    @FXML
    private PasswordField password;


    public void onLoginButtonClick(ActionEvent actionEvent) throws IOException {
        String name = nombre.getText();
        String pass = password.getText();
        if (nombre.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter your name");
            return;
        }
        if (password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter a password");
        } else {
            try {
                conexionSql(name, "DI", pass);
                URL url = new File("src\\main\\resources\\com\\di\\gbl\\empleados.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Empleados");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Form Error!",
                        "Database connection is not avaliable");
            }

        }
    }
}