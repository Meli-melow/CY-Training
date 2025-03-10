package v4.team.service.Classes;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.UEDto;
import v4.team.exceptions.ExceptionMapper;
import v4.team.exceptions.ExceptionRepository;
import v4.team.exceptions.ExceptionRessourceAbsente;
import v4.team.mapper.Interfaces.MatiereMapperImpl;
import v4.team.mapper.Interfaces.UEMapperImpl;
import v4.team.model.Matiere;
import v4.team.model.UE;
import v4.team.repositories.MatiereRepository;
import v4.team.repositories.UERepository;
import v4.team.service.Interfaces.UEService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Le service dédié à l'entité UE.
 *
 * Les services se servent des repositories et des mappers des autres entités.
 */

@Service
public class UEServiceImpl implements UEService {


    @Autowired
    private final UERepository uERepo;

    private final UEMapperImpl uEMap;

    private final MatiereRepository mServRepo;

    private final MatiereMapperImpl mServMap;


    public UEServiceImpl(UERepository uERepo, UEMapperImpl uEMap, MatiereRepository mServRepo, MatiereMapperImpl mServMap) {
        this.uERepo = uERepo;
        this.uEMap = uEMap;
        this.mServRepo = mServRepo;
        this.mServMap = mServMap;
    }

    @Override
    public UEDto creerUE(UEDto ueDto) {
        try {
            UE ue = uEMap.toClasse(ueDto);
            UE savedUE = uERepo.save(ue);
            try {
                return uEMap.toDto(savedUE);
            } catch (ExceptionMapper e) { throw new ExceptionMapper("Erreur lors de la conversion en DTO"); }

        } catch (ExceptionRepository e) { throw new ExceptionRepository("Erreur lors de la création d'une UE"); }

        catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    @Override
    public UEDto getUEByName(String nomUE) {
        try {
            UE ue = uERepo.findUEByNom(nomUE);
            try { return uEMap.toDto(ue);}

            catch (ExceptionMapper e) { throw new ExceptionMapper("Erreur lors de la conversion en DTO"); }

        } catch (ExceptionRessourceAbsente e) { throw new ExceptionRessourceAbsente("Aucune UE associée au nom "+ nomUE); }

        catch (ExceptionRepository e) { throw new ExceptionRepository("Erreur lors de la récupération"); }

        catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    @Override
    public UEDto getUEById(int ueId) {
        UE ue = uERepo.findById(ueId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune UE associée à l'id "+ ueId)
        );
        try { return uEMap.toDto(ue); }

        catch (ExceptionMapper e) { throw new ExceptionMapper("Erreur lors de la conversion en DTO"); }

        catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<UEDto> getAllUEs() {
        try {
            List<UE> matieres = uERepo.findAll();
            return matieres.stream().map( (m) -> uEMap.toDto(m) ).collect(Collectors.toList());
        }
        catch (ExceptionRessourceAbsente ressourceException) {
            throw new ExceptionRessourceAbsente("Erreur dans la récupération de certaines UE.");
        }
        catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    @Override
    @Transactional
    public UEDto updateUE(int ueId, UEDto updatedUE) {
        try {
            UE ue = uERepo.findById(ueId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune UE associée à l'id "+ ueId) );
            ue.setNom( updatedUE.getNom() );
            //Pas besoin de sauvegarger pour chaque matière de l'UE grâce au cascading Merge de JPA
            return uEMap.toDto( uERepo.save(ue) );
        } catch (ExceptionRessourceAbsente e) {
            throw new ExceptionRessourceAbsente("Erreur lors de la modification de la UE associée à l'id "+ ueId);
        } catch (RuntimeException e) { throw new RuntimeException(e); }

    }

    @Override
    @Transactional
    public MatiereDto ajoutMatiere(int ueId, MatiereDto nouvMat) {
        UE ue = uERepo.findById(ueId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune UE associée à l'id "+ ueId) );
        Matiere m = null;
        try { m = mServMap.toClasse(nouvMat); }

        catch (ExceptionMapper e) { throw new ExceptionMapper("Erreur lors de la conversion en DTO"); }

        m.setUEMatiere(ue);
        ue.getMatieresUE().add(m);
        try {
            mServRepo.save(m);
            uERepo.save(ue);
            return mServMap.toDto(m);
        } catch (ExceptionRepository e) {
            throw new ExceptionRepository("Erreur lors de la sauvegarde de l'UE "+ue.toString()+" ou de la matière "+m.toString());
        }
    }

    @Override
    public List<MatiereDto> getUEAllMatieres(int ueId) {
        try {
            UE ue = uERepo.findById(ueId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune UE associée à l'id "+ ueId) );

            List<Matiere> uEMat = ue.getMatieresUE();
            return uEMat.stream().map( (mat) -> mServMap.toDto(mat) ).collect(Collectors.toList());
        }
        catch (ExceptionRessourceAbsente abs) {
            throw new ExceptionRessourceAbsente("Erreur dans la récupération des matieres de l'UE associée à l'id : "+ ueId);
        }
        catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    @Override
    @Transactional
    public void deleteMatiereUE(String nomUE, int mId) {
        try
        {
            UE ue = null;
            try { ue = uERepo.findUEByNom(nomUE); }
            catch (ExceptionRessourceAbsente abs) { throw new ExceptionRessourceAbsente("Aucune UE associée au nom "+nomUE); }
            Matiere m = mServRepo.findById(mId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId)
            );
            //Tests
            if ( !ue.getMatieresUE().contains(m) ) {
                throw new IllegalArgumentException("L'UE "+nomUE+" ne correspond pas à celle de la question");
            } else {
                //Suffisant grâce au cascading MERGE de Matière
                ue.getMatieresUE().remove(m);
                mServRepo.deleteById(mId);
                uERepo.save(ue);
                System.out.println(ue);
                System.out.println("La matière a été supprimée avec succès\n");
            }
        }
        catch (Exception e) { throw new RuntimeException("Erreur pendant la suppression d'une question de l'UE "+nomUE); }
    }

    @Override
    public void deleteUE(int uEId) {
        try {
            //Matières supprimées grâce au orphanRemoval
            uERepo.deleteById(uEId);
        } catch (ExceptionRessourceAbsente e) {
            throw new ExceptionRessourceAbsente("Erreur lors de la suppresion de l'UE d'id "+ uEId + ". Vérifiez que l'UE existe.");
        }
        catch (RuntimeException e) { throw new RuntimeException(e); }
    }
}
