package v3.team.service.Classes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import v3.team.dtos.QuestionDto;
import v3.team.enumerations.EtatValidation;
import v3.team.exceptions.ExceptionRessourceAbsente;
import v3.team.mapper.Interfaces.QuestionMapperImpl;
import v3.team.model.Question;
import v3.team.repositories.QuestionRepository;
import v3.team.service.Interfaces.QuestionService;


@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    final QuestionRepository qRepo;

    final QuestionMapperImpl qMap;

    @Autowired
    final DateEventService dateManager;


    public QuestionServiceImpl(QuestionRepository qRepo, QuestionMapperImpl qMap, DateEventService dateManager) {
        this.qRepo = qRepo;
        this.qMap = qMap;
        this.dateManager = dateManager;
    }

    //TODO : validation immédiate pour des questions créées par des admins
    @Override
    @Transactional
    public QuestionDto createQuestion(QuestionDto qDto) {
        Question q = qMap.toClasse(qDto);
        //Initialize validation question dates
        q.setEtatValidation( EtatValidation.VALIDEE.getValeurEtat() );
        q.setDateValidee( dateManager.dateEvent( q.getDateValidee() ) );
        q.setTempsAttente(" - ");
        q.setDateDemandeAjout(" - ");
        /*System.out.println(q.getCreateur());*/
        Question savedQuestion = qRepo.save(q);
        return qMap.toDto(savedQuestion);
    }

    //TODO : ADMIN
    @Override
    public QuestionDto getQuestionById(int qId) {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Question data is not associated with given id "+qId+"\n"));
        dateManager.voirTempsAttente(q);
        //System.out.println(q.toString());         Exemple d'affichage d'une question
        return qMap.toDto(q);
    }

    //TODO : ADMIN + accès aux questions validées ou en attente uniquement
    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = qRepo.findAll();
        //MAJ le temps d'attente des questions
        for (Question valideeQ : questions) {
            if ( valideeQ.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                dateManager.voirTempsAttente(valideeQ);
            }
        }
        //First map all the corresponding users DTO, then convert to a list
        return questions.stream().map((q) -> qMap.toDto(q))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllQuestionsValidees() {
        List<Question> questions = qRepo.findAllQuestionsValidees();
        return questions.stream().map((q) -> qMap.toDto(q)).collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getAllQuestionsAttente()
    {
        List<Question> questions = qRepo.findAllQuestionsAttente();
        //MAJ le temps d'attente des questions
        for (Question valideeQ : questions) {
            if ( valideeQ.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                dateManager.voirTempsAttente(valideeQ);
            }
        }
        return questions.stream().map((q) -> qMap.toDto(q)).collect(Collectors.toList());
    }


    //TODO : ADMIN
    @Override
    @Transactional
    public QuestionDto updateQuestion(int qId, QuestionDto updatedQuestion) {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId+"\n")
        );
        q.setQuestion(updatedQuestion.getQuestion());
        q.setCorrection(updatedQuestion.getCorrection());
        q.setReponses(updatedQuestion.getReponses());
        q.setIndBonneRep(updatedQuestion.getIndBonneRep());
        q.setIndice(updatedQuestion.getIndice());
        dateManager.voirTempsAttente(q);

        Question updatedQuestionObj = qRepo.save(q);

        return qMap.toDto(updatedQuestionObj);
    }

    //TODO : ADMIN
    @Override
    @Transactional
    public QuestionDto validerQuestion(int qId) {

        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );
        Question updatedQuestionObj = q;
        try
        {
            if ( q.getEtatValidation().equals(EtatValidation.EN_ATTENTE.getValeurEtat()) ) {
                dateManager.voirTempsAttente(q);
                q.setEtatValidation(EtatValidation.VALIDEE.getValeurEtat());
                q.setDateValidee( dateManager.dateEvent(q.getDateValidee()) );
                //Update number of validated questions for existing creators
                if ( !Objects.equals(q.getCreateur(), null) ) {
                    q.getCreateur().setNbQuestionsValides( q.getCreateur().getNbQuestionsValides() + 1 );
                }
                updatedQuestionObj = qRepo.save(q);
                //No need to save changes to creator thanks to the JPA relationship cascading
            } else {
                System.out.println("La question n'a pas été mise en attente de validation\n");
            }
            return qMap.toDto(updatedQuestionObj);
        }
        catch (Exception e) { throw new RuntimeException(e); }

    }

    //TODO : ADMIN
    @Override
    @Transactional
    public QuestionDto refusValidation(int qId) {

        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );

        Question updatedQuestionObj = null;
        if ( q.getEtatValidation().equals(EtatValidation.REFUSEE.getValeurEtat()) ) {
            System.out.println("La question n'a pas été remise en attente de validation\n");
        }
        else if ( Objects.equals(q.getCreateur(), null) ) {
            //Refus d'une question créée par un compte inexistant (effacé entre temps)
            deleteQuestion(q.getId());
        }
        else {
            dateManager.voirTempsAttente(q);
            //Reset l'éventuelle date de validation
            if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
                q.setDateValidee( dateManager.resetDate() );
            }
            q.setEtatValidation(EtatValidation.REFUSEE.getValeurEtat());
            //Update number of validated questions for existing creators that have already tried to contribute
            if ( !Objects.equals(q.getCreateur(), null) && q.getCreateur().getNbQuestionsValides() > 0 ) {
                q.getCreateur().setNbQuestionsValides( q.getCreateur().getNbQuestionsValides() - 1 );
            }
            updatedQuestionObj = qRepo.save(q);
            //No need to save changes to creator thanks to the cascading of the JPA relationship
        }
        return qMap.toDto(updatedQuestionObj);

    }

    //TODO : ADMIN
    @Override
    @Transactional
    public QuestionDto remiseAttente(int qId) {

        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );
        Question updatedQuestionObj = null;
        if ( Objects.equals(q.getCreateur(), null) ) {
            //Supprimer une question remise en attente et créée par un compte inexistant (effacé entre temps)
            deleteQuestion(q.getId());
        }
        else if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
            dateManager.dateNouvDemande(q);
            q.setEtatValidation( EtatValidation.EN_ATTENTE.getValeurEtat() );
            q.setDateValidee( dateManager.resetDate() );
            //Update number of validated questions for existing creators that have already tried to contribute
            if ( !Objects.equals(q.getCreateur(), null) && q.getCreateur().getNbQuestionsValides() > 0 ) {
                q.getCreateur().setNbQuestionsValides( q.getCreateur().getNbQuestionsValides() - 1 );
            }
            updatedQuestionObj = qRepo.save(q);
            //No need to save changes to creator thanks to the cascading of the JPA relationship
            return qMap.toDto(updatedQuestionObj);
        }

        return qMap.toDto(updatedQuestionObj);
    }

    //TODO : ADMIN
    @Override
    public void deleteQuestion(int qId) {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );

        if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat())
           || Objects.equals(q.getCreateur(), null) ) {
            qRepo.deleteById(qId);
            System.out.println("Question was deleted successfully.\n");
        } else {
            System.out.println("SVP ne soyez pas brutal envers le créateur de la question.\n");
        };
    }


}

