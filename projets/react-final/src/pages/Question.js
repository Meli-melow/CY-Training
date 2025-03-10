import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Question.css';

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
  const { nombreQuestions, filiere, matiere, revision } = location.state || {};
  //const { nombreQuestions, revision } = location.state || {}; ca dépend comment c'est géré dans le backend

  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [showCorrection, setShowCorrection] = useState(false);
  const [showIndice, setShowIndice] = useState(false);
  const [isLast, setIsLast] = useState(false);
  const [answerClasses, setAnswerClasses] = useState([]);
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

  const handleAnswer = () => {
    setShowCorrection(true); // affiche la correction
    if (questions.length === 1 || currentQuestionIndex === questions.length - 1) {
      setIsLast(true);
    }

    // gère les classes des réponses pour changer la couleur
    const newAnswerClasses = currentQuestion.options.map((option) => {
      if (currentQuestion.answer.includes(option)) {
        return '.question-no-hover question-correct-answer';
      } else if (selectedAnswers.includes(option)) {
        return '.question-no-hover question-wrong-answer';
      } else {
        return 'question-no-hover';
      }
    });
    setAnswerClasses(newAnswerClasses);
  };

  const handleNextQuestion = () => {
    if (!isLast) { // on passe à la question suivante s'il y en a une
      setCurrentQuestionIndex(currentQuestionIndex + 1);
      setSelectedAnswers([]);
      setShowCorrection(false);
      setShowIndice(false);
      setAnswerClasses([]);
    } else {
      navigate('/menu');
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

  const isCorrectAnswer = selectedAnswers.sort().toString() === currentQuestion.answer.sort().toString();

  return (
    <div className="question-container">
      <h2>Question {currentQuestionIndex + 1} sur {questions.length}</h2>
      <div className="question-div">
        <p className="question-p">{currentQuestion.question}</p>
        <ul className="ul-question">
          {currentQuestion.options.map((option, index) => (
            <li className={`li-question ${showCorrection ? answerClasses[index] : ''}`} key={index}>
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
        {showCorrection && (
          <div className="question-correction">
            <p>{isCorrectAnswer ? 'Bonne réponse!' : 'Mauvaise réponse!'}</p>
            <p className="question-p">Correction: {currentQuestion.correction}</p>
          </div>
        )}
        {currentQuestion.indice && 
          <button className={showIndice ? "button-question question-no-hover" : "button-question"} onClick={handleShowIndice} disabled={showIndice}>
            {showIndice ? currentQuestion.indice : 'Voir Indice'}
          </button>
        }
        <button className="button-question" onClick={handleAnswer} hidden={showCorrection}>Voir Correction</button>
        {showCorrection && (
          <button className="button-question" onClick={handleNextQuestion}>{!isLast ? "Question Suivante" : "Finir la Révision"}</button>
        )}
      </div>
    </div>
  );
}

export default Question;