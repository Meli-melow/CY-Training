# CyTraining - Exercices QCM

Cette branche contient le développement des API REST en mettant l'accent sur les QCM.<br>
Cette partie a été codée en Java avec le framework *Springboot* et l'IDE *IntelliJ IDEA*.
Les outils gestionnaires de projet utilisés sont **Maven** et **Spring Initialzr**.<br>
Bonne lecture!
<br><br>

## I) Fonctionnalités implémentées
Les fonctionnalités suivantes sont implémentées dans les dossiers projets de version supérieure ou égale à TeamV3 : <br>
- Les questions sont répertoriées dans des matières;
- Les questions validées dont le compte créateur n'existe plus restent accessibles (à mettre au point dans la dernière version du projet);
- La modification des questions par les utilisateurs est possible uniquement pour les propositions refusées;
- Accès au temps d'attente, à la date de création et de validation d'une question;
- Possibilité de changer la matière d'une question;
- Possibilité de modifier une matière;
- Possibilité de supprimer des propositions;
- Possibilité de relancer une proposition.

La dernière version du projet a commencé l'intégration des UE.<br>

Les composantes restantes à implémenter sont :<br>
- répertorier les utilisateurs dans des filières;
- répertorier les UE dans des filières;
- les questions à choix unique;
- la paramétrisation des exercices;
- les révisions;
- les taux de complétion.<br><br>

## II) Utilisation des ressources


### A) Les ressorces de la branche
*exercices-fin-stage* contient le développement de l'API liées aux questions (QCM). Il y a plusieurs versions :<br>
- `teamV3` qui implémente la relation entre les entités étudiant-contributeur/question, assigne des statuts aux questions et qui calcule leur temps d'attente,
versio compatible avec le front;
- `teamV3Bis` qui a corrigé la mise à jour du temps d'attente de QCM proposés par n'improte quel étudiant,
version probablement compatible avec le front;
- la dernière version `teamV4`qui d'une part implémente la contribuion des admins certifés dans une matière (voir le code relatif au *service Question*),
et qui d'autre part a commencé l'implémentation des UE, toutefois cette version peut être uniquement testée,
pas être exécutée avec le front;
- `CYTrainV4Bis` est le code pour générer les tables correspondantes à la version `teamV4`;
- `tests_matiere.txt` est un ensemble d'échantillons pour tester la version `teamV4`.

<br><br>

### B) Créer et importer un dossier projet Maven & Spring Initialzr

C'est faisable avec IntelliJ IDEA en choisissant la configuration du projet. On peut également utiliser Spring Initialzr directement au lien suivant :<br>
https://start.spring.io/ <br>
Les dépendances suivantes ont été importées :
- Spring Data JDBC;
- Spring Data JPA;
- MySQL Driver;
- Spring Web Services;
- Jackson-databind;
- Mapstruct;
- Lombok (désactivé).

Elles sont accessibles dans le fichier **pom.xml**, rangé dans le dossier du projet.<br>
Les trois premières servent pour la manipulation de la base de données. La quatrième permet notamment de rendre les contrôleurs accessibles aux appels front-end. Jackson-databind et Mapstruct permettent l'échange de données entre le front et le back. Ces deux dépendances ont été rajoutés manuellement.<br>
Lombok aide à accélérer le codage en créant les constructeurs (avec et sans arguments), getters et setters avec des annotations. Par exemple en écrivant au-dessus d'une classe
<br>`@NoArgsConstructor`
<br>`@AllArgsConstructor`
<br>`@Getters`
<br>`@Setters`<br>
D'autres utilisations sont possibles mais il faut faire une configuration supplémentaire pour l'activer. Le tutoriel suivant peut aider :<br>
https://youtu.be/uzu0nWMXvGE?si=W5CkXoFqV-wM6tfb <br>

Pour utiliser un projet de la branche avec IntelliJ :<br>
1. Importer en local la branche, tous les projets seront donc inclus;
2. Dans IntelliJ, *File > New > Project from Existing Sources*;
3. Choisir le projet voulu (les projets contiennent au moins les dossiers src et .mvn/wrapper);
4. Choisir Maven comme modèle externe;
5. Charger le fichier pom.xml.

Ces étapes sont valables pour les projets de n'importe qu'elle branche.<br>
L'étape 5 s'effectue :<br>
- soit en cliquant sur une icône Maven qui apparaît puis sur *Load Changes*;
- soit est réalisée automatiquement par IntelliJ.

