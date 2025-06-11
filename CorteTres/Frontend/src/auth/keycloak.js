// src/auth/keycloak.js


import Keycloak from "keycloak-js";
const keycloak = new Keycloak({
    url: 'http://localhost:8080/',
    realm: 'sistema',
    clientId: 'sistema-desktop'
});

export default keycloak;
