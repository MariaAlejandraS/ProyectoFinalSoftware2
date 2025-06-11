// src/main.jsx

import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './auth/keycloak';

ReactDOM.createRoot(document.getElementById('root')).render(
  //<React.StrictMode>
    <ReactKeycloakProvider authClient={keycloak} initOptions={{ onLoad: 'login-required' }}>
      <App />
    </ReactKeycloakProvider>
  //</React.StrictMode>
);
