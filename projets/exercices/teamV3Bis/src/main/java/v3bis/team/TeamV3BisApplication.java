package v3bis.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import v3bis.team.repositories.QuestionRepository;
import v3bis.team.repositories.UtilisateurRepository;

@SpringBootApplication
public class TeamV3BisApplication implements CommandLineRunner {

    @Autowired
    UtilisateurRepository uRepo;

    @Autowired
    QuestionRepository qRepo;

    public static void main(String[] args) {
        SpringApplication.run(TeamV3BisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /*Utilisateur u1 = new Utilisateur("user1", "u1@cy-tech.fr", "protecc",null);
        *//*uRepo.save(u1);
        System.out.println(u1);*//*

        Question q1 = new Question("Quelle est la dérivée de x^2 ?", "C'est 2x", List.of(new String[]{"2", "2x", "x"}), 2,
        "Utilisez la règle des puissances", u1);
        qRepo.save(q1);
        System.out.println(q1);

        u1.ajouterQuestion(q1);
        System.out.println(u1);*/
    }



}
