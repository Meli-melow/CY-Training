package v4.team.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v4.team.dtos.QuestionDto;
import v4.team.dtos.UtilisateurDto;
import org.springframework.beans.factory.annotation.Autowired;
import v4.team.service.Interfaces.UtilisateurService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Le contrôleur associé à l'API des utilisateurs.
 * Voir le détail des méthodes dans le service correspondant.
 */

@CrossOrigin
@RestController
@RequestMapping(path = "/cyusers")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }


    @PostMapping(path = "/nouveau_compte", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        UtilisateurDto savedUtilisateur = utilisateurService.createUtilisateur(utilisateurDto);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }


    @GetMapping(path ="/voir_profil/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable("id") int uId) {
        UtilisateurDto utilisateurDto = utilisateurService.getUtilisateurById(uId);
        return ResponseEntity.ok(utilisateurDto);
    }


    @GetMapping(path ="/voir_profil_mail/{email}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> getUtilisateurByEmail(@PathVariable("email") String userEmail) {
        UtilisateurDto utilisateurDto = utilisateurService.getUtilisateurByEmail(userEmail);
        return ResponseEntity.ok(utilisateurDto);
    }


    @GetMapping(path = "/tous_comptes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }


    @PatchMapping(path = "/modifier_profil/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable("id") int uId, @RequestBody UtilisateurDto updatedUtilisateur) {
        UtilisateurDto utilisateurDto = utilisateurService.updateUtilisateur(uId, updatedUtilisateur);
        return ResponseEntity.ok(utilisateurDto);
    }


    @DeleteMapping(path ="/supprimer_compte/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable("id") int uId) {
        utilisateurService.deleteUtilisateur(uId);
        return ResponseEntity.ok("Student deleted successfully.");
    }


    @PatchMapping(path = "/proposer_question/{id}/{nom_ue}/{nom_mat}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> newQuestionByUser (@PathVariable("id") int uId, @PathVariable("nom_mat") String nomMat,
                                                          @PathVariable("nom_ue") String nomUE, @RequestBody QuestionDto newQ) {
        QuestionDto uCreateur = utilisateurService.newQuestionByUser(uId, nomUE, nomMat, newQ);
        return ResponseEntity.ok(uCreateur);
    }


    @GetMapping(path = "/voir_propositions/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getCreatedQuestions(@PathVariable("id") int uId) {
        List<QuestionDto> listeQuestions = utilisateurService.getCreatedQuestions(uId);
        return ResponseEntity.ok(listeQuestions);
    }


    @PatchMapping(path = "/modifier_question/{id_user}/{id_q}/{nom_ue}/{nom_mat}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> updateQuestionByUser (@PathVariable("id_user") int uId, @PathVariable("id_q") int qId,
                                                             @PathVariable("nom_ue") String nomUE, @PathVariable("nom_mat") String nomMat, @RequestBody QuestionDto changedQ) {
        QuestionDto questionModifiee = utilisateurService.updateQuestion(uId, qId, nomUE, nomMat, changedQ);
        return ResponseEntity.ok(questionModifiee);
    }


    @PatchMapping(path = "/nouv_demande_validation/{id_user}/{id_q}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> askQuestionValidation(@PathVariable("id_user") int uId, @PathVariable("id_q") int qId) {
        QuestionDto demandeQ = utilisateurService.nouvDemandevalidation(uId, qId);
        return ResponseEntity.ok(demandeQ);
    }


    @PatchMapping(path = "/supprimer_question_creee/{id_user}/{id_q}")
    public ResponseEntity<String> deleteQuestionUser(@PathVariable("id_user") int uId, @PathVariable("id_q") int qId) {
        utilisateurService.deleteQuestion(uId, qId);
        return ResponseEntity.ok("Vérifiez la suppression dans le CLI.");
    }

}
