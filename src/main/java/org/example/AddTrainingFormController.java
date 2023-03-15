package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.model.Apprentice;
import org.model.Trainer;
import org.repository.TrainerScheduleRepository;
import org.repository.TrainingRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class AddTrainingFormController {
    @FXML
    public ComboBox<Trainer> boxTrainer;
    @FXML
    public DatePicker calendar;
    @FXML
    public ComboBox<LocalTime> selectTime;
    @FXML
    public ComboBox<Integer> boxNumberOfGym;
    @FXML
    public AnchorPane scene;
    private Apprentice apprentice;
    private ArrayList<Trainer> trainers;

    public void initialize(ArrayList<Trainer> trainers, Apprentice apprentice) throws IOException {
        this.apprentice = apprentice;
        this.trainers = trainers;
        this.boxTrainer.setItems(FXCollections.observableList(trainers));
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.listOfGym();
        ArrayList<Integer> listOfGym = trainingRepository.getListOfGym();
        if (listOfGym != null && trainingRepository.getErrorMess().equals("")) {
            this.boxNumberOfGym.setItems(FXCollections.observableList(listOfGym));
        }
        calendar.setOnMouseClicked(mouseEvent -> {
            if (boxTrainer.getSelectionModel().getSelectedItem() == null) {
                App.showAlert("INFO", "Select a trainer!", Alert.AlertType.INFORMATION);
            }
        });
    }

    public void addTraining() throws IOException {
        LocalDate date = this.calendar.getValue();
        Trainer selectedTrainer = this.boxTrainer.getSelectionModel().getSelectedItem();
        LocalTime selectedTime = this.selectTime.getSelectionModel().getSelectedItem();
        Integer selectedNumberOfGym = this.boxNumberOfGym.getSelectionModel().getSelectedItem();
        if (date == null) {
            App.showAlert("INFO", "Select date!", Alert.AlertType.INFORMATION);
            return;
        }
        if (selectedTrainer == null) {
            App.showAlert("INFO", "Select trainer!", Alert.AlertType.INFORMATION);
            return;
        }
        if (selectedTime == null) {
            App.showAlert("INFO", "Select a training time!", Alert.AlertType.INFORMATION);
            return;
        }
        if (selectedNumberOfGym == null) {
            App.showAlert("INFO", "Select a training gym!", Alert.AlertType.INFORMATION);
            return;
        }
        TrainingRepository trainingRepository = new TrainingRepository();
        trainingRepository.addTraining(this.apprentice.getId(), selectedTrainer.getId(),
                selectedNumberOfGym, selectedTime.toString(), date.toString());
        if (trainingRepository.getTraining() != null && trainingRepository.getErrorMess().equals("")) {
            App.showAlert("INFO", "Training successfully added!", Alert.AlertType.INFORMATION);
            fxmlLoader();
        } else {
            App.showAlert("INFO", trainingRepository.getErrorMess(), Alert.AlertType.INFORMATION);
        }
    }

    public void boxTrainer() throws IOException {
        TrainerScheduleRepository trainerScheduleRepository = new TrainerScheduleRepository();
        trainerScheduleRepository.getTrainerScheduleList(this.boxTrainer.getSelectionModel().getSelectedItem().getId());
        if (trainerScheduleRepository.getSchedules().size() == 0) {
            App.showAlert("INFO", "Trainer has no schedule!", Alert.AlertType.INFORMATION);
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
        } else {
            Map<String, Boolean> workDay = trainerScheduleRepository.getSchedules().stream().
                    collect(Collectors.toMap(x -> x.getDayWeek().toUpperCase(Locale.ROOT), x -> x.getStart() != null));
            calendar.setDayCellFactory(new Callback<>() {
                @Override
                public DateCell call(DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate localDate, boolean b) {
                            super.updateItem(localDate, b);
                            setDisable(!workDay.get(localDate.getDayOfWeek().toString()));
                            if (!workDay.get(localDate.getDayOfWeek().toString())) {
                                setStyle("-fx-background-color: #d07684;");
                            }
                        }
                    };
                }
            });
        }
    }

    public void calendar() throws IOException {
        LocalDate value = this.calendar.getValue();
        if (value != null) {
            TrainerScheduleRepository trainerScheduleRepository = new TrainerScheduleRepository();
            trainerScheduleRepository.getTimeStartEnd(boxTrainer.getSelectionModel().getSelectedItem().getId(), value.toString());
            LocalTime[] timeStartEnd = trainerScheduleRepository.getTimeStartEnd();
            if (timeStartEnd[0] != null && timeStartEnd[1] != null && trainerScheduleRepository.getErrorMess().equals("")) {
                selectTime.setItems(FXCollections.observableList(setTime(timeStartEnd)));
            } else {
                App.showAlert("INFO", trainerScheduleRepository.getErrorMess(), Alert.AlertType.INFORMATION);
            }
        }
    }

    private ArrayList<LocalTime> setTime(LocalTime[] localTimes) {
        ArrayList<LocalTime> setTimes = new ArrayList<>();
        LocalTime start = localTimes[0];
        LocalTime end = localTimes[1];
        for (LocalTime i = start; i.plusMinutes(90).isBefore(end)
                || i.plusMinutes(90).equals(end); i = i.plusMinutes(90)) {
            setTimes.add(i);
        }
        return setTimes;
    }

    public void buttonBack() throws IOException {
        fxmlLoader();
    }

    private void fxmlLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "updateDelApprenticeAddDelTraining.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        UpdateDelApprenticeAddDelTrainingController UpdateDelApprenticeAddDelTrainingController = loader.getController();
        UpdateDelApprenticeAddDelTrainingController.initialize(this.apprentice, this.trainers);
        stage.show();
        Stage window = (Stage) scene.getScene().getWindow();
        window.close();
    }
}
