package v3.team.dtos;


import v3.team.model.Question;

import java.util.ArrayList;
import java.util.List;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
public class UtilisateurDto {

    int id;
    String prenom;
    String nom;
    String email;
    String mdp;
    int nbQuestionsProposees;
    int nbQuestionsValides;
    //Taux taux;
    List<Question> questionsCreees;
    //int nbQuestionsValide;
    //Filiere filiere;
    //Revision revision;

    public UtilisateurDto(int id, String nom, String prenom, String email, String mdp, int nbQuestionsProposees, int nbQuestionsValides) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.nbQuestionsProposees = nbQuestionsProposees;
        this.nbQuestionsValides = nbQuestionsValides;
        this.questionsCreees = new ArrayList<>();
    }

    public UtilisateurDto() {}


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getNbQuestionsProposees() {
        return nbQuestionsProposees;
    }

    public void setNbQuestionsProposees(int nbQuestionsProposees) {
        this.nbQuestionsProposees = nbQuestionsProposees;
    }

    public int getNbQuestionsValides() {
        return nbQuestionsValides;
    }

    public void setNbQuestionsValides(int nbQuestionsValide) {
        this.nbQuestionsValides = nbQuestionsValide;
    }

    public List<Question> getQuestionsCreees() { return questionsCreees; }

    public void setQuestionsCreees(List<Question> questionsCreees) { this.questionsCreees = questionsCreees; }
}
