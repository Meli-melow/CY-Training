package v4.team.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import v4.team.enumerations.EtatValidation;
import v4.team.classes.DateEvent;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


/**
 * Implémentation des exercices type QCM.
 */

@Entity
@Table(name = "question")
public class Question {

	/**
	 * Les ids sont générés automatiquement.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	/**
	 * Relation entité Matière : une question est répertoriée dans au plus une matière.
	 *
	 * La table des question possède une clé étrangère correspondant à l'id de sa
	 * matière (@JoinColumn). La table matière est référencée.
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_matiere", nullable = false)
	private Matiere matiereQuestion;

	@Column(nullable = false)
	//Impose une longueur superieure à 5
	@Size(min = 5)
	private String question;

	@Column(nullable = false)
	//Impose d'etre non vide
	@Size(min = 1)
	private String correction;

	/**
	 * Correspond à l'ensemble des réponses proposées par le créateur de
	 * la question.
	 */
	@Column(name = "reponses", nullable = false)
	private List<String> reponses;

	/**
	 * Correspond à l'ensemble des indices des bonnes réponses.
	 */
	@Column(name = "index_bonnes_reponses", nullable = false)
	private HashSet<Integer> indBonneRep;

	@Column(nullable = false)
	private String indice;

	/**
	 * Correspond à l'état de validation de la question
	 */
	@Column(name = "etat_validation")
	private String etatValidation;

	/**
	 * La date de validation de la question.
	 * Par défaut, elle vaut "-".
	 */
	@Column(name = "date_validation")
	private String dateValidee;

	@Column(name = "temps_attente")
	private String tempsAttente;

	/**
	 * Équivalent à la date de création de la question.
	 * Par défaut, elle vaut "-".
	 */
	@Column(name = "date_demande_validation")
	private String dateDemandeAjout;


	/**
	 * Relation entité Utilisateur : un utilisateur peut créer plusieurs questions.
	 *
	 * La table des question possède une clé étrangère correspondant à l'id de son
	 * créateur (@JoinColumn). La table utilisateur est référencée.
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_createur")
	private Utilisateur createur;


	/**
	 * Permet de gérer les données temporelles d'une question.
	 * @Transient permet d'ignorer la connexion du champ à la base de données.
	 */
	@Transient
	private DateEvent dateManager;

	/**
	 * Constructeur sans argument pour le mapper et les contrôleurs.
	 */
	public Question() {
	}


	/**
	 * Création d'une question.
	 * Constructeur por les requêtes API.
	 *
	 * Nécessaire pour le lancement de l'application.
	 *
	 * @param id
	 * @param matiereQuestion
	 * @param question
	 * @param correction
	 * @param reponses
	 * @param indBonneRep
	 * @param indice
	 * @param createur
	 */
	public Question(int id, Matiere matiereQuestion, String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep, String indice,
					Utilisateur createur) {
		this.id = id;
		this.matiereQuestion = matiereQuestion;
		this.question = question;
		this.correction = correction;
		this.reponses = new ArrayList<>(reponses);
		this.indBonneRep = new HashSet<>(indBonneRep);
		this.indice = indice;
		this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
		this.dateValidee = " - ";
		this.createur = createur;
	}

