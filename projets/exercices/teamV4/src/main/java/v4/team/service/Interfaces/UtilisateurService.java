package v4.team.service.Interfaces;


import org.springframework.stereotype.Service;
import v4.team.dtos.QuestionDto;
import v4.team.dtos.UtilisateurDto;
import v4.team.model.Question;

import java.util.List;

/**
 * L'interface du service dédié à l'entité Utilisateur.
 */

@Service
public interface UtilisateurService {

    UtilisateurDto createUtilisateur(UtilisateurDto uDto);

    UtilisateurDto getUtilisateurById(int uId);

    UtilisateurDto getUtilisateurByEmail(String userEmail);

    List<UtilisateurDto> getAllUtilisateurs();

    UtilisateurDto updateUtilisateur(int uId, UtilisateurDto updatedUtilisateur);

    QuestionDto newQuestionByUser(int uId, String nomUE, String matNom, QuestionDto qDto);

    List<QuestionDto> getCreatedQuestions(int uId);

    void updateMatiereQuestion(Question modifQ, String nomMat, String nomUE);

    QuestionDto updateQuestion(int uId, int qId, String nomUE, String nomMat, QuestionDto updatedQuestion);

    //TODO
    QuestionDto nouvDemandevalidation(int uId, int qId);

    void deleteQuestion(int uId, int qId);

    void deleteUtilisateur(int uId);

}
