package atilla.demo.dto;

import atilla.demo.classes.Filiere;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class UtilisateurDto {


    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int nbQuestionsPropose;
    private int nbQuestionsValide;
    private Filiere filiere ;

    private int nbApprouve;
    //private List<Long> tauxReussite;


    public UtilisateurDto(int id, String nom, String prenom, String email, int nbQuestionsPropose, int nbQuestionsValide, Filiere filiere, int nbApprouve) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nbQuestionsPropose = nbQuestionsPropose;
        this.nbQuestionsValide = nbQuestionsValide;
        this.filiere = filiere;
        this.nbApprouve = nbApprouve;
    }



    public int getNbApprouve() {
        return nbApprouve;
    }

    public void setNbApprouve(int nbApprouve) {
        this.nbApprouve = nbApprouve;
    }

    public UtilisateurDto() {
    }

    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public int getNbQuestionsPropose() {
        return nbQuestionsPropose;
    }

    public int getNbQuestionsValide() {
        return nbQuestionsValide;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNbQuestionsPropose(int nbQuestionsPropose) {
        this.nbQuestionsPropose = nbQuestionsPropose;
    }

    public void setNbQuestionsValide(int nbQuestionsValide) {
        this.nbQuestionsValide = nbQuestionsValide;
    }


    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }





}
