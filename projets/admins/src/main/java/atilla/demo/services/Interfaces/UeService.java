package atilla.demo.services.Interfaces;


import atilla.demo.classes.UE;
import atilla.demo.dto.MatiereDto;
import atilla.demo.dto.UeDto;

import java.util.stream.Stream;


public interface UeService {


    UeDto ajouterUe(UeDto ueDto);
    Stream<UeDto> afficherAll();
    UeDto rechercherId ( int id );
    UeDto rechercherNom ( String nom);

    void deleteUE (int id);

    void modifierUe(int id , UeDto ueDto);

    UE getOrCreate(UE ue );

    Stream<MatiereDto> afficherMatiere_UE (int ue);


}
