package v3bis.team.service.Classes;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import v3bis.team.dtos.QuestionDto;
import v3bis.team.dtos.UtilisateurDto;
import v3bis.team.enumerations.EtatValidation;
import v3bis.team.exceptions.ExceptionRessourceAbsente;
import v3bis.team.mapper.Interfaces.QuestionMapperImpl;
import v3bis.team.mapper.Interfaces.UtilisateurMapperImpl;
import v3bis.team.model.Question;
import v3bis.team.model.Utilisateur;

import org.springframework.stereotype.Service;
import v3bis.team.repositories.QuestionRepository;
import v3bis.team.repositories.UtilisateurRepository;
import v3bis.team.service.Interfaces.UtilisateurService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    @Autowired
    final UtilisateurRepository uRepo;

    final UtilisateurMapperImpl uMap;

    @Autowired
    final DateEventService dateManager;

    final QuestionRepository qServRepo;

    final QuestionMapperImpl qServMapper;


    public UtilisateurServiceImpl(UtilisateurRepository uRepo, UtilisateurMapperImpl uMap, DateEventService dateManager,
    QuestionRepository qServRepo, QuestionMapperImpl qServMapper) {
        this.uRepo = uRepo;
        this.uMap = uMap;
        this.dateManager = dateManager;
        this.qServRepo = qServRepo;
        this.qServMapper = qServMapper;
    }

    @Override
    public UtilisateurDto createUtilisateur(UtilisateurDto uDto)
    {
        try {
            Utilisateur u = uMap.toClasse(uDto);
            //TODO : Initialiser les questions créées uniquement à la création d'une question
            u.setQuestionsCreees();
            Utilisateur savedUtilisateur = uRepo.save(u);
            return uMap.toDto(savedUtilisateur);
        } catch (Exception e) {
            System.out.println("ERREUR CRÉATION D'1 USER \n");
            throw new RuntimeException(e);
        }
    }

    @Override
    public UtilisateurDto getUtilisateurById(int uId) {

        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Utilisateur data is not associated with given id "+uId) );
        System.out.println(u.toString());
        return uMap.toDto(u);
    }

    @Override
    public List<UtilisateurDto> getAllUtilisateurs() {

        List<Utilisateur> utilisateurs = uRepo.findAll();
        //First map all the corresponding users DTO, then convert to a list
        return utilisateurs.stream().map((u) -> uMap.toDto(u))
                .collect(Collectors.toList());
    }

    //TODO : ajust so that updating personal data only (no performance data can be updated)
    @Override
    @Transactional
    public UtilisateurDto updateUtilisateur(int uId, UtilisateurDto updatedUtilisateur)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        //Change data with getters and setters
        u.setPrenom(updatedUtilisateur.getPrenom());
        u.setNom(updatedUtilisateur.getNom());
        u.setEmail(updatedUtilisateur.getEmail());
        u.setMdp(updatedUtilisateur.getMdp());
        Utilisateur updatedUtilisateurObj = uRepo.save(u);

        return uMap.toDto(updatedUtilisateurObj);
    }

    /*@Override
    public QuestionDto createQuestion(int uId, QuestionDto newQDto) {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId)
        );
        Question newQ = QuestionMapper.mapToQuestion(newQDto);
        qServ.
        u.ajouterQuestion(newQ);
    }*/

    @Override
    @Transactional
    public QuestionDto newQuestionByUser(int uId, QuestionDto qDto)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        Question q = qServMapper.toClasse(qDto);
        //Setting validation state manually since the mapper class won't allow overwriting
        q.setEtatValidation(EtatValidation.EN_ATTENTE.getValeurEtat());
        //Initialize validation date
        q.setDateValidee( dateManager.resetDate() );
        dateManager.dateNouvDemande(q);
        dateManager.voirTempsAttente(q);
        u.addQuestion(q);
        u.setNbQuestionsProposees( u.getNbQuestionsProposees() + 1 );
        q.setCreateur(u);
        //Updating repositories
        try {
            qServRepo.save(q);
            /*System.out.println(q.getCreateur().toString());*/
            return qServMapper.toDto(q);
        }
        catch (Exception e) {
            System.out.println("ERREUR CRÉATION D'1 QUESTION \n");
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<QuestionDto> getCreatedQuestions(int uId)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );

        List<Question> listeQuestions = u.getQuestionsCreees();
        //MAJ le temps d'attente de chaque question creee
        for (Question creeeQ : listeQuestions) {
            if ( creeeQ.getEtatValidation().equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
                dateManager.voirTempsAttente(creeeQ);
            }
        }
        return listeQuestions.stream().map((q) -> qServMapper.toDto(q))
                .collect(Collectors.toList());
    }

    //TODO : empecher pour les questions en attente
    @Override
    @Transactional
    public QuestionDto updateQuestion(int uId, int qId, QuestionDto updatedQuestion)
    {
        //Modifications dans la table des questions
        Question updatedQuestionObj = null;
        try
        {
            Question q = qServRepo.findById(qId).orElseThrow(
                    () -> new ExceptionRessourceAbsente( "L'utilisateur n'a pas créé de question associée à l'id "+qId) );
            Utilisateur u = q.getCreateur();
            //For tests purposes
            if ( u.getId() != uId ) {
                System.out.println("L'utilisateur n'a pas créé la question associée à l'id "+qId+"\n");
            } else {
                 //Allow creator to modify a question that's neither waiting nor validated
                 if ( q.getEtatValidation().equals( EtatValidation.REFUSEE.getValeurEtat() ) ) {

                     q.setQuestion(updatedQuestion.getQuestion());
                     q.setCorrection(updatedQuestion.getCorrection());
                     q.setReponses(updatedQuestion.getReponses());
                     q.setIndBonneRep(updatedQuestion.getIndBonneRep());
                     q.setIndice(updatedQuestion.getIndice());
                     dateManager.voirTempsAttente(q);
                     updatedQuestionObj = qServRepo.save(q);
                     //No need to save changes to creator thanks to the cascading of the JPA relationship

                 } else {
                     System.out.println("Vous devez attendre l'action d'un admin pour modifier.\n");
                 }
            }
            return qServMapper.toDto(updatedQuestionObj);
        }
        catch (ExceptionRessourceAbsente e) { throw new RuntimeException(e); }
        catch (Exception e) {
            System.out.println("ERREUR MODIFICATION DE LA QUESTION D'ID " + qId + " DE L'UTILISATEUR " + uId +"\n");
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public QuestionDto nouvDemandevalidation(int uId, int qId)
    {
        Question q = qServRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente( "Aucune question associée à l'id "+qId) );
        Utilisateur u = q.getCreateur();
        Question demandeQ = null;
        //For tests purposes
        if ( u.getId() != uId ) {
            System.out.println("Demande Impossible! Vous n'avez pas créé cette question \n");
        }
        else {
            if ( q.getEtatValidation().equals( EtatValidation.REFUSEE.getValeurEtat() ) ) {
                q.setEtatValidation(EtatValidation.EN_ATTENTE.getValeurEtat());
                dateManager.dateNouvDemande(q);
                dateManager.voirTempsAttente(q);
                u.setNbQuestionsProposees( u.getNbQuestionsProposees() + 1 );
                demandeQ = qServRepo.save(q);
                //No need to save changes to creator thanks to the cascading of the JPA relationship
            } else {
                System.out.println("Cette question est déjà validée!\n");
            }
        }
        return qServMapper.toDto(demandeQ);
    }

    @Override
    @Transactional
    public void deleteQuestion(int uId, int qId)
    {
        Question q = qServRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente( "Aucune question associée à l'id "+qId) );
        Utilisateur u = q.getCreateur();
        //For tests purposes
        if ( u.getId() != uId ) {
            System.out.println("Suppression Impossible! Vous n'avez pas créé cette question \n");
        } else {
            if ( !q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
                qServRepo.deleteById(qId);
                uRepo.save(u);
                System.out.println("Suppression réussie \n");
            } else {
                System.out.println("Suppression impossible! La question est déjà validée \n");
            }
        }

    }

    @Override
    @Transactional
    public void deleteUtilisateur(int uId)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente( "Aucun utilisateur associé à l'id "+uId) );
        try
        {
            List<Question> effacerQuestions = u.getQuestionsCreees();
            for (Question userQuestion : effacerQuestions) {
                //Validated questions remain accessible to everyone for revisions
                if ( userQuestion.getEtatValidation().equals( EtatValidation.VALIDEE.getValeurEtat() )) {
                    userQuestion.setCreateur(null);
                    qServRepo.save(userQuestion);
                } else {
                    qServRepo.deleteById(userQuestion.getId());
                }
            }
        }
        catch (Exception e) {
            System.out.println("ERREUR SUPPRESSION DES QUESTIONS DE L'UTILISATEUR " + uId +"\n");
            throw new RuntimeException(e);
        }
        uRepo.deleteById(uId);
    }

}
