package atilla.demo.Controllers;


import atilla.demo.Repositories.MatiereRepository;
import atilla.demo.dto.InscriptionDto;
import atilla.demo.dto.MatiereDto;
import atilla.demo.dto.UtilisateurDto;
import atilla.demo.services.Classes.MatiereServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path="matiere")
public class MatiereController  {

    private MatiereServiceImpl matiereService;

    public MatiereController(MatiereServiceImpl matiereService) {
        this.matiereService = matiereService;
    }

    @PostMapping(path = "ajouter",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> inscrire(@RequestBody MatiereDto matiereDto){

        MatiereDto matiereDtosaved =this.matiereService.creerMatiere(matiereDto);
        return new ResponseEntity<>(matiereDtosaved, HttpStatus.CREATED);
    }

    @GetMapping(path="/id/{id}", produces = APPLICATION_JSON_VALUE)
    public MatiereDto rechercherUtilisateur(@PathVariable int id){
        return this.matiereService.rechercherMatiereId(id);
    }

}
