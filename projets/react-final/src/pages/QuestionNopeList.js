import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../components/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './QuestionList.css';

const questions = [
  {
    id: 1,
    ue: 'Mathématiques',
    matiere: 'Algèbre',
    utilisateur: 'John Doe',
    dateCreation: '2023-07-10',
    question: 'Quelle est la solution de l\'équation x^2 - 4 = 0?'
  },
  {
    id: 2,
    ue: 'Physique',
    matiere: 'Mécanique',
    utilisateur: 'Jane Smith',
    dateCreation: '2023-07-11',
    question: 'Expliquez la deuxième loi de Newton.'
  },
  {
    id: 3,
    ue: 'Physique',
    matiere: 'Mécanique',
    utilisateur: 'Jane Smith',
    dateCreation: '2023-07-11',
    question: 'Expliquez la deuxième loi de mais de manière à ne pas omettre une étape de la démonstration associé, vous choisirez la démonstration de votre choix tout en expliquant ce choix de manière concise et détaillée, mais surtout, ne faites pas trop long, pas comme cette question que je fais plus long dans le but de i know i know i know iknow.'
  },
  {
    id: 4,
    ue: 'Physique',
    matiere: 'Mécanique',
    utilisateur: 'Jane Smith',
    dateCreation: '2023-07-11',
    question: 'Expliquez la deuxième loi de mais de manière à ne pas omettre une étape de la démonstration associé, vous choisirez la démonstration de votre choix tout en expliquant ce choix de manière concise et détaillée, mais surtout, ne faites pas trop long, pas comme cette question que je fais plus long dans le but de i know i know i know iknow.'
  },
  {
    id: 5,
    ue: 'Physique',
    matiere: 'Méczzanique',
    utilisateur: 'Jane Smith',
    dateCreation: '2023-07-11',
    question: 'Expliquez la deuxième loi de mais de manière à ne pas omettre une étape de la démonstration associé, vous choisirez la démonstration de votre choix tout en expliquant ce choix de manière concise et détaillée, mais surtout, ne faites pas trop long, pas comme cette question que je fais plus long dans le but de i know i know i know iknow.'
  }
];

function truncateText(text, maxLength) {
  if(!text)
    return;
  else if (text.length <= maxLength) {
    return text;
  }
  return text.substring(0, maxLength) + '...';
}

function QuestionList() {
  const [data, setData] = useState([]);
  const [selectedUe, setSelectedUe] = useState('');
  const [selectedMatiere, setSelectedMatiere] = useState('');
  const navigate = useNavigate();
  const { idUser } = useContext(AuthContext);

  const handleViewDetails = (id) => {
    navigate('/questionredodetails', { state: { idQuestion: id } });
  };

  const handleUeChange = (e) => {
    setSelectedUe(e.target.value);
    setSelectedMatiere('');
  };

  useEffect(() => {
    async function GetData() {
      var response = await axios.get(`http://localhost:8080/api/cyusers/voir_questions_creees/`+idUser);
      setData(response.data)
    }
    GetData()
  }, [])

  // filtre les questions que l'on veut utiliser
/*  const filteredQuestions = questions.filter(q => 
    (selectedUe === '' || q.ue === selectedUe) &&
    (selectedMatiere === '' || q.matiere === selectedMatiere)
  );*/

  // On filtre les questions que l'on veut utiliser
  const filteredQuestions = data.filter(q => 
    (selectedUe === '' || q.ue === selectedUe) &&
    (selectedMatiere === '' || q.matiere === selectedMatiere) &&
    (q.etatValidation === "Refusée")
  );

  // filtre les ues
  //const ues = [...new Set(data.map(q => q.matiere.ue))];
  const ues = [...new Set(data)];
  /*const matieres = selectedUe 
    ? [...new Set(selectedUe.matieres.map(m => m))]
    : [...new Set(data.map(q => q.matiere))];
  */const matieres = [...new Set(data)];

  

  return (
    <div className="question-list-container">
      <h2>Approuver une Question</h2>
      <div className="question-list-filters">
        <select 
          value={selectedUe}
          onChange={handleUeChange}
        >
          <option value="">Tout UE</option>
          {ues.map((ue, index) => (
            <option key={index} value={ue}>{ue.nom}</option>
          ))}
        </select>
        <select 
          value={selectedMatiere}
          onChange={(e) => setSelectedMatiere(e.target.value)}
        >
          <option value="">Toutes les matières</option>
          {matieres.map((matiere, index) => (
            <option key={index} value={matiere}>{matiere.nom}</option>
          ))}
        </select>
      </div>
      <ul className="question-list">
        {filteredQuestions.map((q) => (
          <li key={q.id} className="question-item">
            <div className="question-details">
              <h3>{truncateText(q.question, 90)}</h3>
              <p><strong>UE:</strong> {truncateText(q.ue, 20)}</p>
              <p><strong>Matière:</strong> {truncateText(q.matiere, 20)}</p>
            </div>
            <div className="question-metadata">
              <p><strong>Correction:</strong> {truncateText(q.correction, 20)}</p>
              <p><strong> Date de demande:</strong> {q.dateDemandeAjout}</p>
            </div>
            <button type="button" onClick={() => handleViewDetails(q.id)}>Voir les détails</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default QuestionList;
