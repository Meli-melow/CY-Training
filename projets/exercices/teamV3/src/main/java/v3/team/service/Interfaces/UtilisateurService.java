package v3.team.service.Interfaces;


import org.springframework.stereotype.Service;
import v3.team.dtos.QuestionDto;
import v3.team.dtos.UtilisateurDto;

import java.util.List;

@Service
public interface UtilisateurService {

    UtilisateurDto createUtilisateur(UtilisateurDto uDto);

    UtilisateurDto getUtilisateurById(int uId);

    List<UtilisateurDto> getAllUtilisateurs();

    UtilisateurDto updateUtilisateur(int uId, UtilisateurDto updatedUtilisateur);

    UtilisateurDto newQuestionByUser(int uId, QuestionDto qDto);

    List<QuestionDto> getCreatedQuestions(int uId);

    QuestionDto updateQuestion(int uId, int qId, QuestionDto updatedQuestion);

    QuestionDto nouvDemandeValidation(int uId, int qId);

    void deleteQuestion(int uId, int qId);

    void deleteUtilisateur(int uId);

}
