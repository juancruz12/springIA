package com.proyectoIA.springia.controller;

import com.proyectoIA.springia.dto.CrearCursoRequest;
import com.proyectoIA.springia.dto.CrearEstudianteRequest;
import com.proyectoIA.springia.dto.EstudianteDto;
import com.proyectoIA.springia.dto.FiltroEstudianteRequest;
import com.proyectoIA.springia.dto.MensajeRespuesta;
import com.proyectoIA.springia.dto.SetearNotaRequest;
import com.proyectoIA.springia.entities.Curso;
import com.proyectoIA.springia.entities.Estudiante;
import com.proyectoIA.springia.entities.Evaluacion;
import com.proyectoIA.springia.service.InstitutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller para gestionar operaciones del instituto.
 * Endpoints:
 * - POST /instituto/estudiantes : Crear nuevo estudiante
 * - POST /instituto/cursos : Crear nuevo curso con evaluaciones
 * - POST /instituto/evaluaciones : Setear nota de evaluación a un estudiante
 */
@RestController
@RequestMapping("/instituto")
public class InstitutoController {

    private final InstitutoService institutoService;

    public InstitutoController(InstitutoService institutoService) {
        this.institutoService = institutoService;
    }

    /**
     * Endpoint para crear un nuevo estudiante.
     * @param request Datos del estudiante (nombre, apellido, email, número de estudiante, etc.)
     * @return Respuesta con el estudiante creado y mensaje de éxito
     */
    @PostMapping("/estudiantes")
    public ResponseEntity<MensajeRespuesta> crearEstudiante(@RequestBody CrearEstudianteRequest request) {
        try {
            Estudiante estudiante = institutoService.crearEstudiante(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeRespuesta("Estudiante creado exitosamente", estudiante));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta("Error al crear estudiante: " + e.getMessage(), null));
        }
    }

    /**
     * Endpoint para crear un nuevo curso con sus evaluaciones.
     * @param request Datos del curso (nombre, horas totales, docente, evaluaciones, etc.)
     * @return Respuesta con el curso creado y mensaje de éxito
     */
    @PostMapping("/cursos")
    public ResponseEntity<MensajeRespuesta> crearCurso(@RequestBody CrearCursoRequest request) {
        try {
            Curso curso = institutoService.crearCurso(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeRespuesta("Curso creado exitosamente", curso));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta("Error al crear curso: " + e.getMessage(), null));
        }
    }

    /**
     * Endpoint para setear la nota de una evaluación a un estudiante.
     * @param request Datos de la evaluación (cursoId, estudianteId, nota, observaciones, etc.)
     * @return Respuesta con la evaluación registrada y mensaje de éxito
     */
    @PostMapping("/evaluaciones")
    public ResponseEntity<MensajeRespuesta> setearNota(@RequestBody SetearNotaRequest request) {
        try {
            Evaluacion evaluacion = institutoService.setearNota(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeRespuesta("Nota registrada exitosamente", evaluacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta("Error al registrar nota: " + e.getMessage(), null));
        }
    }

    /**
     * Endpoint para obtener estudiantes filtrados por criterios opcionales.
     * Los filtros disponibles son:
     * - id: ID del estudiante
     * - cursoId: ID del curso en el que está matriculado
     * - edadMinima: Edad mínima
     * - edadMaxima: Edad máxima
     * - promedioMinimo: Promedio mínimo de evaluaciones
     * - promedioMaximo: Promedio máximo de evaluaciones
     * - fechaInscripcionDesde: Fecha de inscripción desde
     * - fechaInscripcionHasta: Fecha de inscripción hasta
     *
     * @param id ID del estudiante (opcional)
     * @param cursoId ID del curso (opcional)
     * @param edadMinima Edad mínima (opcional)
     * @param edadMaxima Edad máxima (opcional)
     * @param promedioMinimo Promedio mínimo de evaluaciones (opcional)
     * @param promedioMaximo Promedio máximo de evaluaciones (opcional)
     * @param fechaDesde Fecha de inscripción desde (opcional)
     * @param fechaHasta Fecha de inscripción hasta (opcional)
     * @return Lista de EstudianteDto que cumplen con los filtros
     */
    @GetMapping("/estudiantes")
    public ResponseEntity<MensajeRespuesta> obtenerEstudiantes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long cursoId,
            @RequestParam(required = false) Integer edadMinima,
            @RequestParam(required = false) Integer edadMaxima,
            @RequestParam(required = false) Double promedioMinimo,
            @RequestParam(required = false) Double promedioMaximo,
            @RequestParam(required = false) LocalDate fechaDesde,
            @RequestParam(required = false) LocalDate fechaHasta) {
        try {
            FiltroEstudianteRequest filtro = new FiltroEstudianteRequest(
                    id,
                    cursoId,
                    edadMinima,
                    edadMaxima,
                    promedioMinimo,
                    promedioMaximo,
                    fechaDesde,
                    fechaHasta
            );
            List<EstudianteDto> estudiantes = institutoService.obtenerEstudiantesFiltrados(filtro);
            return ResponseEntity.ok()
                    .body(new MensajeRespuesta("Estudiantes obtenidos exitosamente", estudiantes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta("Error al obtener estudiantes: " + e.getMessage(), null));
        }
    }
}
