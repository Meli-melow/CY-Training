package atilla.demo.services.Classes;

import atilla.demo.Mappers.FiliereMapperImpl;
import atilla.demo.Mappers.UeMapper;
import atilla.demo.Repositories.FiliereRepository;
import atilla.demo.Repositories.UtilisateurRepository;
import atilla.demo.classes.Filiere;
import atilla.demo.classes.UE;
import atilla.demo.classes.Utilisateur;
import atilla.demo.dto.FiliereDto;
import atilla.demo.services.Interfaces.FiliereService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service

public class FiliereServiceImpl implements FiliereService {

    private final FiliereRepository filiereRepository;
    private final FiliereMapperImpl filiereMapper;

    private final UtilisateurRepository utilisateurRepository;

    public FiliereServiceImpl(FiliereRepository filiereRepository, FiliereMapperImpl filiereMapper, UtilisateurRepository utilisateurRepository) {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public FiliereDto ajouterFiliere(FiliereDto filiereDto) {

        /*List<UE> ues = filiereDto.getUes().stream()
                .map(UeMapper::toDtoList); */

        Filiere filiere =this.filiereMapper.toClasse(filiereDto);
        Filiere filiereSaved = this.filiereRepository.save(filiere);
        return this.filiereMapper.toDto(filiereSaved);
    }


    public Filiere getOrCreate(Filiere filiere) {
        Optional<Filiere> filierePresente = this.filiereRepository.findByNom(filiere.getNom());
        if (filierePresente.isPresent()) {
            return filierePresente.get();
        } else {
            Filiere filiereSaved =  filiereRepository.save(filiere);
            return filiereSaved;
        }
    }


    @Override
    public Stream<FiliereDto> afficherAll() {
        List<Filiere> filieres = this.filiereRepository.findAll();
        Stream<FiliereDto>  filieresDto= filieres.stream().map(filiereMapper::toDto);
        return filieresDto;

    }

    @Override
    public FiliereDto rechercherId(int id) {
        Optional<Filiere> optionalFiliere= this.filiereRepository.findById(id);
        Filiere filiere = optionalFiliere.orElseThrow(
                ()-> new EntityNotFoundException("ce id ne se trouve pas dans la BDD"));

        return this.filiereMapper.toDto(filiere);
    }

    @Override
    public FiliereDto rechercherNom(String nom) {
        Optional<Filiere> optionalFiliere= this.filiereRepository.findByNom(nom);
        Filiere filiere = optionalFiliere.orElseThrow(
                ()-> new EntityNotFoundException("ce mail ne se trouve pas dans la BDD"));

        return this.filiereMapper.toDto(filiere);
    }

    @Override
    public void deleteFiliere(int id) {

        Filiere filiere = this.filiereRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("la filiere n existe pas "));
        List<Utilisateur> utilisateurs = filiere.getUtilisateurs();
        for (Utilisateur utilisateur : utilisateurs){
            utilisateur.setFiliere(null);
            this.utilisateurRepository.save(utilisateur);
        }

        this.filiereRepository.deleteById(id);



        this.filiereRepository.deleteById(id);
    }

    @Override
    public void modifierFiliere(int id, FiliereDto filiereDto) {

    }
}
