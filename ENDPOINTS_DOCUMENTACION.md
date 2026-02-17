# Documentación de Endpoints del Instituto

## Base URL
```
/instituto
```

## Endpoints de Gestión

### 1. Crear Estudiante
**Endpoint:** `POST /instituto/estudiantes`

**Descripción:** Crea un nuevo estudiante en el sistema.

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "numeroEstudiante": "EST001",
  "email": "juan.perez@example.com",
  "telefono": "123456789",
  "fechaNacimiento": "2000-01-15",
  "direccion": "Calle Principal 123",
  "ciudad": "Madrid",
  "pais": "España"
}
```

**Response (201 Created):**
```json
{
  "mensaje": "Estudiante creado exitosamente",
  "data": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "numeroEstudiante": "EST001",
    "email": "juan.perez@example.com",
    "telefono": "123456789",
    "fechaNacimiento": "2000-01-15",
    "direccion": "Calle Principal 123",
    "ciudad": "Madrid",
    "pais": "España",
    "estado": "ACTIVO",
    "fechaInscripcion": "2024-01-15"
  }
}
```

---

### 2. Crear Curso
**Endpoint:** `POST /instituto/cursos`

**Descripción:** Crea un nuevo curso en el sistema.

**Request Body:**
```json
{
  "nombre": "Matemáticas Avanzadas",
  "horasTotales": 60,
  "docente": "Dr. García",
  "descripcion": "Curso avanzado de matemáticas para estudiantes de ingeniería",
  "fechaInicio": "2024-02-01",
  "fechaFin": "2024-05-31",
  "evaluaciones": []
}
```

**Response (201 Created):**
```json
{
  "mensaje": "Curso creado exitosamente",
  "data": {
    "id": 1,
    "nombre": "Matemáticas Avanzadas",
    "horasTotales": 60,
    "docente": "Dr. García",
    "descripcion": "Curso avanzado de matemáticas para estudiantes de ingeniería",
    "fechaInicio": "2024-02-01",
    "fechaFin": "2024-05-31",
    "evaluaciones": []
  }
}
```

---

### 3. Setear Nota de Evaluación
**Endpoint:** `POST /instituto/evaluaciones`

**Descripción:** Registra la nota de evaluación de un estudiante en un curso específico.

**Request Body:**
```json
{
  "cursoId": 1,
  "estudianteId": 1,
  "nota": 8.5,
  "fechaEvaluacion": "2024-03-15",
  "observaciones": "Buen desempeño, muestra comprensión sólida del contenido"
}
```

**Response (201 Created):**
```json
{
  "mensaje": "Nota registrada exitosamente",
  "data": {
    "id": 1,
    "curso": {
      "id": 1,
      "nombre": "Matemáticas Avanzadas"
    },
    "estudiante": {
      "id": 1,
      "nombre": "Juan",
      "apellido": "Pérez"
    },
    "nota": 8.5,
    "fechaEvaluacion": "2024-03-15",
    "observaciones": "Buen desempeño, muestra comprensión sólida del contenido"
  }
}
```

---

## Códigos de Estado HTTP

- **201 Created**: La operación fue exitosa y se creó el recurso.
- **400 Bad Request**: Hubo un error en la solicitud (parámetros inválidos, entidad no encontrada, etc.).

## Validaciones

### Para Estudiante:
- `nombre` y `apellido`: Requeridos, máx 100 caracteres
- `numeroEstudiante`: Requerido, único, máx 20 caracteres
- `email`: Requerido, único
- `fechaNacimiento`: Opcional (formato YYYY-MM-DD)
- `telefono`: Opcional, máx 20 caracteres
- `direccion`: Opcional, máx 100 caracteres
- `ciudad` y `pais`: Opcional, máx 50 caracteres

### Para Curso:
- `nombre`: Requerido, máx 150 caracteres
- `horasTotales`: Requerido
- `docente`: Requerido, máx 100 caracteres
- `descripcion`: Opcional, máx 500 caracteres
- `fechaInicio` y `fechaFin`: Opcional (formato YYYY-MM-DD)

### Para Evaluación:
- `cursoId`: Requerido (debe existir en base de datos)
- `estudianteId`: Requerido (debe existir en base de datos)
- `nota`: Requerido (número decimal)
- `fechaEvaluacion`: Opcional (si no se proporciona, se usa la fecha actual)
- `observaciones`: Opcional, máx 500 caracteres

## Notas

- El campo `estado` de un estudiante se establece automáticamente a `ACTIVO` cuando se crea.
- La `fechaInscripcion` se establece automáticamente a la fecha actual cuando se crea un estudiante.
- Al setear una nota, si ya existe una evaluación para ese par (curso, estudiante), se actualiza; si no, se crea una nueva.

