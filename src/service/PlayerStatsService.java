package service;

import com.luca.football.model.EventType;
import com.luca.football.model.MatchEvent;
import com.luca.football.model.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerStatsService {

    public Map<Player, Integer> getTopScorers(List<MatchEvent> events) {
        if (events == null) {
            throw new IllegalArgumentException("Events list cannot be null");
        }

        Map<Player, Integer> goalsMap = new HashMap<>();

        for (MatchEvent event : events) {
            EventType type = event.getEventType();
            if (type == EventType.GOAL || type == EventType.PENALTY_GOAL) {
                Player scorer = event.getPlayer();
                int currentGoals = goalsMap.getOrDefault(scorer, 0);
                goalsMap.put(scorer, currentGoals + 1);
            }
        }

        return goalsMap.entrySet().stream()
                .sorted(Map.Entry.<Player, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
    public Map<Player, Integer> getTopAssisters(List<MatchEvent> events) {
        if (events == null) {
            throw new IllegalArgumentException("Events list cannot be null");
        }

        Map<Player, Integer> assistsMap = new HashMap<>();

        for (MatchEvent event : events) {
            Player assistPlayer = event.getAssistPlayer();
            if (assistPlayer != null) {
                int currentAssists = assistsMap.getOrDefault(assistPlayer, 0);
                assistsMap.put(assistPlayer, currentAssists + 1);
            }
        }

        return assistsMap.entrySet().stream()
                .sorted(Map.Entry.<Player, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

}