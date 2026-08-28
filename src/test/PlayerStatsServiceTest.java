package test;

import com.luca.football.model.*;
import service.PlayerStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsServiceTest {

    private PlayerStatsService statsService;
    private Match testMatch;
    private Player mbappe;
    private Player vinicius;
    private Player lewandowski;
    private Player yamal;

    @BeforeEach
    void setUp() {
        statsService = new PlayerStatsService();

        League laLiga = new League(1L, "LaLiga EA Sports");
        Season season = new Season(1L, "2025/2026", laLiga, LocalDate.of(2025, 8, 15), LocalDate.of(2026, 5, 25));

        Team realMadrid = new Team(1L, "Real Madrid", "Madrid", "RMA");
        Team barcelona = new Team(2L, "FC Barcelona", "Barcelona", "FCB");

        testMatch = new Match(1L, season, realMadrid, barcelona, LocalDateTime.now());

        mbappe = new Player(1L, "Kylian", "Mbappé", LocalDate.of(1998, 12, 20), Position.FORWARD, 9);
        vinicius = new Player(2L, "Vinicius", "Junior", LocalDate.of(2000, 7, 12), Position.FORWARD, 7);
        lewandowski = new Player(3L, "Robert", "Lewandowski", LocalDate.of(1988, 8, 21), Position.FORWARD, 9);
        yamal = new Player(4L, "Lamine", "Yamal", LocalDate.of(2007, 7, 13), Position.FORWARD, 19);
    }

    @Test
    @DisplayName("Should correctly calculate and rank top scorers, ignoring own goals and cards")
    void shouldCalculateTopScorersCorrectly() {
        List<MatchEvent> events = List.of(
                new MatchEvent(1L, testMatch, mbappe, vinicius, EventType.GOAL, 12),
                new MatchEvent(2L, testMatch, mbappe, null, EventType.PENALTY_GOAL, 45),
                new MatchEvent(3L, testMatch, lewandowski, yamal, EventType.GOAL, 55),
                new MatchEvent(4L, testMatch, vinicius, null, EventType.YELLOW_CARD, 60),
                new MatchEvent(5L, testMatch, vinicius, null, EventType.OWN_GOAL, 75)
        );

        Map<Player, Integer> topScorers = statsService.getTopScorers(events);

        assertEquals(2, topScorers.size());
        assertEquals(2, topScorers.get(mbappe));
        assertEquals(1, topScorers.get(lewandowski));

        List<Player> rankedPlayers = new ArrayList<>(topScorers.keySet());
        assertEquals(mbappe, rankedPlayers.get(0));
        assertEquals(lewandowski, rankedPlayers.get(1));
    }

    @Test
    @DisplayName("Should correctly count and rank assists")
    void shouldCalculateTopAssistersCorrectly() {
        List<MatchEvent> events = List.of(
                new MatchEvent(1L, testMatch, mbappe, vinicius, EventType.GOAL, 10),
                new MatchEvent(2L, testMatch, mbappe, vinicius, EventType.GOAL, 35),
                new MatchEvent(3L, testMatch, lewandowski, yamal, EventType.GOAL, 70),
                new MatchEvent(4L, testMatch, mbappe, null, EventType.PENALTY_GOAL, 85)
        );

        Map<Player, Integer> topAssisters = statsService.getTopAssisters(events);

        assertEquals(2, topAssisters.size());
        assertEquals(2, topAssisters.get(vinicius));
        assertEquals(1, topAssisters.get(yamal));

        List<Player> rankedPlayers = new ArrayList<>(topAssisters.keySet());
        assertEquals(vinicius, rankedPlayers.get(0));
        assertEquals(yamal, rankedPlayers.get(1));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when event list is null")
    void shouldThrowExceptionWhenNullInput() {
        assertThrows(IllegalArgumentException.class, () -> statsService.getTopScorers(null));
        assertThrows(IllegalArgumentException.class, () -> statsService.getTopAssisters(null));
    }
}