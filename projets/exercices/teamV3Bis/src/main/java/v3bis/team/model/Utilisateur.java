package v3bis.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO : définir manuellement constructeurs (avec/sans args), getters et setters
@Entity
@Table(name = "utilisateur")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "prenom", nullable = false)
	@Size(min = 1)
	String prenom;

	@Column(nullable = false)
	@Size(min = 1)
	String nom;

	@Column(unique = true, nullable = false)
	@Size(min = 1)
	String email;

	@Column(name = "mot_de_passe", nullable = false)
	//TODO : regex ?
	@Size(min = 1)
	String mdp;

	@Column(name = "nb_questions_proposees")
	int nbQuestionsProposees;

	@Column(name = "nb_questions_validees")
	int nbQuestionsValidees;

	//Clé étrangère
	/*
	@Column(name = "filiere")
	 Filiere filiere;
	*/

	/**
	 * Association : Un utilisateur peut créer plusieurs questions
	 * Création DANS L'AUTRE TABLE (question) DE LA CLÉ ÉTRANGÈRE 'id_créateur'
	 * Ce champ EST 'CACHÉ' : PAS DE questions_certifiées
	 *
	 * Utilisation : sélection dans la table question celles dont
	 * id_créateur correspond à l'id de l'utilisateur connecté

	 * Enfant / child / reference
	 *
	 * Une collection d'objets est obligatoire pour une instance qui peut-être liée à des instances d'une autre type
	 * -> faire les méthodes d'ajout dans la liste
	 * Tests :
	 * - jointure en récup seulement les ids des questions (IMPOSSIBLE)
	 * - jointure manuelle (OK)
	 */
	//TODO : MARCHE ENFIN (POST, GET (+ALL))
	//TODO : mais ajout infinis dans createur + questionsCertifiees de Utilisateur
	//TODO : AJOUTER LES METHODES D'AJOUT DE QUESTIONS, SUPPRESSION
	@OneToMany(mappedBy = "createur")
	/*@JsonBackReference*/
	@Column(nullable = false)
	//TODO : N.B 02/08 13h30 -> initialisation dans setter uniquement dans ce projet perso
	List<Question> questionsCreees;

	//Clé étrangère
	/*
	@Column(name = "revision")
	Revision revision;
	*/

	//TODO: Héritage

	// ce constructeur ne sert que pour transformer un utilisateur en admin
	/*public Utilisateur(Utilisateur u)
	{
		id = u.getId();
		nom = u.getNom();
		//filiere = u.getFiliere();
		email = u.getEmail();
		mdp = u.getMdp();
		idsQuestionsCertifiees = u.getIdsQuestionsCertifiees();
		//revision = u.getRevision();
	}*/

	/**
	 * Constructeur sans argument
	 *
	 * Nécessaire pour le mapper et les contrôleurs
	 */
	public Utilisateur() {}


	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 *
	 * @param id nécessaire pour les APIs et la BDD
	 */
	public Utilisateur(int id, String prenom, String nom, String email, String mdp /*, Filiere filiere, Revision revision*/)
	{
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.mdp = mdp;
		this.nbQuestionsProposees = 0;
		this.nbQuestionsValidees = 0;
		this.questionsCreees = new ArrayList<>();
		//this.filiere = filiere;
		//this.revision = revision;
	}

	/**
	 * Constructeur avec clés étrangères (complétion en cours)
	 *
	 * Version CLI SQL natif
	 * les ids sont générés automatiquement, inutile comme paramètre
	 */
	public Utilisateur(String prenom, String nom, String email, String mdp/*, Filiere filiere, Revision revision*/)
	{
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.mdp = mdp;
		this.nbQuestionsProposees = 0;
		this.nbQuestionsValidees = 0;
		this.questionsCreees = new ArrayList<>();
		//this.filiere = filiere;
		//this.revision = revision;
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


	/*public Filiere getFiliere() { return filiere; }


	public void setFiliere(Filiere filiere) { this.filiere = filiere; }*/

	public List<Question> getQuestionsCreees() {
		return questionsCreees;
	}

	//TODO : initialiser les contributions en optimisant la mémoire ; manuel car non fait par mapper (null)
	//TODO : N.B 02/08 13h30 -> uniquement dans ce projet perso
	public void setQuestionsCreees() {questionsCreees = new ArrayList<>(); }


	//TODO : update Question.toString()
	public String userQuestionsInfo() {
		String infosQuestions = "";
		for (Question questionUser : questionsCreees) {
			infosQuestions += questionUser.toString() + ";\n";
		}

		return "{ " + infosQuestions + " }";
	}

	public void addQuestion(Question q)
	{
		questionsCreees.add(q);
	}

	@Override
	public int hashCode() {
		return Objects.hash( this.id, this.nom, this.email );
	}


	//TODO : update Question.toString()
	@Override
	public String toString() {
		return "Utilisateur { " + "id = " + id + ", name = " + nom + ", email = " + email
				+ "mot de passe = " + mdp + ", nbQuestionsProposees = " + nbQuestionsProposees
				+ ",\nquestionsCreees = " + this.userQuestionsInfo() + " }\n";
	}

	/*
	public Revision getRevision() { return revision; }


	public void setRevision(Revision revision) { this.revision = revision; }


	public Revision reviser(List<Question> questions)
	{
		// on gere la liste de question à partir d'un controller (jsp lequel) et on met la liste dans cette methode pour set l'attribut

	    this.revision = new Revision(questions, this);

	    return revision;
	}
	*/

	@Override
	public boolean equals(Object o) {
		if (o instanceof Utilisateur) {
			Utilisateur utilisateur = (Utilisateur) o;
			return this.id == utilisateur.getId();
		}
		return false;
	}

	public void modifierProfil(String nom, /*Filiere filiere,*/ String email, String mdp)
	{
		this.setNom(nom);
		//this.setFiliere(filiere);
		this.setEmail(email);
		this.setMdp(mdp);
	}


}
