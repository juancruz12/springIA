package com.proyectoIA.springia.repository;

import com.proyectoIA.springia.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
    Optional<Estudiante> findByNumeroEstudiante(String numeroEstudiante);

    // Métodos de filtrado
    @Query("SELECT DISTINCT e FROM Estudiante e " +
            "LEFT JOIN e.evaluaciones ev " +
            "LEFT JOIN ev.curso c " +
            "WHERE (:id IS NULL OR e.id = :id) " +
            "AND (:cursoId IS NULL OR c.id = :cursoId) " +
            "AND (:edadMinima IS NULL OR YEAR(CURRENT_DATE) - YEAR(e.fechaNacimiento) >= :edadMinima) " +
            "AND (:edadMaxima IS NULL OR YEAR(CURRENT_DATE) - YEAR(e.fechaNacimiento) <= :edadMaxima) " +
            "AND (:fechaDesde IS NULL OR e.fechaInscripcion >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR e.fechaInscripcion <= :fechaHasta) " +
            "AND (:promedioMinimo IS NULL OR (SELECT AVG(ev2.nota) FROM Evaluacion ev2 WHERE ev2.estudiante.id = e.id) >= :promedioMinimo) " +
            "AND (:promedioMaximo IS NULL OR (SELECT AVG(ev2.nota) FROM Evaluacion ev2 WHERE ev2.estudiante.id = e.id) <= :promedioMaximo)")
    List<Estudiante> filtrarEstudiantes(
            @Param("id") Long id,
            @Param("cursoId") Long cursoId,
            @Param("edadMinima") Integer edadMinima,
            @Param("edadMaxima") Integer edadMaxima,
            @Param("promedioMinimo") Double promedioMinimo,
            @Param("promedioMaximo") Double promedioMaximo,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );
}

