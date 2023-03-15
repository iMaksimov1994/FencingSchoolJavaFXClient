package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.Trainer;
import org.repository.TrainerScheduleRepository;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class AddScheduleFormController {
    @FXML
    public AnchorPane scene;
    @FXML
    private Trainer trainer;
    @FXML
    public ComboBox<String> dayWeekBox;
    @FXML
    public ComboBox<String> startTimeBox;
    @FXML
    public ComboBox<String> endTimeBox;

    public void initialize() {
        setDay();
        setStartTime();
        setEndTime();
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void addSchedule() throws IOException {
        String selectedDayWeek = this.dayWeekBox.getSelectionModel().getSelectedItem();
        String selectedStartTime = this.startTimeBox.getSelectionModel().getSelectedItem();
        String selectedEndTime = this.endTimeBox.getSelectionModel().getSelectedItem();
        if (selectedDayWeek == null) {
            App.showAlert("INFO", "Select day!", Alert.AlertType.INFORMATION);
            return;
        }
        if (selectedStartTime == null) {
            App.showAlert("INFO", "Select start work time!", Alert.AlertType.INFORMATION);
            return;
        }
        if (selectedEndTime == null) {
            App.showAlert("INFO", "Select end work time!", Alert.AlertType.INFORMATION);
            return;
        }
        TrainerScheduleRepository trainerScheduleRepository = new TrainerScheduleRepository();
        trainerScheduleRepository.updateSchedule(trainer.getId(), selectedDayWeek, selectedStartTime, selectedEndTime);
        if (trainerScheduleRepository.getTrainerSchedule() != null && trainerScheduleRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Trainer schedule successfully added/updated", Alert.AlertType.INFORMATION);
            fxmlLoader();
        } else {
            App.showAlert("INFO", trainerScheduleRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }

    private void setDay() {
        String[] dayWeek = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        dayWeekBox.setItems(FXCollections.observableList(Arrays.asList(dayWeek)));
    }

    private void setStartTime() {
        ArrayList<String> startTimes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse("09:00", formatter);
        for (int i = 0; i < 13; i++) {
            startTimes.add(localTime.toString());
            localTime = localTime.plusMinutes(30);
        }
        this.startTimeBox.setItems(FXCollections.observableList(startTimes));
    }

    private void setEndTime() {
        ArrayList<String> endTimes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse("10:30", formatter);
        for (int i = 0; i < 13; i++) {
            endTimes.add(localTime.toString());
            localTime = localTime.plusMinutes(30);
        }
        this.endTimeBox.setItems(FXCollections.observableList(endTimes));
    }

    public void buttonBack() throws IOException {
        fxmlLoader();
    }

    private void fxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "updateDelTrainerUpdateSchedule.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        UpdateDelTrainerUpdateScheduleController UpdateDelTrainerUpdateScheduleController = loader.getController();
        UpdateDelTrainerUpdateScheduleController.initialize(this.trainer);
        stage.show();
        Stage window = (Stage) scene.getScene().getWindow();
        window.close();
    }
}
