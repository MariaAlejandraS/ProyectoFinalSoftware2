import { useEffect, useState } from "react";
import { useKeycloak } from "@react-keycloak/web";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import EmpresaProyectoPage from './pages/EmpresaProyectoPage';
import EstudiantePage from './pages/EstudiantePage';
import CoordinadorProyectoPage from './pages/CoordinadorProyectoPage';
import RegistrarEmpresa from './pages/RegistrarEmpresa'; 

function App() {
  const { keycloak, initialized } = useKeycloak();
  const [usuario, setUsuario] = useState(null);

  useEffect(() => {
    if (initialized && keycloak.authenticated && keycloak.tokenParsed) {
      const parsedToken = keycloak.tokenParsed;
      const token = keycloak.token;

      const realmRoles = parsedToken?.realm_access?.roles || [];
      const clientRoles = parsedToken?.resource_access?.['sistema-desktop']?.roles || [];

      const roles = [...realmRoles, ...clientRoles];

      const usuarioExtraido = {
        id: parsedToken.sub,
        nombre: parsedToken.preferred_username,
        correo: parsedToken.email,
        roles,
        token,
      };

      setUsuario(usuarioExtraido);
      localStorage.setItem("token", token);
      localStorage.setItem("usuario", JSON.stringify(usuarioExtraido));

      const intervalId = setInterval(() => {
        keycloak.updateToken(60).then(refreshed => {
          if (refreshed) {
            localStorage.setItem("token", keycloak.token);
          }
        }).catch(() => {
          keycloak.logout();
        });
      }, 60000);

      return () => clearInterval(intervalId);
    }
  }, [initialized, keycloak]);

  return (
    <Router>
      <Routes>
        {/* Ruta pública para registrar empresa sin autenticación */}
        <Route path="/registrar-empresa" element={<RegistrarEmpresa />} />

        {/* Resto de rutas protegidas */}
        <Route
          path="*"
          element={
            !initialized ? (
              <p>Cargando autenticación...</p>
            ) : !keycloak.authenticated ? (
              <div>
                <p>No autenticado</p>
                <button onClick={() => keycloak.login()}>Iniciar sesión</button>
              </div>
            ) : !usuario ? (
              <p>Cargando usuario...</p>
            ) : (
              <>
                <div style={{ textAlign: "right", margin: "10px" }}>
                  <button onClick={() => keycloak.logout()}>Cerrar sesión</button>
                </div>
                {usuario.roles.includes("company") ? (
                  <EmpresaProyectoPage usuario={usuario} />
                ) : usuario.roles.includes("student") ? (
                  <EstudiantePage usuario={usuario} />
                ) : usuario.roles.includes("coordinator") ? (
                  <CoordinadorProyectoPage usuario={usuario} />
                ) : (
                  <div>Rol desconocido. Roles: {usuario.roles.join(", ")}</div>
                )}
              </>
            )
          }
        />
      </Routes>
    </Router>
  );
}

export default App;

{/*import { useEffect, useState } from "react";
import { useKeycloak } from "@react-keycloak/web";

import EmpresaProyectoPage from './pages/EmpresaProyectoPage';
import EstudiantePage from './pages/EstudiantePage';
import CoordinadorProyectoPage from './pages/CoordinadorProyectoPage';


function App() {
  const { keycloak, initialized } = useKeycloak();
  const [usuario, setUsuario] = useState(null);

  useEffect(() => {
    if (initialized && keycloak.authenticated && keycloak.tokenParsed) {
      const parsedToken = keycloak.tokenParsed;
      const token = keycloak.token;

      // Extraer roles de realm y cliente (cliente es 'sistema-desktop', cambia si es otro)
      const realmRoles = parsedToken?.realm_access?.roles || [];
      const clientRoles = parsedToken?.resource_access?.['sistema-desktop']?.roles || [];

      const roles = [...realmRoles, ...clientRoles];

      console.log("Roles combinados:", roles);

      const usuarioExtraido = {
        id: parsedToken.sub,
        nombre: parsedToken.preferred_username,
        correo: parsedToken.email,
        roles,
        token,
      };

      setUsuario(usuarioExtraido);
      localStorage.setItem("token", token);
      localStorage.setItem("usuario", JSON.stringify(usuarioExtraido));

      // Refrescar token cada 60 segundos
      const intervalId = setInterval(() => {
        keycloak.updateToken(60).then(refreshed => {
          if (refreshed) {
            console.log("Token renovado");
            localStorage.setItem("token", keycloak.token);
          }
        }).catch(() => {
          console.error("No se pudo renovar el token, cerrando sesión");
          keycloak.logout();
        });
      }, 60000);

      return () => clearInterval(intervalId);
    }
  }, [initialized, keycloak]);

  if (!initialized) return <p>Cargando autenticación...</p>;
  if (!keycloak.authenticated) return (
    <div>
      <p>No autenticado</p>
      <button onClick={() => keycloak.login()}>Iniciar sesión</button>
    </div>
  );

  if (!usuario) return <p>Cargando usuario...</p>;

  const handleLogout = () => {
    keycloak.logout();
  };

  let contenido;
  if (usuario.roles.includes("company")) {
    contenido = <EmpresaProyectoPage usuario={usuario} />;
  } else if (usuario.roles.includes("student")) {
    contenido = <EstudiantePage usuario={usuario} />;
  } else if (usuario.roles.includes("coordinator")) {
    contenido = <CoordinadorProyectoPage usuario={usuario} />;
  } else {
    contenido = <div>Rol desconocido. Roles disponibles: {usuario.roles.join(", ")}</div>;
  }

  return (
    <>
      <div style={{ textAlign: "right", margin: "10px" }}>
        <button onClick={handleLogout}>Cerrar sesión</button>
      </div>
      {contenido}
    </>
  );
}

export default App;*/}

