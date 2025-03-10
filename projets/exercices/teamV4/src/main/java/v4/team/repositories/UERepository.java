package v4.team.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v4.team.model.UE;

/**
 * Le repository de l'entité UE.
 *
 * À chaque ajout d'un champ à l'entité, le repository associé créé
 * une méthode qui permet de récupérer une instance à partir de ce
 * champ.
 */

@Repository
public interface UERepository extends JpaRepository<UE, Integer> {


    UE findUEByNom(String nomUE);

}
