import React, { createContext, useState, useEffect } from 'react';

const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(localStorage.getItem("isAuthenticated") || false);
  const [idUser, setIdUser] = useState(localStorage.getItem("idUser") || null);
  
  const [isAdmin, setIsAdmin] = useState(true); ////////////////////////////// à changer comme le idUser avec le localstorage.getitem
  // const [isAdmin, setIsAdmin] = useState(localStorage.getItem("isAdmin") || null);
  const [isRootAdmin, setIsRootAdmin] = useState(true);
  // const [isRootAdmin, setIsRootAdmin] = useState(localStorage.getItem("isRootAdmin") || null);


  const login = (id) => {    //const login = (id, role) => { quand on fera le login correctement
    setIsAuthenticated(true);
    localStorage.setItem('isAuthenticated', true);
    setIdUser(id);
    localStorage.setItem('idUser', id);

    // set les variables si admin ou rootadmin

    //if(role == "Admin" || role == "RootAdmin") 
    //{setIsAdmin(true);
    //localStorage.setItem('isAdmin', true);}
    // if(role == "RootAdmin"){setIsRootAdmin(true);
    // localStorage.setItem('isRootAdmin', true ou false jsp)};
    //else{localStorage.setItem('isAdmin', false);
    //localStorage.setItem('isRootAdmin', false);}
  };

  const logout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem('isAuthenticated');
    setIdUser(null);
    localStorage.removeItem('idUser');

    // set les variables si admin ou rootadmin

    //setIsAdmin(false);
    //localStorage.removeItem('isAdmin');
    //setIsRootAdmin(false);
    //localStorage.removeItem('isRootAdmin');
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isAdmin, idUser, setIdUser, setIsAdmin, isRootAdmin, setIsAdmin, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthContext, AuthProvider };