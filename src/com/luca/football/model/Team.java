package com.luca.football.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Team {
    private final Long id;
    private final String name;
    private final String city;
    private final String shortCode;
    private final List<Player> squad;


    public Team(Long id, String name, String city, String shortCode) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive non-null number");
        }

        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if(city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if(shortCode == null || shortCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }

        this.id = id;
        this.name = name.trim();
        this.city = city.trim();
        this.shortCode = shortCode.trim().toUpperCase();
        this.squad = new ArrayList<>();
    }
    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (squad.contains(player)) {
            throw new IllegalArgumentException("Player is already in the squad");
        }
        boolean shirtTaken = squad.stream()
                .anyMatch(p -> p.getShirtNumber() == player.getShirtNumber());
        if (shirtTaken) {
            throw new IllegalArgumentException("Shirt number " + player.getShirtNumber() + " is already taken");
        }
        squad.add(player);
    }
    public void removePlayer(Player player){
        if(player==null||!squad.contains(player))
        {
            throw new IllegalArgumentException("Player cannot be null or not in the squad");
        }
        squad.remove(player);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getShortCode() {
        return shortCode;
    }
    public List<Player> getSquad() {
        return Collections.unmodifiableList(this.squad);
    }
    public int getSquadSize() {
        return squad.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", squad=" + squad +
                '}';
    }


}
