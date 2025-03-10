import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './QuestionList.css';


function truncateText(text, maxLength) {
  if (text.length <= maxLength) {
    return text;
  }
  return text.substring(0, maxLength) + '...';
}

function QuestionList() {
  const [data, setData] = useState([]);
  const [selectedUe, setSelectedUe] = useState('');
  const [selectedMatiere, setSelectedMatiere] = useState('');
  const navigate = useNavigate();

  const handleViewDetails = (id) => {
    navigate('/questionapprouveesdetails', { state: { idQuestion: id } });
  };

  const handleueChange = (e) => {
    setSelectedUe(e.target.value);
    setSelectedMatiere('');
  };

  // Get les questions validées
  useEffect(() => {
    async function GetData() {
      try {
        var response = await axios.get(`http://localhost:8080/api/questions/all_validees`);
      } catch (e) {
        console.log(`Axios request failed: ${e}`);
      }
      
      setData(response.data)
    }
    GetData()
  }, [])

  const filteredQuestions = data.filter(q => 
    (selectedUe === '' || q.ue === selectedUe) &&
    (selectedMatiere === '' || q.matiere === selectedMatiere)
  );

  // on configure les ues et matieres selectionnables
  //const ues = [...new Set(data.map(q => q.matiere.ue))];
  const ues = [...new Set(data)];
  /*const matieres = selectedUe 
    ? [...new Set(selectedUe.matieres.map(m => m))]
    : [...new Set(data.map(q => q.matiere))];
  */const matieres = [...new Set(data)];

  return (
    <div className="question-list-container">
      <h2>Supprimer une Question</h2>
      <div className="question-list-filters">
        <select 
          value={selectedUe}
          onChange={handleueChange}
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
              <h3>{q.question && truncateText(q.question, 90)}</h3>
              <p><strong>UE:</strong> {q.ue &&  truncateText(q.ue, 20)}</p>
              <p><strong>Matière:</strong> {q.matiere && truncateText(q.matiere, 20)}</p>
            </div>
            <div className="question-metadata">
              <p><strong>Correction:</strong> {truncateText(q.correction, 20)}</p>
              <p><strong>Date d'approbation:</strong> {q.dateValidee}</p>
            </div>
            <button type="button" onClick={() => handleViewDetails(q.id)}>Voir les détails</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default QuestionList;
