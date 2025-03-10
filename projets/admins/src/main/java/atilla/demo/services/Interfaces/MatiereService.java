package atilla.demo.services.Interfaces;

import atilla.demo.classes.Matiere;
import atilla.demo.dto.MatiereDto;

import java.util.stream.Stream;

public interface MatiereService {

    MatiereDto rechercherMatiereId(int id);
    MatiereDto creerMatiere(MatiereDto matiereDto);

    Stream<MatiereDto> afficherMatiere();

}
