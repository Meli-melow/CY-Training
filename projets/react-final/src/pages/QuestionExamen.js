import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Question.css';
import './QuestionExamen.css';

const allQuestions = [
  {
    filiere: 'Filière A',
    matiere: 'Mathématiques',
    question: 'Quelle est la dérivée de x² ?',
    options: ['2x', 'x²', '2', 'x'],
    answer: ['2x', 'x²'],
    correction: 'La dérivée de x² est 2x.',
    indice: 'Pensez à la règle de dérivation de la puissance.'
  },
  {
    filiere: 'Filière A',
    matiere: 'Mathématiques',
    question: 'Quel est l\'angle d\'un triangle équilatéral ?',
    options: ['60°', '90°', '45°', '120°'],
    answer: ['60°'],
    correction: 'Chaque angle d\'un triangle équilatéral mesure 60°.',
    indice: 'Tous les angles sont égaux dans un triangle équilatéral.'
  },
  {
    filiere: 'Filière A',
    matiere: 'Mathématiques',
    question: 'Quelle est la solution de l\'équation x + 5 = 12 ?',
    options: ['7', '17', '12', '5'],
    answer: ['7'],
    correction: 'En isolant x, on obtient x = 12 - 5 = 7.',
    indice: 'Soustrayez 5 des deux côtés de l\'équation.'
  }
];

function Question() {
  const location = useLocation();
  const { filiere, matiere, nombreQuestions } = location.state || {};
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [showCorrection, setShowCorrection] = useState(false);
  const [showIndice, setShowIndice] = useState(false);
  const [userAnswers, setUserAnswers] = useState([]);
  const [showSummary, setShowSummary] = useState(false);
  const [correctCount, setCorrectCount] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    //setQuestions(revision) ////// il n'y pas besoin du reste du useEffect
    if (filiere && matiere) {
      const filteredQuestions = allQuestions.filter(
        (q) => q.filiere === filiere && q.matiere === matiere
      );
      setQuestions(filteredQuestions.slice(0, Math.min(filteredQuestions.length, nombreQuestions)));
    }
  }, [filiere, matiere, nombreQuestions]);


  const handleNextQuestion = () => {

    const isCorrectAnswer = selectedAnswers.sort().toString() === currentQuestion.answer.sort().toString();

    // Ajouter la réponse de l'utilisateur au tableau des réponses
    setUserAnswers((prev) => [
      ...prev,
      {
        question: currentQuestion.question,
        selected: selectedAnswers,
        correct: currentQuestion.answer,
        isCorrect: isCorrectAnswer,
        correction: currentQuestion.correction
      }
    ]);

    // Incrémenter le compteur si la réponse est correcte
    if (isCorrectAnswer) {
      setCorrectCount(prevCount => prevCount + 1);
    }

    if (questions.length === 1 || currentQuestionIndex === questions.length - 1) {      
      // Afficher le résumé des réponses
      setShowSummary(true);
    }
    else{ // avancer dans les questions
      setCurrentQuestionIndex(currentQuestionIndex + 1);
      setSelectedAnswers([]);
      setShowCorrection(false);
      setShowIndice(false);
    }
  };

  const handleCheckboxChange = (option) => {
    if (selectedAnswers.includes(option)) {
      setSelectedAnswers(selectedAnswers.filter(answer => answer !== option));
    } else {
      setSelectedAnswers([...selectedAnswers, option]);
    }
  };

  const handleShowIndice = () => {
    setShowIndice(true);
  };

  if (questions.length === 0) {
    return <div className="question-container">Aucune question disponible pour cette sélection.</div>;
  }

  const currentQuestion = questions[currentQuestionIndex];

  if (!currentQuestion) {
    return <div className="question-container">Aucune question disponible pour cette sélection.</div>;
  }

  return (
    <div className="question-container">
      {!showSummary ? (
        <>
          <h2>Question {currentQuestionIndex + 1} sur {questions.length}</h2>
          <div className="question-div">
            <p className="question-p">{currentQuestion.question}</p>
            <ul className="ul-question">
              {currentQuestion.options.map((option, index) => (
                <li className={`li-question`} key={index}>
                  <input
                    className="input-question"
                    type="checkbox"
                    id={`option-${index}`}
                    name="answer"
                    value={option}
                    checked={selectedAnswers.includes(option)}
                    onChange={() => handleCheckboxChange(option)}
                    disabled={showCorrection}
                  />
                  <label htmlFor={`option-${index}`}>{option}</label>
                </li>
              ))}
            </ul>
            {currentQuestion.indice && 
              <button className={showIndice ? "button-question question-no-hover" : "button-question"} onClick={handleShowIndice} disabled={showIndice}>
                {showIndice ? currentQuestion.indice : 'Voir Indice'}
              </button>
            }
            
            <button className="button-question" onClick={handleNextQuestion}>
              {!(questions.length === 1 || currentQuestionIndex === questions.length - 1) ? "Question Suivante" : "Voir Bilan"}
            </button>
          </div>
        </>
      ) : (
        <div className="summary-container">
          <h2>Bilan de l'examen</h2>
          <ul className="summary-list">
            {userAnswers.map((answer, index) => (
              <li key={index} className="summary-item">
                <p className="question-p"><strong>Question {index + 1}:</strong> {answer.question}</p>

                  <p className="question-p question-p-reponses"><strong>Votre réponse:</strong></p>
                  <ul className="ul-summary-reponses">
                    {answer.selected.map((selectedAnswer, idx) => (
                      <li
                        className="li-summary-reponses"
                        key={idx}
                        style={{ color: answer.correct.includes(selectedAnswer) ? 'green' : 'red' }}
                      >
                        • {selectedAnswer}
                      </li>
                    ))}
                  </ul>

                <p className="question-p"><strong>Bonne réponse:</strong> {answer.correct.join(', ')}</p>
                <p className="question-p"><strong>Correction:</strong> {answer.correction}</p>
              </li>
            ))}
          </ul>
          <h3 className="summary-result">Résultat: {correctCount} / {questions.length} bonnes réponses</h3>
          <button className="button-question" onClick={() => navigate('/menu')}>Retour au Menu</button>
        </div>
      )}
    </div>
  );
}

export default Question;
