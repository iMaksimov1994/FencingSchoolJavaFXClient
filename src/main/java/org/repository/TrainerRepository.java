package org.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.model.Trainer;
import org.utills.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainerRepository {
    private Trainer trainer;
    private ArrayList<Trainer> trainers;
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

    public void addTrainer(String surname, String name, String patronymic, int experience) throws IOException {
        getData(Constants.SERVER_URL + "/trainer/surname/" + surname + "/name/" +
                name + "/patronymic/" + patronymic + "/experience/" + experience, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.trainer = objectMapper.readValue(this.inputStream, Trainer.class);
        }
    }

    public void getTrainersAll() throws IOException {
        getData(Constants.SERVER_URL + "/trainer", "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.trainers = objectMapper.readValue(this.inputStream, new TypeReference<>() {
            });
        }
    }

    public void deleteTrainer(long id) throws IOException {
        getData(Constants.SERVER_URL + "/trainer/id/" + id, "DELETE");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.trainer = objectMapper.readValue(this.inputStream, Trainer.class);
        }
    }

    public void updateTrainer(String surname, String name, String patronymic, int experience, long id) throws IOException {
        getData(Constants.SERVER_URL + "/trainer/surname/" + surname + "/name/"
                + name + "/patronymic/" + patronymic + "/experience/" + experience + "/id/" + id, "PUT");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.trainer = objectMapper.readValue(this.inputStream, Trainer.class);
        }
    }



    public Trainer getTrainer() {
        return trainer;
    }

    public String getErrorMess() {
        return errorMess;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }
}
