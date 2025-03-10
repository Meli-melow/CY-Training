package v4.team.dtos;

import v4.team.model.Matiere;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO de l'entité UE.
 */
public class UEDto {

    private int id;

    private String nom;

    private List<Matiere> matieresUE;


    public UEDto(int id, String nom) {
        this.id = id;
        this.nom = nom;
        matieresUE = new ArrayList<>();
    }


    public UEDto() {}


    public int getId() { return id; }


    public void setId(int id) { this.id = id; }


    public String getNom() { return nom; }


    public void setNom(String nom) { this.nom = nom; }

    public List<Matiere> getMatieresUE() { return matieresUE; }

    public void setMatieresUE(List<Matiere> matieresUE) { this.matieresUE = matieresUE; }
}
