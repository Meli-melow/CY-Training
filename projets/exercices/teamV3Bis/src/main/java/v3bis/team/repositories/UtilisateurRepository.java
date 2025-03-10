package v3bis.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v3bis.team.model.Utilisateur;


//Définir vraiment le repo
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
}
