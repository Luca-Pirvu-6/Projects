package com.luca.football.model.test;

import com.luca.football.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.StandingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandingServiceTest {

    private StandingService standingService;
    private Season testSeason;
    private Team realMadrid;
    private Team barcelona;
    private Team atletico;

    @BeforeEach
    void setUp() {
        standingService = new StandingService();
        League league = new League(1L, "LaLiga EA Sports");
        testSeason = new Season(
                1L,
                "2025/2026",
                league,
                LocalDate.of(2025, 8, 15),
                LocalDate.of(2026, 5, 25)
        );

        realMadrid = new Team(1L, "Real Madrid", "Madrid", "RMA");
        barcelona = new Team(2L, "FC Barcelona", "Barcelona", "FCB");
        atletico = new Team(3L, "Atletico Madrid", "Madrid", "ATM");
    }

    @Test
    @DisplayName("Should calculate points, goals, and positions correctly for finished matches")
    void shouldCalculateStandingsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        Match m1 = new Match(1L, testSeason, realMadrid, barcelona, now.minusDays(2));
        m1.startMatch();
        m1.endMatch(3, 1);

        Match m2 = new Match(2L, testSeason, barcelona, atletico, now.minusDays(1));
        m2.startMatch();
        m2.endMatch(2, 2);

        Match m3 = new Match(3L, testSeason, atletico, realMadrid, now.plusDays(1));

        List<Team> teams = List.of(realMadrid, barcelona, atletico);
        List<Match> matches = List.of(m1, m2, m3);

        List<StandingsRow> table = standingService.calculateTable(teams, matches);

        assertEquals(3, table.size());

        StandingsRow first = table.get(0);
        assertEquals(realMadrid, first.getTeam());
        assertEquals(1, first.getMatchesPlayed());
        assertEquals(1, first.getWon());
        assertEquals(3, first.getPoints());
        assertEquals(2, first.getGoalsDifference());

        StandingsRow second = table.get(1);
        assertEquals(atletico, second.getTeam());
        assertEquals(1, second.getMatchesPlayed());
        assertEquals(1, second.getDraw());
        assertEquals(1, second.getPoints());
        assertEquals(0, second.getGoalsDifference());


        StandingsRow third = table.get(2);
        assertEquals(barcelona, third.getTeam());
        assertEquals(2, third.getMatchesPlayed());
        assertEquals(0, third.getWon());
        assertEquals(1, third.getDraw());
        assertEquals(1, third.getLost());
        assertEquals(1, third.getPoints());
        assertEquals(-2, third.getGoalsDifference());
    }

    @Test
    @DisplayName("Should break tie by goal difference when points are equal")
    void shouldTieBreakByGoalDifference() {
        LocalDateTime now = LocalDateTime.now();

        Match m1 = new Match(1L, testSeason, realMadrid, atletico, now.minusDays(2));
        m1.startMatch();
        m1.endMatch(4, 0);

        Match m2 = new Match(2L, testSeason, barcelona, atletico, now.minusDays(1));
        m2.startMatch();
        m2.endMatch(1, 0);

        List<StandingsRow> table = standingService.calculateTable(
                List.of(realMadrid, barcelona, atletico),
                List.of(m1, m2)
        );

        assertEquals(realMadrid, table.get(0).getTeam());
        assertEquals(barcelona, table.get(1).getTeam());
        assertEquals(atletico, table.get(2).getTeam());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when inputs are null")
    void shouldThrowExceptionWhenInputsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> standingService.calculateTable(null, List.of()));
        assertThrows(IllegalArgumentException.class, () -> standingService.calculateTable(List.of(), null));
    }
}