package atilla.demo.Repositories;

import atilla.demo.classes.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository <Filiere, Integer> {

    Optional<Filiere> findByNom  (String nom);




}
