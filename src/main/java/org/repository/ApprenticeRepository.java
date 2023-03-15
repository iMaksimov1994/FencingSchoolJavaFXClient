package org.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.model.Apprentice;
import org.utills.Constants;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ApprenticeRepository {
    private Apprentice apprentice;
    private ArrayList<Apprentice> apprentices;
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

    public void addApprentice(String surname, String name, String patronymic, String phoneNumber) throws IOException {
        getData(Constants.SERVER_URL + "/apprentice/surname/" + surname + "/name/" +
                name + "/patronymic/" + patronymic + "/phoneNumber/" + phoneNumber, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.apprentice = objectMapper.readValue(this.inputStream, Apprentice.class);
        }
    }

    public void getApprenticesAll() throws IOException {
        getData(Constants.SERVER_URL + "/apprentice/", "GET");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.apprentices = objectMapper.readValue(this.inputStream, new TypeReference<>() {
            });
        }
    }

    public void deleteApprentice(long id) throws IOException {
        getData(Constants.SERVER_URL + "/apprentice/id/" + id, "DELETE");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.apprentice = objectMapper.readValue(this.inputStream, Apprentice.class);
        }
    }

    public void updateApprentice(String surname, String name, String patronymic, String phoneNumber, long id) throws IOException {
        getData(Constants.SERVER_URL + "/apprentice/surname/" + surname + "/name/"
                + name + "/patronymic/" + patronymic + "/phoneNumber/" + phoneNumber + "/id/" + id, "PUT");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.apprentice = objectMapper.readValue(this.inputStream, Apprentice.class);
        }
    }

    public Apprentice getApprentice() {
        return apprentice;
    }

    public String getErrorMess() {
        return errorMess;
    }

    public ArrayList<Apprentice> getApprentices() {
        return apprentices;
    }
}
