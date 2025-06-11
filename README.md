# SoftwareFinal
**SISTEMA ACADEMICO
Se ha desarrollado un proyecto práctico enfocado en la aplicación de conocimientos sobre arquitectura de software moderna, con énfasis en el uso de microservicios, arquitectura hexagonal (puertos y adaptadores) y la filosofía de Domain-Driven Design (DDD). Además, se integraron diversos patrones de diseño, mecanismos de seguridad con JWT, y se aplicaron buenas prácticas de contenedorización con Docker, todo con el objetivo de construir un sistema escalable, mantenible y profesionalmente robusto.

**REQUISITOS
1.Yo como empresa necesito registrarme en el sistema para posteriormente subir proyectos de software que serán realizados por estudiantes en alguna modalidad de pasantía, proyecto de curso o práctica profesional. Contexto: para registrarse se debe pedir: *Nit de la empresa, *nombre, *email, *sector, *teléfono de contacto, *nombres del contacto, *apellidos del contacto y *cargo del contacto. (*: Datos obligatorios).
2.Yo como empresa necesito subir mi proyecto de software para que pueda ser implementado por estudiantes en alguna modalidad de pasantía, proyecto de curso o práctica profesional. Contexto: Los datos del proyecto serán: *nombre, *resumen, *objetivos, *descripción, *tiempo máximo en meses, presupuesto y *fecha. Por defecto, el estado inicial será recibido.
3.Yo como coordinador necesito listar los proyectos que han postulado las empresas en un periodo académico para emitir una evaluación y cambiar el estado del proyecto. Contexto: el listado debe mostrar los datos básicos de la empresa y los datos básicos del proyecto; al darle clic en el botón “Ver detalles” mostrará todos los datos de la empresa, del proyecto y su estado actual. Se puede cambiar el estado a: Recibido, aceptado, rechazado. El coordinador puede colocar comentarios. Una vez cambiado el estado, el sistema enviará un correo electrónico a la empresa notificando el cambio de estado.
4.Yo como estudiante necesito ver el listado de proyectos que las empresas han postulado para contactarme con el coordinador y manifestar mi interés en uno de ellos (se presta para enviar mensajes asíncronos). Contexto: el listado tiene que manejar como columnas: No, fecha, nombre empresa, nombre del proyecto, resumen. Al darle clic debe permitir mostrar todos los datos de la empresa y el proyecto. Aquí se puede aplicar el patrón Observer mediante varias vistas que muestren los datos de los proyectos. Proponer esas vistas, por ejemplo, una vista podria mostrar gráficamente el numero de proyectos presentados.
5.Yo como usuario del sistema (empresa, coordinador, estudiante) necesito iniciar sesión en el sistema para consultar las novedades de los proyectos.
6.Yo como coordinador necesito visualizar gráficas estadísticas en barras y pastel sobre cantidad de proyectos postulados, aprobados, rechazados y terminados en un período académico para tener un control estadístico.


**TECNOLOGIAS USADAS
Backend: 
1. Java 17
2. Spring Boot 3.x
3. Maven
4. PostgreSQL (persistencia en un microservicio)
5. H2 / SQLite (bases en memoria para otros microservicios)
6. JWT / Keycloak (autenticación y autorización)
7. Docker & Docker Compose

Frontend:
1. ReactJS
2. Chart.js / Recharts (visualización de gráficos)
3. Axios (comunicación HTTP)
4. Context API + Hooks

Otros:
1. API Gateway (opcional)
2. Swagger/OpenAPI (documentación de APIs)
3. Lombok (reducción de boilerplate)
4. MapStruct (mapeo de DTOs)
5. ModelMapper / JPA

**ARQUITECTURA DEL PROYECTO
Este sistema adopta la arquitectura hexagonal (puertos y adaptadores) con orientación a Domain-Driven Design (DDD), separando claramente la lógica del dominio de los detalles de infraestructura y frameworks.

Componentes clave:
Capa de Dominio: Entidades, agregados y lógica de negocio.

Puertos (Interfaces): Contratos de entrada y salida.

Adaptadores:
1. Entrada: Controladores REST (Spring Boot)
2. Salida: Persistencia con JPA, Repositorios PostgreSQL / H2

Infraestructura: Configuración de seguridad, autenticación JWT, Docker, etc.

**Se aplican al menos seis patrones de diseño a lo largo del sistema, incluyendo:
1. Observer
2. Strategy
3. Adapter
4. Builder
5. Proxy
6. Facade

**SEGURIDAD
El sistema implementa autenticación y autorización basada en JWT, con dos esquemas disponibles:
1. Manual: Generación y validación de tokens directamente desde el backend.
2. Integración con Keycloak: Gestión externa de identidad. Spring Boot valida los tokens emitidos.

**Dockerización
Todos los microservicios están dockerizados y orquestados mediante Docker Compose. Un microservicio principal (estadísticas) persiste datos usando PostgreSQL con un volumen Docker que asegura durabilidad.
Los otros microservicios utilizan bases de datos en memoria (H2 o SQLite) para facilitar desarrollo y pruebas.

**Estructura del Repositorio
CorteTres/
    Backend/
    │   ├── api-gateway/              
    │   ├── estudiante-service/       
    │   ├── proyecto-service:/ │     
    │   ├── empresa-service/
    │   ├── coordinador-service/
    │ 
    ├── auth-service/             # Autenticación (manual o Keycloak)
    ├── frontend/                 # Aplicación React
    ├── docker-compose.yml
    ├   ── README.md
    └── docs/
    └── arquitectura.pdf      # Documento final con la arquitectura del sistema
    
**DOCUMENTACION TECNICA
La documentación detallada de la arquitectura, decisiones de diseño, diagrama de despliegue, modelos de dominio y flujos de autenticación se encuentra a continuación:
https://docs.google.com/document/d/1fAb68_fE2BmldnjrMkHxUDUPMghQMMlG/edit

**CREDITOS
Desarrollado por estudiantes del curso Ingeniería de Software 2
Universidad del Cauca - Departamento de Ingeniería de Sistemas
Semestre 2025-I
