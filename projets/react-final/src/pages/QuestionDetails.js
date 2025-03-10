import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import './QuestionDetails.css';

const questionDetails = {
  id: 1,
  matiere: 'Mathématiques',
  utilisateur: 'John Doe',
  dateCreation: '2023-07-10',
  question: 'Quelle est la solution de l\'équation x^2 - 4 = 0?',
  reponses: ['x = 2', 'x = -2', 'x = 0', 'Aucune de ces réponses'],
  correction: 'x = 2',
  indice: 'Pensez aux racines carrées'
};

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

function QuestionDetail() {
  const navigate = useNavigate();
  const location = useLocation();
  const idQuestion = location.state?.idQuestion;

  const [data, setData] = useState([]);
  
  const [filiere, setFiliere] = useState(data.filiere);
  const [ue, setUe] = useState(data.ue);
  const [matiere, setMatiere] = useState(data.matiere);
  const [idxBonneRep, setIdxBonneRep] = useState(data.indBonneRep);
  const [matieresOptions, setMatieresOptions] = useState([]);
  const [question, setQuestion] = useState(data.question);
  const [reponses, setReponses] = useState(data.reponses);
  const [correction, setCorrection] = useState(data.correction);
  const [indice, setIndice] = useState(data.indice);
  
  const [errorMessage, setErrorMessage] = useState('');

  
  // on Get les filieres, les UE et les matieres
  /*useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/filiere/all`);
      setDataFiliere(response.data)
    }
    GetData()
  }, [])
  */

  useEffect(() => { // on récupère les données de la question de l'api
    try {async function GetData() {
        var response = await axios.get(`http://localhost:8080/api/questions/voir/${idQuestion}`);
        setData(response.data);
        setReponses(data.reponses);
      }
      GetData() 
    }
    catch(error) {
      //try to fix the error or
      //notify the users about somenthing went wrong
      console.log(error.message)
    }
  }, []);

  useEffect(() => { // on met à jour correctement les states
    setReponses(data.reponses);
    setQuestion(data.question);
    setCorrection(data.correction);
    setUe(data.ue);
    setMatiere(data.matiere);
    setFiliere(data.filiere);
    setIndice(data.indice);
    setIdxBonneRep(data.indBonneRep);
  }, [data]);

  //On change les matières si on change l'ue
  /*useEffect(() => {
    if (ue) {
      setMatieresOptions(ue.matieres] || []);
    } else {
      setMatieresOptions([]);
    }
    setMatiere('');
  }, [ue]);
  */
  
  const handleInputChange = (index, value) => {
    const newReponses = [...reponses];
    newReponses[index] = value;
    setReponses(newReponses);
  };

  const handleApprove = async () => {
    
     // Réinitialiser le message d'erreur
     setErrorMessage('');
     console.log(idxBonneRep)
   
     // Vérification des champs obligatoires
     /*if (!filiere || !ue || !matiere || !question) {
       setErrorMessage('Tous les champs doivent être remplis.');
       return;
     }*/
   
     // Vérification que toutes les réponses ont été remplies
     const emptyOptions = reponses.filter(option => option.trim() === '').length;
     if (emptyOptions > 0) {
       setErrorMessage('Toutes les réponses doivent être remplies.');
       return;
     }
   
     // Vérification qu'il y a au moins une bonne réponse sélectionnée
     let correctAnswerCount = 0;
     let idxBonneRepEdit = [];
     for (let i = 0; i < reponses.length; i++) {
       const radioTrue = document.getElementById(`${i}B`);
       if (radioTrue && radioTrue.checked) {
         correctAnswerCount++;
         idxBonneRepEdit = [...idxBonneRepEdit, i];  
       }
     }
     if (correctAnswerCount === 0) {
       setErrorMessage('Une réponse correcte doit être sélectionnée.');
       return;
     }
     if(correction.length < 150) {
       setErrorMessage('La correction doit dépasser les 150 caractères. Vous êtes à '+correction.length);
       return;
     }
   
    // Si le formulaire est correcte, on modifie et valide la question
    try {     /// il manque la filiere, ue et matiere////////
      async function GetData() {
        var response = await axios.patch('http://localhost:8080/api/questions/modifier/'+idQuestion,
          {
            "question" : question,
            "correction" : correction,
            "reponses" : reponses,
            "indBonneRep" : idxBonneRepEdit,
            "indice" : indice,
            "matiere" : matiere
          });
        var response = await axios.patch('http://localhost:8080/api/questions/valider_question/'+idQuestion);
        setData(response.data)
      }
      GetData() 
      alert('Question approuvée');
    }
    catch(error) {
      //try to fix the error or
      //notify the users about somenthing went wrong
      console.log(error.message)
    }

    navigate("/menu");
    
  };

  const handleReject = async () => {
    
    try {
      async function GetData() {
        var response = await axios.patch('http://localhost:8080/api/questions/refuser_question/'+idQuestion);
        setData(response.data)
      }
      GetData() 
      alert('Question refusée');
      navigate("/questionlist");
    }
    catch(error) {
      //try to fix the error or
      //notify the users about somenthing went wrong
      console.log(error.message)
    }
  };

  return (
    <div className="question-details-container">
      <h2>Détail de la Question</h2>
      <form>
      <div>
          <label className="label-question-details" htmlFor="filiere">
            Filière
          </label>
          <select className="select-question-details" name="filiere" defaultValue={filiere} onChange={(e) => setFiliere(e.target.value)}>
            <option value="">Choisir une filière</option>
            {
              /*dataFiliere.map((f, index) => (// afficher les bonnes options
                <option key={index} value={f}>{f.nom}</option>
              ))*/
              filieres.map((f, index) => (
                <option key={index} value={f}>{f}</option>
              ))
            }
          </select>
        
          <label className="label-question-details">
            UE
          </label>
          <select className="select-question-details" defaultValue={ue} onChange={(e) => setUe(e.target.value)} disabled={!filiere}>
            <option value="">Choisir une UE</option>
            {
              filiere && ues[filiere]?.map((u, index) => (// afficher les bonnes options
             // filiere && filiere.ues?.map((u, index) => (
              <option key={index} value={u}>{u}</option>
              //<option key={index} value={u}>{u.nom}</option>
            ))}
          </select>
        

          <label className="label-question-details">
            Matière
          </label>
          <select className="select-question-details" defaultValue={matiere} onChange={(e) => setMatiere(e.target.value)} disabled={!ue}>
            <option value="">Choisir une matière</option>
            {ue && matieresOptions.map((m, index) => (
              <option key={index} value={m}>{m}</option>// afficher les bonnes options
              //<option key={index} value={m}>{m.nom}</option>
            ))}
          </select>
        </div>
        <label>
          <div className="titre-question-details">
            Question
          </div>
          <div className="reponses-field-question-details">
            <textarea defaultValue={data.question} onChange={(e) => setQuestion(e.target.value)} />
          </div>
        </label>
        <label>
          <div className="titre-question-details">
            Réponses
          </div>
           {data.reponses && data.reponses.map((reponse, index) => (
            <div className="reponses-field-question-details">  
              <textarea rows ="2" cols="100" 
                key={index}
                defaultValue={reponse}
                onChange={(e) => handleInputChange(index, e.target.value)}
                required
              ></textarea>
              <div>
                <input type="radio" id={index} name={index} defaultChecked={!data.indBonneRep.includes(index)/* On regarde si la réponse est mauvaise */} required/>
                <label className="question-details-reponse question-details-fausse" htmlFor={index}>
                  ✘
                </label>

                <input type="radio" id={index + "B"} name={index} defaultChecked={data.indBonneRep.includes(index)/* On regarde si la réponse est bonne */} />
                <label className="question-details-reponse question-details-bonne" htmlFor={index + "B"}>
                ✔ 
                </label>
                {/*https://textkool.com/fr/symbols/tick-check-mark-symbols?utm_content=cmp-true*/}
              </div>
              
            </div>
          ))}
        </label>
        <label>
          <div className="titre-question-details">
            Correction
          </div>
          <div className="reponses-field-question-details">
            <textarea defaultValue={data.correction} onChange={(e) => setCorrection(e.target.value)} />
          </div>
        </label>
        <label>
          <div className="titre-question-details">
            Indice
          </div>
          <div className="reponses-field-question-details">
            <textarea defaultValue={data.indice} onChange={(e) => setIndice(e.target.value)} />
          </div>
        </label>
        <p>Proposé par {data.createur && data.createur.nom + ' ' + data.createur.prenom} il y a {data.tempsAttente}</p>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <div className="buttons">
          <input className="refuser" type="button" value="Refuser" onClick={handleReject}/>
          <input className="approuver" type="button" value="Approuver" onClick={handleApprove}/>
        </div>
      </form>
    </div>
  );
}

export default QuestionDetail;
