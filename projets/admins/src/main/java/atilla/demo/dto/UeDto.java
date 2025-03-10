package atilla.demo.dto;

import atilla.demo.classes.Filiere;
import atilla.demo.classes.Matiere;

import java.util.ArrayList;
import java.util.List;

public class UeDto {

    private int id ;
    private  String nom ;

    private List<Filiere> filieres= new ArrayList<>();

    private List<Matiere> matieres = new ArrayList<>();

    public UeDto(int id, String nom, List<Filiere> filieres, List<Matiere> matieres) {
        this.id = id;
        this.nom = nom;
        this.filieres = filieres;
        this.matieres = matieres;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public UeDto() {
    }

    public List<Filiere> getFilieres() {
        return filieres;
    }

    public void setFilieres(List<Filiere> filieres) {
        this.filieres = filieres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
