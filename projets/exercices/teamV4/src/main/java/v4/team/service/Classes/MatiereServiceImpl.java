package v4.team.service.Classes;

import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import v4.team.dtos.MatiereDto;
import v4.team.dtos.QuestionDto;
import v4.team.enumerations.EtatValidation;
import v4.team.exceptions.ExceptionRessourceAbsente;
import v4.team.mapper.Interfaces.MatiereMapperImpl;
import v4.team.mapper.Interfaces.QuestionMapperImpl;
import v4.team.model.Matiere;
import v4.team.model.Question;
import v4.team.repositories.MatiereRepository;
import v4.team.repositories.QuestionRepository;
import v4.team.service.Interfaces.MatiereService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Le service dédié à l'entité Matiere.
 * Certaines méthodes préparent l'intégration des admins ou du super admin.
 *
 * Les services se servent des repositories et des mappers des autres entités.
 */

@Service
public class MatiereServiceImpl implements MatiereService {


    //Permet d'ajouter le repository de son entité associé dans le contexte de Spring
    @Autowired
    private final MatiereRepository mRepo;

    private final MatiereMapperImpl mMap;

    private final QuestionRepository qServRepo;

    private final QuestionMapperImpl qServMapper;


    public MatiereServiceImpl(MatiereRepository mRepo, MatiereMapperImpl mMap, QuestionRepository qServRepo,
    QuestionMapperImpl qServMapper) {
        this.mRepo = mRepo;
        this.mMap = mMap;
        this.qServRepo = qServRepo;
        this.qServMapper = qServMapper;
    }

    /**
     * Requête POST.
     * Création d'une matière.
     *
     * @param matiereDto le corps de la requête
     * @return la matière créée
     */
    @Override
    public MatiereDto createMatiere(MatiereDto matiereDto) {
        try {
            Matiere m = mMap.toClasse(matiereDto);
            Matiere savedMatiere = mRepo.save(m);
            return mMap.toDto(savedMatiere);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    /**
     * Requête GET.
     * Récupérer une matière à partir d'un nom.
     * Les questions de la matière ne sont pas mises à jour.
     *
     * @param nomMatiere
     * @return la matiè associée
     */
    @Override
    public MatiereDto getMatiereByName(String nomMatiere){
        try
        {
            Matiere m = mRepo.findMatiereByNom(nomMatiere);
            System.out.println(m.toString());
            return mMap.toDto(m);
        }
        catch (ExceptionRessourceAbsente abs) {
            throw new ExceptionRessourceAbsente("Aucune matière associée au nom "+nomMatiere);
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    /**
     * Requête GET.
     * Récupérer une matière à partir d'un id.
     *
     * Les questions de la matière ne sont pas mises à jour.
     *
     * @param mId
     * @return la matière associée à l'id
     */
    @Override
    public MatiereDto getMatiereById(int mId) {
        Matiere m = mRepo.findById(mId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId));
        System.out.println(m.toString());
        return mMap.toDto(m);
    }

    /**
     * Requête GET.
     * Récupérer toutes les matières créées et enregistrées dans
     * la base de données.
     *
     * @return les matières enregistrées dans la base de données
     */
    @Override
    public List<MatiereDto> getAllMatieres() {
        try {
            List<Matiere> matieres = mRepo.findAll();
            return matieres.stream().map( (m) -> mMap.toDto(m) ).collect(Collectors.toList());
        }
        catch (ExceptionRessourceAbsente ressourceException) {
            throw new ExceptionRessourceAbsente("Erreur dans la récupération de certaines matières.");
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }


    /**
     * Requête GET.
     * Récupérer tous les questions de la matière créées et enregistrées dans
     * la base de données.
     *
     * @return les questions associées à la matière enregistrées dans la base de données
     */
    @Override
    public List<QuestionDto> getMatiereAllQuestions(int mId) {
        try {
            Matiere m = mRepo.findById(mId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId) );
            List<Question> matiereQ = m.getQuestionsMatiere();
            for (Question questionMat : matiereQ)
                qServRepo.updateTempsAttente(questionMat.getId(), questionMat.voirTempsAttente());

            return matiereQ.stream().map( (q) -> qServMapper.toDto(q) ).collect(Collectors.toList());
        }
        catch (ExceptionRessourceAbsente abs) {
            throw new ExceptionRessourceAbsente("Erreur dans la récupération des questions " +
                    "de la matière associée à l'id : "+mId);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Requête GET.
     * Récupérer tous les questions de la matière, créées et enregistrées dans
     * la base de données.
     * Les questions validées sont renvoyées. Elles sont accessibles aux révisions.
     *
     * @return les questions associées à la matière enregistrées dans la base de données
     */
    @Override
    public List<QuestionDto> getMatiereQuestionsAccessibles(int mId)
    {
        Matiere m = mRepo.findById(mId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId) );

        List<Question> accessibleQ = mRepo.findAllQuestionsValidees(m.getNom());
        try {
            for (Question questionMat : accessibleQ)
                qServRepo.updateTempsAttente(questionMat.getId(), questionMat.voirTempsAttente());

            return accessibleQ.stream().map( (q) -> qServMapper.toDto(q) ).collect(Collectors.toList());
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Erreur dans la récupération des questions accessibles " +
                    "de la matière associée à l'id "+mId);
        }
    }

    /**
     * Requête GET.
     * Récupérer tous les questions de la matière, créées et enregistrées dans
     * la base de données.
     * Les questions en attente sont renvoyées.
     *
     * @return les questions associées à la matière enregistrées dans la base de données
     */
    @Override
    public List<QuestionDto> getMatiereQuestionsAttente(int mId)
    {
        Matiere m = mRepo.findById(mId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId) );

        List<Question> accessibleQ = mRepo.findAllQuestionsAttente(m.getNom());
        try {
            for (Question questionMat : accessibleQ)
                qServRepo.updateTempsAttente(questionMat.getId(), questionMat.voirTempsAttente());

            return accessibleQ.stream().map( (q) -> qServMapper.toDto(q) ).collect(Collectors.toList());
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Erreur dans la récupération des questions accessibles " +
                    "de la matière associée à l'id "+mId);
        }
    }

    /**
     * Requête PATCH.
     * Mettre à jour une matière.
     *
     * @param mId l'id de la matière
     * @param updatedMatiere le corps de la requête
     * @return la matière mise à jour
     */
    @Override
    @Transactional
    public MatiereDto updateMatiere(int mId, MatiereDto updatedMatiere) {
        try {
            Matiere m = mRepo.findById(mId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId) );
            m.setNom( updatedMatiere.getNom() );
            //Inutile d'enregistrer les changements pour chaque question creees grace au cascading
            return mMap.toDto( mRepo.save(m) );
        } catch (ExceptionRessourceAbsente e) {
            throw new RuntimeException("Erreur lors de la modification de la matière associée à l'id "+mId);
        }
    }

