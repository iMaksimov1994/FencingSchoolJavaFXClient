package org.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.model.Training;
import org.utills.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainingRepository {
    private Training training;
    private ArrayList<Training> trainings;
    private ArrayList<Integer> listOfGym;

    private String errorMess = "";
    private InputStream inputStream;

    private void getData(String link, String method) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        if (httpURLConnection.getResponseCode() == 400) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()))) {
                this.errorMess = bufferedReader.readLine();
            }
        } else {
            this.inputStream = httpURLConnection.getInputStream();

        }
    }

    public void addTraining(long idApprentice, long idTrainer, int numberOfGym, String start, String date) throws IOException {
        getData(Constants.SERVER_URL + "/training/idApprentice/" + idApprentice + "/idTrainer/" +
                idTrainer + "/numberOfGym/" + numberOfGym + "/start/" + start + "/date/" + date, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.training = objectMapper.readValue(this.inputStream, Training.class);
        }
    }

    public void getTrainingsByIdTrainer(long idT) throws IOException {
        getData(Constants.SERVER_URL + "/training/" + idT, "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.trainings = objectMapper.readValue(this.inputStream, new TypeReference<>() {
            });
        }
    }


    public void deleteTraining(long id) throws IOException {
        getData(Constants.SERVER_URL + "/training/id/" + id, "DELETE");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.training = objectMapper.readValue(this.inputStream, Training.class);
        }
    }

    public void listOfGym() throws IOException {
        getData(Constants.SERVER_URL + "/training/listOfGym", "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.listOfGym = objectMapper.readValue(this.inputStream, new TypeReference<>() {
            });
        }
    }

    public ArrayList<Integer> getListOfGym() {
        return listOfGym;
    }

    public Training getTraining() {
        return training;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public String getErrorMess() {
        return errorMess;
    }


}
