import React, { useState, useContext, useEffect } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/AuthContext';
import './Creation.css';

function Creation() {
  const [data, setData] = useState([]);
  const [nom, setNom] = useState('');
  const [prenom, setPrenom] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [confirmPassword, setConfirmPassword] = useState('');
  const [filiere, setFiliere] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  // Get les différentes filière possibles dès le lancement de la page
  useEffect(() => {
    async function GetData() {
      try {
        var response = await axios.get('http://localhost:8080/api/filiere/all');
        setData(response.data);
      } catch (error) {
        console.error('Erreur lors de la récupération des filières:', error);
      }
    }
    GetData();
  }, []);

  // fonction pour voir le mot de passe
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // regex
    let re = /\S+@\S+\S+\S+\S\.\S+/;;

    if ( re.test(email) ) {
      // c'est un email valide
      setEmail(email);
    }
    else {
      setErrorMessage("Cet email est invalide");
      return;
    }

    if (!nom || !prenom || !email || !password || !confirmPassword/* || !filiere*/ ) {
      setErrorMessage('Tous les champs doivent être remplis');
      return;
    }

    if (password !== confirmPassword) {
      setErrorMessage('Les mots de passe ne correspondent pas');
      return;
    }

    // envoyer les données pour créer l'utilisateur
    try {
      const response = await axios.post('http://localhost:8080/api/cyusers/nouveau_compte', {
        email: email,
        mdp: password,
        nom: nom,
        prenom: prenom,
        filiere: {
          nom: filiere
        }
      });
      
      // Redirige vers la page Menu.js si la création de compte est réussie
      login(response.data.id) //login(response.data.id, response.data.role) 
      navigate('/menu');
    } catch (error) {
      if (error.response) {
        // La requête a été faite et le serveur a répondu avec un code de statut qui n'est pas dans la plage 2xx
        if (error.response.status === 500) {
          setErrorMessage('Cet email est déjà associé à un compte.');
        } else {
          setErrorMessage(`Erreur: ${error.response.data.message}`);
        }
      } else if (error.request) {
        // La requête a été faite mais aucune réponse n'a été reçue
        setErrorMessage('Aucune réponse du serveur.');
      } else {
        // Quelque chose s'est passé en configurant la requête qui a déclenché une erreur
        setErrorMessage(`Erreur: ${error.message}`);
      }
      console.error('Axios request failed:', error);
    }
  };

  return (
    <div className="creation-container">
      <h1 className="h1-creation">Création de Compte</h1>
      <form className="form-creation" onSubmit={handleSubmit}>
        <div className="creation-field-container">
          <label className="label-creation" htmlFor="nom">Nom:</label>
          <input
            className="input-creation"
            type="text"
            name="nom"
            value={nom}
            onChange={(e) => setNom(e.target.value)}
          />
          <label className="label-creation" htmlFor="prenom">Prénom:</label>
          <input
            className="input-creation"
            type="text"
            name="prenom"
            value={prenom}
            onChange={(e) => setPrenom(e.target.value)}
          />
          <label className="label-creation" htmlFor="email">Email:</label>
          <input
            className="input-creation"
            type="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <label className="label-creation" htmlFor="password">Mot de passe:</label>
          <div>
            <input
              className="input-creation"
              type={showPassword ? 'text' : 'password'}
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button type="button" onClick={togglePasswordVisibility}>
              {showPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <label className="label-creation" htmlFor="confirmPassword">Confirmer le mot de passe:</label>
          <input
            className="input-creation"
            type="password"
            name="confirmPassword"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
          <label className="label-creation" htmlFor="filiere">Filière:</label>
          <select
            name="filiere"
            value={filiere}
            onChange={(e) => setFiliere(e.target.value)}>
            <option value="">Sélectionnez une filière</option>
            {data.map((filiere, index) => (
              <option key={index} value={filiere.nom}>{filiere.nom}</option>
            ))}
          </select>
        </div>
        <button type="submit" className="creation-button">Créer le compte</button>
        {errorMessage && <p className="error">{errorMessage}</p>}
      </form>
    </div>
  );
}

export default Creation;
