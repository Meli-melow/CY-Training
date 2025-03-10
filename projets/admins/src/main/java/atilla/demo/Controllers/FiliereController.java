package atilla.demo.Controllers;


import atilla.demo.dto.FiliereDto;
import atilla.demo.dto.UeDto;
import atilla.demo.services.Classes.FiliereServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "filiere")

public class FiliereController {
    private FiliereServiceImpl filiereService ;

    public FiliereController(FiliereServiceImpl filiereService){
        this.filiereService= filiereService;   }




    @PostMapping(path="ajouter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FiliereDto> ajouterUtilisateur(@RequestBody FiliereDto filiereDto) {
        FiliereDto savedFiliere = filiereService.ajouterFiliere(filiereDto);
        return new ResponseEntity<>(savedFiliere, HttpStatus.CREATED);
    }

    @GetMapping(path="/all" , produces = APPLICATION_JSON_VALUE)
    public Stream<FiliereDto> all (){
        return this.filiereService.afficherAll();
    }



    @GetMapping(path="/id/{id}", produces = APPLICATION_JSON_VALUE)
    public FiliereDto rechercherUtilisateur(@PathVariable int id){
        return this.filiereService.rechercherId(id);
    }

    @GetMapping(path="nom/{nom}", produces=APPLICATION_JSON_VALUE)
    public FiliereDto rechercherUtilisateur(@PathVariable String nom){
        return this.filiereService.rechercherNom(nom);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void supprimerParId ( @PathVariable int id ){
        this.filiereService.deleteFiliere(id);

    }


}
