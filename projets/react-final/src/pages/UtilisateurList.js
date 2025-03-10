import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UtilisateurList.css';

function truncateText(text, maxLength) {
  if (text.length <= maxLength) {
    return text;
  }
  return text.substring(0, maxLength) + '...';
}

function UtilisateurList() {
  const [data, setData] = useState([]);
  const [selectedFiliere, setSelectedFiliere] = useState('');
  const [selectedMatiere, setSelectedMatiere] = useState('');
  const navigate = useNavigate();

  // On envoi sur la page de détails de l'utilisateur
  const handleViewDetails = (id) => {
    navigate('/utilisateurdetails', { state: { idUtilisateur: id } });
  };

  const handleFiliereChange = (e) => {
    setSelectedFiliere(e.target.value);
  };

  const handleMatiereChange = (e) => {
    setSelectedMatiere(e.target.value);
  };

  // on récupère la liste de utilisateurs dès le lancement de la page
  useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/cyusers/all`);
      setData(response.data)
    }
    GetData()
  }, [])
  
  // On filtre les utilisateurs si une matiere ou une filiere a été choisie
  const filteredUsers = data.filter(u => 
    u
    // filtre les users par rapport aux filières et matières selectionées 
    //((selectedMatiere === '' || u.certif.some(c => c.id === selectedMatiere.id)) && (selectedFiliere === '' || u.filiere.id == selectedFiliere.id))
  );

  // Matières et filières selectionnable pour le filtrage
  //const matieres = [...new Set(data && data.map(u => u.certif.map(m => m)))];
  const matieres = [...new Set(data)];
  //const filieres = [...new Set(data.map(u => u.filiere))];
  const filieres = [...new Set(data)];

  return (
    <div className="utilisateur-list-container">
      <h2>Liste des Utilisateurs</h2>
      
      <div className="utilisateur-list-filters">
        <select 
            value={selectedFiliere}
            onChange={handleFiliereChange}
          >
          <option value="">Toutes les filiere</option>
          {filieres.map((filiere, index) => (
            <option key={index} value={filiere}>{filiere.nom}</option>
          ))}
        </select>
        <select 
          value={selectedMatiere}
          onChange={handleMatiereChange}
        >
          <option value="">Toutes certifications</option>
          {matieres.map((matiere, index) => (
            <option key={index} value={matiere}>{matiere.nom}</option>
          ))}
        </select>
      </div>

      <ul className="utilisateur-list">
        {filteredUsers.map((u) => (
          <li key={u.id} className="utilisateur-item">
            <div className="utilisateur-details">
              <p><strong>Nom:</strong> {u.nom}</p>
              <p><strong>Prénom:</strong> {u.prenom}</p>
            </div>
            <div className="utilisateur-metadata">
              <p><strong>Filière:</strong> {u.filiere}</p>
              <p><strong>Certifications:</strong> {(u.certif && truncateText(u.certif.map(c => c + ' '), 20)) || "Aucune"}</p>
            </div>
            <button type="button" onClick={() => handleViewDetails(u.id)}>Voir les détails</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default UtilisateurList;
