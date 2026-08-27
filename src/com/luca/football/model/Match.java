package com.luca.football.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {
    private final Long id;
    private final Season season;
    private final Team homeTeam;
    private final Team awayTeam;
    private MatchStatus status;
    private final LocalDateTime matchDateTime;
    private int homeScore;
    private int awayScore;

    public Match(Long id, Season season, Team homeTeam, Team awayTeam, LocalDateTime matchDateTime) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive non-null number");
        }
        if (season == null || homeTeam == null || awayTeam == null || matchDateTime == null) {
            throw new IllegalArgumentException("None of the parameters can be null");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home team and away team cannot be the same");
        }

        this.id = id;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDateTime = matchDateTime;
        this.status = MatchStatus.SCHEDULED;
        this.homeScore = 0;
        this.awayScore = 0;
    }
    public void startMatch(){
        if(this.status != MatchStatus.SCHEDULED) {
            throw new IllegalStateException("Match can only be started if it is scheduled");
        }
        this.status = MatchStatus.IN_PROGRESS;
    }
    public void endMatch(int homeScore, int awayScore){
        if(this.status != MatchStatus.IN_PROGRESS) {
            throw new IllegalStateException("Match can only be ended if it is in progress");
        }
        if(homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.status = MatchStatus.FINISHED;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Long getId() {
        return id;
    }

    public Season getSeason() {
        return season;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", season=" + season +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", status=" + status +
                ", matchDateTime=" + matchDateTime +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                '}';
    }
}
