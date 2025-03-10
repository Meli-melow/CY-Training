package atilla.demo.services.Classes;

import atilla.demo.Mappers.UtilisateurMapper;
import atilla.demo.Repositories.MatiereRepository;
import atilla.demo.Repositories.UtilisateurRepository;
import atilla.demo.classes.*;
import atilla.demo.dto.RootAdminDto;
import atilla.demo.services.Interfaces.RootAdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class RootAdminServiceImpl implements RootAdminService {

    @Autowired
    private UtilisateurRepository utilisateurRepository ;

    @Autowired
    private UtilisateurMapper utilisateurMapper ;

    @Autowired
    private  FiliereServiceImpl filiereService ;

    private MatiereRepository matiereRepository ;

    public RootAdminServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper, FiliereServiceImpl filiereService, MatiereRepository matiereRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.filiereService = filiereService;
        this.matiereRepository = matiereRepository;
    }

    @Override
    public RootAdminDto findRootAdmin(int id) {
        return null;
    }

    @Override
    public RootAdminDto inscrireAdmin(RootAdminDto rootAdminDto) {
        Filiere filiere = filiereService.getOrCreate(rootAdminDto.getFiliere());
        RootAdmin rootAdmin;

        rootAdmin = utilisateurMapper.toClasseSup(rootAdminDto);

        rootAdmin.setFiliere(filiere);

        RootAdmin savedAdmin = utilisateurRepository.save(rootAdmin);

        return utilisateurMapper.toDtoSup(savedAdmin);
    }
    public void certifierAdmin( int adminId , int matiereId, int rootAdminId){
        Admin admin = utilisateurRepository.findAdminById(adminId).orElseThrow(() -> new EntityNotFoundException("Admin n'est pas trouvé"));
        Matiere matiere = matiereRepository.findById(matiereId).orElseThrow(() -> new EntityNotFoundException("Matiere n'est pas trouvé"));
        RootAdmin rootAdmin = (RootAdmin) utilisateurRepository.findRootAdminById(rootAdminId).orElseThrow(()->new EntityNotFoundException(("vous n'avez pas l'accès à cette opération")));
        rootAdmin.certifierAdmin(admin, matiere);
        utilisateurRepository.save(admin);
        matiereRepository.save(matiere);

    }



    public void certifierUtilisateur(int utilisateurId, int matiereId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElseThrow(() -> new EntityNotFoundException("Admin n'est pas trouvé"));
        Matiere matiere = matiereRepository.findById(matiereId).orElseThrow(() -> new EntityNotFoundException("Matiere n'est trouvée"));
       // this.utilisateurRepository.updateDtypeToAdmin(utilisateur.getId());

        Utilisateur tempUtilisateur = utilisateur;
        this.utilisateurRepository.deleteById(utilisateur.getId());



        Admin admin = new Admin();
        admin.setNom(tempUtilisateur.getNom());
        admin.setMail(tempUtilisateur.getMail());
        admin.setFiliere(tempUtilisateur.getFiliere());
        admin.setNbQuestionsPropose(tempUtilisateur.getNbQuestionsPropose());
        admin.setMdp(tempUtilisateur.getMdp());
        admin.setPrenom(tempUtilisateur.getPrenom());
        admin.setCertifs(new ArrayList<>());
        admin.setNbApprouve(0);
        admin.addCertif(matiere);


        this.utilisateurRepository.save(admin);

    }

    @Override
    public void removeCertification(int adminId, int matiereId, int rootAdminId) {
        Admin admin = utilisateurRepository.findAdminById(adminId).orElseThrow(() -> new EntityNotFoundException("Admin n'est pas trouvé"));
        Matiere matiere = matiereRepository.findById(matiereId).orElseThrow(() -> new EntityNotFoundException("Matiere n'est pas trouvé"));
        RootAdmin rootAdmin = (RootAdmin) utilisateurRepository.findRootAdminById(rootAdminId).orElseThrow(()->new EntityNotFoundException(("vous n'avez pas l'accès à cette opération")));
        rootAdmin.removeCertification( admin, matiere);
        utilisateurRepository.save(admin);
        matiereRepository.save(matiere);

    }


}
