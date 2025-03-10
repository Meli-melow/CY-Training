package atilla.demo.classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "utilisateur")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType =DiscriminatorType.STRING )
//@DiscriminatorValue("utilisateur" )
@SuperBuilder

public class  Utilisateur  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name ="mail", unique = true)
    private String mail;
    @Column(name="mdp")
    private String mdp;
    @Column(name = "nbQuestionsPropose")
    private int nbQuestionsPropose;
    @Column(name="nbQuestionsValide")
    private int nbQuestionsValide;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_Filiere" )
    private Filiere filiere;
/*
    @ElementCollection
    @Column(name="taux de Reussite")
    private List<Long> tauxReussite;*/


    public Utilisateur(int id, String nom, String prenom, String mail, String mdp, Filiere filiere) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.filiere = filiere;
    }

    public Utilisateur() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
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

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilisateur that)) return false;
        return Objects.equals(getNom(), that.getNom()) && Objects.equals(getPrenom(), that.getPrenom()) && Objects.equals(getMail(), that.getMail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getPrenom(), getMail());
    }


}
