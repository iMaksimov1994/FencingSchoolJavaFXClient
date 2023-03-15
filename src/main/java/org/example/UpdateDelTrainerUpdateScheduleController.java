package org.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.Schedule;
import org.model.Trainer;
import org.model.TrainerSchedule;
import org.repository.TrainerRepository;
import org.repository.TrainerScheduleRepository;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class UpdateDelTrainerUpdateScheduleController {
    @FXML
    public TextField surname;
    @FXML
    public TextField name;
    @FXML
    public TextField patronymic;
    @FXML
    public TextField experience;
    @FXML
    public TableView<Schedule> tableSchedule;
    @FXML
    public TableColumn<Schedule, String> dayWeek;
    @FXML
    public TableColumn<Schedule, LocalTime> startTime;
    @FXML
    public TableColumn<Schedule, LocalTime> endTime;
    @FXML
    public AnchorPane scene;
    private Trainer trainer;

    public void initialize(Trainer selectedTrainer) {
        this.trainer = selectedTrainer;
        this.surname.setText(this.trainer.getSurname());
        this.name.setText(this.trainer.getName());
        this.patronymic.setText(this.trainer.getPatronymic());
        this.experience.setText(String.valueOf(this.trainer.getExperience()));
        TrainerScheduleRepository trainerScheduleRepository = new TrainerScheduleRepository();
        try {
            trainerScheduleRepository.getTrainerScheduleList(this.trainer.getId());
            ArrayList<Schedule> schedules = trainerScheduleRepository.getSchedules();
            if (schedules != null && trainerScheduleRepository.getErrorMess().equals("")) {
                this.dayWeek.setCellValueFactory(new PropertyValueFactory<>("dayWeek"));
                this.startTime.setCellValueFactory(new PropertyValueFactory<>("start"));
                this.endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
                tableSchedule.setItems(FXCollections.observableList(schedules));
            } else {
                tableSchedule.setItems(FXCollections.observableList(new ArrayList<>()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTrainer() throws IOException {
        String surname = this.surname.getText();
        String name = this.name.getText();
        String patronymic = this.patronymic.getText();
        String experience = this.experience.getText();
        if (checkField(surname, name, patronymic)) return;
        if (experience.isEmpty()) {
            App.showAlert("INFO", "Enter experience!", Alert.AlertType.INFORMATION);
        }
        TrainerRepository trainerRepository = new TrainerRepository();
        trainerRepository.updateTrainer(surname, name, patronymic, Integer.parseInt(experience), this.trainer.getId());
        if (trainerRepository.getTrainer() != null && trainerRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Trainer updated!", Alert.AlertType.INFORMATION);
        } else {
            App.showAlert("INFO", trainerRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }

    static boolean checkField(String surname, String name, String experience) {
        return checkSurname(surname, name, experience);
    }

    static boolean checkSurname(String surname, String name, String experience) {
        if (surname.isEmpty()) {
            App.showAlert("INFO", "Enter surname!", Alert.AlertType.INFORMATION);
            return true;
        }
        if (name.isEmpty()) {
            App.showAlert("INFO", "Enter name!", Alert.AlertType.INFORMATION);
            return true;
        }
        if (experience.isEmpty()) {
            App.showAlert("INFO", "Enter patronymic!", Alert.AlertType.INFORMATION);
            return true;
        }
        return false;
    }

    public void deleteTrainer() throws IOException {
        TrainerRepository trainerRepository = new TrainerRepository();
        trainerRepository.deleteTrainer(this.trainer.getId());
        if (trainerRepository.getTrainer() != null && trainerRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Trainer deleted!", Alert.AlertType.INFORMATION);
            fxmlLoader();
        } else {
            App.showAlert("INFO", trainerRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            fxmlLoader();
        }
    }

    public void addSchedule() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "addScheduleForm.fxml"
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
        AddScheduleFormController addScheduleFormController = loader.getController();
        addScheduleFormController.setTrainer(this.trainer);
        stage.showAndWait();
        Stage window = (Stage) scene.getScene().getWindow();
        window.close();
    }

    public void deleteSchedule() throws IOException {
        Schedule selectedSchedule = (Schedule) this.tableSchedule.getSelectionModel().getSelectedItem();
        if (selectedSchedule == null || selectedSchedule.getStart() == null || selectedSchedule.getEnd() == null) {
            App.showAlert("INFO", "Please select a schedule for the day!", Alert.AlertType.INFORMATION);
        } else {
            TrainerScheduleRepository trainerScheduleRepository = new TrainerScheduleRepository();
            trainerScheduleRepository.deleteScheduleByDay(this.trainer.getId(), selectedSchedule.getDayWeek());
            TrainerSchedule trainerSchedule = trainerScheduleRepository.getTrainerSchedule();
            if (trainerSchedule != null && trainerScheduleRepository.getErrorMess().
                    equals("")) {
                ArrayList<Schedule> schedules = trainerSchedule.scheduleList();
                if (schedules != null) {
                    this.tableSchedule.setItems(FXCollections.observableList(schedules));
                    fxmlLoader();
                }
            } else {
                App.showAlert("INFO", trainerScheduleRepository.getErrorMess(), Alert.AlertType.INFORMATION);
                fxmlLoader();
            }
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
        Stage window = (Stage) scene.getScene().getWindow();
        window.close();
    }

    public void buttonBack() throws IOException {
        fxmlLoader();
    }
}
