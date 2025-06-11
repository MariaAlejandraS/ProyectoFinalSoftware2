// LogoutButton.jsx
import keycloak from "../auth/keycloak";

function LogoutButton() {
  const handleLogout = () => {
    localStorage.removeItem("usuario");
    localStorage.removeItem("token");
    keycloak.logout(); // Esto redirige al logout real
  };

  return <button onClick={handleLogout}>Cerrar sesión</button>;
}

export default LogoutButton;

  