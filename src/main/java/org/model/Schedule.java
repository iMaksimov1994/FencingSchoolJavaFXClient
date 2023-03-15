package org.model;


import java.time.LocalTime;
import java.util.Objects;

public class Schedule {
    private String dayWeek;
    private LocalTime start;
    private LocalTime end;

    public Schedule() {
    }

    public Schedule(String dayWeek, LocalTime start, LocalTime end) {
        this.dayWeek = dayWeek;
        this.start = start;
        this.end = end;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(dayWeek, schedule.dayWeek) && Objects.equals(start, schedule.start)
                && Objects.equals(end, schedule.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayWeek, start, end);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "dayWeek='" + dayWeek + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
