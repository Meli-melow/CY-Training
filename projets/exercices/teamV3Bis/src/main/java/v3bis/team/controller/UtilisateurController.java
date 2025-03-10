package v3bis.team.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v3bis.team.dtos.QuestionDto;
import v3bis.team.dtos.UtilisateurDto;
import org.springframework.beans.factory.annotation.Autowired;
import v3bis.team.service.Interfaces.UtilisateurService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@CrossOrigin
@RestController
@RequestMapping(path = "/cyusers")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    //In postman : enter data in Body > raw and choose JSON format (in blue)
    @PostMapping(path = "/nouveau", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto savedUtilisateur = utilisateurService.createUtilisateur(utilisateurDto);
            return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(path ="/profil/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable("id") int uId) {
        UtilisateurDto utilisateurDto = utilisateurService.getUtilisateurById(uId);
        return ResponseEntity.ok(utilisateurDto);
    }


    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }


    //In postman : enter data in Body > raw and choose JSON format (in blue)
    //Fields are inside ""
    @PatchMapping(path = "/modifier/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable("id") int uId, @RequestBody UtilisateurDto updatedUtilisateur) {
        UtilisateurDto utilisateurDto = utilisateurService.updateUtilisateur(uId, updatedUtilisateur);
        return ResponseEntity.ok(utilisateurDto);
    }


    @DeleteMapping(path ="/supprimer/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable("id") int uId) {
        utilisateurService.deleteUtilisateur(uId);
        return ResponseEntity.ok("Student deleted successfully.");
    }


    @PatchMapping(path = "/nouvelle_question/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> newQuestionByUser (@PathVariable("id") int uId, @RequestBody QuestionDto newQ) {
        QuestionDto uCreateur = utilisateurService.newQuestionByUser(uId, newQ);
        return ResponseEntity.ok(uCreateur);
    }


    @GetMapping(path = "/voir_questions_creees/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getCreatedQuestions(@PathVariable("id") int uId) {
        List<QuestionDto> listeQuestions = utilisateurService.getCreatedQuestions(uId);
        return ResponseEntity.ok(listeQuestions);
    }


    @PatchMapping(path = "/modifier_question/{id_user}/{id_q}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> updateQuestionByUser (@PathVariable("id_user") int uId, @PathVariable("id_q") int qId,
    @RequestBody QuestionDto changedQ) {
        QuestionDto questionModifiee = utilisateurService.updateQuestion(uId, qId, changedQ);
        return ResponseEntity.ok(questionModifiee);
    }


    @PatchMapping(path = "/nouv_demande_validation/{id_user}/{id_q}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> askQuestionValidation(@PathVariable("id_user") int uId, @PathVariable("id_q") int qId) {
        QuestionDto demandeQ = utilisateurService.nouvDemandevalidation(uId, qId);
        return ResponseEntity.ok(demandeQ);
    }


    @PatchMapping(path = "/supprimer_question/{id_user}/{id_q}")
    public ResponseEntity<String> deleteQuestionUser(@PathVariable("id_user") int uId, @PathVariable("id_q") int qId) {
        utilisateurService.deleteQuestion(uId, qId);
        return ResponseEntity.ok("Vérifiez la suppression dans le CLI.");
    }

}
