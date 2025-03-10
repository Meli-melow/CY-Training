package v4.team.service.Interfaces;

import org.springframework.stereotype.Service;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.QuestionDto;
import v4.team.model.Matiere;

import java.util.List;

/**
 * L'interface du service dédié à l'entité Matiere.
 */

@Service
public interface MatiereService {

    MatiereDto createMatiere(MatiereDto matiereDto);

    MatiereDto getMatiereById(int mId);

    MatiereDto getMatiereByName(String name);

    MatiereDto updateMatiere(int mId, MatiereDto updatedMatiere);

    List<MatiereDto> getAllMatieres();

    List<QuestionDto> getMatiereAllQuestions(int mId);

    List<QuestionDto> getMatiereQuestionsAccessibles(int mId);

    public List<QuestionDto> getMatiereQuestionsAttente(int mId);

    void deleteQuestionMatiere(String nomMatiere, int qId);

    void deleteMatiere(int mId);

}
