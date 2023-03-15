package org.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


public class Training {
    private long id;
    private int numberGym;
    private Trainer trainer;
    private Apprentice apprentice;
    private LocalDate date;
    private LocalTime timeStart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberGym() {
        return numberGym;
    }

    public void setNumberGym(int numberGym) {
        this.numberGym = numberGym;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Apprentice getApprentice() {
        return apprentice;
    }

    public void setApprentice(Apprentice apprentice) {
        this.apprentice = apprentice;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return id == training.id && numberGym == training.numberGym && Objects.equals(trainer, training.trainer)
                && Objects.equals(apprentice, training.apprentice) && Objects.equals(date, training.date)
                && Objects.equals(timeStart, training.timeStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberGym, trainer, apprentice, date, timeStart);
    }


    @Override
    public String toString() {
        return
                "Number of gym: " + numberGym +
                        ", Trainer: " + "Name: " + trainer.getName() + ", Surname: " + trainer.getSurname() +
                        ", Date: " + date +
                        ", Time start: " + timeStart;
    }
}
