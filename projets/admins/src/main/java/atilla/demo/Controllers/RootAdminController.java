package atilla.demo.Controllers;

import atilla.demo.services.Classes.RootAdminServiceImpl;
import atilla.demo.services.Classes.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("RootAdmin")
public class RootAdminController {

    @Autowired
    private RootAdminServiceImpl rootAdminService ;


    public RootAdminController( RootAdminServiceImpl rootAdminService) {

        this.rootAdminService = rootAdminService;
    }

    @PostMapping(path = "ajouterCertification")
    public ResponseEntity<String> certifierUtilisateur(@RequestParam int adminId, @RequestParam int matiereId, @RequestParam int rootAdminId){

        this.rootAdminService.certifierAdmin(adminId, matiereId, rootAdminId ) ;
        return ResponseEntity.ok("Opération Réussie");

    }


    @PostMapping(path = "CertifierUtilisateur")
    public ResponseEntity<String> certifierUtilisateur(@RequestParam int utilisateurId, @RequestParam int matiereId){

        this.rootAdminService.certifierUtilisateur(utilisateurId, matiereId); ;
        return ResponseEntity.ok("Opération Réussie");

    }

    @PostMapping(path = "removeCertification")
    public ResponseEntity<String> removeCertification(@RequestParam int adminId, @RequestParam int matiereId, @RequestParam int rootAdminId){

        this.rootAdminService.removeCertification(adminId, matiereId, rootAdminId ); ;
        return ResponseEntity.ok("Opération Réussie");

    }

}
