import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from './AuthContext';
import logo from '../img/logo_atilla.png';
import './Navbar.css';

function Navbar() {
  const { isAuthenticated, logout } = useContext(AuthContext);

  return (
    <nav className="navbar">
      <ul className="navbar-ul">
        {/*On affiche les boutons selon si l'utilisateur est connecté*/}
        {!isAuthenticated && <li className="navbar-li"><Link className="navbar-a" to="/">Accueil</Link></li>}
        {!isAuthenticated && <li className="navbar-li"><Link className="navbar-a" to="/connexion">Connexion</Link></li>}
        {!isAuthenticated && <li className="navbar-li"><Link className="navbar-a" to="/creation">Création</Link></li>}
        {isAuthenticated && <li className="navbar-li"><Link className="navbar-a" to="/menu">Menu</Link></li>}
        {isAuthenticated && <li className="navbar-li"><Link className="navbar-a" to="/profil">Profil</Link></li>}
        {isAuthenticated && <li className="navbar-li"><button className="deconnexion-button" onClickCapture={logout}>Déconnexion</button></li>}
      </ul>
      <img src={logo} className="logoAtilla"/>
    </nav>
  );
}

export default Navbar;
