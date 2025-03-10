package atilla.demo.Repositories;

import atilla.demo.classes.UE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UeRepository extends JpaRepository<UE, Integer> {

    Optional<UE> findByNom (String nom);




}
