package atilla.demo.services.Interfaces;

import atilla.demo.dto.AdminDto;
import atilla.demo.dto.RootAdminDto;
import atilla.demo.dto.UtilisateurDto;

import java.util.stream.Stream;

public interface UtilisateurService {



    UtilisateurDto inscrire (UtilisateurDto utilisateurDto);
    UtilisateurDto inscrire1 (UtilisateurDto utilisateurDto);



    RootAdminDto inscrire3 (RootAdminDto rootAdminDto);




    UtilisateurDto rechercherId (int id);
    Stream<UtilisateurDto> afficherAll() ;

    UtilisateurDto rechercherMail (String mail );


    void deleteUtilsateur(int id);

    void modifierUtilisateur ( int id , UtilisateurDto utilisateurDto);


    String connexion ( String mail, String mdp);








}
