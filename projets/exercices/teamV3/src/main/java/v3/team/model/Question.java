package v3.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import v3.team.enumerations.EtatValidation;
import v3.team.service.Classes.DateEventService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;


@Entity
@Table(name = "question")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "createur")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String question;

	String correction;

	@Column(name = "reponses")
	List<String> reponses = new ArrayList<>();

	@Column(name = "index_bonne_reponse")
	int indBonneRep;

	String indice;

	@Column(name = "etat_validation")
	String etatValidation;

	@Column(name = "date validation")
	String dateValidee;

	String tempsAttente;

	@Column(name = "date de demande")
	String dateDemandeAjout;

	//TODO : uniD -> @ dans UNE DES DEUX ; nom clé étrangère = nom_autre_table_id
	/**
	 * Parent / owner
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	//@JsonManagedReference
	@JoinColumn(name = "id_createur")
	Utilisateur createur;


	//TODO : Clé étrangère
	//Chapitre chapitre;

	/**
	 * Ignorer dans la BDD
	 */
	@Transient
	DateEventService dateManager;

	/**
	 * Constructeur sans argument
	 *
	 * Nécessaire pour les contrôleurs
	 */
	public Question() {}

	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 */
	public Question(int id, String question, String correction, List<String> reponses, int indBonneRep, String indice, Utilisateur createur /*, Chapitre chapitre*/) {
		this.id = id;
		this.question = question;
		this.correction = correction;
		this.reponses = reponses;
		this.indBonneRep = indBonneRep;
		this.indice = indice;
		this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
		this.dateValidee = " -";
		this.createur = createur;
		//this.chapitre = chapitre;
	}

	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 *
	 * Version CLI SQL natif : id inutile car généré automatiquement
	 */
	public Question(String question, String correction, List<String> reponses, int indBonneRep, String indice, Utilisateur createur /*, Chapitre chapitre*/) {
		this.question = question;
		this.correction = correction;
		this.reponses = reponses;
		this.indBonneRep = indBonneRep;
		this.indice = indice;
		this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
		this.dateValidee = " - ";
		this.createur = createur;
		//this.chapitre = chapitre;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) { this.id = id; }


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrection() {
		return correction;
	}

	public void setCorrection(String correction) {
		this.correction = correction;
	}

	public List<String> getReponses() { return this.reponses; }

	public void setReponses(List<String> reponses) { this.reponses = reponses; }

	public String infosQuestionsReponses() {
		String reponsesCreees = "";
		for (String reponse : reponses) {
			reponsesCreees += reponse + " ,";
		}
		return "[" + reponsesCreees + "]";
	}

	public boolean repondre(int rep) { return rep == indBonneRep; }

	/**
	 * Changer une réponse
	 *
	 * Avec la méthode native de Object
	 */
    /*public void setReponse (int indRep, String rep) {
		this.reponses.set(indRep, rep);
	}

	public boolean addReponse(String rep) {
		this.reponses.add(rep);

		return true;
	}*/

	public int getIndBonneRep() {
		return indBonneRep;
	}

	public void setIndBonneRep(int idxBonneRep) {
		this.indBonneRep = idxBonneRep;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {this.indice = indice;}

	public String getEtatValidation() { return etatValidation; }

	public void setEtatValidation(String etat) { etatValidation = etat; }

	public String getDateValidee() {
		return dateValidee;
	}

	public String getTempsAttente() { return tempsAttente; }

	public long demandeMillisecondes()
	{
		Date dateDemande = null;
		if ( !this.getDateDemandeAjout().equals(null) ) {
			SimpleDateFormat eng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String engDemande = eng.format( new Date(this.getDateDemandeAjout()) );
			dateDemande = new Date(engDemande);
			System.out.println(dateDemande);
		}
		return dateDemande.getTime();
	}

	public void setTempsAttente(String tempsAttente) { this.tempsAttente = tempsAttente; }

	/**
	 * Mettre à jour le temps d'attente à l'appel de .toString().
	 * Utile pour des utilisations en CLI.
	 *
	 * NB : inutile pour les requêtes API actuelles car la méthode .voirTempsAttente() de
	 * DateEventService est appelée à chaque Get.
	 */
	public String voirTempsAttente() {
		if ( this.etatValidation.equals( EtatValidation.EN_ATTENTE.getValeurEtat() ) ) {
			dateManager.voirTempsAttente(this);
		}
		return tempsAttente;
	}

	public String getDateDemandeAjout() { return dateDemandeAjout; }

	public void setDateDemandeAjout(String dateDemandeAjout) { this.dateDemandeAjout = dateDemandeAjout; }

	public DateEventService getDateManager() { return dateManager; }

	public void setDateManager(DateEventService dateManager) { this.dateManager = dateManager; }

	public void setDateValidee(String approuve) {
		this.dateValidee = approuve;
	}

	public Utilisateur getCreateur() { return createur; }

	public void setCreateur(Utilisateur createur) { this.createur = createur; }


	@Override
	public String toString() {
		return "Question {" + "id = " + id + ", question = " + question + ", correction = " + correction +
				",\nreponses = " + this.infosQuestionsReponses() + ",\nindBonneRep = " + indBonneRep +", indice = " + indice +
				", etat = " + this.etatValidation + ", date validation = " + dateValidee + ", date de demande = " + dateDemandeAjout +
				", temps d'attente = " + this.voirTempsAttente() + /*", createur = " + createur.toString() +*/ "}\n";
	}

    /*
	public Chapitre getChapitre() { return chapitre; }

	public void setChapitre(Chapitre chapitre) { this.chapitre = chapitre; }
	 */

}
