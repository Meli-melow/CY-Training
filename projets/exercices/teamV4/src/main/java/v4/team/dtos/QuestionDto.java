package v4.team.dtos;

import v4.team.enumerations.EtatValidation;
import v4.team.model.Matiere;
import v4.team.model.Utilisateur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * DTO de l'entité Question.
 */
public class QuestionDto {

    private int id;

    private Matiere matiereQuestion;

    private String question;

    private String correction;

    private List<String> reponses = new ArrayList<>();

    private HashSet<Integer> indBonneRep;

    private String indice;

    private String etatValidation;

    private String dateValidee;

    private String tempsAttente;

    private String dateDemandeAjout;

    private Utilisateur createur;


    /**
     * Constructeur pour les requêtes API.
     * Utilisé à la conversion d'une instance de l'entité Question.
     *
     * @param id celui de l'instance d'entité associée
     * @param matiereQuestion
     * @param question
     * @param correction
     * @param reponses
     * @param indBonneRep
     * @param indice
     * @param createur
     */
    public QuestionDto (int id, Matiere matiereQuestion, String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep,
                        String indice, Utilisateur createur) {
        this.id = id;
        this.matiereQuestion = matiereQuestion;
        this.question = question;
        this.correction = correction;
        this.reponses = reponses;
        this.indBonneRep = indBonneRep;
        this.indice = indice;
        this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
        this.dateValidee = " - ";
        this.createur = createur;
    }

    /**
     * Constructeur sans argument pour le mapper.
     */
    public QuestionDto() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


    public Matiere getMatiereQuestion() { return matiereQuestion; }


    public void setMatiereQuestion(Matiere matiereQuestion) { this.matiereQuestion = matiereQuestion; }


    public String getQuestion() { return question; }


    public void setQuestion(String question) { this.question = question; }

    public String getCorrection() { return correction; }

    public void setCorrection(String correction) { this.correction = correction; }

    public List<String> getReponses() { return reponses; }

    public void setReponses(List<String> reponses) { this.reponses = reponses; }

    public HashSet<Integer> getIndBonneRep() { return indBonneRep; }

    public void setIndBonneRep(HashSet<Integer> indBonneRep) { this.indBonneRep = indBonneRep; }

    public String getIndice() { return indice; }

    public void setIndice(String indice) { this.indice = indice; }

    public String getEtatValidation() { return etatValidation; }

    public void setEtatValidation(String etat) { etatValidation = etat; }

    public String getDateValidee() { return dateValidee; }

    public void setDateValidee(String dateValidee) { this.dateValidee = dateValidee; }

    public String getTempsAttente() { return tempsAttente; }

    public void setTempsAttente(String tempsAttente) { this.tempsAttente = tempsAttente; }

    public String getDateDemandeAjout() { return dateDemandeAjout; }

    public void setDateDemandeAjout(String dateDemandeAjout) { this.dateDemandeAjout = dateDemandeAjout; }

    public Utilisateur getCreateur() { return createur; }

    public void setCreateur(Utilisateur createur) { this.createur = createur; }
}
