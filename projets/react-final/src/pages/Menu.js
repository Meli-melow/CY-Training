import React, { useContext } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { AuthContext } from '../components/AuthContext';
import './Menu.css';

function Menu() {
  const location = useLocation();
  const { isAdmin, isRootAdmin } = useContext(AuthContext);
  const message = location.state?.message;

  return (
    <div className="menu-container">
      <h1 className="h1-menu">Menu</h1>

      {
        // affichage du message s'il y en a un
        message && <div className="confirmation-message">{message}</div>
      }
      {/* affichage des boutons disponible à tous les utilisateurs*/}
      <div className={isAdmin ? "button-row" : "button-row big"}>
        <Link to="/creation-question"><button className="large-button">
          Créer une Question
        </button></Link>
        <Link to="/revision"><button className="large-button">
          Réviser une Matière
        </button></Link>
        <Link to="/questionredolist"><button className="large-button">
          Refaire une Question
        </button></Link>
      </div>
      { // affichage des boutons disponible pour un admin si l'utilisateur est un admin
      isAdmin &&
      <div className="button-row">
        <Link to="/questionlist">
          <button className="small-button">
            Approuver une Question
          </button>
        </Link>
        <Link to="/utilisateurlist">
          <button className="small-button">
            Certifier {isRootAdmin && 'ou Supprimer'} un Utilisateur
          </button>
        </Link>
        <Link to="/questionapprouveeslist">
          <button className="small-button">
            Supprimer une Question
          </button>
        </Link>
      </div>}
    </div>
  );
}

export default Menu;