package org.model;


import java.util.List;
import java.util.Objects;

public class Apprentice {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private List<Training> trainings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apprentice that = (Apprentice) o;
        return id == that.id && Objects.equals(surname, that.surname) && Objects.equals(name, that.name) &&
                Objects.equals(patronymic, that.patronymic) && Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(trainings, that.trainings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, phoneNumber, trainings);
    }

    @Override
    public String toString() {
        return
                "Surname: '" + surname + '\'' +
                        ", Name: '" + name + '\'' +
                        ", Patronymic: '" + patronymic + '\'' +
                        ", Phone Number: '" + phoneNumber;
    }
}
