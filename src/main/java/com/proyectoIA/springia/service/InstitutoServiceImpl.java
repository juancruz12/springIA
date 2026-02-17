package com.proyectoIA.springia.service;

import com.proyectoIA.springia.dto.CrearCursoRequest;
import com.proyectoIA.springia.dto.CrearEstudianteRequest;
import com.proyectoIA.springia.dto.EstudianteDto;
import com.proyectoIA.springia.dto.FiltroEstudianteRequest;
import com.proyectoIA.springia.dto.SetearNotaRequest;
import com.proyectoIA.springia.entities.Curso;
import com.proyectoIA.springia.entities.Estudiante;
import com.proyectoIA.springia.entities.Evaluacion;
import com.proyectoIA.springia.entities.EstadoEstudiante;
import com.proyectoIA.springia.repository.CursoRepository;
import com.proyectoIA.springia.repository.EstudianteRepository;
import com.proyectoIA.springia.repository.EvaluacionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InstitutoServiceImpl implements InstitutoService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final EvaluacionRepository evaluacionRepository;

    public InstitutoServiceImpl(EstudianteRepository estudianteRepository,
                               CursoRepository cursoRepository,
                               EvaluacionRepository evaluacionRepository) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    @Override
    public Estudiante crearEstudiante(CrearEstudianteRequest request) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.nombre());
        estudiante.setApellido(request.apellido());
        estudiante.setNumeroEstudiante(request.numeroEstudiante());
        estudiante.setEmail(request.email());
        estudiante.setTelefono(request.telefono());
        estudiante.setFechaNacimiento(request.fechaNacimiento());
        estudiante.setDireccion(request.direccion());
        estudiante.setCiudad(request.ciudad());
        estudiante.setPais(request.pais());
        estudiante.setEstado(EstadoEstudiante.ACTIVO);
        estudiante.setFechaInscripcion(LocalDate.now());

        return estudianteRepository.save(estudiante);
    }

    @Override
    public Curso crearCurso(CrearCursoRequest request) {
        Curso curso = new Curso();
        curso.setNombre(request.nombre());
        curso.setHorasTotales(request.horasTotales());
        curso.setDocente(request.docente());
        curso.setDescripcion(request.descripcion());
        curso.setFechaInicio(request.fechaInicio());
        curso.setFechaFin(request.fechaFin());
        // Las evaluaciones se crean por separado mediante el endpoint de setearNota
        // No se asignan aquí directamente

        return cursoRepository.save(curso);
    }

    @Override
    public Evaluacion setearNota(SetearNotaRequest request) {
        // Validar que existan el curso y el estudiante
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + request.cursoId()));
        Estudiante estudiante = estudianteRepository.findById(request.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + request.estudianteId()));

        // Buscar si ya existe una evaluación para este estudiante y curso
        // Si no existe, crear una nueva
        Evaluacion evaluacion = evaluacionRepository.findByCursoIdAndEstudianteId(request.cursoId(), request.estudianteId())
                .orElse(new Evaluacion());

        evaluacion.setCurso(curso);
        evaluacion.setEstudiante(estudiante);
        evaluacion.setNota(request.nota());
        evaluacion.setFechaEvaluacion(request.fechaEvaluacion() != null ? request.fechaEvaluacion() : LocalDate.now());
        evaluacion.setObservaciones(request.observaciones());

        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public List<EstudianteDto> obtenerEstudiantesFiltrados(FiltroEstudianteRequest filtro) {
        return estudianteRepository.filtrarEstudiantes(
                filtro.id(),
                filtro.cursoId(),
                filtro.edadMinima(),
                filtro.edadMaxima(),
                filtro.promedioMinimo(),
                filtro.promedioMaximo(),
                filtro.fechaInscripcionDesde(),
                filtro.fechaInscripcionHasta()
        ).stream()
                .map(this::convertirEstudianteADto)
                .toList();
    }

    // Nueva tool específica y parametrizada para llamadas desde el modelo de IA.
    // Usar parámetros simples ayuda al modelo a decidir y ejecutar la tool más rápido.
    @Tool(name = "obtenerEstudiantes", description = "Devuelve estudiantes filtrados por criterios: id, cursoId, edadMinima, edadMaxima, promedioMinimo, promedioMaximo, fechaDesde (YYYY-MM-DD), fechaHasta (YYYY-MM-DD)")
    public List<EstudianteDto> obtenerEstudiantes(
            @ToolParam(description = "ID del estudiante", required = false) Long id,
            @ToolParam(description = "ID del curso", required = false) Long cursoId,
            @ToolParam(description = "Edad mínima", required = false) Integer edadMinima,
            @ToolParam(description = "Edad máxima", required = false) Integer edadMaxima,
            @ToolParam(description = "Promedio mínimo", required = false) Double promedioMinimo,
            @ToolParam(description = "Promedio máximo", required = false) Double promedioMaximo,
            @ToolParam(description = "Fecha de inscripción desde (YYYY-MM-DD)", required = false) String fechaDesde,
            @ToolParam(description = "Fecha de inscripción hasta (YYYY-MM-DD)", required = false) String fechaHasta
    ) {
        LocalDate desde = null;
        LocalDate hasta = null;
        try {
            if (fechaDesde != null && !fechaDesde.isBlank()) desde = LocalDate.parse(fechaDesde);
        } catch (Exception ignored) {}
        try {
            if (fechaHasta != null && !fechaHasta.isBlank()) hasta = LocalDate.parse(fechaHasta);
        } catch (Exception ignored) {}

        FiltroEstudianteRequest filtro = new FiltroEstudianteRequest(
                id,
                cursoId,
                edadMinima,
                edadMaxima,
                promedioMinimo,
                promedioMaximo,
                desde,
                hasta
        );
        return obtenerEstudiantesFiltrados(filtro);
    }

    /**
     * Convierte una entidad Estudiante a EstudianteDto
     */
    private EstudianteDto convertirEstudianteADto(Estudiante estudiante) {
        return new EstudianteDto(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getNumeroEstudiante(),
                estudiante.getEmail(),
                estudiante.getTelefono(),
                estudiante.getFechaNacimiento(),
                estudiante.getDireccion(),
                estudiante.getCiudad(),
                estudiante.getPais(),
                estudiante.getEstado().toString(),
                estudiante.getFechaInscripcion()
        );
    }
}
