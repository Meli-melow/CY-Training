-- Creation de la BDD a l'execution d'une nouvelle application (mode dév)
CREATE DATABASE IF NOT EXISTS cytrainv4b;

-- Supprimer la table d'association entre les utilisateurs et la liste de leur questions validées
DROP TABLE IF EXISTS cytrainv4b.utilisateur_questions_certifiees;

-- Supprimer la table d'association entre les questions et les matières
DROP TABLE IF EXISTS cytrainv4b.matiere_question;

-- Supprimer la table d'association entre les questions QCM et les réponses 
DROP table IF EXISTS cytrainv4b.question_reponses;

-- Supprimer la table d'association entre les matières et leurs questions
DROP table IF EXISTS cytrainv4b.matiere_questions_matiere;

-- Supprimer les tables ayant des clés étrangères
DROP table if exists cytrainv4b.question;
DROP table if exists cytrainv4b.matiere;

-- Supprimer les tables
DROP table if exists cytrainv4b.ue;
DROP table if exists cytrainv4b.utilisateur;