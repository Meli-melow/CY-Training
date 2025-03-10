import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { AuthContext } from './AuthContext';

const PrivateRouteAdmin = ({ children }) => {
  const { isAuthenticated, isAdmin } = useContext(AuthContext);

  return isAuthenticated && isAdmin ? children : <Navigate to="/" />;
};

export default PrivateRouteAdmin;
