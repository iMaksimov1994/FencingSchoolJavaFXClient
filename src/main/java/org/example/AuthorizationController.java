package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.repository.UserRepository;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField loginField;
    @FXML
    public AnchorPane AuthScene;

    public void buttonSign() throws IOException {
        String loginField = this.loginField.getText();
        String passwordField = this.passwordField.getText();

        if (loginField.isEmpty()) {
            App.showAlert("INFO", "Enter login!", Alert.AlertType.INFORMATION);
            return;
        }
        if (passwordField.isEmpty()) {
            App.showAlert("INFO", "Enter password!", Alert.AlertType.INFORMATION);
            return;
        }
        UserRepository userRepository = new UserRepository();
        userRepository.checkRegUser(loginField, passwordField);
        if (userRepository.getUser() != null) {
            Preferences preferences = Preferences.userRoot();
            preferences.put("fencingSchoolLogin", String.valueOf(userRepository.getUser().getId()));

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "mainForm.fxml"
                    )
            );
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
            Stage window = (Stage) AuthScene.getScene().getWindow();
            window.close();
        } else {
            App.showAlert("INFO", "User not registered, please register!", Alert.AlertType.INFORMATION);
            fxmlLoader();
        }
    }

    public void buttonReg() throws IOException {
        fxmlLoader();
    }

    private void fxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "registrationForm.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        stage.showAndWait();
        Stage window = (Stage) AuthScene.getScene().getWindow();
        window.close();
    }
}
