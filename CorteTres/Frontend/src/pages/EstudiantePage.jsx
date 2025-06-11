import { useEffect, useState } from 'react';
import LogoutButton from '../components/LogoutButton';

function EstudiantePage({ usuario }) {
  const [proyectos, setProyectos] = useState([]);
  const [postulaciones, setPostulaciones] = useState([]);
  const [mensaje, setMensaje] = useState('');

  const token = localStorage.getItem('token');

  // Cargar proyectos disponibles y postulaciones del estudiante
  useEffect(() => {
    if (!usuario?.id || !token) return;

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    // Obtener proyectos disponibles
    fetch('http://localhost:8081/c/student/proyectos', { headers })
      .then(res => {
        if (!res.ok) throw new Error('Error al obtener proyectos');
        return res.json();
      })
      .then(data => setProyectos(data))
      .catch(err => console.error('Error al cargar proyectos', err));

    // Obtener postulaciones del estudiante
    fetch(`http://localhost:8081/c/student/${usuario.id}/postulaciones`, { headers })
      .then(res => {
        if (!res.ok) throw new Error('Error al obtener postulaciones');
        return res.json();
      })
      .then(data => setPostulaciones(data))
      .catch(err => console.error('Error al cargar postulaciones', err));
  }, [usuario?.id, token]);

  const postularse = (proyectoId) => {
    const headers = {
      Authorization: `Bearer ${token}`,
    };

    fetch(`http://localhost:8081/c/student/postular?estudianteId=${usuario.id}&proyectoId=${proyectoId}`, {
      method: 'POST',
      headers,
    })
      .then(res => {
        if (!res.ok) throw new Error('Ya estás postulado o hubo un error');
        return res.json();
      })
      .then((nuevaPostulacion) => {
        setPostulaciones([...postulaciones, nuevaPostulacion]);
        setMensaje('✔ Postulación exitosa');
      })
      .catch(err => setMensaje(`✘ ${err.message}`));
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Bienvenido, {usuario.nombre}</h2>

      <h3>Proyectos disponibles</h3>
      <ul>
        {proyectos.map(p => (
          <li key={p.id}>
            <strong>{p.nombre}</strong> - {p.descripcion}
            <button onClick={() => postularse(p.id)} style={{ marginLeft: '10px' }}>
              Postularme
            </button>
          </li>
        ))}
      </ul>

      <h3>Mis postulaciones</h3>
      <ul>
        {postulaciones.map(pos => (
          <li key={pos.id}>
            Proyecto ID: {pos.proyectoId} - Estado: {pos.estado}
          </li>
        ))}
      </ul>

      {mensaje && <p style={{ color: mensaje.includes('✘') ? 'red' : 'green' }}>{mensaje}</p>}
      <LogoutButton />
    </div>
  );
}

export default EstudiantePage;