À chaque changement apporté dans le fichier pom.xml, l'icône Maven apparaît pour sauvegarder.
<br><br>

### C) Organisation des fichiers
Les sources principales sont localisées dans le package src/main/java/v_n.team où *_n* est la version du dossier projet, la dernière étant 4. Les fichiers principaux ont été répartis dans différents packages :
- **models** contient les modèles, les entités qui correspondent aux classes et qui sont utilisées par la base de données;
- **dtos** pour *Data Transfer Object* correspond aux données qui sont échangées entre le front et le back (détails au **IV.A**);
- **mappers** qui contient les mappers créés avec Mapstruct (détails au **IV.A**);
- **classes** comporte les composantes de l'app qui ne sont pas enregistrées dans la base de données et qui fonctionnent sans action directe de la part des utilisateurs;
- **enumerations** est lié aux énumérations implémentées;
- **exceptions**;
- **repositories** rassemble les *repositories*, chacun est associé à un seul modèle;
- **services**, chacun associé à une API et qui contient toute la logique derrière leur fonctionnement;
- **controllers**, comme les services mais ils servent uniquement à appeler les requêtes API.

Le fichier application se trouve dans le package src/main/java/v_n.team (*_n* étant la version du dossier projet). L'exécuter activera les API.<br>

Le dossier src/main/ressources contient notamment le fichier **application.properties**. Il permet de configurer le serveur des API, la connexion à la base de données ainsi que son fonctionnement par rapport aux API (détails au **III.C**).<br>

Le dossier target contient les fichiers cibles qui sont générés après chaque exécution du fichier application. Ils sont associés à chaque dossier ou fichier de src/main/java. Les sous-dossiers **classes** et **generated-classes** permettent de vérifier que les mappers génèrent bien leurs classes associées (détails au **IV.A**).<br><br>

### D) Configuration de la base de données
Le paramétrage de la base de données est réalisée dans le fichier application.properties. ***La base de données doit être créée avant l'exécution de l'application.*** Ce fichier contient plusieurs champs.

`spring.application.name` correspond au nom de l'application.

`spring.datasource.url` contient au moins le lien vers la base de données. Spring Data JDBC étant utilisé, ce lien s'écrit comme `jdbc:mysql://localhost:port_localhost/nom_bdd`.

`spring.jpa.properties.hibernate.dialect` comporte ici `org.hibernate.dialect.MySQL8Dialect` car le serveur de base de données utilisé est MySQL version 8.

`spring.datasource.driver-class-name` contient le lien vers le driver de la BDD.

`spring.jpa.hibernate.ddl-auto` permet notamment de gérer l'utilisation des tables (création, activer la modification, suppression, détails au **III.C**)

`server-port` correspond au port de l'application. Il vaut 8080 par défaut. Il est facultatif.

`server.servlet.context-path` correspond à l'URL vers l'api mais est réduit à son nom. Il contient le lien vers toutes les apis. Il apparaît après `http://localhost:port_app`.

Ce sont les propriétés de base. Les autres ont été ajoutées au fur et à mesure du développement.<br><br>

## III) Stratégie d'implémentation des modèles

### A) Les entités
Les entités sont manipulées avec des annotations appartenant à la librarie **jakarta.persistence** de JPA.<br>

De telles classes doivent êtré annotées par `@Entity`. On définit leur table associée dans la base de données avec `@Table`. Le paramètre `name`, facultatif, permet de renommer la table. Par défaut c'est le nom de l'entité en minuscule et sans '_'. Dès qu'une entité est associée à une table, ses champs le sont aussi. `@Column` permet de les renommer. On peut aussi annuler leur association avec `@Transient` : il sera impossible d'accéder à ces champs avec la base de données, et donc impossible d'y accéder avec les API.<br>
Chaque entité possède au moins deux constructeurs, l'un avec tous les arguments (dont l'id) et l'autre sans argument pour les mappers.

#### L'entité Question

Des entités-classes qui ont été codées, c'est la seule qui possède des champs de type collection qui n'ont rien à voir avec les autres entités. Ce sont `reponses` pour les réponses possibles et `indBonneRep` pour l'ensemble des indices des bonnes réponses. Le 1er champ est une liste de string afin de faciliter les traitements des réponses envoyées par le front (équivalentes à du texte). Le 2e est un hashset d'entiers, chacun correspondant au numéro de la réponse. Le but étant, lors de la détermination d'un résultat à une question, de comparer l'ensemble des indices des bonnes réponses avec ceux qu'à entré l'utilisateur pendant une révision. C'est réalisable avec la méthode `.values()` des collections Java.<br>

