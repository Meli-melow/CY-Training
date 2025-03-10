package v4.team.service.Classes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import v4.team.dtos.QuestionDto;
import v4.team.enumerations.EtatValidation;
import v4.team.exceptions.ExceptionRessourceAbsente;
import v4.team.mapper.Interfaces.MatiereMapperImpl;
import v4.team.mapper.Interfaces.QuestionMapperImpl;
import v4.team.mapper.Interfaces.UEMapperImpl;
import v4.team.mapper.Interfaces.UtilisateurMapperImpl;
import v4.team.model.Matiere;
import v4.team.model.Question;
import v4.team.model.UE;
import v4.team.repositories.MatiereRepository;
import v4.team.repositories.QuestionRepository;
import v4.team.repositories.UERepository;
import v4.team.repositories.UtilisateurRepository;
import v4.team.service.Interfaces.QuestionService;

/**
 * Le service dédié à l'entité Question.
 * Certaines méthodes préparent l'intégration des admins.
 *
 * Les services se servent des repositories et des mappers des autres entités.
 */

@Service
public class QuestionServiceImpl implements QuestionService {


    //Permet d'ajouter le repository de son entité associé dans le contexte de Spring
    @Autowired
    private final QuestionRepository qRepo;

    private final QuestionMapperImpl qMap;

    private final UtilisateurRepository uServRepo;

    private final UtilisateurMapperImpl uServMapper;

    private final MatiereRepository mServRepo;

    private final MatiereMapperImpl mServMapper;

    private final UERepository ueServRepo;

    //Il faut peut-etre autowire avec @Autowire pour rajouter le mapper des UE au contexte Spring
    private final UEMapperImpl ueServMapper;



    public QuestionServiceImpl(QuestionRepository qRepo, QuestionMapperImpl qMap, UtilisateurRepository uServRepo,
                               UtilisateurMapperImpl uServMapper, MatiereRepository mServRepo, MatiereMapperImpl mServMapper, UERepository ueServRepo,
                               UEMapperImpl ueServMapper) {
        this.qRepo = qRepo;
        this.qMap = qMap;
        this.uServRepo = uServRepo;
        this.uServMapper = uServMapper;
        this.mServRepo = mServRepo;
        this.mServMapper = mServMapper;
        this.ueServRepo = ueServRepo;
        this.ueServMapper = ueServMapper;
    }

    //TODO : ADMIN -> validation immédiate
    /**
     * Requête POST.
     * Création d'une question (version pour préparer l'intégration des admins).
     * La question est validée immédiatement après sa création. On peut
     * imaginer que ce soit le cas pour les admins rajoutant une question dans
     * la matière correspondant à celle de leur certification.
     *
     * La question est repertoriée dans une matière à partir de son plutôt que
     * toute l'instance.
     *
     * @param nomMat la matière de la question
     * @param qDto le corps de la requête
     * @return la question créée
     */
    @Override
    @Transactional
    public QuestionDto createQuestion(String nomUE, String nomMat, QuestionDto qDto) {
        try {
            Question q = qMap.toClasse(qDto);
            //Initialiser les données liées à la validation
            q.setEtatValidation( EtatValidation.VALIDEE.getValeurEtat() );
            q.defDateValidee();
            q.setTempsAttente(" - ");
            q.setDateDemandeAjout(" - ");
            //Trouver la matiere dans laquelle répertorier la question
            //Des matières de mêmes nom peuvent appartenir à des UE ou filières différentes
            UE ue = ueServRepo.findUEByNom(nomUE);
            Matiere m = ue.getMatiereUEByName(nomMat);
            //Ajout question dans matiere
            q.setMatiereQuestion(m);
            m.getQuestionsMatiere().add(q);
            mServRepo.save(m);
            //TODO : check if matiere saved in question
            return qMap.toDto(q);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la question ou de la sauvegarde dans la matière "+nomMat);
        }
    }

