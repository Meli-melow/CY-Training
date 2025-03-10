package atilla.demo.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "UE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idUE")
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUE;


    @Column(unique = true)
    private String nom;
    @OneToMany
    private List<Matiere> matieres;

    @ManyToMany(mappedBy = "ues")
    //@JsonBackReference
    //@JoinTable(name = "UE_Filiere", joinColumns = @JoinColumn(name = "idUE"), inverseJoinColumns =@JoinColumn(name="id"))
    private List<Filiere> filieres= new ArrayList<>();;

    public int getIdUE() {
        return idUE;
    }

    public void setIdUE(int idUE) {
        this.idUE = idUE;
    }

    public UE(int idUE, String nom, List<Filiere> filieres) {
        this.idUE = idUE;
        this.nom = nom;
        this.filieres = filieres;
    }

    public UE() {
    }

    public String getNom()
    {
        return nom;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Filiere> getFilieres() {
        return filieres;
    }

    public void setFilieres(List<Filiere> filieres) {
        this.filieres = filieres;
    }

    public List<Matiere> getMatieres()

    {
        return this.matieres;
    }

    public void ajoutMatiere(Matiere matiere)
    {
        matieres.add(matiere);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof UE))
            return false;
        UE ue = (UE) o;
        return Objects.equals(this.idUE, ue.getIdUE()) && Objects.equals(this.nom, ue.getNom());
               /* && Objects.equals(this.matieres, ue.getMatieres());*/
    }



}
