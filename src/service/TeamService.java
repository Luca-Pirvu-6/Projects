package service;

import com.luca.football.model.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamService {
    private final Map<Long, Team> teamDatabase = new HashMap<>();
    public void addTeam(Team team) {
        if(teamDatabase.containsKey(team.getId())) {
            throw new IllegalArgumentException("Team with ID " + team.getId() + " already exists");
        }
        if(team !=null) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
        teamDatabase.put(team.getId(), team);
    }
    public Team getTeamById(Long id) {
        if(!teamDatabase.containsKey(id)) {
            throw new IllegalArgumentException("Team with ID " + id + " does not exist");
        }
        if(id<0||id==null) {
            throw new IllegalArgumentException("Team ID cannot be negative or null");
        }
        return teamDatabase.get(id);
    }
    public List<Team> getAllTeams() {
        return List.copyOf(teamDatabase.values());
    }

}