    /**
     * Requête GET.
     * Récupérer une question à partir d'un id.
     *
     * Le dateManager calcule le temps d'attente.
     * Le repository s'occupe de modifier la donnée.
     *
     * @param qId
     * @return la question associée à l'id
     */
    @Override
    @Transactional
    public QuestionDto getQuestionById(int qId) {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId));
        //Mettre a jour le temps d'attente de la question si necessaire
        qRepo.updateTempsAttente(qId, q.voirTempsAttente());
        return qMap.toDto(q);
    }

    /**
     * Requête GET.
     * Récupérer toutes les questions créées et enregistrées dans
     * la base de données.
     *
     * @return les questions enregistrées dans la base de données
     */
    @Override
    @Transactional
    public List<QuestionDto> getAllQuestions()
    {

            List<Question> questions = null;
            try {
                questions = qRepo.findAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //MAJ le temps d'attente des questions
            for (Question valideeQ : questions)
                valideeQ.voirTempsAttente();

            //Convertir en stream pour envoyer au front-end
            //Conevertir en DTO chaque instance
            try {
                return questions.stream().map( (q) -> qMap.toDto(q)) .collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }


    /**
     * Requête GET.
     * Récupérer toutes les questions créées et enregistrées dans
     * la base de données qui sont validées.
     *
     * @return les questions validées enregistrées dans la base de données
     */
    @Override
    public List<QuestionDto> getAllQuestionsValidees() {
        try {
            List<Question> questions = qRepo.findAllQuestionsValidees();
            return questions.stream().map( (q) -> qMap.toDto(q)) .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des questions validées");
        }
    }


    /**
     * Requête GET.
     * Récupérer toutes les questions créées et enregistrées dans
     * la base de données qui sont en attente.
     *
     * @return les questions validées enregistrées dans la base de données
     */
    @Override
    public List<QuestionDto> getAllQuestionsAttente()
    {
        try {
            List<Question> questions = qRepo.findAllQuestionsAttente();
            //MAJ le temps d'attente des questions
            for (Question attenteQ : questions)
                attenteQ.voirTempsAttente();

            return questions.stream().map((q) -> qMap.toDto(q)).collect(Collectors.toList());
        } catch (Exception e) { throw new RuntimeException("Erreur lors de la récupération des questions en attente"); }
    }

    /**
     * Requête PATCH.
     * Mettre à jour une question.
     * N.B : cette version est obsolète. Prendre pour référence la méthode
     * .updateQuestion() du service utilisateur.
     *
     * @param qId l'id de la question
     * @param updatedQuestion le corps de la requête
     * @return la question mise à jour
     */
    @Override
    @Transactional
    public QuestionDto updateQuestion(int qId, QuestionDto updatedQuestion)
    {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId+"\n")
        );
        try {
            //Changer les données avec les getters et setters
            q.setQuestion(updatedQuestion.getQuestion());
            q.setCorrection(updatedQuestion.getCorrection());
            q.setReponses(updatedQuestion.getReponses());
            q.setIndBonneRep(updatedQuestion.getIndBonneRep());
            q.setIndice(updatedQuestion.getIndice());
            q.voirTempsAttente();
            Question updatedQuestionObj = qRepo.save(q);

            return qMap.toDto(updatedQuestionObj);

        } catch (Exception e) { throw new RuntimeException("Erreur lors de la modification de la question d'id "+qId); }
    }

    //TODO : ADMIN
    /**
     * Requête PATCH.
     * Valider une question.
     *
     * @param qId l'id de la question
     * @return la question à l'état validée
     */
    @Override
    @Transactional
    public QuestionDto validerQuestion(int qId)
    {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );
        Question updatedQuestionObj = null;
        try
        {
            if ( q.getEtatValidation().equals(EtatValidation.EN_ATTENTE.getValeurEtat()) ) {
                q.voirTempsAttente();
                q.setEtatValidation(EtatValidation.VALIDEE.getValeurEtat());
                q.defDateValidee();
                //Augmenter le nombre de questions validées du createur
                if ( !Objects.equals(q.getCreateur(), null) ) {
                    q.getCreateur().setNbQuestionsValidees( q.getCreateur().getNbQuestionsValidees() + 1 );
                }
                updatedQuestionObj = qRepo.save(q);
                //Inutile de sauvegarder les changements dans les entites Utilisateur et Matiere grace aux cascading
            } else {
                System.out.println("La question n'a pas été mise en attente de validation\n");
            }
            return qMap.toDto(updatedQuestionObj);
        }
        catch (Exception e) { throw new RuntimeException("Erreur lors de la validation de la question d'id "+qId); }
    }

    //TODO : ADMIN
    /**
     * Requête PATCH.
     * Refuser une question.
     * Les questions dont le compte du créateur a été effacé entretemps sont supprimées.
     *
     * @param qId l'id de la question
     * @return la question à l'état refusée
     */
    @Override
    @Transactional
    public QuestionDto refusValidation(int qId)
    {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );

        Question updatedQuestionObj = null;
        try
        {
            if ( q.getEtatValidation().equals(EtatValidation.REFUSEE.getValeurEtat()) ) {
                System.out.println("La question n'a pas été remise en attente de validation\n");
            }
            else if ( Objects.equals(q.getCreateur(), null) ) {
                //Suppresion d'une question créée par un compte inexistant
                //Retirer la question de la liste de sa matiere associee
                Matiere m = mServRepo.findById( q.getMatiereQuestion().getId() ).orElseThrow(
                        () -> new ExceptionRessourceAbsente("Aucune matière associée à la question d'id "+qId)
                );
                m.getQuestionsMatiere().remove(q);
                deleteQuestion(qId);
                mServRepo.save(m);
            }
            else {
                q.voirTempsAttente();
                //Reinitialiser l'éventuelle date de validation
                if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
                    q.resetDateValidee();
                }
                q.setEtatValidation(EtatValidation.REFUSEE.getValeurEtat());
                //Diminuer le nombre de questions validees
                if ( q.getCreateur().getNbQuestionsValidees() > 0 ) {
                    q.getCreateur().setNbQuestionsValidees( q.getCreateur().getNbQuestionsValidees() - 1 );
                }
                updatedQuestionObj = qRepo.save(q);
                //Inutile de sauvegarder les changements dans l'entite Utilisateur grace aux cascading
            }
            return qMap.toDto(updatedQuestionObj);
        }
        catch (Exception e) { throw new RuntimeException("Erreur lors du refus de la question d'id "+qId); }
    }


    //TODO : ADMIN
    /**
     * Requête PATCH.
     * Remettre en attente une question.
     * Les questions dont le compte du créateur a été effacé entretemps sont supprimées.
     *
     * @param qId l'id de la question
     * @return la question en attente
     */
    @Override
    @Transactional
    public QuestionDto remiseAttente(int qId)
    {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );
        try
        {
            Question updatedQuestionObj = null;
            if ( Objects.equals(q.getCreateur(), null) ) {
                //Supprimer une question remise en attente et créée par un compte inexistant (effacé entre temps)
                deleteQuestion(q.getId());
            }
            else if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
                q.dateNouvDemande();
                q.setEtatValidation( EtatValidation.EN_ATTENTE.getValeurEtat() );
                q.defDateValidee();
                //Diminuer le nombre de questions validees
                if ( !Objects.equals(q.getCreateur(), null) && q.getCreateur().getNbQuestionsValidees() > 0 ) {
                    q.getCreateur().setNbQuestionsValidees( q.getCreateur().getNbQuestionsValidees() - 1 );
                }
                updatedQuestionObj = qRepo.save(q);
                //Inutile de sauvegarder les changements dans l'entite Utilisateur grace aux cascading
                return qMap.toDto(updatedQuestionObj);
            }

            return qMap.toDto(updatedQuestionObj);
        }
        catch (Exception e) { throw new RuntimeException("Erreur lors de la remise en attente de la question d'id "+qId); }
    }

    //TODO : ADMIN
    /**
     * Requête DELETE.
     * Supprimer une question. C'est possible uniquement pour les questions validées.
     *
     * @param qId l'id de la question
     */
    @Override
    public void deleteQuestion(int qId)
    {
        Question q = qRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
        );
        try {
            //Suppression directe d'une question validee ou d'une question dont le compte créateur a été efface
            if ( q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat())
                    || Objects.equals(q.getCreateur(), null) ) {
                qRepo.deleteById(qId);
                System.out.println("Question was deleted successfully.\n");
            } else {
                //La question est en attente, eviter d'etre brutal
                System.out.println("SVP ne soyez pas brutal envers le créateur de la question.\n");
            }
        }
        catch (Exception e) { throw new RuntimeException("Erreur lors de la suppression de la question d'id "+qId); }

    }

}

