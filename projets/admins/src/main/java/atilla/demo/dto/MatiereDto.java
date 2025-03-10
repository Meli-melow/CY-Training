package atilla.demo.dto;

import atilla.demo.classes.UE;

public class MatiereDto {

    private int idMatiere;
    private  String nom;

    private UE ue ;

    public MatiereDto(int idMatiere, String nom, UE ue) {
        this.idMatiere = idMatiere;
        this.nom = nom;
        this.ue = ue;
    }

    public UE getUe() {
        return ue;
    }

    public void setUe(UE ue) {
        this.ue = ue;
    }

    public MatiereDto() {
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
}
