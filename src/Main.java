import com.luca.football.model.Team;
import repository.JdbcTeamRepository;
import repository.TeamRepository;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testare Conexiune & Persistență SQL Server ===");

        TeamRepository teamRepo = new JdbcTeamRepository();

        System.out.println("\n1. Salvare echipe în baza de date...");
        try {
            Team savedRMA = teamRepo.save(new Team(1L, "Real Madrid", "Madrid", "RMA"));
            Team savedFCB = teamRepo.save(new Team(2L, "FC Barcelona", "Barcelona", "FCB"));
            Team savedATM = teamRepo.save(new Team(3L, "Atletico Madrid", "Madrid", "ATM"));

            System.out.println("Salvat cu succes: " + savedRMA);
            System.out.println("Salvat cu succes: " + savedFCB);
            System.out.println("Salvat cu succes: " + savedATM);
        } catch (Exception e) {
            System.out.println("Eroare la salvare: " + e.getMessage());
        }

        System.out.println("\n2. Preluare listă completă echipe din DB:");
        List<Team> allTeams = teamRepo.findAll();
        allTeams.forEach(team -> System.out.println(" -> " + team));

        System.out.println("\n3. Căutare echipă după codul 'RMA':");
        Optional<Team> foundTeam = teamRepo.findByShortCode("RMA");
        foundTeam.ifPresentOrElse(
                team -> System.out.println("Găsit: " + team.getName() + " (" + team.getCity() + ")"),
                () -> System.out.println("Echipa nu a fost găsită.")
        );

        System.out.println("\n=== Test Finalizat cu Succes ===");
    }
}