package v4.team.enumerations;


/**
 * Cette énumération permet de gérer les questions.
 * Les question ont un état de validation.
 * Après leur création, les questions sont attente.
 */
public enum EtatValidation {

    EN_ATTENTE("En attente"),
    VALIDEE("Validée"),
    REFUSEE("Refusée");

    private String valeurEtat;

    EtatValidation(String valeurEtat) { this.valeurEtat = valeurEtat; }

    public String getValeurEtat() { return valeurEtat; }

    public void setValeurEtat(String nouvelEtat) { valeurEtat = nouvelEtat; }

}
