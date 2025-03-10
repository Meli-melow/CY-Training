package atilla.demo.services.Interfaces;

import atilla.demo.classes.Filiere;
import atilla.demo.dto.FiliereDto;

import java.util.stream.Stream;

public interface FiliereService {

    FiliereDto ajouterFiliere (FiliereDto filiereDto);
    Filiere getOrCreate (Filiere filiere);
    Stream<FiliereDto> afficherAll ();

    FiliereDto rechercherId ( int id );
    FiliereDto rechercherNom (String nom);

    void deleteFiliere (int id);

    void modifierFiliere( int id , FiliereDto filiereDto);


}
