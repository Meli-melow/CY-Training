package v4.team.service.Interfaces;


import org.springframework.stereotype.Service;
import v4.team.dtos.QuestionDto;

import java.util.List;

/**
 * L'interface du service dédié à l'entité Question.
 */

@Service
public interface QuestionService {

    QuestionDto createQuestion(String nomUE, String nomMat, QuestionDto qDto);

    QuestionDto getQuestionById(int qId);

    List<QuestionDto> getAllQuestions();

    List<QuestionDto> getAllQuestionsValidees();

    List<QuestionDto> getAllQuestionsAttente();

    QuestionDto updateQuestion(int qId, QuestionDto updatedQuestion);

    QuestionDto validerQuestion(int qId);

    QuestionDto refusValidation(int qId);

    QuestionDto remiseAttente(int qId);

    void deleteQuestion(int qId);

}
