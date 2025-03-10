package atilla.demo.dto;

import atilla.demo.classes.Filiere;

import java.util.Objects;


public class AdminDto extends UtilisateurDto{
    private int nbApprouve;

    public AdminDto(int id, String nom, String prenom, String email, int nbQuestionsPropose, int nbQuestionsValide, Filiere filiere, int nbApprouve, int nbApprouve1) {
        super(id, nom, prenom, email, nbQuestionsPropose, nbQuestionsValide, filiere, nbApprouve);
        this.nbApprouve = nbApprouve1;
    }

    public AdminDto(int nbApprouve) {
        this.nbApprouve = nbApprouve;
    }



    public AdminDto() {
    }

    public int getNbApprouve() {
        return nbApprouve;
    }

    public void setNbApprouve(int nbApprouve) {
        this.nbApprouve = nbApprouve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminDto adminDto)) return false;
        return getNbApprouve() == adminDto.getNbApprouve();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNbApprouve());
    }
}
