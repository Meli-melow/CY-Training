package v4.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import v4.team.exceptions.ExceptionRessourceAbsente;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation des UE.
 */

@Entity
@Table(name = "ue")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "nom")
public class UE {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    /**
     * Relation entité Matiere : une UE répertorie plusieurs matières.
     *
     * La classe UE est l'hôte (contient mappedBy).
     * */
    @OneToMany(mappedBy = "ueMatiere", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Matiere> matieresUE;


    public UE(int id, String nom) {
        this.id = id;
        this.nom = nom;
        matieresUE = new ArrayList<>();
    }


    public UE() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


    public String getNom() { return nom; }


    public void setNom(String nom) { this.nom = nom; }

    public List<Matiere> getMatieresUE() { return matieresUE; }

    public void setMatieresUE(List<Matiere> matieresUE) { this.matieresUE = matieresUE; }


    /**
     * Accéder aux détails des matières répertoriées dans une UE.
     */
    public String matieresInfo() {
        String infosMatieres = "";
        for (Matiere matUE : matieresUE)
            infosMatieres += matUE.toStringUE() + ";\n";

        return infosMatieres;
    }

    /**
     * Récupèrer la matière d'une UE à partir de son nom.
     * Cette méthode évite d'avoir à l'implémenter dans chaque service.
     *
     * @param nomMatiere le nom de la matière voulue
     * @return la matière associée au nom
     */
    public Matiere getMatiereUEByName(String nomMatiere) {
        for(Matiere matUE : matieresUE) {
            if (matUE.getNom().equals(nomMatiere) )
                return matUE;
        }
        throw new ExceptionRessourceAbsente("La matière " + nomMatiere + " n'appartient pas à l'UE " + this.nom);
    }


    @Override
    public String toString() { return "{ id UE = " + id + "; nom = " + nom + ";\nmatieres :\n{" + this.matieresInfo() + " }\n"; }


    @Override
    public boolean equals(Object o) {
        if (o instanceof UE) {
            UE ue = (UE) o;
            return this.nom.equals(ue.getNom());
        }

        return false;
    }

}
