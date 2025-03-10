package v4.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation des matières.
 */

@Entity
@Table(name = "matiere")
/**
 * Les résultats des requêtes API qui ne renvoient pas d'utilisateur indiqueront son nom dans les autres entités en association
 * avec Utilisateur
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "nom")
public class Matiere {

    /**
     * Les ids sont générés automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    /**
     * Relation entité UE : une matière est répertoriée dans une UE.
     *
     * La table des matières possède une clé étrangère correspondant à l'id de sa
     * matière (@JoinColumn). La table ue est référencée.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_ue")
    private UE ueMatiere;


    /**
     * Relation entité Question : une matière répertorie plusieurs questions.
     *
     * La classe Matiere est l'hôte (contient mappedBy).
     */
    @OneToMany(mappedBy = "matiereQuestion", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
    orphanRemoval = true)
    private List<Question> questionsMatiere;

    /**
     * Création d'une matière.
     * Constructeur por les requêtes API.
     *
     * Nécessaire pour le lancement de l'application.
     *
     * @param id
     * @param nom
     */
    public Matiere(int id, UE ueMatiere, String nom) {
        this.id = id;
        this.nom = nom;
        this.ueMatiere = ueMatiere;
        questionsMatiere = new ArrayList<>();
    }

    public Matiere() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


    public String getNom() { return nom; }


    public void setNom(String nom) { this.nom = nom; }

    public UE getUEMatiere() { return ueMatiere; }

    public void setUEMatiere(UE uEMatiere) { this.ueMatiere = uEMatiere; }

    public List<Question> getQuestionsMatiere() { return questionsMatiere; }

    public void setQuestionsMatiere(List<Question> questionsMatiere) { this.questionsMatiere = questionsMatiere; }

    /**
     * Accéder aux détails des questions répertoriées dans une matière.
     */
    public String matiereQuestionsInfo() {
        String infosQuestions = "";
        for (Question questionRevisionMat : getQuestionsMatiere()) {
            infosQuestions += questionRevisionMat.toString() + ";\n";
        }

        return "{ " + infosQuestions + " }";
    }

    /**
     * Accéder aux détails d'une question.
     * Concernant la matière, on renvoie uniquement son nom.
     *
     * @return l'état de la matière.
     */
    @Override
    public String toString() {
        return "{ id matiere : " + getId() + "; nom : " + getNom()
                + "\n questions à reviser : \n" + this.matiereQuestionsInfo() + " }; \n";
    }

    /**
     * Accèder aux informations d'une matière à partir de son UE.
     * Allège l'affichage en retirant l'état des questions des matières.
     */
    public String toStringUE() { return "{ id matiere : "+ id +"; nom : "+ nom +"}";}

    /**
     * Indique l'égalité de deux instances de Matiere.
     * La méthode se sert de critères d'unicité autres que l'id.
     *
     * @param o
     * @return indique si l'objet est égal à l'instance de Matiere.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Matiere) {
            Matiere m = (Matiere) o;
            return this.getNom().equals(m.getNom());
        }
        return false;
    }

}
