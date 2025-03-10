package atilla.demo.Repositories;

import atilla.demo.classes.Admin;
import atilla.demo.classes.RootAdmin;
import atilla.demo.classes.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByMail (String mail);

    @Query( "SELECT u FROM Utilisateur u WHERE TYPE(u) = Admin")
    List<Admin> findallAdmins();

    @Query("SELECT a FROM Utilisateur a WHERE a.id = :id and Type(a) =Admin ")
    Optional<Admin> findAdminById(@Param("id") int id);


    @Query("SELECT u FROM Utilisateur u WHERE u.id = :id AND Type(u) = 'RootAdmin'")
    Optional<Utilisateur> findRootAdminById ( @Param("id") int id );


    /*@Modifying
    @Query("UPDATE Utilisateur u SET u.dtype = 'Admin' WHERE u.id = :id")
    void updateDtypeToAdmin(@Param("id") int id);*/
}
