package v4.team.service.Classes;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import v4.team.dtos.QuestionDto;
import v4.team.dtos.UtilisateurDto;
import v4.team.enumerations.EtatValidation;
import v4.team.exceptions.ExceptionDateManager;
import v4.team.exceptions.ExceptionMapper;
import v4.team.exceptions.ExceptionRepository;
import v4.team.exceptions.ExceptionRessourceAbsente;
import v4.team.mapper.Interfaces.MatiereMapperImpl;
import v4.team.mapper.Interfaces.QuestionMapperImpl;
import v4.team.mapper.Interfaces.UEMapperImpl;
import v4.team.mapper.Interfaces.UtilisateurMapperImpl;
import v4.team.model.Matiere;
import v4.team.model.Question;
import v4.team.model.UE;
import v4.team.model.Utilisateur;

import org.springframework.stereotype.Service;
import v4.team.repositories.MatiereRepository;
import v4.team.repositories.QuestionRepository;
import v4.team.repositories.UERepository;
import v4.team.repositories.UtilisateurRepository;
import v4.team.service.Interfaces.UtilisateurService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Le service dédié à l'entité Utilisateur.
 *
 * Les services se servent des repositories et des mappers des autres entités.
 */

@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    //Permet d'ajouter le repository de son entité associé dans le contexte de Spring
    @Autowired
    private final UtilisateurRepository uRepo;

    private final UtilisateurMapperImpl uMap;


    private final QuestionRepository qServRepo;

    private final QuestionMapperImpl qServMapper;

    private final MatiereRepository mServRepo;

    private final MatiereMapperImpl mServMapper;

    private final UERepository ueServRepo;

    private final UEMapperImpl ueServMapper;


    public UtilisateurServiceImpl(UtilisateurRepository uRepo, UtilisateurMapperImpl uMap, QuestionRepository qServRepo,
                                  QuestionMapperImpl qServMapper, MatiereRepository mServRepo, MatiereMapperImpl mServMapper, UERepository ueServRepo, UEMapperImpl ueServMapper) {
        this.uRepo = uRepo;
        this.uMap = uMap;
        this.qServRepo = qServRepo;
        this.qServMapper = qServMapper;
        this.mServRepo = mServRepo;
        this.mServMapper = mServMapper;
        this.ueServRepo = ueServRepo;
        this.ueServMapper = ueServMapper;
    }


    /**
     * Requête POST.
     * Création d'un compte utilisateur (admins exclus).
     *
     * @param uDto le corps de la requête
     * @return l'utilisateur créé
     */
    @Override
    public UtilisateurDto createUtilisateur(UtilisateurDto uDto)
    {
        try {
            Utilisateur u = uMap.toClasse(uDto);
            //Initialiser la liste des questions creees
            u.setQuestionsCreees(new ArrayList<>());
            Utilisateur savedUtilisateur = uRepo.save(u);
            return uMap.toDto(savedUtilisateur);
        } catch (Exception e) { throw new RuntimeException("Erreur lors de la création d'un utilisateur"); }
    }

    /**
     * Requête GET.
     * Récupérer le profil d'un utilisateur à partir d'un id.
     *
     * @param uId
     * @return l'utilisateur associée à l'id
     */
    @Override
    public UtilisateurDto getUtilisateurById(int uId)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        return uMap.toDto(u);
    }

    /**
     * Requête GET.
     * Récupérer le profil d'un utilisateur à partir d'un email.
     *
     * @param userEmail
     * @return l'utilisateur associée à l'email
     */
    @Override
    public UtilisateurDto getUtilisateurByEmail(String userEmail)
    {
        try {
            Utilisateur u = uRepo.findUtilisateurByEmail(userEmail);
            return uMap.toDto(u);
        }
        catch (ExceptionRessourceAbsente abs) { throw new ExceptionRessourceAbsente("Aucun utilisateur associé au mail "+userEmail); }
    }

    /**
     * Requête GET.
     * Récupérer tous les utilisateurs créés et enregistrés dans
     * la base de données.
     *
     * @return les utilisateurs enregistrés dans la base de données
     */
    @Override
    public List<UtilisateurDto> getAllUtilisateurs()
    {
        try {
            List<Utilisateur> utilisateurs = uRepo.findAll();
            //Convertir en stream pour envoyer au front-end
            //Conevertir en DTO chaque instance
            return utilisateurs.stream().map( (u) -> uMap.toDto(u)) .collect(Collectors.toList());
        } catch (ExceptionRessourceAbsente abs) {
            throw new RuntimeException("Erreur dans la récupération de certains utilisateurs");
        }
    }

    /**
     * Requête PATCH.
     * Mettre à jour un utilisateur.
     *
     * @param uId l'id de l'utilisateur
     * @param updatedUtilisateur le corps de la requête
     * @return l'utilisateur mis à jour
     */
    @Override
    @Transactional
    public UtilisateurDto updateUtilisateur(int uId, UtilisateurDto updatedUtilisateur)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        try {
            //Changer les données avec les getters et setters
            u.setPrenom(updatedUtilisateur.getPrenom());
            u.setNom(updatedUtilisateur.getNom());
            u.setEmail(updatedUtilisateur.getEmail());
            u.setMdp(updatedUtilisateur.getMdp());
            Utilisateur updatedUtilisateurObj = uRepo.save(u);
            //Inutile d'enregistrer les changements pour chaque question creees grace au cascading
            return uMap.toDto(updatedUtilisateurObj);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur associé à l'id "+uId);
        }
    }

    /**
     * Requête PATCH.
     * Création d'une question par un utilisateur quelconque.
     * La question est mise en attente immédiatement après sa création.
     * C'est une requête PATCH car par rapport à l'entité Utilisateur, elle revient
     * à ajouter une question dans la liste des questions créées.
     * La question est repertoriée dans une matière à partir de son plutôt que
     * toute l'instance.
     *
     * @param uId id du createur
     * @param matNom la matière de la question
     * @param qDto le corps de la requête
     * @return la question créée
     */
    @Override
    @Transactional
    public QuestionDto newQuestionByUser(int uId, String nomUE, String matNom, QuestionDto qDto)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        //Recuperer l'UE avec son nom
        UE ue = ueServRepo.findUEByNom(nomUE);
        if (Objects.equals(ue,null))
            throw new ExceptionRessourceAbsente("Erreur avec la matière "+nomUE+". Vérifiez qu'elle existe");
        //Recuperer la matiere associee a l'UE
        Matiere m = ue.getMatiereUEByName(matNom);

        Question q = null;
        try {
            q = qServMapper.toClasse(qDto);
            //Mettre la question en attente
            q.setEtatValidation(EtatValidation.EN_ATTENTE.getValeurEtat());
            //Initialiser les données relatives à la proposition
            q.defDateValidee();
            q.dateNouvDemande();
            qServRepo.updateTempsAttente(q.getId(), q.voirTempsAttente());
            //Infos createur
            u.addQuestion(q);
            u.setNbQuestionsProposees( u.getNbQuestionsProposees() + 1 );
            q.setCreateur(u);
            q.setMatiereQuestion(m);
            //Rendre accessible aux admins
            m.getQuestionsMatiere().add(q);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur d'id "+uId+" ou de la matière "+matNom); }
        try {
            //Sauvegarder les nouvelles données et les changements
            qServRepo.save(q);
            mServRepo.save(m);
            ueServRepo.save(ue);
            return qServMapper.toDto(q);
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde d'une question par l'utilisateur d'id "+uId);
        }

    }

    /**
     * Requête GET.
     * Récupérer les propositions d'un utilisateur.
     * Seules les questions en attente ou refusees sont renvoyées.
     * Les temps en attente sont mis à jour.
     *
     * @param uId id du createur
     * @return la question créée
     */
    @Transactional
    @Override
    public List<QuestionDto> getCreatedQuestions(int uId) throws ExceptionRessourceAbsente, ExceptionRepository, ExceptionDateManager,
    ExceptionMapper
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );

        try {
            List<Question> listeQuestions = null;
            try {
                listeQuestions = uRepo.findUtilisateurPropositions(uId);
            } catch (ExceptionRepository e) {
                throw new ExceptionRepository("Erreur repository question pour get all");
            }
            //MAJ le temps d'attente des questions creees en attente
            for (Question creeeQ : listeQuestions) {
                try {
                    qServRepo.updateTempsAttente(creeeQ.getId(), creeeQ.voirTempsAttente());
                    qServRepo.save(creeeQ);
                } catch (ExceptionDateManager e) {
                    throw new ExceptionDateManager("Erreur lors de la maj du tps d'attente de la question d'id"+creeeQ.getId());
                }
            }
            try {
                return listeQuestions.stream().map( (q) -> qServMapper.toDto(q) ).collect(Collectors.toList());
            } catch (ExceptionMapper e) {
                throw new ExceptionMapper("Erreur lors de la conversion dto des questions");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des propositions par l'utilisateur d'id "+uId);
        }
    }

    /**
     * Changer la matière d'une question.
     *
     * @param q la proposition
     * @param nomMat la matière de la question
     * @param nomUE nom de l'UE de la matière
     */
    @Override
    @Transactional
    public void updateMatiereQuestion(Question q, String nomMat, String nomUE) {
        try {
            UE ue = ueServRepo.findUEByNom(nomUE);
            Matiere nouvMat = mServRepo.findMatiereByNom(nomMat);
            //Changer la matiere si l'utilisateur change la matiere de la question
            if ( !q.getMatiereQuestion().equals(nouvMat) ) {
                Matiere ancMat = q.getMatiereQuestion();
                ancMat.getQuestionsMatiere().remove(q);
                nouvMat.getQuestionsMatiere().add(q);
                q.setMatiereQuestion( nouvMat );
                //Enregistrer les changements
                mServRepo.save(nouvMat);
                mServRepo.save(ancMat);
            }
        } catch (ExceptionRessourceAbsente abs) {
            throw new ExceptionRessourceAbsente("Aucune matière associée au nom "+nomMat);
        } catch (RuntimeException e) { throw new RuntimeException("Erreur lors du changement de matière de la question"); }

    }

    /**
     * Requête PATCH.
     * Mettre à jour une proposition refusée.
     *
     * @param uId l'id du créateur
     * @param qId l'id de la question
     * @param nomUE nom de l'UE où est répertoriée la matière de la question
     * @param nomMat nom de la matière de la question, elle peut être nouvelle
     * @param updatedQuestion le corps de la requête
     * @return la question mise à jour
     */
    @Override
    @Transactional
    public QuestionDto updateQuestion(int uId, int qId, String nomUE, String nomMat, QuestionDto updatedQuestion)
    {
        //Modifications dans la table des questions
        Question updatedQuestionObj = null;
        Question q = qServRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("L'utilisateur n'a pas créé de question associée à l'id "+qId) );
        try
        {
            Utilisateur u = q.getCreateur();
            //Tests
            if ( u.getId() != uId ) {
                throw new IllegalArgumentException("L'utilisateur d'id "+ u.getId() + " n'a pas créé la question associée à l'id "+qId);
            } else {
                //Autoriser la modification d'une proposition refusee
                if ( q.getEtatValidation().equals( EtatValidation.REFUSEE.getValeurEtat() ) ) {

                    q.setQuestion(updatedQuestion.getQuestion());
                    q.setCorrection(updatedQuestion.getCorrection());
                    q.setReponses(updatedQuestion.getReponses());
                    q.setIndBonneRep(updatedQuestion.getIndBonneRep());
                    q.setIndice(updatedQuestion.getIndice());
                    //Trouver la matiere dans laquelle répertorier la question
                    //Des matières de mêmes nom peuvent appartenir à des UE ou filières différentes
                    updateMatiereQuestion(q, nomMat, nomUE);
                    qServRepo.updateTempsAttente(q.getId(), q.voirTempsAttente());
                    updatedQuestionObj = qServRepo.save(q);
                    //Inutile de sauvegarder les modifications pour le createur grace au cascading

                } else {
                    System.out.println("Vous devez attendre l'action d'un admin pour modifier.\n");
                }
            }
            return qServMapper.toDto(updatedQuestionObj);
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la question d'id " + qId + " par l'utilisateur d'id " + uId);
        }

    }

    /**
     * Requête PATCH.
     * Proposer à nouveau une question.
     *
     * @param uId l'id du créateur
     * @param qId l'id de la question
     * @return la question ajoutée en liste d'attente
     */
    @Override
    @Transactional
    public QuestionDto nouvDemandevalidation(int uId, int qId)
    {
        Question q = qServRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId) );
        try {
            Utilisateur u = q.getCreateur();
            Question demandeQ = null;
            //Tests
            if ( u.getId() != uId ) {
                throw new IllegalArgumentException("Demande Impossible! Cette question n'a pas été créée par " +
                        "l'utilisateur d'id "+uId);
            }
            else {
                Matiere m = q.getMatiereQuestion();
                if ( q.getEtatValidation().equals( EtatValidation.REFUSEE.getValeurEtat() ) ) {
                    q.setEtatValidation(EtatValidation.EN_ATTENTE.getValeurEtat());
                    //Renouveler la date de demande de la question
                    q.dateNouvDemande();
                    //Initialiser son temps d'attente
                    qServRepo.updateTempsAttente(q.getId(), q.voirTempsAttente());
                    u.setNbQuestionsProposees( u.getNbQuestionsProposees() + 1 );
                    m.getQuestionsMatiere().add(q);
                    //Enregistrer les changements
                    mServRepo.save(m);
                    demandeQ = qServRepo.save(q);
                    //Inutile de sauvegrader le createur grace au cascading de l'entite Question
                } else { System.out.println("Cette question est déjà validée!\n"); }
            }
            return qServMapper.toDto(demandeQ);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la nouvelle demande de validation pour la question d'id "+qId
            +" par l'utilisateur d'id "+uId);
        }
    }

    /**
     * Requête PATCH.
     * Supprimer une question. Les questions en attente peuvent être supprimées.
     * C'est une requête PATCH car par rapport à l'entité Utilisateur, elle revient
     * à supprimer une question de la liste des questions créées.
     *
     * @param uId l'id du créateur
     * @param qId l'id de la question
     */
    @Override
    @Transactional
    public void deleteQuestion(int uId, int qId)
    {
        Question q = qServRepo.findById(qId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId) );
        try {
            Utilisateur u = q.getCreateur();
            //Tests
            if ( u.getId() != uId ) {
                throw new IllegalArgumentException("Suppression Impossible! Vous n'avez pas créé cette question\n");
            } else {
                Matiere m = q.getMatiereQuestion();
                if ( !q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat()) ) {
                    qServRepo.deleteById(qId);
                    //Inutile de parcourir la liste des questions creees pour supprimer la question grace au cascading
                    uRepo.save(u);
                    m.getQuestionsMatiere().remove(q);
                    mServRepo.save(m);
                    System.out.println("Suppression réussie\n");
                } else {
                    System.out.println("Suppression impossible! La question est déjà validée\n");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la question d'id "+qId+" par l'utilisateur "+uId);
        }

    }

    /**
     * Requête DELETE.
     * Supprimer un utilisateur.
     * Les questions validées restent accessibles aux révisions. Les autres sont supprimées.
     *
     * @param uId l'id du créateur
     */
    @Override
    @Transactional
    public void deleteUtilisateur(int uId)
    {
        Utilisateur u = uRepo.findById(uId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucun utilisateur associé à l'id "+uId) );
        try
        {
            List<Question> effacerQuestions = u.getQuestionsCreees();
            for (Question userQuestion : effacerQuestions) {
                //Les questions validees restent accessibles aux revisions
                if ( userQuestion.getEtatValidation().equals( EtatValidation.VALIDEE.getValeurEtat() )) {
                    userQuestion.setCreateur(null);
                    qServRepo.save(userQuestion);
                } else {
                    //Retirer les questions non validées de la matiere
                    Matiere m = userQuestion.getMatiereQuestion();
                    qServRepo.deleteById(userQuestion.getId());
                    m.getQuestionsMatiere().remove(userQuestion);
                    mServRepo.save(m);
                }
            }
        }
        catch (Exception e) { throw new RuntimeException("Erreur lors de la suppression des propositions de l'utilisateur d'id "+uId); }
        uRepo.deleteById(uId);
    }

}
