package atilla.demo.services.Classes;

import atilla.demo.Mappers.UtilisateurMapper;
import atilla.demo.Repositories.UtilisateurRepository;
import atilla.demo.classes.Admin;
import atilla.demo.classes.Filiere;
import atilla.demo.dto.AdminDto;
import atilla.demo.services.Interfaces.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;


@Service
public class AdminServiceImpl implements AdminService {

    private UtilisateurRepository utilisateurRepository ;
    private UtilisateurMapper utilisateurMapper;

    private FiliereServiceImpl filiereService ;

    public AdminServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper, FiliereServiceImpl filiereService) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.filiereService = filiereService;
    }

    public AdminDto inscrire(AdminDto adminDto) {

        Filiere filiere = filiereService.getOrCreate(adminDto.getFiliere());
        Admin admin;

        admin = utilisateurMapper.toClasseA(adminDto);

        admin.setFiliere(filiere);

        Admin savedAdmin = utilisateurRepository.save(admin);

        return utilisateurMapper.toDtoA(savedAdmin);
    }

    @Override
    public Stream<AdminDto> afficherAdmins() {
        List<Admin> admins = this.utilisateurRepository.findallAdmins();

        Stream<AdminDto> adminsDto = admins.stream().map(utilisateurMapper::toDtoA);
        return adminsDto ;
    }
}