La gestion des questions (validation, refus, relance et remise en attente) a été implémentée avec l'énumération *EtatValidation*. Il y a trois valeurs chacunes associées à un string :<br>
- `"En attente"` pour les questions qui viennent d'être créées, sauf pour celles "créées par des admins" (détails partie *Le service des questions* du **IV.C**);
- `"Validée"`;
- `"Refusée"`.

L'énumération a été codée comme une classe avec un constructeur, getter et setter afin de rendre son utilisation flexible.<br>

Les données temporelles sont représentées par des string. Leur gestion est réalisée par la classe `DateEvent` du package *classes*. Il permet de gérer tout événement faisant intervenir des dates ou des temps. Il est utilisé pour :
- les dates d'ajout d'une proposition à la liste d'attente;
- calculer le temps d'attente d'une question;
- les dates de validation et de refus d'une question.

`DateEvent` s'occupe de faire les conversions de formats de date, de celui de la JVM au format "jj/MM/aaaa hh:mm:ss", puis en millisecondes pour le temps d'attente. Pour faciliter son usage, l'entité Question possède un champ `@Transient dateManager`.<br>
Il n'a pas été possible de coder les données temporelles pour les utilisateurs (date de certification et durée, notification pour mettre à jour le profil à la rentrée) dans le temps imparti, mais des méthodes de `DateEvent` pourraient bien servir.<br>
Le debugging de cette classe est réalisé avec `ExceptionDateManager`.<br><br>

### B) Les associations entre les entités
Elles sont également implémentées par jakarta.persistence. J'ai utilisé les annotations :<br>
- `@ManyToOne` dans les entités dont plusieurs instances sont en relation avec une seule instance d'une autre entité;
- `@OneToMany` dans le cas contraire;
- `@OneToOne` lorsqu'une seule instance d'une entité est liée à une seule instance d'une autre entité.

Les relations suivantes ont été codées :<br>
- un utilisateur peut créer plusieurs questions, une question peut-être créée par au plus un utilisateur;
- une matière répertorie plusieurs questions, une question est répertoriée par plusieurs matières;
- une UE comporte plusieurs matières, une matières appartient à une seule UE;
- **une filière comporte plusisuers UE, une UE appartient à une seule filière.**

Les deux dernières relations ont été implémentées en partant sur la base qu'une matière est caractérisée à la fois par sa filière et son UE.<br>
Par exemple, les filières ING1 GI et GM ont chacune une UE Maths et la matière Probabilités, or seuls les GM étudient la théorie de la mesurabilité. Dans ce cas, il n'est pas intéressant pour les ING1 GI d'être confronté à ce thème pendant qu'ils révisent les Probabilités de la filière GI. Les questions associées seront rangées dans les Maths de la filière GM. Mais ça n'empêche pas les GI curieux d'y accéder. Ainsi, le nom d'une matière ne permet pas de l'identifier. Les API ont été ajustées en conséquence (détails partie *L'API des matières* du **IV.D**).

Le champ de l'entité qui possède `@OneToMany` sera une collection d'objets. De telles collections d'objets sont des listes car les hashmap sont incompatibles.<br>
Pour rendre les associations bi-directionnelles, les deux entités doivent contenir une des annotations ci-dessus. Dans ce cas l'entité *hôte* (pour laquelle il est naturel d'avoir l'information sur l'autre entité) doit contenir un champ `mappedBy`. C'est un paramètre des annotations associées. C'est un string dont la valeur est le nom du champ de l'autre entité en jeu.<br>
Par exemple entre les entités Utilisateur et Question, la 1ère est l'hôte et contient donc `mappedBy=questionsCreees` (voir *Utilisateur.java*).<br>
***Il est à noter que les bases de données ne peuvent pas stocker les champs de type objet. Ils sont automatiquement ignorés lors de la connexion à la base de données.*** <br>