    //TODO : ADMIN
    /**
     * Requête PATCH.
     * Mettre à jour une matière.
     * C'est une requête PATCH car du point de vue de l'entité Matiere, une question est
     * retirée de la liste.
     *
     * @param matNom nom de la matière
     * @param qId id de la question
     * @return la matière mise à jour
     */
    @Override
    @Transactional
    @PreRemove
    public void deleteQuestionMatiere(String matNom, int qId) {
        try
        {
            Matiere m = null;
            try { m = mRepo.findMatiereByNom(matNom); }
            catch (ExceptionRessourceAbsente abs) { throw new ExceptionRessourceAbsente("Aucune matière associée au nom "+matNom); }
            Question q = qServRepo.findById(qId).orElseThrow(
                    () -> new ExceptionRessourceAbsente("Aucune question associée à l'id "+qId)
            );
            //Tests
            if ( !q.getMatiereQuestion().equals(m.getNom()) ) {
                throw new IllegalArgumentException("La matière "+matNom+" ne correspond pas à celle de la question");
            }
            else if ( !q.getEtatValidation().equals(EtatValidation.VALIDEE.getValeurEtat())
                    && !Objects.equals(q.getCreateur(), null) ) {
                System.out.println("SVP ne soyez pas brutal envers le créateur de la question.\n");
            }
            else {
                m.getQuestionsMatiere().remove(q);
                qServRepo.deleteById(qId);
                mRepo.save(m);
                System.out.println(m.toString());
                System.out.println("Question was deleted successfully.\n");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur pendant la suppression d'une question de la matière "+matNom);
        }

    }

    //TODO : ADMIN
    /**
     * Requête DELETE.
     * Supprimer une matière.
     *
     * @param mId l'id de la matière
     */
    @Override
    @Transactional
    public void deleteMatiere(int mId)
    {
        Matiere m = mRepo.findById(mId).orElseThrow(
                () -> new ExceptionRessourceAbsente("Aucune matière associée à l'id "+mId));
        //Questions supprimées grâce au orphanRemoval
        mRepo.deleteById(mId);
    }

}
