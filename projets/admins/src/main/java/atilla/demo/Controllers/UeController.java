package atilla.demo.Controllers;


import atilla.demo.dto.MatiereDto;
import atilla.demo.dto.UeDto;
import atilla.demo.dto.UtilisateurDto;
import atilla.demo.services.Interfaces.UeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path="Ue")

public class UeController {

    private UeService ueService ;

    public UeController(UeService ueService) {
        this.ueService = ueService;
    }

    @PostMapping(path="ajouter", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UeDto> ajouterUtilisateur(@RequestBody UeDto ueDto) {
        UeDto savedUe = this.ueService.ajouterUe(ueDto);
        return new ResponseEntity<>(savedUe, HttpStatus.CREATED);
    }


    @GetMapping(path="/all" , produces = APPLICATION_JSON_VALUE)
    public Stream<UeDto> all (){
        return this.ueService.afficherAll();
    }

    @GetMapping(path="/id/{id}", produces = APPLICATION_JSON_VALUE)
    public UeDto rechercherUtilisateur(@PathVariable int id){
        return this.ueService.rechercherId(id);
    }

    @GetMapping(path="nom/{nom}", produces=APPLICATION_JSON_VALUE)
    public UeDto rechercherUtilisateur(@PathVariable String nom){
        return this.ueService.rechercherNom(nom);
    }



    @DeleteMapping(path = "/delete/{id}")
    public void supprimerParId ( @PathVariable int id ){
        this.ueService.deleteUE(id);

    }

    @PostMapping(path = "matieres", produces = APPLICATION_JSON_VALUE)
    public Stream<MatiereDto> afiicherALL (@RequestParam  int idUe ){
        return this.ueService.afficherMatiere_UE(idUe);
    }







}
