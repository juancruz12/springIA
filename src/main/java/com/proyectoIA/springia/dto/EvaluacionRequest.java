package com.proyectoIA.springia.dto;

public record EvaluacionRequest(
        Long id,
        Long idAlumno,
        String nombreAlumno,
        Double nota,
        String observaciones,
        Long cursoId,
        String nombreCurso
) {

}
