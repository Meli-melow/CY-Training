import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../components/AuthContext';
import { useNavigate } from 'react-router-dom';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import axios from 'axios';
import './Profil.css';

function Profil() {
  const [data, setData] = useState([]);
  const { idUser, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [profile, setProfile] = useState({
    nom: 'Doe',
    prenom: 'John',
    email: 'john.doe@example.com',
    mdp: 'password123',
    filiere: 'Informatique'
  });

  useEffect(() => {
    async function GetData() {
      try {
        var response = await axios.get('http://localhost:8080/api/cyusers/profil/' + idUser);
        setProfile(response.data);
      } catch (error) {
        console.error('Erreur lors de la récupération du profil:', error);
      }
    }
    GetData();
  }, [idUser]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProfile({
      ...profile,
      [name]: value
    });
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put('http://localhost:8080/api/cyusers/modifier/' + idUser, {
        email: profile.email,
        mdp: profile.mdp,
        nom: profile.nom,
        prenom: profile.prenom
      });
      console.log('Profil mis à jour avec succès', response);
    } catch (e) {
      console.log(`La mise à jour du profil a échoué: ${e}`);
    }
  };

  const handleDelete = async () => {
    const confirmed = window.confirm("Êtes-vous sûr de vouloir supprimer votre profil ?");
    if (confirmed) {
      try {
        // on envoie une requête de suppression au serveur
        const response = await axios.delete('http://localhost:8080/api/cyusers/supprimer/' + idUser);
        logout();
        
        alert('Profil supprimé avec succès');
        navigate('/');
      } catch (e) {
        console.log(`La suppression du profil a échoué: ${e}`);
      }
    }
  };

  return (
    <div className="container">
      <h1>Profil</h1>
      <form onSubmit={handleSubmit}>
        <div className="field-profil">
          <label className="label-profil" htmlFor="nom">Nom</label>
          <input
            className="input-profil"
            type="text"
            id="nom"
            name="nom"
            value={profile.nom}
            onChange={handleChange}
          />
        </div>
        <div className="field-profil">
          <label className="label-profil" htmlFor="prenom">Prénom</label>
          <input
            className="input-profil"
            type="text"
            id="prenom"
            name="prenom"
            value={profile.prenom}
            onChange={handleChange}
          />
        </div>
        <div className="field-profil">
          <label className="label-profil" htmlFor="email">Email</label>
          <input
            className="input-profil"
            type="email"
            id="email"
            name="email"
            value={profile.email}
            onChange={handleChange}
          />
        </div>
        <div className="field-profil">
          <label className="label-profil" htmlFor="mdp">Mot de passe</label>
          <div>
            <input
              className="input-profil"
              type={showPassword ? 'text' : 'password'}
              id="mdp"
              name="mdp"
              value={profile.mdp}
              onChange={handleChange}
            />
            <button type="button" className="visbilityEye" onClick={togglePasswordVisibility}>
              {showPassword ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>
        </div>
        <div className="field-profil">
          <label className="label-profil" htmlFor="filiere">Filière</label>
          <select
            name="filiere"
            value={profile.filiere}
            onChange={handleChange}>
            <option value="">Sélectionnez une filière</option>
            {/*data.map((filiere, index) => ( // affichage des filières
              <option key={index} value={filiere.nom}>{filiere.nom}</option>
            ))*/}
          </select>
        </div>
        <p><i>Nombre de question proposées: </i> {profile.nbQuestionsProposees}</p>
        <p><i>Nombre de question validées: </i> {profile.nbQuestionsValidees}</p>
        <button type="submit" className="button-profil">Mettre à jour</button>
        <button type="button" className="button-profil" onClick={handleDelete}>Supprimer</button>
      </form>
    </div>
  );
}

export default Profil;
