package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.repository.ApprenticeRepository;
import org.repository.TrainerRepository;

import java.io.IOException;

public class AddTrainerOrApprenticeFormController {
    @FXML
    public TextField surnameField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField patronymicField;
    @FXML
    public TextField setData;
    @FXML
    public RadioButton rdBtnTrainer;
    @FXML
    public AnchorPane AddTrainerOrApprentice;
    @FXML
    public RadioButton rdBtnApprentice;
    @FXML
    private final Label label = new Label();

    private void setLabel(String labelName) {
        label.setLayoutX(198.0);
        label.setLayoutY(247.0);
        label.prefHeight(18);
        label.prefWidth(69);
        label.setFont(new Font("System", 14));
        label.setText(labelName);
    }

    public void initialize() {
        rdBtnTrainer.setSelected(true);
        rdBtnApprentice.setSelected(false);
        setLabel("Experience");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label);
        AddTrainerOrApprentice.getChildren().addAll(label);
    }

    public void rdBtnTrainer() {
        rdBtnApprentice.setSelected(false);
        setLabel("Experience");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label);
        AddTrainerOrApprentice.getChildren().addAll(label);
    }

    public void rdBtnApprentice() {
        rdBtnTrainer.setSelected(false);
        setLabel("Phone Number           ");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label);
        AddTrainerOrApprentice.getChildren().addAll(label);
    }

    public void addPerson() throws IOException {
        String surnameField = this.surnameField.getText();
        String nameField = this.nameField.getText();
        String patronymicField = this.patronymicField.getText();
        String dataField = this.setData.getText();
        if (UpdateDelApprenticeAddDelTrainingController.checkField(surnameField, nameField, patronymicField)) return;

        if (dataField.isEmpty()) {
            App.showAlert("INFO", "Enter data!", Alert.AlertType.INFORMATION);
        }
        if (rdBtnApprentice.isSelected()) {
            ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
            apprenticeRepository.addApprentice(surnameField, nameField, patronymicField, dataField);
            if (apprenticeRepository.getApprentice() != null && apprenticeRepository.getErrorMess().equals("")) {
                App.showAlert("INFO", "Apprentice added!", Alert.AlertType.INFORMATION);
            } else {
                App.showAlert("INFO", apprenticeRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
            fxmlLoader();
        }
        if (rdBtnTrainer.isSelected()) {
            TrainerRepository trainerRepository = new TrainerRepository();
            trainerRepository.addTrainer(surnameField, nameField, patronymicField, Integer.parseInt(dataField));
            if (trainerRepository.getTrainer() != null && trainerRepository.getErrorMess().equals("")) {
                App.showAlert("INFO", "Trainer added!", Alert.AlertType.INFORMATION);
            } else {
                App.showAlert("INFO", trainerRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
            fxmlLoader();
        }
    }

    private void fxmlLoader() throws IOException {
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
        Stage window = (Stage) AddTrainerOrApprentice.getScene().getWindow();
        window.close();
    }

    public void buttonBack() throws IOException {
        fxmlLoader();
    }
}
