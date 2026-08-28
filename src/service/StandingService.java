package service;

import com.luca.football.model.Match;
import com.luca.football.model.MatchStatus;
import com.luca.football.model.StandingsRow;
import com.luca.football.model.Team;

import java.util.*;

public class StandingService {

    public List<StandingsRow> calculateTable(List<Team> teams, List<Match> matches) {
        if (teams == null || matches == null) {
            throw new IllegalArgumentException("Teams and matches lists cannot be null");
        }

        Map<Long, StandingsRow> tableMap = new HashMap<>();
        for (Team team : teams) {
            tableMap.put(team.getId(), new StandingsRow(team));
        }

        for (Match match : matches) {
            if (match.getStatus() == MatchStatus.FINISHED) {
                StandingsRow homeRow = tableMap.get(match.getHomeTeam().getId());
                StandingsRow awayRow = tableMap.get(match.getAwayTeam().getId());

                if (homeRow == null || awayRow == null) {
                    continue;
                }

                int homeScore = match.getHomeScore();
                int awayScore = match.getAwayScore();

                if (homeScore > awayScore) {
                    homeRow.recordwin(homeScore, awayScore);
                    awayRow.recordloss(awayScore, homeScore);
                } else if (homeScore < awayScore) {
                    homeRow.recordloss(homeScore, awayScore);
                    awayRow.recordwin(awayScore, homeScore);
                } else {
                    homeRow.recorddraw(homeScore, awayScore);
                    awayRow.recorddraw(awayScore, homeScore);
                }
            }
        }


        List<StandingsRow> standings = new ArrayList<>(tableMap.values());
        standings.sort(
                Comparator.comparingInt(StandingsRow::getPoints)
                        .thenComparingInt(StandingsRow::getGoalsDifference)
                        .thenComparingInt(StandingsRow::getGoalsFor)
                        .reversed()
        );

        return Collections.unmodifiableList(standings);
    }
}