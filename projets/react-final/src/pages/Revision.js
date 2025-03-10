import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Revision.css';

const filieres = ['Filière A', 'Filière B', 'Filière C'];
const ues = {
  'Filière A': ['UE1', 'UE2', 'UE3'],
  'Filière B': ['UE4', 'UE5', 'UE6'],
  'Filière C': ['UE7', 'UE8', 'UE9']
};
const matieres = {
  'UE1': ['Mathématiques', 'Physique', 'Chimie'],
  'UE2': ['Biologie', 'Informatique', 'Économie'],
  'UE3': ['Histoire', 'Géographie', 'Philosophie'],
  'UE4': ['Statistiques', 'Probabilités'],
  'UE5': ['Algorithmes', 'Systèmes d\'exploitation'],
  'UE6': ['Marketing', 'Gestion'],
  'UE7': ['Archéologie', 'Sociologie'],
  'UE8': ['Climatologie', 'Urbanisme'],
  'UE9': ['Psychologie', 'Linguistique']
};

function Revision() {
  const [data, setData] = useState([]);
  const [filiere, setFiliere] = useState('');
  const [ue, setUe] = useState('');
  const [matiere, setMatiere] = useState('');
  const [nombreQuestions, setNombreQuestions] = useState(1);
  const [matieresOptions, setMatieresOptions] = useState([]);
  const navigate = useNavigate();

  // on Get les filieres UE matiere
  /*useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/filiere/all`);
      setData(response.data)
    }
    GetData()
  }, [])
  */

  useEffect(() => {
    if (ue) {
      setMatieresOptions(matieres[ue] || []);
    } else {
      setMatieresOptions([]);
    }
    setMatiere('');
  }, [ue]);

  const handleStartExamen = () => {
    // on get les questions pour essayer de les trier en fonction des choix de matières et de modes et en faire une liste
    ///////// il faut vérifier s'il n'y a pas de méthode pour faire ca dans le back maintenant
    /*try {
        const response = await axios.get('http://localhost:8080/api/questions/all');
        console.log('Returned data:', response);
      } catch (e) {
        console.log(`Axios request failed: ${e}`);
      }
      

      ////////////// faut faire en sorte que le nombre de question de la matiere soit inferieur à nombreQuestion dans le if du dessous
      const questions = response.data.filter(q => 
        (q.matiere.id === matiere.id)
      )    
      */

    if (matiere && nombreQuestions) { // if (matiere && nombreQuestions && nombreQuestion < questions.length) {
      // On get les données des questions avant de les trier et en faire une liste comme il faudrait
      
         
  
      // 👇️ Get the number between min (inclusive) and max (inclusive)
/*
        let revision = [];
        let j;
        for(let i = 0; i < nombreQuestions; i++)
        {
          j = Math.floor(Math.random() * (questions.length + 1));
          revision = [...revision, questions[j]]
        }

      */
      //navigate('/questionexamen', { state: { nombreQuestions, revision } });
    } else {
      alert('Veuillez remplir tous les champs et sélectionner un nombre de questions admissible.');
    }
  };

  const handleStartRevisionNouvelles = () => {
        // on get les questions pour essayer de les trier en fonction des choix de matières et de modes et en faire une liste
    ///////// il faut vérifier s'il n'y a pas de méthode pour faire ca dans le back maintenant
    /*try {
        const response = await axios.get('http://localhost:8080/api/questions/all'); ////////////////// et utilisateur ??????????????????,
        console.log('Returned data:', response);
      } catch (e) {
        console.log(`Axios request failed: ${e}`);
      }
      setData(response.data);
      
      ////////////// faut faire en sorte que le nombre de question de la matiere soit inferieur à nombreQuestion dans le if du dessous 
      const questions = response.data.filter(q => 
        (q.matiere.id === matiere.id)
      )  
    */
    if (matiere && nombreQuestions) { // if (matiere && nombreQuestions && nombreQuestions < questions.length) {
      // On get les données des questions avant de les trier et en faire une liste comme il faudrait
      

      // 👇️ Get the number between min (inclusive) and max (inclusive)
      /*return Math.floor(Math.random() * (max - min + 1)) + min;
        let revision = [];
        let j;
        for(let i = 0; i < nombreQuestions; i++)
        {
          j = Math.floor(Math.random() * (questions.length + 1));
          revision = [...revision, questions[j]]
        }

      */
        //navigate('/question', { state: { nombreQuestions, revision } });
      } else {
        alert('Veuillez remplir tous les champs et sélectionner un nombre de questions admissible.');
      }
  }

  const handleStartRevision = () => {
        // on get les questions pour essayer de les trier en fonction des choix de matières et de modes et en faire une liste
    ///////// il faut vérifier s'il n'y a pas de méthode pour faire ca dans le back maintenant
    /*try {
        const response = await axios.get('http://localhost:8080/api/questions/all');
        console.log('Returned data:', response);
      } catch (e) {
        console.log(`Axios request failed: ${e}`);
      }
      
      ////////////// faut faire en sorte que le nombre de question de la matiere soit inferieur à nombreQuestion dans le if du dessous
      
      */

      /*
        const questions = response.data.filter(q => 
          (q.matiere.id === matiere.id)
        )   
      */
    if (matiere && nombreQuestions) { // if (matiere && nombreQuestions && nombreQuestions < questions.length) {
      // On get les données des questions avant de les trier et en faire une liste comme il faudrait
      

      // 👇️ Get the number between min (inclusive) and max (inclusive)
      /*return Math.floor(Math.random() * (max - min + 1)) + min;
        let revision = [];
        let j;
        for(let i = 0; i < nombreQuestions; i++)
        {
          j = Math.floor(Math.random() * (questions.length + 1));
          revision = [...revision, questions[j]]
        }

      */
     // navigate('/question', { state: { nombreQuestions, revision } });
    } else {
      alert('Veuillez remplir tous les champs et sélectionner un nombre de questions admissible.');
    }
  };

  return (
    <div className="revision-container">
      <h1 className="h1-revision">Révision</h1>
      <form className="form-revision">
        <div className="revision-field-container">
          <label className="label-revision" htmlFor="filiere">
            Filière :
          </label>
          <select className="select-revision" name="filiere" value={filiere} onChange={(e) => setFiliere(e.target.value)}>
            <option value="">Choisir une filière</option>
            {
            filieres.map((f, index) => (
              // on fait les options correctement
            //data.map((f, index) => (
            //<option key={index} value={f}>{f.nom}</option>
              <option key={index} value={f}>{f}</option>
            ))}
          </select>
        
          <label className="label-revision">
            UE : 
          </label>
          <select className="select-revision" value={ue} onChange={(e) => setUe(e.target.value)} disabled={!filiere}>
            <option value="">Choisir une UE</option>
            {
            filiere && ues[filiere]?.map((u, index) => (
              // on affiche les bonnes options
            //filiere && ues[filiere]?.map((u, index) => (
            //<option key={index} value={u}>{u.nom}</option>
              <option key={index} value={u}>{u}</option>
            ))}
          </select>
        

          <label className="label-revision">
            Matière:
          </label>
          <select className="select-revision" value={matiere} onChange={(e) => setMatiere(e.target.value)} disabled={!ue}>
            <option value="">Choisir une matière</option>
            {
            ue && matieresOptions.map((m, index) => (
              // les bonnes possiblités
            // ue && matieresOptions.map((m, index) => (
              <option key={index} value={m}>{m}</option>
              //<option key={index} value={m}>{m.nom}</option>
            ))}
          </select>
          
          <label className="label-revision">
            Nombre de questions:
          </label>
          <input className="input-revision" type="number" value={nombreQuestions} onChange={(e) => setNombreQuestions(e.target.value)} min="1" />
        </div>

        <div>
          <button className="button-revision" type="button" onClick={handleStartExamen}>Examen</button>
          <button className="button-revision" type="button" onClick={handleStartRevision}>Réviser la matière</button>
          <button className="button-revision" type="button" onClick={handleStartRevisionNouvelles}>Réviser de nouvelles questions</button>
        </div>
      </form>
    </div>
  );
}

export default Revision;