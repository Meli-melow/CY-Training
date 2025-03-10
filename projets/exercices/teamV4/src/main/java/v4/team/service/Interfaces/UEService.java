package v4.team.service.Interfaces;

import org.springframework.stereotype.Service;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.UEDto;

import java.util.List;

/**
 * L'interface du service dédié à l'entité UE.
 */

@Service
public interface UEService {

    UEDto creerUE(UEDto ueDto);

    UEDto getUEByName(String nomUE);

    UEDto getUEById(int ueId);

    List<UEDto> getAllUEs();

    UEDto updateUE(int ueId, UEDto updatedUE);

    MatiereDto ajoutMatiere(int ueId, MatiereDto nouvMat);

    List<MatiereDto> getUEAllMatieres(int ueId);

    void deleteMatiereUE(String nomUE, int mId);

    void deleteUE(int uEId);
}
