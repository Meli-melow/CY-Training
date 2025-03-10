package atilla.demo.services.Classes;

import atilla.demo.Mappers.InscrpitionMapperImpl;
import atilla.demo.Mappers.UtilisateurMapperImpl;
import atilla.demo.Repositories.MatiereRepository;
import atilla.demo.Repositories.UtilisateurRepository;
import atilla.demo.classes.*;
import atilla.demo.dto.AdminDto;
import atilla.demo.dto.RootAdminDto;
import atilla.demo.dto.InscriptionDto;
import atilla.demo.dto.UtilisateurDto;
import atilla.demo.services.Interfaces.UtilisateurService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private UtilisateurMapperImpl utilisateurMapper;
    @Autowired
    private FiliereServiceImpl filiereService;
    @Autowired
    private InscrpitionMapperImpl inscrpitionMapper ;



    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapperImpl utilisateurMapper, FiliereServiceImpl filiereService) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.filiereService = filiereService;
    }

    @Override
    public UtilisateurDto inscrire(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = this.utilisateurMapper.toClasse(utilisateurDto);
        Utilisateur utilisateurSaved= this.utilisateurRepository.save(utilisateur);
        return this.utilisateurMapper.toDTo(utilisateurSaved);
    }

    @Override
    public UtilisateurDto inscrire1(UtilisateurDto utilisateurDto) {
        Filiere filiere = utilisateurDto.getFiliere();
        Utilisateur utilisateur = this.utilisateurMapper.toClasse(utilisateurDto);
        Filiere filiereBDD = this.filiereService.getOrCreate(filiere);
        utilisateur.setFiliere(filiereBDD);
        Utilisateur utilisateurBDD = utilisateurRepository.save(utilisateur);
        return this.utilisateurMapper.toDTo(utilisateurBDD);

    }

    public InscriptionDto inscrireUtilisateur(InscriptionDto inscriptionDto) {
        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByMail(inscriptionDto.getMail());
        if (utilisateurOptional.isPresent()) {
            throw new EntityExistsException("ce mail existe déja");
        }
        Filiere filiere = inscriptionDto.getFiliere();
        Utilisateur utilisateur = this.inscrpitionMapper.toClasse(inscriptionDto);
        Filiere filiereBDD = this.filiereService.getOrCreate(filiere);
        utilisateur.setFiliere(filiereBDD);

        Utilisateur utilisateurBDD = utilisateurRepository.save(utilisateur);
        return this.inscrpitionMapper.toDto(utilisateurBDD);

    }






    @Override
    public RootAdminDto inscrire3(RootAdminDto rootAdminDto) {
        return null;
    }


    @Override
    public UtilisateurDto rechercherId(int id) {
        Optional<Utilisateur> optionalUtilisateur = this.utilisateurRepository.findById(id);
        Utilisateur utilisateur = optionalUtilisateur.orElseThrow(
                ()-> new EntityNotFoundException("ce id ne se trouve pas dans la BDD"));

        return this.utilisateurMapper.toDTo(utilisateur);
    }

    @Override
    public Stream<UtilisateurDto> afficherAll() {
        List<Utilisateur> utilisateurs = this.utilisateurRepository.findAll();
        Stream<UtilisateurDto> utilisateursDTO = utilisateurs.stream().map(utilisateurMapper::toDTo);
        return utilisateursDTO;
    }



    @Override
    public UtilisateurDto rechercherMail(String mail) {
        Optional<Utilisateur> optionalUtilisateur= this.utilisateurRepository.findByMail(mail);
        Utilisateur utilisateur = optionalUtilisateur.orElseThrow(
                ()-> new EntityNotFoundException("ce mail ne se trouve pas dans la BDD"));

        return this.utilisateurMapper.toDTo(utilisateur);

    }



    @Override
    public void deleteUtilsateur(int id) {
        this.utilisateurRepository.deleteById(id);

    }

    @Override
    public void modifierUtilisateur(int id, UtilisateurDto utilisateurDto) {

        UtilisateurDto utilisateurDtoBdd = rechercherId(id);
        Utilisateur utilisateurBDD = this.utilisateurMapper.toClasse(utilisateurDtoBdd);


        utilisateurBDD.setMail(utilisateurDto.getEmail());
        // utilisateurBDD.setMdp(utilisateurDto.getMdp());
        utilisateurBDD.setNom(utilisateurDto.getNom());
        utilisateurBDD.setPrenom(utilisateurDto.getPrenom());
        utilisateurBDD.setNbQuestionsPropose(utilisateurDto.getNbQuestionsPropose());
        utilisateurBDD.setNbQuestionsValide(utilisateurDto.getNbQuestionsValide());


        if (utilisateurDto.getFiliere() != null) {
            utilisateurBDD.setFiliere(utilisateurDto.getFiliere());
        }


        this.utilisateurRepository.save(utilisateurBDD);

    }

    @Override
    public String connexion(String mail, String mdp) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByMail(mail);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            if (utilisateur.getMdp().equals(mdp)) {
                if (utilisateur instanceof Admin) {
                    return "Admin";
                } else {
                    return "Utilisateur";
                }
            } else {
                throw new EntityNotFoundException("le mot de passe est incorrecte");
            }
        } else {
            throw new EntityNotFoundException("Ce mail n'existe pas");
        }
    }




    /*@PostConstruct
    public void init() {
        // Ajout de données de test
        Utilisateur user = new Utilisateur();
        user.setNom("User1");
        utilisateurRepository.save(user);

        Admin admin = new Admin();
        admin.setNom("Admin1");
        admin.setNbApprouve(7);
        utilisateurRepository.save(admin);
    }*/
}

