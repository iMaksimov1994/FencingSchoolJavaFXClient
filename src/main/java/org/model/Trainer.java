package org.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trainer {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private int experience;
    private TrainerSchedule trainerSchedule;

    private List<Training> trainings = new ArrayList<>();

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

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public TrainerSchedule getTrainerSchedule() {
        return trainerSchedule;
    }

    public void setTrainerSchedule(TrainerSchedule trainerSchedule) {
        this.trainerSchedule = trainerSchedule;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return id == trainer.id && experience == trainer.experience && Objects.equals(surname, trainer.surname)
                && Objects.equals(name, trainer.name) && Objects.equals(patronymic, trainer.patronymic)
                && Objects.equals(trainerSchedule, trainer.trainerSchedule) &&
                Objects.equals(trainings, trainer.trainings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, experience, trainerSchedule, trainings);
    }

    @Override
    public String toString() {
        return
                "Surname: '" + surname + '\'' +
                        ", Name: '" + name + '\'' +
                        ", Patronymic: '" + patronymic + '\'' +
                        ", Experience: " + experience + " years";
    }
}
