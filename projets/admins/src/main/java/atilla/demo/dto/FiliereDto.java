package atilla.demo.dto;

import atilla.demo.classes.UE;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

public class FiliereDto {

    private int id;
    private String nom;
    private List<UE> ues = new ArrayList<>();

    public FiliereDto(int id, String nom, List<UE> ues) {
        this.id = id;
        this.nom = nom;
        this.ues = ues;
    }

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }

    public FiliereDto() {
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


}
