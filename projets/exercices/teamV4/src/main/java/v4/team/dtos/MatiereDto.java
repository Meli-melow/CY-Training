package v4.team.dtos;

import v4.team.model.Question;
import v4.team.model.UE;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO de l'entité Matiere.
 */
public class MatiereDto {

    private int id;

    private String nom;

    private UE ueMatiere;

    private List<Question> questionsMatiere;


    /**
     * Constructeur pour le mapper et les requêtes API (complétion en cours)
     */
    public MatiereDto (int id, UE ueMatiere, String nom) {
        this.id = id;
        this.nom = nom;
        this.ueMatiere = ueMatiere;
        setQuestionsMatiere(new ArrayList<>());
    }

    /**
     * Constructeur sans argument pour le mapper.
     */
    public MatiereDto() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


    public String getNom() { return nom; }


    public void setNom(String nom) { this.nom = nom; }

    public UE getUEMatiere() { return ueMatiere; }

    public void setUEMatiere(UE uEMatiere) { this.ueMatiere = uEMatiere; }

    public List<Question> getQuestionsMatiere() { return questionsMatiere; }

    public void setQuestionsMatiere(List<Question> questionsMatiere) { this.questionsMatiere = questionsMatiere; }
}
