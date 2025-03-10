package v4.team.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v4.team.dtos.QuestionDto;
import v4.team.service.Interfaces.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Le contrôleur associé à l'API des questions.
 * Voir le détail des méthodes dans le service correspondant.
 */

@CrossOrigin
@RestController
@RequestMapping(path = "questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;


	@Autowired
	public QuestionController(QuestionService questionService){
		this.questionService = questionService;
	}


	@PostMapping(path = "/ajout/{nom_ue}/{nom_mat}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> newQuestion(@PathVariable("nom_ue") String nomUE, @PathVariable("nom_mat") String nomMat,
												   @RequestBody QuestionDto request) {
		QuestionDto savedQuestion = questionService.createQuestion(nomUE, nomMat, request);
		return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
	}


	@GetMapping(path = "/voir_question/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("id") int qId) {
		QuestionDto questionDto = questionService.getQuestionById(qId);
		return ResponseEntity.ok(questionDto);
	}


	@GetMapping(path = "/toutes_questions", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionDto>> getAllQuestions() {
		List<QuestionDto> questions = questionService.getAllQuestions();
		return ResponseEntity.ok(questions);
	}

	/**
	 * Afficher les questions validées uniquement.
	 */
	@GetMapping(path = "/questions_validees", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionDto>> getAllQuestionsValidees() {
		List<QuestionDto> questions = questionService.getAllQuestionsValidees();
		return ResponseEntity.ok(questions);
	}

	/**
	 * Afficher les questions en attente uniquement.
	 */
	@GetMapping(path = "/questions_attente", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionDto>> getAllQuestionsAttente() {
		List<QuestionDto> questions = questionService.getAllQuestionsAttente();
		return ResponseEntity.ok(questions);
	}

	//In postman : enter data in Body > raw and choose JSON format (in blue)
	//Check JSON format
	@PatchMapping(path ="/modifier/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("id") int qId, @RequestBody QuestionDto updatedQuestion) {
		QuestionDto questionDto = questionService.updateQuestion(qId, updatedQuestion);
		return ResponseEntity.ok(questionDto);
	}


	@PatchMapping(path = "/valider_question/{id_q}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> acceptQuestion(@PathVariable("id_q") int qId) {
		QuestionDto valideeQ = questionService.validerQuestion(qId);
		return ResponseEntity.ok(valideeQ);
	}


	@PatchMapping(path = "/refuser_question/{id_q}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> refusQuestion(@PathVariable("id_q") int qId) {
		QuestionDto refuseeQ = questionService.refusValidation(qId);
		return ResponseEntity.ok(refuseeQ);
	}


	@PatchMapping(path = "/remise_attente/{id_q}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> remiseAttente(@PathVariable("id_q") int qId) {
		QuestionDto questionAtt = questionService.remiseAttente(qId);
		return ResponseEntity.ok(questionAtt);
	}


	@DeleteMapping(path ="/supprimer/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("id") int qId) {
		questionService.deleteQuestion(qId);
		return ResponseEntity.ok("Vérifiez le résultat dans le CLI");
	}

}
