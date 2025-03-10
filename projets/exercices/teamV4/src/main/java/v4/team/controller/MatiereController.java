package v4.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.QuestionDto;
import v4.team.model.Matiere;
import v4.team.service.Interfaces.MatiereService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Le contrôleur associé à l'API des matières.
 * Voir le détail des méthodes dans le service correspondant.
 */

@CrossOrigin
@RestController
@RequestMapping(path = "matieres")
public class MatiereController {


    @Autowired
    private MatiereService matiereService;

    @Autowired
    public MatiereController(MatiereService matiereService) { this.matiereService = matiereService; }


    @PostMapping(path = "/ajout", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> newMatiere(@RequestBody MatiereDto request) {
        try {
            MatiereDto matiereQuestion = matiereService.createMatiere(request);
            return new ResponseEntity<>(matiereQuestion, HttpStatus.CREATED);
        } catch (Exception e) { throw new RuntimeException("ERREUR POST MATIERE"); }
    }


    @GetMapping(path = "/voir_matiere_id/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> getMatiereById(@PathVariable("id") int mId) {
        MatiereDto matiereDto = matiereService.getMatiereById(mId);
        return ResponseEntity.ok(matiereDto);
    }


    @GetMapping(path = "/voir_matiere_nom/{nom}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> getMatiereByName(@PathVariable("nom") String nomMatiere) {
        MatiereDto matiereDto = matiereService.getMatiereByName(nomMatiere);
        return ResponseEntity.ok(matiereDto);
    }


    @GetMapping(path = "/toutes_matieres", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MatiereDto>> getAllMatieres() {
        List<MatiereDto> matieres = matiereService.getAllMatieres();
        return ResponseEntity.ok(matieres);
    }


    @PatchMapping(path ="/modifier/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MatiereDto> updateMatiere(@PathVariable("id") int mId, @RequestBody MatiereDto updatedMatiere) {
        MatiereDto matiereDto = matiereService.updateMatiere(mId, updatedMatiere);
        return ResponseEntity.ok(matiereDto);
    }


    @GetMapping(path = "/toutes_questions/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getMatiereAllQuestions(@PathVariable("id") int mId) {
        List<QuestionDto> questionsMatiere = matiereService.getMatiereAllQuestions(mId);
        return ResponseEntity.ok(questionsMatiere);
    }

    //TODO : ADMIN
    @GetMapping(path = "/matiere_questions_validees/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getMatiereQuestionsAccessibles(@PathVariable("id") int mId) {
        List<QuestionDto> questionsAccessiblesMatiere = matiereService.getMatiereQuestionsAccessibles(mId);
        return ResponseEntity.ok(questionsAccessiblesMatiere);
    }

    //TODO : ADMIN
    @GetMapping(path = "/matiere_questions_attente/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestionDto>> getMatiereQuestionsAttente(@PathVariable("id") int mId) {
        List<QuestionDto> questionsAccessiblesMatiere = matiereService.getMatiereQuestionsAttente(mId);
        return ResponseEntity.ok(questionsAccessiblesMatiere);
    }

    //TODO : ADMIN
    @PatchMapping(path = "/supprimer_question/{nom}/{id_q}")
    public ResponseEntity<String> deleteQuestionMatiere(@PathVariable("nom") String nomMatiere, @PathVariable("id_q") int qId) {
        matiereService.deleteQuestionMatiere(nomMatiere, qId);
        return ResponseEntity.ok("Vérifiez la suppression dans le CLI.");
    }


    //TODO : ADMIN
    @DeleteMapping(path ="/supprimer/{id}")
    public ResponseEntity<String> deleteMatiere(@PathVariable("id") int mId) {
        matiereService.deleteMatiere(mId);
        return ResponseEntity.ok("Matière supprimée avec succès.");
    }
}
