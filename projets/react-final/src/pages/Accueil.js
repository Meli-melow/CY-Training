import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../components/AuthContext';
import './Accueil.css';

function Accueil() {
  // affichage de la page d'accueil
  return (
    <div className="accueil-container">
      <h1 className="h1-accueil">Bienvenue</h1>
      <div className="button-container">
        <Link to="/connexion">
          <button className="accueil-button">
            Connexion
          </button>
        </Link>
        <Link to="/creation">
          <button className="accueil-button">
            Création
          </button>
        </Link>
      </div>
    </div>
  );
}

export default Accueil;
