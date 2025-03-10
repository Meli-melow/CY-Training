package v4.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v4.team.model.Question;
import v4.team.model.Utilisateur;

import java.util.List;

/**
 * Le repository de l'entité Utilisateur.
 *
 * À chaque ajout d'un champ à l'entité, le repository associé créé
 * une méthode qui permet de récupérer une instance à partir de ce
 * champ.
 */

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findUtilisateurByEmail(String email);

    /**
     * Permet à un utilisateur d'accéder à ses propositions.
     */
    @Query("SELECT q From Question q WHERE q.createur.id = :uId AND q.etatValidation != 'Validée'")
    List<Question> findUtilisateurPropositions(int uId);
}
