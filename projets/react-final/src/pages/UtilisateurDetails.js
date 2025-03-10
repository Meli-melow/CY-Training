import React, { useState, useEffect, useContext } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../components/AuthContext';
import './UtilisateurDetails.css';

// faut supprimer ca la 
const adminCertifs = ['Math', 'Physique']


function UtilisateurDetail() {

  const [data, setData] = useState([]);
  const { isRootAdmin } = useContext(AuthContext);
  const [certif, setCertif] = useState();

  const location = useLocation();
  const idUtilisateur = location.state?.idUtilisateur;

  // on récupère les données de l'utilisateur à afficher
  useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/cyusers/profil/`+ idUtilisateur);
      setData(response.data)
    }
    GetData()
  }, [])
  
  // certifie l'utilisateur
  const handleCertif = () => {//const handleCertif = (matiere ou jsp il mettre quoi) =>{
    /*
      async function GetData() {
        var response = await axios.patch(`http://localhost:8080/api/cyusers/certifier/`+ idUtilisateur +'/'+ matiereoujspquoi);
        setData(response.data)
      }
      GetData()
    */
    alert('Utilisateur certifié');
  };

  // supprime la certification
  const handleSupprimer = () => {//const handleCertif = (matiere ou jsp il mettre quoi) =>{
    /*
      async function GetData() {
        var response = await axios.patch(`http://localhost:8080/api/cyusers/supprimer_certif/`+ idUtilisateur +'/'+ matiereoujspquoi);
        setData(response.data)
      }
      GetData()
    */
    alert('Utilisateur supprimé');
  };

  return (
    <div className="utilisateur-details-container">
      <h2>Détail de la Utilisateur</h2>
      <form>
        <p><strong>Nom:</strong> {data.nom}</p>
        <p><strong>Prénom:</strong> {data.prenom}</p>
        <p><strong>Certifications:</strong> {data.certif}</p>
        <p><strong>Nombre de questions proposées:</strong> {data.nbQuestionsProposees}</p>
        <p><strong>Nombre de questions approuvées:</strong> {data.nbQuestionsValidees}</p>
        <div className="buttons">
          <select value={certif} onChange={(e) => setCertif(e.target.value)}>
            <option value="">Choisir une certification</option>
            {adminCertifs.map((c, index) => (
              <option key={index} value={c}>{c}</option>
            ))
            /*affiches les options possibles
              data.certif.map((c, index) => (
                <option key={index} value={c}>{c.nom}</option>
              ))
            */
            }
          </select>
          {isRootAdmin && <button className="supprimer-certif" type="button" onClick={handleSupprimer(/*certif ??????*/)}>Supprimer</button>}
          <button type="button" className="certifier" onClick={handleCertif(/*certif ??????? */)}>Certifier</button>
          </div>
      </form>
    </div>
  );
}

export default UtilisateurDetail;
