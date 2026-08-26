package com.luca.football.model;

import java.util.Objects;

public class League {

    private final Long id;
    private final String name;

    public League(Long id, String name) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive non-null number");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.id = id;
        this.name = name.trim();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return Objects.equals(id, league.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}