package com.luca.football.model;

import java.time.LocalDate;
import java.util.Objects;

public class Player {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private Position position;
    private int shirtNumber;

    public Player(Long id, String firstName, String lastName, LocalDate birthDate, Position position, int shirtNumber) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive non-null number");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be null or in the future");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        this.id = id;
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.birthDate = birthDate;
        this.position = position;


        setShirtNumber(shirtNumber);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        if (shirtNumber < 1 || shirtNumber > 99) {
            throw new IllegalArgumentException("Shirt number must be between 1 and 99");
        }
        this.shirtNumber = shirtNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", birthDate=" + birthDate +
                ", position=" + position +
                ", shirtNumber=" + shirtNumber +
                '}';
    }
}