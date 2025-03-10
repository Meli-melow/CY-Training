package v3bis.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v3bis.team.model.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    /**
     * Afficher les questions validées uniquement.
     *
     * Requête SQL personnélisée avec JPQL.
     */
    @Query(value = "SELECT * FROM Question q WHERE q.etat_validation = 'Validée'", nativeQuery = true)
    List<Question> findAllQuestionsValidees();

    /**
     * Afficher les questions en attente uniquement.
     *
     * Requête SQL personnélisée avec JPQL.
     */
    @Query(value = "SELECT * FROM Question q WHERE q.etat_validation = 'En attente'", nativeQuery = true)
    List<Question> findAllQuestionsAttente();

}
