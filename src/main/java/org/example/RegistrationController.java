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

public class RegistrationController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public AnchorPane RegScene;

    public void regButton() throws IOException {
        String loginField = this.loginField.getText();
        String passwordField = this.passwordField.getText();
        String nameField = this.nameField.getText();
        if (loginField.isEmpty()) {
            App.showAlert("INFO", "Enter login", Alert.AlertType.INFORMATION);
            return;
        }
        if (passwordField.isEmpty()) {
            App.showAlert("INFO", "Enter password!", Alert.AlertType.INFORMATION);
            return;
        }
        if (nameField.isEmpty()) {
            App.showAlert("INFO", "Enter name!", Alert.AlertType.INFORMATION);
            return;
        }
        UserRepository userRepository = new UserRepository();
        userRepository.regUser(loginField, passwordField, nameField);
        if (userRepository.getUser() != null) {
            App.showAlert("INFO", "User registered!", Alert.AlertType.INFORMATION);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "authorizationForm.fxml"
                    )
            );
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.show();
            Stage window = (Stage) RegScene.getScene().getWindow();
            window.close();
        } else {
            App.showAlert("INFO", userRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }
}
