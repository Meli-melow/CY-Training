package v3bis.team.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import v3bis.team.enumerations.EtatValidation;
import v3bis.team.service.Classes.DateEventService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "question")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "createur")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(nullable = false)
	@Size(min = 5)
	String question;

	@Column(nullable = false)
	@Size(min = 1)
	String correction;

	@Column(name = "reponses", nullable = false)
	List<String> reponses;

	@Column(name = "index_bonnes_reponses", nullable = false)
	HashSet<Integer> indBonneRep;

	@Column(nullable = false)
	String indice;

	@Column(name = "etat_validation")
	String etatValidation;

	@Column(name = "date_validation")
	String dateValidee;

	String tempsAttente;

	String dateDemandeAjout;


	//TODO : uniD -> @ dans UNE DES DEUX ; nom clé étrangère = nom_autre_table_id
	/**
	 * Parent / owner
	 *
	 * Une collection d'objets est obligatoire pour une instance qui peut-être liée à des instances d'une autre type
	 * -> faire les méthodes d'ajout dans la liste de questions
	 * Tests :
	 * - jointure en récup seulement les ids des questions (IMPOSSIBLE)
	 * - jointure manuelle (OK)
	 */
	//ALL : toutes les cascades
	//PERSIST : Création possible de question (hôte) même quand le créateur (champ de la référence) n'existe pas dans la BDD
	//DETACH : question (ici hôte) est détachée de la bdd donc aucune modif possible de createur
	//MERGE : les modifs de createur (champ de la réf) dans question (table hôte) sont prise en compte dans la table utilisateur (réf)
	//REMOVE : supprimer une question (hôte) supprime un utilisateur (réf)
	//REFRESH :
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	/*@JsonManagedReference*/
	@JoinColumn(name = "id_crateur")
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
	 * Nécessaire pour le mapper et les contrôleurs
	 */
	public Question() {}


	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 */
	public Question(int id, String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep, String indice,
	Utilisateur createur /*, Chapitre chapitre*/) {
		this.id = id;
		this.question = question;
		this.correction = correction;
		this.reponses = new ArrayList<>(reponses);
		this.indBonneRep = new HashSet<>(indBonneRep);
		this.indice = indice;
		this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
		this.dateValidee = " - ";
		this.createur = createur;
		//this.chapitre = chapitre;
	}

	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 *
	 * Version CLI SQL natif : id inutile car généré automatiquement
	 */
	public Question(String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep, String indice,
					Utilisateur createur /*, Chapitre chapitre*/) {
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
			reponsesCreees += reponse + ", ";
		}
		return "[" + reponsesCreees + "]";
	}

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
	public HashSet<Integer> getIndBonneRep() { return indBonneRep; }

	public void setIndBonneRep(HashSet<Integer> idxBonneRep) { this.indBonneRep = idxBonneRep; }

	public String getIndice() { return indice; }

	public void setIndice(String indice) { this.indice = indice; }

	public String getEtatValidation() { return etatValidation; }

	public void setEtatValidation(String etat) { etatValidation = etat; }

	public String getDateValidee() { return dateValidee; }

	public void setDateValidee(String nouvDateValidee) { this.dateValidee = nouvDateValidee; }


	//TODO : ternary operator to update = f(state)
	public String getTempsAttente() { return tempsAttente; }

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

	/**
	 * Convertit en ms la date de demande de validation
	 */
	public long demandeMillisecondes()
	{
		Date dateDemande = null;
		if ( !this.getDateDemandeAjout().equals(null) ) {
			SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String millisecDemande = fr.format( new Date(this.getDateDemandeAjout()) );
			dateDemande = new Date(millisecDemande);
			System.out.println(dateDemande);
		}
		return dateDemande.getTime();
	}

	public String getDateDemandeAjout() { return dateDemandeAjout; }

	public void setDateDemandeAjout(String dateDemandeAjout) { this.dateDemandeAjout = dateDemandeAjout; }

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
