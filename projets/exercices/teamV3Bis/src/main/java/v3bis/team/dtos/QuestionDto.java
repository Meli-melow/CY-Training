package v3bis.team.dtos;

import v3bis.team.enumerations.EtatValidation;
import v3bis.team.model.Utilisateur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString*/
public class QuestionDto {

    int id;

    String question;

    String correction;

    List<String> reponses = new ArrayList<>();

    HashSet<Integer> indBonneRep;

    String indice;

    String etatValidation;

    String dateValidee;

    String tempsAttente;

    String dateDemandeAjout;

    Utilisateur createur;

    public QuestionDto (int id, String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep,
    String indice, Utilisateur createur) {
        this.id = id;
        this.question = question;
        this.correction = correction;
        this.reponses = reponses;
        this.indBonneRep = indBonneRep;
        this.indice = indice;
        this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
        this.dateValidee = " - ";
        this.createur = createur;
    }

    public QuestionDto() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


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