	/**
	 * Création d'une question.
	 * Constructeur version terminal.
	 *
	 * @param matiereQuestion
	 * @param question
	 * @param correction
	 * @param reponses
	 * @param indBonneRep
	 * @param indice
	 * @param createur
	 */
	public Question(Matiere matiereQuestion, String question, String correction, List<String> reponses, HashSet<Integer> indBonneRep, String indice,
					Utilisateur createur) {
		this.matiereQuestion = matiereQuestion;
		this.question = question;
		this.correction = correction;
		this.reponses = new ArrayList<>(reponses);
		this.indBonneRep = new HashSet<>(indBonneRep);
		this.indice = indice;
		this.etatValidation = EtatValidation.EN_ATTENTE.getValeurEtat();
		this.dateValidee = " - ";
		this.createur = createur;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Matiere getMatiereQuestion() {
		return matiereQuestion;
	}

	public void setMatiereQuestion(Matiere matiere) {
		this.matiereQuestion = matiere;
	}


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

	public List<String> getReponses() {
		return this.reponses;
	}

	public void setReponses(List<String> reponses) {
		this.reponses = reponses;
	}

	/**
	 * Permet d'accéder aux réponses possibles de la question.
	 * .toString() adapté à une collection d'objets.
	 */
	public String infosQuestionsReponses() {
		String reponsesCreees = "";
		for (String reponse : reponses) {
			reponsesCreees += reponse + ", ";
		}
		return "[" + reponsesCreees + "]";
	}

	public HashSet<Integer> getIndBonneRep() {
		return indBonneRep;
	}

	public void setIndBonneRep(HashSet<Integer> idxBonneRep) {
		this.indBonneRep = idxBonneRep;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getEtatValidation() {
		return etatValidation;
	}

	public void setEtatValidation(String etat) {
		etatValidation = etat;
	}

	public String getDateValidee() {
		return dateValidee;
	}

	public void setDateValidee(String nouvDateValidee) {
		this.dateValidee = nouvDateValidee;
	}

	/**
	 * Renseigner la date de validation de la question.
	 */
	public void defDateValidee() {
		this.setDateValidee(dateManager.dateEvent(this.getDateValidee()));
	}

	/**
	 * Réinitialiser la date de validation de la question.
	 */
	public void resetDateValidee() {
		this.setDateValidee(dateManager.resetDate());
	}

	/**
	 * Non mis à jour dans la base de données.
	 */
	public String getTempsAttente() {
		return tempsAttente;
	}

	public void setTempsAttente(String tempsAttente) {
		this.tempsAttente = tempsAttente;
	}

	/**
	 * Mettre à jour le temps d'attente à l'appel de .toString().
	 * Utile pour des utilisations en CLI.
	 *
	 * NB : inutile pour les requêtes API actuelles car la méthode .voirTempsAttente() de
	 * DateEventService est appelée à chaque Get.
	 */
	public String voirTempsAttente() {
		if (this.etatValidation.equals(EtatValidation.EN_ATTENTE.getValeurEtat()))
			dateManager.voirTempsAttente(this);


		return tempsAttente;
	}


	/**
	 * Nécessaire pour mettre à jour le temps d'attente.
	 */
	public String getDateDemandeAjout() {
		return dateDemandeAjout;
	}

	public void setDateDemandeAjout(String dateDemandeAjout) {
		this.dateDemandeAjout = dateDemandeAjout;
	}

	/**
	 * Renouveler la date de création de la question.
	 */
	public void dateNouvDemande() {
		dateManager.dateNouvDemande(this);
	}


	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}

	/**
	 * Accéder aux détails d'une question.
	 * Concernant la matière, on renvoie uniquement son nom.
	 *
	 * @return les détails de l'instance de Question.
	 */
	@Override
	public String toString() {
		return "Question {" + "id question = " + id + ", nom matiere = " + this.matiereQuestion.getNom() + ", question = " + question + ", correction = " + getCorrection() +
				",\nreponses = " + this.infosQuestionsReponses() + ",\nindBonneRep = " + indBonneRep + ", indice = " + indice +
				", etat = " + etatValidation + ", date validation = " + dateValidee + ", date de demande = " + dateDemandeAjout +
				", temps d'attente = " + this.voirTempsAttente() + /*", createur = " + createur.toString() +*/ "}\n";
	}

	/**
	 * Indique l'égalité de deux instances de Question.
	 * La méthode se sert de critères d'unicité autres que l'id.
	 *
	 * @param o
	 * @return indique si l'objet est égal à l'instance de Question.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Question) {
			Question q = (Question) o;
			return    //Criteres determinants
					(this.createur.equals(q.getCreateur()) && this.question.equals(q.getQuestion())
							&& this.matiereQuestion.getNom().equals(q.getMatiereQuestion().getNom())
							&& this.dateDemandeAjout.equals(q.getDateDemandeAjout())
					);
		}
		return false;
	}

}
