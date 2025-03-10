package atilla.demo.classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "filiere")
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", unique = true)
    private String nom;


    @OneToMany(mappedBy = "filiere")
    private List<Utilisateur> utilisateurs= new ArrayList<>();


    @Column(name ="UE")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name = "UE_Filiere",
            joinColumns = @JoinColumn( name = "id" ),
            inverseJoinColumns = @JoinColumn( name = "idUE" ) )
    //@JsonManagedReference
    private List<UE> ues = new ArrayList<>();


    public Filiere(int id, String nom, List<Utilisateur> utilisateurs, List<UE> ues) {
        this.id = id;
        this.nom = nom;
        this.utilisateurs = utilisateurs;
        this.ues = ues;
    }

    public Filiere() {
    }

    @Override
    public String toString() {
        return "filiere{id : "+id +"nom:"+nom + "}";
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filiere filiere = (Filiere) o;
        return Objects.equals(nom, filiere.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
