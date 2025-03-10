package v3bis.team.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v3bis.team.dtos.QuestionDto;
import v3bis.team.service.Interfaces.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@CrossOrigin
@RestController
@RequestMapping(path = "questions")
public class QuestionController {

	@Autowired
	QuestionService questionService;


	@Autowired
	public QuestionController(QuestionService questionService){
		this.questionService = questionService;
	}


	@PostMapping(path = "/ajout", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> newQuestion(@RequestBody QuestionDto request) {
		try {
			QuestionDto savedQuestion = questionService.createQuestion(request);
			return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.print("ERREUR POST METHOD \n \n");
			throw new RuntimeException(e);
		}
	}


	@GetMapping(path = "/voir/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("id") int qId) {
		QuestionDto questionDto = questionService.getQuestionById(qId);
		return ResponseEntity.ok(questionDto);
	}


	@GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionDto>> getAllQuestions() {
		List<QuestionDto> questions = questionService.getAllQuestions();
		return ResponseEntity.ok(questions);
	}

	/**
	 * Afficher les questions validées uniquement.
	 */
	@GetMapping(path = "/all_validées", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuestionDto>> getAllQuestionsValidees() {
		List<QuestionDto> questions = questionService.getAllQuestionsValidees();
		return ResponseEntity.ok(questions);
	}

	/**
	 * Afficher les questions en attente uniquement.
	 */
	@GetMapping(path = "/all_attente", produces = APPLICATION_JSON_VALUE)
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
