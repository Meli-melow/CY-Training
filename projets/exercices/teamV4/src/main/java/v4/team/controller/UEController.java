package v4.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.UEDto;
import v4.team.service.Interfaces.UEService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Le contrôleur associé à l'API des UE.
 * Voir le détail des méthodes dans le service correspondant.
 */

@CrossOrigin
@RestController
@RequestMapping(path = "ues")
public class UEController {


    @Autowired
    private UEService ueService;


    @Autowired
    public UEController(UEService ueService) { this.ueService = ueService; }


    @PostMapping(path = "/ajout", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UEDto> newUE(@RequestBody UEDto request) {
        try {
            UEDto ueQuestion = ueService.creerUE(request);
            return new ResponseEntity<>(ueQuestion, HttpStatus.CREATED);
        } catch (Exception e) { throw new RuntimeException("ERREUR POST MATIERE"); }
    }


    @GetMapping(path = "/id_voir_ue/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UEDto> getUEById(@PathVariable("id") int ueId) {
        UEDto ueDto = ueService.getUEById(ueId);
        return ResponseEntity.ok(ueDto);
    }


    @GetMapping(path = "/nom_voir_ue/{nom}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UEDto> getUEByName(@PathVariable("nom") String nomUE) {
        UEDto ueDto = ueService.getUEByName(nomUE);
        return ResponseEntity.ok(ueDto);
    }


    @GetMapping(path = "/toutes_ues", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UEDto>> getAllUEs() {
        List<UEDto> ues = ueService.getAllUEs();
        return ResponseEntity.ok(ues);
    }


    @PatchMapping(path ="/modifier/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UEDto> updateMatiere(@PathVariable("id") int ueId, @RequestBody UEDto updatedUE) {
        UEDto ueDto = ueService.updateUE(ueId, updatedUE);
        return ResponseEntity.ok(ueDto);
    }


    @PatchMapping(path = "/ajout_matiere/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> ajoutMatiereUE(@PathVariable("id") int ueId, @RequestBody MatiereDto nouvMat) {
        MatiereDto matCreee = ueService.ajoutMatiere(ueId, nouvMat);
        return ResponseEntity.ok(matCreee);
    }


    //TODO : ADMIN
    @GetMapping(path = "/matieres_ue/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MatiereDto>> getUEAllMatieres(@PathVariable("id") int ueId) {
        List<MatiereDto> matieresUE = ueService.getUEAllMatieres(ueId);
        return ResponseEntity.ok(matieresUE);
    }


    //TODO : ADMIN
    @PatchMapping(path = "/supprimer_matiere/{nom}/{id_m}")
    public ResponseEntity<String> deleteMatiereUE(@PathVariable("nom") String nomUE, @PathVariable("id_m") int ueId) {
        ueService.deleteMatiereUE(nomUE, ueId);
        return ResponseEntity.ok("Vérifiez la suppression dans le CLI.");
    }


    //TODO : ADMIN
    @DeleteMapping(path ="/supprimer/{id}")
    public ResponseEntity<String> deleteUE(@PathVariable("id") int ueId) {
        ueService.deleteUE(ueId);
        return ResponseEntity.ok("Matière supprimée avec succès.");
    }
}
