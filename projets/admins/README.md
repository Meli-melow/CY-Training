

Bonjour,

Dans cette branche, vous trouverez l'API qui gère la partie : Utilisateur/Admin/RootAdmin et les matières/UEs/filières. En ce qui concerne les questions, regardez la branche de Lyz (spring-questions). En attendant, nous allons réunir les deux parties.

/////////////Prérequis

Assurez-vous d'avoir une base de données MySQL en cours d'exécution et configurez les informations d'identification dans le fichier application.properties. Installez Postman pour tester les requêtes. Ajoutez MapStruct à la configuration.

//////////////Endpoints principaux


N'oubliez pas d'ajouter : /api avant toute requête. Pour le modifier, allez dans le fichier application.properties et supprimez : server.servlet.context-path=/api. Cette configuration était utilisée pour différencier les différentes versions de l'application ou les différentes API.

Inscription d'un utilisateur
POST /api/utilisateur/inscrireUtilisateur

Corps de la requête : JSON contenant les informations de l'utilisateur (sa filière aussi). Permet de vérifier si son mail existe déjà. Vérifie également si la filière existe déjà ; si oui, elle fait la jointure entre les tables Utilisateur et Filière. Sinon, elle la crée et fait la jointure. La méthode responsable est : Filière getOrCreate(Filière filière), que j'ai appelée dans la méthode : InscriptionDto inscrireUtilisateur(InscriptionDto inscriptionDto) dans le service Utilisateur. On utilise le DTO : InscriptionDto qui ne contient que les infos nécessaires pour l'inscription : id, nom, prénom, mail, mot de passe, filière.

Connexion d'un utilisateur
POST /api/utilisateur/connexion

Paramètres : password et email (Strings)
Retourne : String : "RootAdmin" ou "Admin" ou "Utilisateur".
Lance : des exceptions pour un mot de passe incorrect ou un mail non trouvé.

Certifier un utilisateur pour une matière pour la première fois
POST /api/RootAdmin/CertifierUtilisateur

Paramètres : des int : utilisateurId, matièreId

Certifier un admin pour une nouvelle matière
POST /api/utilisateur/ajouterCertification

Paramètres : @RequestParam int adminId, @RequestParam int matiereId, @RequestParam int rootAdminId (ajouté pour vérifier que celui qui fait l'opération est un RootAdmin).


//////////////Endpoints secondaires


Ajouter filière : POST /api/filière/ajouter

Ajouter matière : POST /api/matière/ajouter

Ajouter UE : POST /api/ue/ajouter

Supprimer filière : DELETE /api/filière/delete/55

Supprimer utilisateur : DELETE /api/utilisateur/delete/int

Supprimer UE : DELETE /api/ue/delete/14

Rechercher UE : GET /api/ue/matieres?idUe=int ou passer l'ID en paramètre

Rechercher utilisateur par mail : GET /api/utilisateur/mail/{mail}

Rechercher utilisateur par ID : GET /api/utilisateur/id/{id}

Modifier un utilisateur : PUT /api/utilisateur/modification/20

Et d'autres... Les exceptions sont gérées dans la plupart des méthodes.



L'héritage



Admin étend Utilisateur
RootAdmin étend Admin
La stratégie de l'héritage est : @Inheritance(strategy = InheritanceType.SINGLE_TABLE). Tout est présenté dans la même table même si au-dessus de la classe RootAdmin c'est déclaré comme @Entity. Lombok était intéressant pour appeler les méthodes equals et hashCode : @EqualsAndHashCode(callSuper=true) et @SuperBuilder.

///////////À savoir pour les prochains stagiaires



Annotation : @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

À ajouter au-dessus de chaque classe pour éviter les boucles dans les méthodes get des objets, concernant les classes liées aux autres. Pensez aussi à créer un nouveau DTO sans l'attribut qui est responsable de la boucle. La dépendance responsable : com.fasterxml.jackson.core jackson-databind.

Mapper : @Mapper(componentModel = "spring", uses = { UeMapper.class })

Public interface FilièreMapper : puisque dans Filière j'ai des UEs, dans le mapping de Filière vous aurez besoin de mapper les UEs. Vous pouvez ajouter uses = { UeMapper.class } au-dessus de l'interface de mapping comme dans l'exemple ci-dessus.

Il y a plusieurs types de jointures utilisées : par colonne de jointure ou table de jointure. @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})


Persist permet de : Lorsque vous persistez (enregistrez) une instance de l'entité parent, les entités enfants associées seront également persistées.

Merge permet de : Lorsque vous effectuez une opération de fusion (merge) sur une instance de l'entité parent, les entités enfants associées seront également fusionnées.



Les exceptions sont gérées avec @ExceptionHandler({EntityNotFoundException.class}) dans le package Classes.Exceptions.

Les DTOs (Data Transfer Objects) permettent de transmettre uniquement les données nécessaires entre les différentes couches de l'API, tout en gardant la logique de l'application discrète. Le mapping entre les classes DTO et les classes est géré automatiquement avec MapStruct.