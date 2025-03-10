import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Accueil from './pages/Accueil';
import Menu from './pages/Menu';
import Profil from './pages/Profil';
import CreationQuestion from './pages/CreationQuestion';
import Revision from './pages/Revision';
import Question from './pages/Question';
import QuestionExamen from './pages/QuestionExamen';
import Connexion from './pages/Connexion';
import Creation from './pages/Creation';
import QuestionList from './pages/QuestionList';
import QuestionDetails from './pages/QuestionDetails';
import QuestionApprouveesList from './pages/QuestionApprouveesList';
import QuestionApprouveesDetails from './pages/QuestionApprouveesDetails';
import QuestionNopeList from './pages/QuestionNopeList';
import QuestionNopeDetails from './pages/QuestionNopeDetails';
import UtilisateurList from './pages/UtilisateurList';
import UtilisateurDetails from './pages/UtilisateurDetails';
import Navbar from './components/Navbar';
import { AuthProvider, AuthContext } from './components/AuthContext';
import PrivateRoute from './components/PrivateRoute';
import PrivateRouteAdmin from './components/PrivateRouteAdmin';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <AuthContext.Consumer>
          {({ isAuthenticated }) => (
            <Routes>
              <Route
                path="/"
                element={  // on fais en sorte que le lien ne soit pas accessible si l'utilisateur est connecté
                  !isAuthenticated ? (
                    <Accueil />
                  ) : (
                    <Navigate replace to={"/menu"} />
                  )
                }
              />
              <Route
                path="/connexion"
                element={
                  !isAuthenticated ? (
                    <Connexion />
                  ) : (
                    <Navigate replace to={"/menu"} />
                  )
                }
              />
              <Route
                path="/creation"
                element={
                  !isAuthenticated ? (
                    <Creation />
                  ) : (
                    <Navigate replace to={"/menu"} />
                  )
                }
              />

              <Route
                path="/menu"
                element={ // les privateroute sont des liens accessible uniquement si l'utilisateur est connecté
                  <PrivateRoute>
                    <Menu />
                  </PrivateRoute>
                }
              />
              <Route
                path="/profil"
                element={
                  <PrivateRoute>
                    <Profil />
                  </PrivateRoute>
                }
              />
              <Route
                path="/creation-question"
                element={
                  <PrivateRoute>
                    <CreationQuestion />
                  </PrivateRoute>
                }
              />
              <Route
                path="/revision"
                element={
                  <PrivateRoute>
                    <Revision />
                  </PrivateRoute>
                }
              />
              <Route
                path="/question"
                element={
                  <PrivateRoute>
                    <Question />
                  </PrivateRoute>
                }
              />

              <Route
                path="/questionexamen"
                element={
                  <PrivateRoute>
                    <QuestionExamen />
                  </PrivateRoute>
                }
              />

              <Route
                path="/questionredolist"
                element={ // Les privateroutadmin sont accessible uniquement si l'utilisateur est un admin
                  <PrivateRouteAdmin>
                    <QuestionNopeList />
                  </PrivateRouteAdmin>
                }
              />
            
              <Route path="/questionredodetails" 
                element={
                <PrivateRouteAdmin>
                    <QuestionNopeDetails />
                </PrivateRouteAdmin>} 
              />

              

              <Route
                path="/questionlist"
                element={
                  <PrivateRouteAdmin>
                    <QuestionList />
                  </PrivateRouteAdmin>
                }
              />
            
              <Route path="/questiondetails" 
                element={
                <PrivateRouteAdmin>
                    <QuestionDetails />
                </PrivateRouteAdmin>} 
              />

              <Route
                path="/questionapprouveeslist"
                element={
                  <PrivateRouteAdmin>
                    <QuestionApprouveesList />
                  </PrivateRouteAdmin>
                }
              />
              <Route
                path="/questionapprouveesdetails"
                element={
                  <PrivateRouteAdmin>
                    <QuestionApprouveesDetails />
                  </PrivateRouteAdmin>
                }
              />

              <Route
                path="/utilisateurlist"
                element={
                  <PrivateRouteAdmin>
                    <UtilisateurList />
                  </PrivateRouteAdmin>
                }
              />
              <Route
                path="/utilisateurdetails"
                element={
                  <PrivateRouteAdmin>
                    <UtilisateurDetails />
                  </PrivateRouteAdmin>
                }
              />


              {/* Redirige si le lien n'existe pas */}
              <Route
                path="*"
                element={
                  !isAuthenticated ? (
                    <Navigate replace to={"/"} />
                  ) : (
                    <Navigate replace to={"/menu"} />
                  )
                }
              />
            </Routes>
          )}
        </AuthContext.Consumer>
      </Router>
    </AuthProvider>
  );
}

export default App;