Lorsqu'une association est créée, une jointure est créée entre les tables correspondantes. Les clés étrangères ont été privilégiées devant les tables d'association afin d'économiser de la mémoire. Pour se faire, `@JoinColumn` doit obligatoirement être utilisé. Dans les projets de cette branche, les tables non hôtes la possède, ce qui permet d'accéder à leur entité partenaire quand nécessaire.<br>
Toutes les entités sont en relations bi-directionnelles afin de respecter le mieux possible les fonctionnalités attendues.<br>
Par exemple, une question est liée à un utilisateur afin de permettre aux admins d'accéder aux informations des utilisateurs contributeurs (nom, prénom, filière, éventuellement leur mail).<br><br>

### C) Gestion des tables de la base de données
Les changements apportés à l'ensemble des champs (ajout ou modification) influencent les tables. Les changement dépendent de la valeur de `spring.jpa.hibernate.ddl-auto` :<br>
- `create` ou `create-drop` suppriment les tables à chaque exécution de l'application et prennent en compte les nouveaux champs;
- `update` rend les tables modifiables mais ajoute les nouveaux champs à part.

Avec le 2e mode, les anciens champs ne sont pas supprimés mais s'accumulent. Dans ce cas, il faut supprimer les tables avant de relancer l'application.<br>
Avec le 1er mode, seules les requêtes API Get peuvent être utilisées.<br>

Avec l'utilisation de jointures et de tables d'association, les tables doivent être supprimées en respectant cet ordre :<br>
- en premier les tables d'association;
- ensuite les tables possédant des clés étrangères;
- enfin les autres tables.

Un exemple se trouve dans le ficher **CYTrainV4Bis.sql** de la branche.

## IV) Stratégie d'implémentation des API
La mise en place des API est divisée en plusieurs couches :<br>
- les mappers;
- les *repositories* qui permettent de manipuler les données stockées dans la BDD;
- les services;
- les contrôleurs.
<br>

### A) Les mappers
Créés avec Mapstruct, ce sont des interfaces qui doivent être annotées avec `@Mapper(componentModel = "spring")`. Ils réalisent les conversions des instances des entités en DTO avec les méthodes respectives `.toDto()` et `.toClasse()`.<br>
Les DTO peuvent être vus comme les versions classes des entités. Leurs champs sont au moins ceux utilisés par la table associée. Ce sont les données envoyées au serveur client à l'appel d'une requête API.<br>
Pour faire correspondre un champ d'un DTO avec son entité, au-dessus des méthodes `.toDto()` et `.toClasse()` doit être ajouté `@Mapping` avec les champs `source` et `target`. Ce sont les noms des champs de l'entité et de son DTO.<br>

Les services utilisent les implémentations des mappers. Ceux-ci sont générés à chaque exécution de l'application et sont accessibles dans les sous-dossiers *classes* et *generated-classes* de target. Ce dossier respecte l'organisation des fichiers et packages des sources. Pour importer correctement les mappers, il suffit donc d'indiquer le package de l'interface mapper, même si la classe associée est en réalité dans un autre emplacement.<br>

Le débugging des mappers a été commencé avec la classe *ExceptionMapper*. Les erreurs sont souvent associées à des problèmes de (dé)sérialisation pendant les conversions.<br><br>

### B) Les repositories
Ils sont utilisés en tant qu'interfaces et héritent des *repositories CRUD* de JPA. Ils doivent être annotés par `@Repository`. Ils exécutent des requêtes SQL pour manipuler les données des tables. Ils sont utilisés par les services.<br>
On peut coder des requêtes SQL personnalisées avec JPQL. Il suffit d'utiliser l'annotation `@Query`. Les requêtes en SQL natif doivent correspondre au paramètre `value` et `nativeQuery` doit valoir `true`. JPQL se repose sur les champs des entités plutôt que celui des tables. Il s'avère être très utile pour réaliser des manipulations entre des entités en association. Cependant, il ne permet donc pas de manipuler les tables d'association.<br>

Les services se servent des *repositories* tels quels.

Le débugging des *repositories* a été commencé avec les classes `ExceptionRepository` et `ExceptionRessourceAbsente`.<br><br>

### C) Les services
Ils sont séparés en deux sous packages à savoir *Classes* et *Interfaces*. Ils doivent être annotés par `@Service`. Les classes contiennent l'implémentation du fonctionnement des API.<br>
Les requêtes CRUD de base ont été implémentées puis personnalisées pour se rapprocher des attentes de l'application. Ils se reposent sur deux aspects importants: <br>
- le *cascading* des entités;
- les transactions.


