package atilla.demo.Controllers;

import atilla.demo.classes.Filiere;
import atilla.demo.dto.AdminDto;
import atilla.demo.dto.FiliereDto;
import atilla.demo.dto.InscriptionDto;
import atilla.demo.dto.UtilisateurDto;
import atilla.demo.services.Classes.UtilisateurServiceImpl;
import jdk.jshell.execution.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController

@Slf4j
@RequestMapping(path= "utilisateur" )
public class UtilisateurController  {

    private UtilisateurServiceImpl utilisateurService;

    public UtilisateurController(UtilisateurServiceImpl utilisateurService){

        this.utilisateurService=utilisateurService;
    }

    @PostMapping(path = "inscrireUtilisateur",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<InscriptionDto> inscrire(@RequestBody InscriptionDto inscriptionDto){

        //log.info("Inscription");
        InscriptionDto inscriptionDtoBDD =this.utilisateurService.inscrireUtilisateur(inscriptionDto);
        return new ResponseEntity<>(inscriptionDtoBDD, HttpStatus.CREATED);
    }




    @PostMapping(path = "inscrire",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> inscrire(@RequestBody UtilisateurDto utilisateurDto){

        UtilisateurDto savedUtilisateur =this.utilisateurService.inscrire1(utilisateurDto);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }





    @GetMapping(path="/id/{id}", produces = APPLICATION_JSON_VALUE)
    public UtilisateurDto rechercherUtilisateur(@PathVariable int id){
        return this.utilisateurService.rechercherId(id);
    }

    @GetMapping(path="mail/{mail}", produces=APPLICATION_JSON_VALUE)
    public UtilisateurDto rechercherUtilisateur(@PathVariable String mail){
        return this.utilisateurService.rechercherMail(mail);
    }


    @GetMapping(path = "all", produces = APPLICATION_JSON_VALUE)
    public Stream<UtilisateurDto> afiicherALL (){
        return this.utilisateurService.afficherAll();
    }

    @DeleteMapping(path = "/delete/{id}")
    public void supprimerParId ( @PathVariable int id ){
        this.utilisateurService.deleteUtilsateur(id);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping( path = "/modification/{id}", consumes = APPLICATION_JSON_VALUE )
    public void modifierUtilisateur ( @PathVariable int id , @RequestBody UtilisateurDto nvUtilisateur){
        this.utilisateurService.modifierUtilisateur(id, nvUtilisateur);
    }

    @PostMapping("/connexion")
    public String login(@RequestParam String email, @RequestParam String password) {
        return this.utilisateurService.connexion(email, password);
    }


}
