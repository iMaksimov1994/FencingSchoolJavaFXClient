package org.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.model.User;
import org.utills.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserRepository {
    private User user;
    private ArrayList<User> users;
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

    public void regUser(String login, String password, String name) throws IOException {
        getData(Constants.SERVER_URL + "/user/login/" + login + "/password/" + password + "/name/" + name, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.user = objectMapper.readValue(this.inputStream, User.class);
        }
    }

    public void checkRegUser(String login, String password) throws IOException {
        getData(Constants.SERVER_URL + "/user/login/" + login + "/password/" + password, "POST");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.user = objectMapper.readValue(this.inputStream, User.class);
        }
    }

    public void deleteUser(long id) throws IOException {
        getData(Constants.SERVER_URL  + "/user/id/" + id, "DELETE");
        if (this.errorMess.equals("")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.user = objectMapper.readValue(this.inputStream, User.class);
        }
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getErrorMess() {
        return errorMess;
    }
}
