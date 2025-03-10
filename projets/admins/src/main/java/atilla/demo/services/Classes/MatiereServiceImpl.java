package atilla.demo.services.Classes;

import atilla.demo.Mappers.MatiereMapper;
import atilla.demo.Repositories.MatiereRepository;
import atilla.demo.classes.Matiere;
import atilla.demo.classes.Utilisateur;
import atilla.demo.dto.MatiereDto;
import atilla.demo.services.Interfaces.MatiereService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class MatiereServiceImpl implements MatiereService {

    private MatiereRepository matiereRepository;
    private MatiereMapper matiereMapper ;

    public MatiereServiceImpl(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }

    @Override
    public MatiereDto rechercherMatiereId(int id) {
        Optional<Matiere> optionalMatiere = this.matiereRepository.findById(id);
        Matiere matiere= optionalMatiere.orElseThrow(
                ()-> new EntityNotFoundException("cette matiere n'est pas trouvée"));

        return this.matiereMapper.toDto(matiere);
    }

    @Override
    public MatiereDto creerMatiere(MatiereDto matiereDto) {
        Matiere matiere = this.matiereMapper.toClasse(matiereDto);
        Matiere matiererSaved= this.matiereRepository.save(matiere);
        return  this.matiereMapper.toDto(matiererSaved);

    }

    @Override
    public Stream<MatiereDto> afficherMatiere() {
        List<Matiere> matieres= this.matiereRepository.findAll();
        Stream<MatiereDto> matiereDtoStream = matieres.stream().map(matiereMapper::toDto);
        return matiereDtoStream;
    }


}
