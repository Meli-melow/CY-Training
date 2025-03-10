package v4.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import v4.team.model.Question;

import java.util.List;


//TODO : suppr matière -> récup les questions par nomMatière + supp celles en attente
/**
 * Le repository de l'entité Question.
 *
 * Les repositories héritent des repositories CRUD de JPA en
 * associant la clé primaire et son instance d'entité.
 * À chaque ajout d'un champ à l'entité, le repository associé créé
 * une méthode qui permet de récupérer une instance à partir de ce
 * champ.
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    /**
     * Afficher les questions validées uniquement.
     *
     * Requête SQL personalisée avec JPQL.
     */
    @Query(value = "SELECT * FROM Question q WHERE q.etat_validation = 'Validée' GROUP BY q.id_matiere", nativeQuery = true)
    List<Question> findAllQuestionsValidees();

    /**
     * Afficher les questions en attente.
     *
     * Requête SQL personalisée avec JPQL.
     */
    @Query(value = "SELECT * FROM Question q WHERE q.etat_validation = 'En attente' GROUP BY q.id_matiere", nativeQuery = true)
    List<Question> findAllQuestionsAttente();

    /**
     * Mettre à jour le temps d'attente d'une question.
     * Modifie la donnée "tempsAttente".
     *
     * @Modifying indique que la requête modifie les instances.
     */
    @Modifying
    @Query(value = "UPDATE Question q SET q.temps_attente = ?2 WHERE q.id = ?1", nativeQuery = true)
    void updateTempsAttente(@Param("qId") int qId, @Param("nouvTpsAttente") String nouvTpsAttente);
}
