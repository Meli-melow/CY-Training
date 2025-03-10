import React, { useState, useEffect, useContext } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/AuthContext';
import './Connexion.css';

function Connexion() {
  const [data, setData] = useState([]);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  // récupération des données de tous les utilisateurs au lancement de la page
  useEffect(() => {
    try {async function GetData() { 
        var response = await axios.get(`http://localhost:8080/api/cyusers/all`);
        setData(response.data)
      }
      GetData() 
    }
    catch(error) {
      //erreur
      console.error(error.message)
    }
  }, [])

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    
    // on cherche parmi les utilisateur un utilisateur qui aurait le même mot de passe et email
    const utilisateur = data.filter(u => 
      (u.email === email) &&
      (u.mdp === password)
    )

    if (utilisateur.length > 0) { // si il y a un utilisateur correspondant, on se connecte
      login(utilisateur[0].id); // login(utilisateur[0].id, utilisateur[0].role)
      navigate('/menu');
    }
    
    setErrorMessage('Identifiant ou mot de passe incorrect');
  };

  return (
    <div className="connexion-container">
      <h1 className="h1-connexion">Connexion</h1>
      <form className="connexion-form" onSubmit={handleSubmit}>
        <div className="connexion-field-container">
          <label className="connexion-label" htmlFor="email">
            Email :
          </label>
          <input
            className="connexion-input"
            type="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
       
          <label className="connexion-label" htmlFor="password">
            Mot de passe :
          </label>
          <div>
            <input
              className="connexion-input"
              type={showPassword ? 'text' : 'password'}
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button type="button" onClick={togglePasswordVisibility}>
              {showPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>
        </div>
        <button type="submit" className="connexion-button">Se connecter</button>
        {errorMessage && <p className="error">{errorMessage}</p>}
      </form>
    </div>
  );
}

export default Connexion;