package org.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.Apprentice;
import org.model.Trainer;
import org.repository.ApprenticeRepository;
import org.repository.TrainerRepository;
import org.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class MainFormController {
    @FXML
    public ListView<Apprentice> listApprentice;
    @FXML
    public ListView<Trainer> listTrainer;
    @FXML
    public AnchorPane mainScene;
    @FXML
    private TrainerRepository trainerRepository;
    @FXML
    private ArrayList<Trainer> trainers;

    public void initialize() throws IOException {
        this.trainerRepository = new TrainerRepository();
        setTrainers();
        Preferences preferences = Preferences.userRoot();
        String idU = preferences.get("fencingSchoolLogin", null);
        if (!idU.equals("null")) {
            ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
            apprenticeRepository.getApprenticesAll();
            ArrayList<Apprentice> apprentices = apprenticeRepository.getApprentices();
            if (apprentices != null && apprenticeRepository.getErrorMess().equals("")) {
                listApprentice.setItems(FXCollections.observableList(apprentices));
                listApprentice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Apprentice> observableValue, Apprentice apprentice, Apprentice t1) {
                        Apprentice selectedApprentice = listApprentice.getSelectionModel().getSelectedItem();
                        if (selectedApprentice != null) {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource(
                                            "updateDelApprenticeAddDelTraining.fxml"
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
                            UpdateDelApprenticeAddDelTrainingController updateDelApprenticeAddDelTrainingController = loader.getController();
                            try {
                                updateDelApprenticeAddDelTrainingController.initialize(selectedApprentice, trainers);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            stage.show();
                            Stage window = (Stage) mainScene.getScene().getWindow();
                            window.close();
                        }
                    }
                });
            } else {
                listApprentice.setItems(FXCollections.observableList(new ArrayList<>()));
                App.showAlert("INFO", apprenticeRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
            if (this.trainers != null && this.trainerRepository.getErrorMess().equals("")) {
                listTrainer.setItems(FXCollections.observableList(trainers));
                listTrainer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Trainer> observableValue, Trainer trainer, Trainer t1) {
                        Trainer selectedTrainer = listTrainer.getSelectionModel().getSelectedItem();
                        if (selectedTrainer != null) {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource(
                                            "updateDelTrainerUpdateSchedule.fxml"
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
                            UpdateDelTrainerUpdateScheduleController updateDelTrainerUpdateScheduleController = loader.getController();
                            updateDelTrainerUpdateScheduleController.initialize(selectedTrainer);
                            stage.show();
                            Stage window = (Stage) mainScene.getScene().getWindow();
                            window.close();
                        }
                    }
                });
            } else {
                listTrainer.setItems(FXCollections.observableList(new ArrayList<>()));
                App.showAlert("INFO", trainerRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
        } else {
            App.showAlert("INFO", "You shall be logged in to continue!", Alert.AlertType.INFORMATION);
        }
    }

    private void setTrainers() throws IOException {
        this.trainerRepository.getTrainersAll();
        ArrayList<Trainer> trainers = trainerRepository.getTrainers();
        if (trainers != null && trainerRepository.getErrorMess().equals("")) {
            this.trainers = trainers;
        } else {
            this.trainers = new ArrayList<>();
        }
    }

    public void AddToSystem() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "addTrainerOrApprenticeForm.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        stage.show();
        Stage window = (Stage) mainScene.getScene().getWindow();
        window.close();
    }

    public void reauthorization() {
        Preferences preferences = Preferences.userRoot();
        preferences.put("fencingSchoolLogin", "null");
        Stage window = (Stage) mainScene.getScene().getWindow();
        window.close();
    }

    public void deleteUser() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String idU = preferences.get("fencingSchoolLogin", null);
        if (!idU.equals("null")) {
            UserRepository userRepository = new UserRepository();
            userRepository.deleteUser(Long.parseLong(idU));
            if (userRepository.getUser() != null && userRepository.getErrorMess().equals("")) {
                App.showAlert("INFO", "User successfully deleted!", Alert.AlertType.INFORMATION);
                this.listTrainer.setItems(FXCollections.observableList(new ArrayList<>()));
                this.listApprentice.setItems(FXCollections.observableList(new ArrayList<>()));
            } else {
                App.showAlert("INFO", userRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
        } else {
            App.showAlert("INFO", "You shall be logged in to continue!", Alert.AlertType.INFORMATION);
        }
    }
}



