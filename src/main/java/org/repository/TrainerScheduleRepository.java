package org.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.model.Schedule;
import org.model.TrainerSchedule;
import org.utills.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TrainerScheduleRepository {
    private TrainerSchedule trainerSchedule;
    private ArrayList<Schedule> schedules;
    private LocalTime[] timeStartEnd;
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

    public void updateSchedule(long id, String dayWeek, String start, String end) throws IOException {
        getData(Constants.SERVER_URL + "/trainerSchedule/" + id + "?dayWeek=" +
                dayWeek + "&start=" + start + "&end=" + end, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.trainerSchedule = objectMapper.readValue(this.inputStream, TrainerSchedule.class);
        }
    }

    public void getTrainerScheduleList(long id) throws IOException {
        getData(Constants.SERVER_URL + "/trainerSchedule/list/" + id, "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.schedules = objectMapper.readValue(this.inputStream, new TypeReference<>() {
            });
        }
    }

    public void getTimeStartEnd(long idT, String localDate) throws IOException {
        getData(Constants.SERVER_URL + "/trainerSchedule/idT/" + idT + "/localDate/" + localDate, "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.timeStartEnd = objectMapper.readValue(this.inputStream, LocalTime[].class);
        }
    }

    public void deleteScheduleByDay(long id, String dayWeek) throws IOException {
        getData(Constants.SERVER_URL + "/trainerSchedule/" + id + "?dayWeek=" + dayWeek, "DELETE");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            this.trainerSchedule = objectMapper.readValue(this.inputStream, TrainerSchedule.class);
        }
    }


    public TrainerSchedule getTrainerSchedule() {
        return trainerSchedule;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public LocalTime[] getTimeStartEnd() {
        return timeStartEnd;
    }

    public String getErrorMess() {
        return errorMess;
    }
}
