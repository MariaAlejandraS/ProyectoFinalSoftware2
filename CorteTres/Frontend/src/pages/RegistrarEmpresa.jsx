import { useState } from 'react';
import axios from 'axios';

function RegistrarEmpresa() {
  const [empresa, setEmpresa] = useState({
    nit: '',
    nombre: '',
    email: '',
    sector: '',
    telefono: '',
    nombresContacto: '',
    apellidosContacto: '',
    cargoContacto: '',
    password: ''
  });

  const handleChange = (e) => {
    setEmpresa({ ...empresa, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        empresa: {
          nit: empresa.nit,
          nombre: empresa.nombre,
          email: empresa.email,
          sector: empresa.sector,
          telefono: empresa.telefono,
          nombresContacto: empresa.nombresContacto,
          apellidosContacto: empresa.apellidosContacto,
          cargoContacto: empresa.cargoContacto
        },
        password: empresa.password
      };

      await axios.post('http://localhost:8081/a/company', payload);
      alert('Empresa registrada con éxito');
    } catch (error) {
      console.error(error);
      alert('Error al registrar empresa');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="p-4 space-y-4">
      <input name="nit" onChange={handleChange} placeholder="NIT" required />
      <input name="nombre" onChange={handleChange} placeholder="Nombre" required />
      <input name="email" onChange={handleChange} placeholder="Email" required />
      <input name="password" onChange={handleChange} placeholder="Contraseña" type="password" required />
      <input name="sector" onChange={handleChange} placeholder="Sector" required />
      <input name="telefono" onChange={handleChange} placeholder="Teléfono" required />
      <input name="nombresContacto" onChange={handleChange} placeholder="Nombres contacto" required />
      <input name="apellidosContacto" onChange={handleChange} placeholder="Apellidos contacto" required />
      <input name="cargoContacto" onChange={handleChange} placeholder="Cargo contacto" required />
      <button type="submit">Registrar Empresa</button>
    </form>
  );
}

export default RegistrarEmpresa;
