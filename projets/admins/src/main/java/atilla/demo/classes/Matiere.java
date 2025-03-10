package atilla.demo.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMatiere;
    private  String nom;

    @ManyToMany(mappedBy = "certifs")
    private List<Admin> adminList= new ArrayList<>();
    @ManyToOne
    private UE ue;

    public Matiere() {
    }
    public Matiere(int idMatiere, String nom, UE ue ) {
        this.idMatiere = idMatiere;
        this.nom = nom;
        this.ue = ue ;
    }

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    public UE getUe() {
        return ue;
    }

    public void setUe(UE ue) {
        this.ue = ue;
    }
}
