package com.luca.football.model;

import java.util.Objects;

public class MatchEvent {
    private final Long id;
     private final Match match;
     private final Player player;
     private final Player assistPlayer;
     private final EventType eventType;
     private final int minute;

    public MatchEvent(Long id, Match match, Player player, Player assistPlayer, EventType eventType, int minute) {
        if(id<=0||minute<0){
            throw new IllegalArgumentException("Invalid input for MatchEvent");
        }
        if(match==null||player==null||eventType==null){
            throw new IllegalArgumentException("Match, Player, and EventType cannot be null");
        }
        if(assistPlayer!=null && player.equals(assistPlayer)){
            throw new IllegalArgumentException("Assist player cannot be the same as the main player");
        }
        if(minute<1||minute>120){
            throw new IllegalArgumentException("Minute must be between 1 and 120");
        }
        this.id = id;
        this.match = match;
        this.player = player;
        this.assistPlayer = assistPlayer;
        this.eventType = eventType;
        this.minute = minute;
    }

    public Long getId() {
        return id;
    }

    public Match getMatch() {
        return match;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getAssistPlayer() {
        return assistPlayer;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MatchEvent that = (MatchEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MatchEvent{" +
                "id=" + id +
                ", match=" + match +
                ", player=" + player +
                ", assistPlayer=" + assistPlayer +
                ", eventType=" + eventType +
                ", minute=" + minute +
                '}';
    }
}
