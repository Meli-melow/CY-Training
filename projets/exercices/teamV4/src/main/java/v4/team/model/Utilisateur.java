package v4.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation des utilisateurs.
 */

@Entity
@Table(name = "utilisateur")
/**
 * Les résultats des requêtes API qui ne renvoient pas d'utilisateur indiqueront son id dans les autres entités en association
 * avec Utilisateur
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Utilisateur {

	/**
	 * Les ids sont générés automatiquement.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "prenom", nullable = false)
	private String prenom;

	@Column(nullable = false)
	private String nom;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "mot_de_passe", nullable = false)
	private String mdp;

	@Column(name = "nb_questions_proposees")
	private int nbQuestionsProposees;

	@Column(name = "nb_questions_validees")
	private int nbQuestionsValidees;


	/**
	 * Relation entité Question : un utilisateur peut créer plusieurs questions.
	 *
	 * La classe Utilisateur est l'hôte (contient mappedBy).
	 */
	@OneToMany(mappedBy = "createur")
	@Column(nullable = false)
	private List<Question> questionsCreees;

	/**
	 * Constructeur sans argument pour le mapper et les contrôleurs
	 */
	public Utilisateur() {}


	/**
	 * Création d'un nouvel utilisateur.
	 * Constructeur pour les requêtes API.
	 *
	 * Nécessaire pour le lancement de l'application.
	 *
	 * @param id
	 * @param prenom
	 * @param nom
	 * @param email
	 * @param mdp
	 */
	public Utilisateur(int id, String prenom, String nom, String email, String mdp)
	{
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
		this.nbQuestionsProposees = 0;
		this.nbQuestionsValidees = 0;
		this.questionsCreees = new ArrayList<>();
	}


	/**
	 * Création d'un nouvel utilisateur.
	 * Constructeur version terminal.
	 *
	 * @param prenom
	 * @param nom
	 * @param email
	 * @param mdp
	 */
	public Utilisateur(String prenom, String nom, String email, String mdp)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
		this.nbQuestionsProposees = 0;
		this.nbQuestionsValidees = 0;
		this.questionsCreees = new ArrayList<>();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {return prenom;}

	public void setPrenom(String nouvPrenom) { prenom = nouvPrenom; }


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public int getNbQuestionsProposees() { return this.nbQuestionsProposees; }

	public void setNbQuestionsProposees(int nbQProposees) { this.nbQuestionsProposees = nbQProposees;}

	public int getNbQuestionsValidees() { return this.nbQuestionsValidees;}

	public void setNbQuestionsValidees(int nbQValidees) { this.nbQuestionsValidees = nbQValidees;}
	
	public List<Question> getQuestionsCreees() {
		return questionsCreees;
	}

	public void setQuestionsCreees(List<Question> questionsCreees) { this.questionsCreees = questionsCreees; }

	/**
	 * Accéder aux détails des questions créées par un utilisateur.
	 */
	public String userQuestionsInfo() {
		String infosQuestions = "";
		for (Question questionUser : questionsCreees) {
			infosQuestions += questionUser.toString() + ";\n";
		}

		return "{ " + infosQuestions + " }";
	}


	/**
	 * Ajouter une question à la liste des questions créées par l'utilisateur.
	 *
	 * @param q la question qui vient d'être proposée.
	 */
	public void addQuestion(Question q) { questionsCreees.add(q); }


	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.nom, this.email);
	}


	/**
	 * Accéder aux détails d'un profil utilisateur.
	 *
	 * @return les détails de l'instance d'Utilisateur.
	 */
	@Override
	public String toString() {
		return "Utilisateur {\nid utilisateur = " + id + ", name = " + nom + ", email = " + email
				+ "mot de passe = " + mdp + ", nbQuestionsProposees = " + nbQuestionsProposees
				+ ",\nquestionsCreees = " + this.userQuestionsInfo() + "\n}\n";
	}

	/**
	 * Indique l'égalité de deux instances d'Utilisateur.
	 * La méthode se sert de critères d'unicité autres que l'id.
	 *
	 * @param o
	 * @return indique si l'objet est égal à l'instance d'Utilisateur.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Utilisateur) {
			Utilisateur utilisateur = (Utilisateur) o;
			//Crtitere d'unicite
			return this.email == utilisateur.getEmail();
		}
		return false;
	}

}
