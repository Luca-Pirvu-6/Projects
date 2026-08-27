package com.luca.football.model;

public class StandingsRow {
    private final Team team;
    private int matchesPlayed;
    private int won;
    private int draw;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public StandingsRow(Team team) {
        if(team==null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        this.team = team;
        this.matchesPlayed=0;
        this.won=0;
        this.draw=0;
        this.lost=0;
        this.goalsFor=0;
        this.goalsAgainst=0;
        this.points=0;

    }
    public int getGoalsDifference() {
        return goalsFor - goalsAgainst;

    }
    public void recordwin(int scored, int conceded) {
        matchesPlayed++;
        won++;
        goalsFor += scored;
        goalsAgainst += conceded;
        points += 3;
    }
    public void recorddraw(int scored, int conceded) {
        matchesPlayed++;
        draw++;
        goalsFor += scored;
        goalsAgainst += conceded;
        points += 1;
    }
    public void recordloss(int scored, int conceded) {
        matchesPlayed++;
        lost++;
        goalsFor += scored;
        goalsAgainst += conceded;
    }

    public int getWon() {
        return won;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getDraw() {
        return draw;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return String.format("%-15s | P: %2d | W: %2d | D: %2d | L: %2d | GF: %2d | GA: %2d | GD: %+3d | PTS: %2d",
                team.getName(), matchesPlayed, won, draw, lost, goalsFor, goalsAgainst, getGoalsDifference(), points);
    }
}
