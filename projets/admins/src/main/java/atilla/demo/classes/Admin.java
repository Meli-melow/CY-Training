package atilla.demo.classes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("admin")
@Data
public class Admin extends Utilisateur{
   //private List<Matiere> certifs;

    private int nbApprouve;
    @ManyToMany
    @JoinTable(name = "Matiere_Admin",
            joinColumns = @JoinColumn( name = "id" ),
            inverseJoinColumns = @JoinColumn( name = "idMatiere" ))
    private List<Matiere> certifs= new ArrayList<>();

    public Admin(int id, String nom, String prenom, String mail, String mdp, Filiere filiere) {
        super(id, nom, prenom, mail, mdp, filiere);
    }

    public Admin() {
    }



    public Admin(int nbApprouve) {
        this.nbApprouve = nbApprouve;
    }

    public int getNbApprouve() {
        return nbApprouve;
    }

    public void setNbApprouve(int nbApprouve) {
        this.nbApprouve = nbApprouve;
    }

    public List<Matiere> getCertifs() {
        return certifs;
    }

    public void setCertifs(List<Matiere> certifs) {
        this.certifs = certifs;
    }

    public void addCertif(Matiere matiere)
    {
        certifs.add(matiere);
    }
    public void removeCertif(Matiere matiere)
    {
        certifs.remove(matiere);
    }



    /*public void certifier(Matiere matiere, Admin admin)
    {
        if(certifs.contains(matiere))
        {
            admin.addCertif(matiere);
        }
    }*/


   /* public Admin(UtilisateurBuilder<?, ?> b, int nbApprouve) {
        super(b);
        this.nbApprouve = nbApprouve;
    }*/


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin admin)) return false;
        if (!super.equals(o)) return false;
        return nbApprouve == admin.nbApprouve;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nbApprouve);
    }
}
