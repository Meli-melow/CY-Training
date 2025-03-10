import React, { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/AuthContext';
import './CreationQuestion.css';

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

function CreationQuestion() {
  const [data, setData] = useState([]);
  const [filiere, setFiliere] = useState('');
  const [ue, setUe] = useState('');
  const [matiere, setMatiere] = useState('');
  const [matieresOptions, setMatieresOptions] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [question, setQuestion] = useState('');
  const [options, setOptions] = useState(['', '', '', '']);
  const [correction, setCorrection] = useState('');
  const [indice, setIndice] = useState('');

  const navigate = useNavigate();
  const { idUser } = useContext(AuthContext);

  // on Get les filieres, les UE et les matieres
  /*useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/filiere/all`);
      setData(response.data)
    }
    GetData()
  }, [])
  */

  useEffect(() => {
    if (ue) { // on met comme options les matières correspondants à l'ue choisi
      setMatieresOptions(ue.matieres || []); 
    } else {
      setMatieresOptions([]);
    }
    setMatiere('');
  }, [ue]);


  const handleOptionChange = (index, value) => {
    const newOptions = [...options];
    newOptions[index] = value;
    setOptions(newOptions);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    // Réinitialiser le message d'erreur
    setErrorMessage('');
  
    // Vérification des champs obligatoires
    if (!filiere || !ue || !matiere || !question) {
      setErrorMessage('Tous les champs doivent être remplis.');
      return;
    }
  
    // Vérification que toutes les réponses ont été remplies
    const emptyOptions = options.filter(option => option.trim() === '').length;
    if (emptyOptions > 0) {
      setErrorMessage('Toutes les réponses doivent être remplies.');
      return;
    }
  
    // Vérification qu'il y a au moins une bonne réponse
    let correctAnswerCount = 0;
    let idxBonneRep = [];
    for (let i = 0; i < 4; i++) {
      const radioTrue = document.getElementById(`true${i}`);
      if (radioTrue && radioTrue.checked) {
        correctAnswerCount++;
        idxBonneRep = [...idxBonneRep, i];
        console.log(idxBonneRep);  
      }
    }
  
    if (correctAnswerCount === 0) {
      setErrorMessage('Une réponse correcte doit être sélectionnée.');
      return;
    }
    if (question.length < 5) {
      setErrorMessage('La question doit dépasser les 5 caractères. Vous êtes à '+question.length);
      return;
    }
    if (correction.length < 150) {
      setErrorMessage('La correction doit dépasser les 150 caractères. Vous êtes à '+correction.length);
      return;
    }

    try {
      // Envoyer les données au backend pour créer la question
      const response = await axios.patch('http://localhost:8080/api/cyusers/nouvelle_question/'+idUser, {
        "question" : question,
        "correction" : correction,
        "reponses" : options,
        "indBonneRep" : idxBonneRep,
        "indice" : indice,
        //"matiere" : matiere
    });
      
      console.log('Returned data:', response);
    } catch (e) {
      console.log(`Axios request failed: ${e}`);
    }
  
    navigate('/menu', { state: { message: 'Question créée avec succès!' } });
  };

  return (
    <div className="creation-question-container">
      <h2>Créer une Question</h2>
      <form className="form-creation-question" onSubmit={handleSubmit}>
        <div>
          <label className="label-creation-question" htmlFor="filiere">
            Filière
          </label>
          <select className="select-creation-question" name="filiere" value={filiere} onChange={(e) => setFiliere(e.target.value)}>
            <option value="">Choisir une filière</option>
            {
            /*data.map((f, index) => (
                <option key={index} value={f}>{f.nom}</option>
              ))*/
            filieres.map((f, index) => (
              <option key={index} value={f}>{f}</option>
            ))
            
            }
          </select>
        
          <label className="label-creation-question">
            UE
          </label>
          <select className="select-creation-question" value={ue} onChange={(e) => setUe(e.target.value)} disabled={!filiere}>
            <option value="">Choisir une UE</option>
            {
            filiere && ues[filiere]?.map((u, index) => (
             // filiere && filiere.ues?.map((u, index) => (
              <option key={index} value={u}>{u}</option>
              //<option key={index} value={u}>{u.nom}</option>
            ))}
          </select>
        

          <label className="label-creation-question">
            Matière
          </label>
          <select className="select-creation-question" value={matiere} onChange={(e) => setMatiere(e.target.value)} disabled={!ue}>
            <option value="">Choisir une matière</option>
            {//ue && matieresOptions.map((m, index) => (
              ue && matieres[ue]?.map((m, index) => (
              <option key={index} value={m}>{m}</option>
              //<option key={index} value={m}>{m.nom}</option>
            ))}
          </select>
        </div>
        <div className="titre-field-creation">
          <label htmlFor="question">Question</label>
        </div>
        <div className="reponses-field-creation">
          <textarea
            name="question"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            
          ></textarea>
        </div>
        <div className="titre-field-creation">
          <label>Réponses possibles</label>
        </div>
          {options.map((option, index) => (
            <div className="reponses-field-creation" key={index + "div"}>  
              <textarea rows="2" cols="100" 
                key={"reponse" + index}
                name={"reponse" + index}
                value={option}
                onChange={(e) => handleOptionChange(index, e.target.value)}
                
              />
              <div>
                <input type="radio" id={"false" + index} name={"reponse" + index} />
                <label className="creation-reponse creation-fausse" htmlFor={"false" + index}>
                  ✘
                </label>

                <input type="radio" id={"true" + index} name={"reponse" + index} />
                <label className="creation-reponse creation-bonne" htmlFor={"true" + index}>
                  ✔
                </label>
              </div>
            </div>
          ))}
        
        <div className="titre-field-creation">
          <label htmlFor="correction">Correction détaillée</label>
        </div>
        <div className="reponses-field-creation">
          <textarea rows="4" cols="300" 
            name="correction"
            value={correction}
            minLength="150"
            onChange={(e) => setCorrection(e.target.value)}
            required
          ></textarea>
        </div>
        
        <div className="titre-field-creation">
          <label htmlFor="indice">Indice (optionnel)</label>
        </div>
        <div className="reponses-field-creation">
          <textarea
            name="indice"
            value={indice}
            onChange={(e) => setIndice(e.target.value)}
          ></textarea>
        </div>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <button type="submit">Créer la Question</button>
      </form>
    </div>
  );
}

export default CreationQuestion;
