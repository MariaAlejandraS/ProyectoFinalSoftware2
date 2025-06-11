import { useEffect, useState } from "react";
import axios from "axios";

const API_URL = "http://localhost:8081/b/coordinator/proyectos";

export default function CoordinadorProyectoPage() {
  const [proyectos, setProyectos] = useState([]);
  const [comentarios, setComentarios] = useState({});
  const [estados, setEstados] = useState({});
  const token = localStorage.getItem("token");
  const [periodoSeleccionado, setPeriodoSeleccionado] = useState("2025-1");
  const [postulacionesEstudiantes, setPostulacionesEstudiantes] = useState([]);



  useEffect(() => {
    cargarProyectos();
    cargarPostulacionesEstudiantes();
  }, []);

  const cargarProyectos = async () => {
    try {
      const res = await axios.get(API_URL, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setProyectos(res.data);
    } catch (err) {
      console.error("Error cargando proyectos:", err);
    }
  };

  const cambiarEstado = async (id) => {
    try {
      await axios.put(
        `http://localhost:8081/b/coordinator/proyectos/${id}/estado`,
        {}, // cuerpo vacío, no se usa
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            estado: estados[id],
            comentario: comentarios[id],
            periodo: periodoSeleccionado,

          },
        }
      );
      alert("Estado actualizado correctamente");
      cargarProyectos();
    } catch (err) {
      console.error("Error cambiando estado:", err);
      alert("Error al cambiar estado: " + (err.response?.data?.message || err.message));
    }
  };
  const cargarPostulacionesEstudiantes = async () => {
    try {
      const res = await axios.get("http://localhost:8081/b/coordinator/postulaciones/proyectos", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setPostulacionesEstudiantes(res.data);
    } catch (err) {
      console.error("Error cargando postulaciones:", err);
      alert("Error al cargar postulaciones: " + (err.response?.data?.message || err.message));
    }
  };




  return (
    <div className="p-4">
      {/* Sección: Proyectos registrados por empresas */}
      <h2 className="text-2xl font-bold mb-4">Proyectos registrados</h2>
      <div className="mb-4">
        <label className="mr-2 font-semibold">Período académico:</label>
        <select
          value={periodoSeleccionado}
          onChange={(e) => setPeriodoSeleccionado(e.target.value)}
          className="border p-1"
        >
          <option value="2025-1">2025-1</option>
          <option value="2025-2">2025-2</option>
          <option value="2024-2">2024-2</option>
          <option value="2024-1">2024-1</option>
        </select>
      </div>

      <table className="min-w-full table-auto border border-gray-300">
        <thead className="bg-gray-100">
          <tr>
            <th className="px-4 py-2 border">ID</th>
            <th className="px-4 py-2 border">Nombre</th>
            <th className="px-4 py-2 border">Empresa</th>
            <th className="px-4 py-2 border">Estado</th>
            <th className="px-4 py-2 border">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {proyectos.map((p) => (
            <tr key={p.id}>
              <td className="px-4 py-2 border">{p.id}</td>
              <td className="px-4 py-2 border">{p.nombre}</td>
              <td className="px-4 py-2 border">{p.empresa?.nombre || "N/A"}</td>
              <td className="px-4 py-2 border">{p.estado}</td>
              <td className="px-4 py-2 border">
                <select
                  value={estados[p.id] || "RECIBIDO"}
                  onChange={(e) =>
                    setEstados({ ...estados, [p.id]: e.target.value })
                  }
                  className="border p-1"
                >
                  <option value="RECIBIDO">Recibido</option>
                  <option value="ACEPTADO">Aceptado</option>
                  <option value="RECHAZADO">Rechazado</option>
                  <option value="TERMINADO">Terminado</option>
                </select>
                <input
                  type="text"
                  placeholder="Comentario"
                  className="border ml-2 p-1"
                  value={comentarios[p.id] || ""}
                  onChange={(e) =>
                    setComentarios({ ...comentarios, [p.id]: e.target.value })
                  }
                />
                <button
                  onClick={() => cambiarEstado(p.id)}
                  className="ml-2 px-3 py-1 bg-blue-600 text-white rounded"
                >
                  Cambiar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Sección: Proyectos postulados por estudiantes */}
      <h2 className="text-2xl font-bold mt-10 mb-4">Proyectos postulados por estudiantes</h2>
      <table className="min-w-full table-auto border border-gray-300">
        <thead className="bg-gray-100">
          <tr>
            <th className="px-4 py-2 border">Estudiante ID</th>
            <th className="px-4 py-2 border">Proyecto ID</th>
            <th className="px-4 py-2 border">Nombre Proyecto</th>
            <th className="px-4 py-2 border">Empresa</th>
            <th className="px-4 py-2 border">Estado</th>
          </tr>
        </thead>
        <tbody>
          {postulacionesEstudiantes.map(({ estudianteId, proyecto }) => (
            <tr key={`${estudianteId}-${proyecto.id}`}>
              <td className="px-4 py-2 border">{estudianteId}</td>
              <td className="px-4 py-2 border">{proyecto.id}</td>
              <td className="px-4 py-2 border">{proyecto.nombre}</td>
              <td className="px-4 py-2 border">{proyecto.empresa?.nombre || "N/A"}</td>
              <td className="px-4 py-2 border">{proyecto.estado}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );

}


