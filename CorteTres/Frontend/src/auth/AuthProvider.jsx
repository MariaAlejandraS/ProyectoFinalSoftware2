// AuthProvider.jsx
import { useEffect, useState } from "react";
import keycloak from "./auth/keycloak";
import { useNavigate } from "react-router-dom"; // 👈 importar

export default function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(null);
  const navigate = useNavigate(); // 👈 hook para redirección

  useEffect(() => {
    keycloak
      .init({
        onLoad: "login-required",
        checkLoginIframe: false,
        promiseType: "native",
      })
      .then((authenticated) => {
        setIsAuthenticated(authenticated);

        if (authenticated) {
          localStorage.setItem("token", keycloak.token);
          localStorage.setItem("usuario", JSON.stringify(keycloak.tokenParsed));
        } else {
          localStorage.removeItem("token");
          localStorage.removeItem("usuario");
        }

        setInterval(() => {
          keycloak
            .updateToken(70)
            .then((refreshed) => {
              if (refreshed) {
                localStorage.setItem("token", keycloak.token);
              }
            })
            .catch(() => {
              console.error("Token refresh failed. Logging out...");
              keycloak.logout();
            });
        }, 60000);
      })
      .catch((err) => {
        console.error("Keycloak init failed:", err);
        setIsAuthenticated(false);
      });
  }, []);

  if (isAuthenticated === null) {
    return <div>Cargando autenticación...</div>;
  }

  if (!isAuthenticated) {
    return (
      <div>
        <p>No estás autenticado.</p>
        <button onClick={() => keycloak.login()}>Iniciar sesión</button>
        <button onClick={() => navigate("/registro-empresa")}>
          Registrar empresa
        </button>
      </div>
    );
  }

  return children;
}

{/*import { useEffect, useState } from "react";
import keycloak from "./auth/keycloak"; // Ajusta si la ruta es distinta

export default function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(null);

  useEffect(() => {
    keycloak
      .init({
        onLoad: "login-required",
        checkLoginIframe: false,
        promiseType: "native",
      })
      .then((authenticated) => {
        setIsAuthenticated(authenticated);

        if (authenticated) {
          localStorage.setItem("token", keycloak.token);
          localStorage.setItem("usuario", JSON.stringify(keycloak.tokenParsed));
        } else {
          localStorage.removeItem("token");
          localStorage.removeItem("usuario");
        }

        // Token refresh
        setInterval(() => {
          keycloak
            .updateToken(70)
            .then((refreshed) => {
              if (refreshed) {
                localStorage.setItem("token", keycloak.token);
              }
            })
            .catch(() => {
              console.error("Token refresh failed. Logging out...");
              keycloak.logout();
            });
        }, 60000);
      })
      .catch((err) => {
        console.error("Keycloak init failed:", err);
        setIsAuthenticated(false);
      });
  }, []);

  if (isAuthenticated === null) {
    return <div>Cargando autenticación...</div>;
  }

  if (!isAuthenticated) {
    return (
      <div>
        <p>No estás autenticado.</p>
        <button onClick={() => keycloak.login()}>Iniciar sesión</button>
      </div>
    );
  }

  return children;
}
*/}