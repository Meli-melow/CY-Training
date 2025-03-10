package v4.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import v4.team.model.Matiere;
import v4.team.model.Question;

import java.util.List;

/**
 * Le repository de l'entité Utilisateur.
 *
 * Les repositories héritent des repositories CRUD de JPA en
 * associant la clé primaire et son instance d'entité.
 * À chaque ajout d'un champ à l'entité, le repository associé créé
 * une méthode qui permet de récupérer une instance à partir de ce
 * champ.
 */

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {

    Matiere findMatiereByNom(String nom);

    @Query("SELECT q FROM Question q WHERE q.etatValidation = 'Validée' AND q.matiereQuestion.nom = :nomMatiere")
    List<Question> findAllQuestionsValidees(@Param("nomMatiere") String nomMatiere);

    @Query("SELECT q FROM Question q WHERE q.etatValidation = 'En attente' AND q.matiereQuestion.nom = :nomMatiere")
    List<Question> findAllQuestionsAttente(@Param("nomMatiere") String nomMatiere);


}