Le *cascading* correspond à la propagation des modifications entre des entités en association. Il y a différents types de cascades. Dans les dossiers projets de cette branche ont été utilisés : <br>
- `PERSIST` permet de prendre en compte la création d'une instance d'une entité dans celles qui lui sont associées;
- `MERGE` permet de prendre en compte la modification d'une instance d'une entité.

Ils sont définis dans les annotations des associations JPA (détails au **III.B**). Dans le dossier contenant la dernière version, en général, les classes hôtes ont les deux cascades et leurs partenaires ont uniquement `MERGE`.

Les transactions s'assurent que les modifications apportées aux instances d'entités respectent l'intégrité des données. ***Les méthodes des services servant à faire des changements sur une ou des entités en association doivent être annotées par `@Transactional`.*** <br>

Au plus un service peut dépendre directement d'un autre. Les projets créés se servent directement des *repositories* et des mappers des autres entités.

#### Le service des questions

Ce service est utilisable en front-end. Mais pour certaines méthodes, il est peut-être judicieux de passer par les autres API pour des raisons de sécurité (par exemple, `.getAllQuestions()` renvoit toutes les questions existantes dans la base de données). Ainsi, la création de questions est possible directement dans le service dédié aux utilisateurs. Cependant, certaines méthodes des questions ont été créées dans l'optique de les rendre utilisables par les admins.<br>
Par exemple, la méthode `.createQuestion()` permet de créer des questions qui sont immédiatement validées. On peut imaginer que ce soit le cas pour les admins qui rajoutent des exercices dans la matière correspondant à celle de leur certification.

Accéder à une question (requête *GET*) recquiert de mettre à jour son temps d'attente. Les méthodes associées doivent être `@Transactional`. `DateEvent` s'occupe de calculer le temps d'attente. Ensuite le *repository* s'occupe de la mise à jour avec `.updateTempsAttente()`. Il faut dans ce cas faire intervenir le *repository* ou autrement, il ne comprendra pas de passer par un *GET* pour modifier une question.

La matière d'une question peut être changée avec son nom.<br><br>

### D) Utilisation des API
Elles sont mises à disposition par les contrôleurs. Ils doivent être annotés par `@RestController` et `@RequestMapping`. Le `path` de la 2e permet de diviser le serveur des API en API plus petites. Le dernier dossier projet de la branche contient les API pour les utilisateurs, les questions et les matières. `@CrossOrigin` rend les API utilisables en front-end.<br>
Le corps et le résultat de toute requête est un DTO. Des arguments peuvent être ajoutés directement dans le lien de la requête.

#### Mise au point

C'est possible avec Postman. Il suffit de créer un compte, d'installer Postmant Agent, et de rassembler les requêtes API dans des collections. Avant l'appel d'une requête, Postman Agent doit être exécuté. Sur Windows, c'est le cas lorsque son icône apparaît dans la barre des tâches ou la fenêtre des icônes cachées.<br>
Un exemple d'utilisation se trouve dans le fichier **tests_matiere.txt** de la branche. Il contient le format des DTO compatible avec la dernière version

L'emploi des associations a complexifié les résultats des requêtes. Pour éviter les boucles infinies, les entités en association doivent être annotées par `@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)`. Le champ `property` doit aussi être renseigné. C'est le nom du champ qui représentera les instances.<br>
Par exemple dans le dernier dossier projet, la propriété correspondante est `nom`. Dans le résultat d'une requête API qui ne renvoie pas de `MatiereDto`, les entités associées à `Matiere` feront apparaître sont nom uniquement.<br>
Cependant il y a des limites : cela ne s'applique pas à la première instance du résultat de la requête.

Les requêtes API *PUT* ont été changées en *PATCH* grâce au mappers : on peut créer ou modifier une instance d'entité en renseignant une partie de ses champs.

#### L'API des matières

Plus on rajoute de questions, plus manipuler des matières devient encombrant. C'est pourquoi, pour des raisons de conception (détails au 1er exemple du **III.B**) et de praticité, les autres API faisant appel aux matières utilisent uniquement son nom.<br>
Cette stratégie pourrait se transposer aux filières et aux UE. Toute API faisant appel à une filière, une UE ou une matière utliserait leur nom. L'UE voulue serait obtenue en récupérant sa filière grâce aux *repository* associé, puis rechercherait l'UE dans la liste des UE de la filière. Si on cherchait une matière, on regarderait ensuite dans la liste des matière de l'UE précédemment trouvée.<br>
La dernière version du projet a commencé l'intégration des UE.
