package com.luca.football.model.test;

import com.luca.football.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private Season testSeason;
    private Team realMadrid;
    private Team barcelona;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when home and away teams are the same")
    void shouldThrowExceptionWhenTeamsAreIdentical() {
        LocalDateTime matchTime = LocalDateTime.now().plusDays(1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Match(1L, testSeason, realMadrid, realMadrid, matchTime)
        );

        assertEquals("Home team and away team cannot be the same", exception.getMessage());
    }

    @Test
    @DisplayName("Should correctly transition status through match lifecycle: SCHEDULED -> IN_PROGRESS -> FINISHED")
    void shouldTransitionStateCorrectlyFromScheduledToFinished() {
        LocalDateTime matchTime = LocalDateTime.now().plusDays(1);
        Match match = new Match(1L, testSeason, realMadrid, barcelona, matchTime);

        // 1. Inițial
        Assertions.assertEquals(MatchStatus.SCHEDULED, match.getStatus());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());

        // 2. Start meci
        match.startMatch();
        assertEquals(MatchStatus.IN_PROGRESS, match.getStatus());

        // 3. Final meci
        match.endMatch(3, 1);
        assertEquals(MatchStatus.FINISHED, match.getStatus());
        assertEquals(3, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }

    @Test
    @DisplayName("Should throw IllegalStateException if starting a match that is not SCHEDULED")
    void shouldThrowExceptionWhenStartingNonScheduledMatch() {
        LocalDateTime matchTime = LocalDateTime.now().plusDays(1);
        Match match = new Match(1L, testSeason, realMadrid, barcelona, matchTime);
        match.startMatch(); // trece în IN_PROGRESS

        assertThrows(IllegalStateException.class, match::startMatch);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when ending match with negative score")
    void shouldThrowExceptionWhenEndingMatchWithNegativeScore() {
        LocalDateTime matchTime = LocalDateTime.now().plusDays(1);
        Match match = new Match(1L, testSeason, realMadrid, barcelona, matchTime);
        match.startMatch();

        assertThrows(IllegalArgumentException.class, () -> match.endMatch(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> match.endMatch(2, -1));
    }
}