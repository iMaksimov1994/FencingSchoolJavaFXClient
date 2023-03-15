package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.Apprentice;
import org.model.Trainer;
import org.model.Training;
import org.repository.ApprenticeRepository;
import org.repository.TrainingRepository;

import java.io.IOException;
import java.util.ArrayList;

import static org.example.UpdateDelTrainerUpdateScheduleController.checkSurname;

public class UpdateDelApprenticeAddDelTrainingController {
    @FXML
    public TextField surnameFiled;
    @FXML
    public TextField nameField;
    @FXML
    public TextField patronymicField;
    @FXML
    public TextField phNumberField;
    @FXML
    public AnchorPane scene;
    @FXML
    public ListView<Training> listTraining;
    private Apprentice apprentice;
    private ArrayList<Trainer> trainers;

    public void initialize(Apprentice selectedApprentice, ArrayList<Trainer> trainers) throws IOException {
        this.apprentice = selectedApprentice;
        this.surnameFiled.setText(apprentice.getSurname());
        this.nameField.setText(apprentice.getName());
        this.patronymicField.setText(apprentice.getPatronymic());
        this.phNumberField.setText(apprentice.getPhoneNumber());
        this.trainers = trainers;
        setListOfTraining();
    }

    private void setListOfTraining() throws IOException {
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.getTrainingsByIdTrainer(apprentice.getId());
        ArrayList<Training> trainings = trainingRepository.getTrainings();
        this.listTraining.setItems(FXCollections.observableList(trainings));
    }

    public void updateApprentice() throws IOException {
        String surnameFiled = this.surnameFiled.getText();
        String nameField = this.nameField.getText();
        String patronymicField = this.patronymicField.getText();
        String phNumberField = this.phNumberField.getText();
        if (checkField(surnameFiled, nameField, patronymicField)) return;
        if (phNumberField.isEmpty()) {
            App.showAlert("INFO", "Enter phone number!", Alert.AlertType.INFORMATION);
        }
        ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
        apprenticeRepository.updateApprentice(surnameFiled, nameField, patronymicField, phNumberField, this.apprentice.getId());
        if (apprenticeRepository.getApprentice() != null && apprenticeRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Apprentice updated!", Alert.AlertType.INFORMATION);
        } else {
            App.showAlert("INFO", apprenticeRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }

    static boolean checkField(String surnameFiled, String nameField, String patronymicField) {
        return checkSurname(surnameFiled, nameField, patronymicField);
    }

    public void deleteApprentice() throws IOException {
        ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
        apprenticeRepository.deleteApprentice(this.apprentice.getId());
        if (apprenticeRepository.getApprentice() != null && apprenticeRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Apprentice deleted!", Alert.AlertType.INFORMATION);
            fxmlLoader();
        } else {
            App.showAlert("INFO", apprenticeRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            fxmlLoader();
        }
    }

    void fxmlLoader() throws IOException {
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
        Stage window = (Stage) scene.getScene().getWindow();
        window.close();
    }

    public void addTraining() throws IOException {
        if (this.trainers == null || this.trainers.size() == 0) {
            App.showAlert("INFO", "List of trainers is empty," +
                    " go back to the main menu and add a trainer!", Alert.AlertType.INFORMATION);
            fxmlLoader();
        } else {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "addTrainingForm.fxml"
                    )
            );
            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.setScene(
                        new Scene(loader.load())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AddTrainingFormController addTrainingFormController = loader.getController();
            addTrainingFormController.initialize(this.trainers, this.apprentice);
            stage.show();
            Stage window = (Stage) scene.getScene().getWindow();
            window.close();
        }
    }

    public void deleteTraining() throws IOException {
        Training selectedTraining = this.listTraining.getSelectionModel().getSelectedItem();
        if (selectedTraining == null) {
            App.showAlert("INFO", "Select training!", Alert.AlertType.INFORMATION);
            return;
        }
        if (listTraining.getItems().size() == 0) {
            App.showAlert("INFO", "List of training is empty!", Alert.AlertType.INFORMATION);
            return;
        }
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.deleteTraining(selectedTraining.getId());
        if (trainingRepository.getTraining() != null && trainingRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Training deleted!", Alert.AlertType.INFORMATION);
            setListOfTraining();
        } else {
            App.showAlert("INFO", trainingRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }

    public void buttonBack() throws IOException {
        fxmlLoader();
    }
}
