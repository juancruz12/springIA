package com.proyectoIA.springia.repository;

import com.proyectoIA.springia.entities.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByCursoId(Long cursoId);
    List<Evaluacion> findByEstudianteId(Long estudianteId);
    Optional<Evaluacion> findByCursoIdAndEstudianteId(Long cursoId, Long estudianteId);
}

