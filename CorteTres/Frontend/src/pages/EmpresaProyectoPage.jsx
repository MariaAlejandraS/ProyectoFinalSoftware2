import { useEffect, useState } from 'react';
import LogoutButton from "../components/LogoutButton";

function EmpresaProyectoPage({ usuario }) {
    const [proyectos, setProyectos] = useState([]);
    const [nuevoProyecto, setNuevoProyecto] = useState({
        nombreproyecto: '',
        descripcion: '',
        resumen: '',
        objetivos: '',
        tiempoMaxMeses: '',
        presupuesto: '',
        fecha: '',
        estado: 'RECIBIDO',
        comentarios: ''
    });
    const [proyectoEditar, setProyectoEditar] = useState(null);
    const [mensajeExito, setMensajeExito] = useState("");

    const empresaId = usuario.id;

    useEffect(() => {
        fetch(`http://localhost:8081/d/proyecto/empresa/${empresaId}`, {
            headers: {
                'Authorization': `Bearer ${usuario.token}`
            }
        })
            .then(res => {
                if (!res.ok) throw new Error('No autorizado');
                return res.json();
            })
            .then(data => setProyectos(data))
            .catch(err => console.error('Error al cargar proyectos', err));
    }, [empresaId, usuario.token]);

    const handleSubmit = (e) => {
        e.preventDefault();

        const proyecto = {
            ...nuevoProyecto,
            empresaNit: empresaId,
            tiempoMaxMeses: parseInt(nuevoProyecto.tiempoMaxMeses),
            presupuesto: parseFloat(nuevoProyecto.presupuesto)
        };

        fetch('http://localhost:8081/d/proyecto', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${usuario.token}`
            },
            body: JSON.stringify(proyecto),
        })
            .then(res => res.json())
            .then(data => {
                setProyectos([...proyectos, data]);
                setNuevoProyecto({
                    nombre: '',
                    descripcion: '',
                    resumen: '',
                    objetivos: '',
                    tiempoMaxMeses: '',
                    presupuesto: '',
                    fecha: '',
                    estado: '',
                    comentarios: ''
                });
                setMensajeExito("Proyecto guardado con éxito.");
                setTimeout(() => setMensajeExito(""), 3000);
            })
            .catch(err => console.error('Error al registrar proyecto', err));
    };

    const eliminarProyecto = async (id) => {
        const confirmacion = window.confirm('¿Estás seguro de eliminar este proyecto?');
        if (!confirmacion) return;

        try {
            const respuesta = await fetch(`http://localhost:8081/d/proyecto/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${usuario.token}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!respuesta.ok) {
                if (respuesta.status === 401) throw new Error('No autorizado');
                throw new Error('Error al eliminar el proyecto');
            }

            setProyectos(prevProyectos => prevProyectos.filter(p => p.id !== id));
            alert('Proyecto eliminado correctamente');
        } catch (error) {
            console.error('Error al eliminar proyecto:', error);
            alert(`Error: ${error.message}`);
        }
    };


    const guardarEdicion = (e) => {
        e.preventDefault();

        fetch(`http://localhost:8081/d/proyecto/${proyectoEditar.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${usuario.token}`
            },
            body: JSON.stringify(proyectoEditar),
        })
            .then(() => {
                setProyectos(proyectos.map(p => (p.id === proyectoEditar.id ? proyectoEditar : p)));
                setProyectoEditar(null);
                alert('Proyecto editado correctamente');
            })
            .catch(err => console.error('Error al editar proyecto', err));
    };


    return (
        <div style={{ padding: '20px' }}>
            <h2>Bienvenido, {usuario.nombre}</h2>

            <h3>Registrar nuevo proyecto</h3>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Nombre" value={nuevoProyecto.nombre} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, nombre: e.target.value })} required />
                <input type="text" placeholder="Resumen" value={nuevoProyecto.resumen} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, resumen: e.target.value })} required />
                <input type="text" placeholder="Objetivos" value={nuevoProyecto.objetivos} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, objetivos: e.target.value })} required />
                <input type="text" placeholder="Descripción" value={nuevoProyecto.descripcion} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, descripcion: e.target.value })} required />
                <input type="number" placeholder="Tiempo máximo (meses)" value={nuevoProyecto.tiempoMaxMeses} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, tiempoMaxMeses: e.target.value })} required />
                <input type="number" placeholder="Presupuesto" value={nuevoProyecto.presupuesto} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, presupuesto: e.target.value })} required />
                <input type="date" placeholder="Fecha" value={nuevoProyecto.fecha} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, fecha: e.target.value })} required />
               {/* <select value={nuevoProyecto.estado} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, estado: e.target.value })} required>
                    <option value="">Seleccione un estado</option>
                    <option value="RECIBIDO">Recibido</option>
                    <option value="ACEPTADO">Aceptado</option>
                    <option value="RECHAZADO">Rechazado</option>
                    <option value="TERMINADO">Terminado</option>
                </select>*/}
                <input type="text" value={nuevoProyecto.estado} disabled />
               {/* <input type="text" placeholder="Comentarios" value={nuevoProyecto.comentarios} onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, comentarios: e.target.value })} required />
                       */}
                <button type="submit">Guardar</button>
            </form>

            {mensajeExito && <p style={{ color: "green", fontWeight: "bold" }}>{mensajeExito}</p>}

            {proyectoEditar && (
                <form onSubmit={guardarEdicion} style={{ marginTop: '20px' }}>
                    <h3>Editar proyecto</h3>
                    <input type="text" value={proyectoEditar.nombre} onChange={(e) => setProyectoEditar({ ...proyectoEditar, nombre: e.target.value })} />
                    <input type="text" value={proyectoEditar.resumen} onChange={(e) => setProyectoEditar({ ...proyectoEditar, resumen: e.target.value })} />
                    <input type="text" value={proyectoEditar.objetivos} onChange={(e) => setProyectoEditar({ ...proyectoEditar, objetivos: e.target.value })} />
                    <input type="text" value={proyectoEditar.descripcion} onChange={(e) => setProyectoEditar({ ...proyectoEditar, descripcion: e.target.value })} />
                    <input type="number" value={proyectoEditar.tiempoMaxMeses} onChange={(e) => setProyectoEditar({ ...proyectoEditar, tiempoMaxMeses: e.target.value })} />
                    <input type="number" value={proyectoEditar.presupuesto} onChange={(e) => setProyectoEditar({ ...proyectoEditar, presupuesto: e.target.value })} />
                    <input type="date" value={proyectoEditar.fecha} onChange={(e) => setProyectoEditar({ ...proyectoEditar, fecha: e.target.value })} />
                    <button type="submit">Guardar cambios</button>
                    <button type="button" onClick={() => setProyectoEditar(null)} style={{ marginLeft: '10px' }}>Cancelar</button>
                </form>
            )}

            <h3>Mis proyectos</h3>
            <ul>
                {proyectos.map((p) => (
                    <li key={p.id || p.nombre}>
                        {p.nombre} - {p.estado}
                        <button onClick={() => eliminarProyecto(p.id)} style={{ marginLeft: '10px' }}>Eliminar</button>
                        <button onClick={() => setProyectoEditar({
                            ...p,
                            nombre: p.nombre || '',
                            descripcion: p.descripcion || '',
                            resumen: p.resumen || '',
                            objetivos: p.objetivos || '',
                            tiempoMaxMeses: p.tiempoMaxMeses || '',
                            presupuesto: p.presupuesto || '',
                            fecha: p.fecha || '',
                            estado: p.estado || '',
                            comentarios: p.comentarios || ''
                        })} style={{ marginLeft: '10px' }}>Editar</button>
                    </li>
                ))}
            </ul>

            <LogoutButton />
        </div>
    );
}

export default EmpresaProyectoPage;

