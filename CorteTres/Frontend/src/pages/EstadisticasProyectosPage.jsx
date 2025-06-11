import { useEffect, useState } from "react";
import axios from "axios";
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, PieChart, Pie, Cell, Legend
} from "recharts";

const API_URL = "http://localhost:8081/b/estadisticas/periodos";
const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];
const PERIODOS = ["2024-1", "2024-2", "2025-1", "2025-2"];

export default function EstadisticasProyectosPage() {
  const [datos, setDatos] = useState([]);
  const [periodoSeleccionado, setPeriodoSeleccionado] = useState(PERIODOS[0]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    cargarEstadisticas();
  }, [periodoSeleccionado]);

  const cargarEstadisticas = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await axios.get(`${API_URL}?periodo=${periodoSeleccionado}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setDatos(res.data);
    } catch (error) {
      setError("Error cargando estadísticas.");
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">
        Estadísticas de Proyectos - {periodoSeleccionado}
      </h2>

      {/* Selector de período académico */}
      <div className="mb-6">
        <label className="mr-2 font-medium">Período académico:</label>
        <select
          value={periodoSeleccionado}
          onChange={(e) => setPeriodoSeleccionado(e.target.value)}
          className="border rounded px-3 py-1"
        >
          {PERIODOS.map((periodo) => (
            <option key={periodo} value={periodo}>{periodo}</option>
          ))}
        </select>
      </div>

      {/* Cargando */}
      {loading && <p className="text-blue-500 font-medium">Cargando estadísticas...</p>}

      {/* Error */}
      {error && <p className="text-red-600">{error}</p>}

      {/* Sin datos */}
      {!loading && datos.length === 0 && !error && (
        <p className="text-gray-600">No hay datos disponibles para este período.</p>
      )}

      {/* Gráficas */}
      {!loading && datos.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Gráfico de barras */}
          <div>
            <h3 className="text-xl font-semibold mb-2">Proyectos por Estado (Barra)</h3>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={datos}>
                <XAxis dataKey="estado" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="cantidad" fill="#007bff" />
              </BarChart>
            </ResponsiveContainer>
          </div>

          {/* Gráfico de pastel */}
          <div>
            <h3 className="text-xl font-semibold mb-2">Distribución por Estado (Pastel)</h3>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={datos}
                  dataKey="cantidad"
                  nameKey="estado"
                  cx="50%"
                  cy="50%"
                  outerRadius={100}
                  label
                >
                  {datos.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Legend />
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      )}
    </div>
  );
}
