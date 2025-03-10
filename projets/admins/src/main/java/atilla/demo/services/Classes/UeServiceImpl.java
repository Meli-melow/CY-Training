package atilla.demo.services.Classes;

import atilla.demo.Mappers.MatiereMapper;
import atilla.demo.Mappers.UeMapper;
import atilla.demo.Mappers.UeMapperImpl;
import atilla.demo.Repositories.FiliereRepository;
import atilla.demo.Repositories.UeRepository;
import atilla.demo.classes.Filiere;
import atilla.demo.classes.Matiere;
import atilla.demo.classes.UE;
import atilla.demo.dto.MatiereDto;
import atilla.demo.dto.UeDto;
import atilla.demo.services.Interfaces.UeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class UeServiceImpl implements UeService {

    private UeRepository ueRepository;
    private UeMapperImpl ueMapper;

    private FiliereRepository filiereRepository ;

    private MatiereMapper matiereMapper;

    public UeServiceImpl(UeRepository ueRepository, UeMapperImpl ueMapper, FiliereRepository filiereRepository, MatiereMapper matiereMapper) {
        this.ueRepository = ueRepository;
        this.ueMapper = ueMapper;
        this.filiereRepository = filiereRepository;
        this.matiereMapper = matiereMapper;
    }

    @Override
    public UeDto ajouterUe(UeDto ueDto) {

        UE ue =this.ueMapper.toClasse(ueDto);
        UE ueSaved = this.ueRepository.save(ue);
        return this.ueMapper.todTO(ueSaved);

    }

    @Override
    public Stream<UeDto> afficherAll() {
        List<UE> ues = this.ueRepository.findAll();
        Stream<UeDto> uesDTO = ues.stream().map(ueMapper::todTO);
        return uesDTO;

    }

    @Override
    public UeDto rechercherId(int id) {
        Optional<UE> optionalUE= this.ueRepository.findById( id);
        UE ue = optionalUE.orElseThrow( ()->new EntityNotFoundException("cet id n'esxiste pas dans la table"));
        return  this.ueMapper.todTO(ue);

    }

    @Override
    public UeDto rechercherNom(String nom) {

        Optional<UE> optionalUE= this.ueRepository.findByNom( nom);
        UE ue = optionalUE.orElseThrow( ()->new EntityNotFoundException("cet nom n'existe pas dans la table"));
        return  this.ueMapper.todTO(ue);

    }

    @Override
    public void deleteUE(int id) {


        UE ue = this.ueRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ce ue n existe pas "));
        List<Filiere> filieres = ue.getFilieres();

        for (Filiere filiere: filieres){
            filiere.getUes().remove(ue);
            this.filiereRepository.save(filiere);

        }
        this.ueRepository.deleteById(id);
    }

    @Override
    public void modifierUe(int id, UeDto ueDto) {

    }

    @Override
    public UE getOrCreate(UE ue) {
        Optional<UE> uePresente = this.ueRepository.findByNom(ue.getNom());
        if (uePresente.isPresent()) {
            return uePresente.get();
        } else {
            UE ueSaved =  ueRepository.save(ue);
            return ueSaved;
        }
    }

    @Override
    public Stream<MatiereDto> afficherMatiere_UE(int idUe) {
        UE ue = this.ueRepository.findById(idUe).orElseThrow(()-> new EntityNotFoundException("UE n'est pas trouvé"));
        List<Matiere> matieres = ue.getMatieres();
        Stream<MatiereDto> stream = matieres.stream().map(matiereMapper::toDto);
        return stream ;

    }


}
