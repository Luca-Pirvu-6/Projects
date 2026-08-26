package com.luca.football.model;

import java.time.LocalDate;
import java.util.Objects;

public class Season {
    private  final Long id;
    private  final String name;
    private  final League league;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Season(Long id, String name, League league, LocalDate startDate, LocalDate endDate) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive non-null number");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (league == null) {
            throw new IllegalArgumentException("League cannot be null");
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date and both cannot be null");
        }
        this.id = id;
        this.name = name;
        this.league = league;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public League getLeague() {
        return league;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return Objects.equals(id, season.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", league=" + league.getName() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
