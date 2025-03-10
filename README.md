# CyTraining


## Organisation des branches du dépôt
Le dépôt git contient 2 branches au total :<br>
- *main* regroupe les documents de conception et des indications d'utilisation des codes;
- *front* et *admins* regroupe le code réalisé par les 2 autres stagiaires avant leur départ à la mi-août, respectivement le code du front et de l'API dédiéé aux utilisateurs et aux admins;
- *exercises-fin-stage* contient les versions du développement des API liées aux questions.<br>
Cette dernière branche comporte des modifications majeures en avance sur la dernière version du front.
<br><br>

## Utilisation des ressources
Étant donné que nous avions eu seulement 8 semaines pour nous habituer à 2 frameworks, les fonctionnalités des admins et des exercices ont été séparées.
De plus, aucun projet *Spring* n'a été configuré pour être lancé avec les ressources statiques`.js` du front (dossier *ressources* non configuré).<br>

Il faut donc exécuter séparément les serveurs web et back (Springboot). Plus d'informations sont disponibles dans les README :<br>
- dans la branche *front* pour le front (le fichier `ReadMe.docx` détaille le lancement de l'app) et *admins* pour les admins;
- dans la branche *exercises-fin-stage* pour les exercices, la version `teamV3`est compatible, peut-être la `teamV3Bis` mais pas la `teamV4` car elle a été dévelopée après le départ du stagiare assigné au front.<br>

Les explications liés à l'utilisation générale des dossiers projets *Spring* (configuration des dépendances, de la base de données, conception des données, etc.) est détaillée dans le `README.md` de la branche *exercises-late-august*.
