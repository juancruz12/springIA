package com.proyectoIA.springia.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO para filtrar estudiantes por diversos criterios.
 * Todos los campos son opcionales.
 */
@Builder
public record FiltroEstudianteRequest(

        @JsonPropertyDescription("ID único del estudiante")
        Long id,
        @JsonPropertyDescription("ID único del curso")
        Long cursoId,
        @JsonPropertyDescription("Edad minima del estudiante")
        Integer edadMinima,
        @JsonPropertyDescription("Edad maxima del estudiante")
        Integer edadMaxima,
        @JsonPropertyDescription("Promedio minimo de evaluaciones del estudiante")
        Double promedioMinimo,
        @JsonPropertyDescription("Promedio maximo de evaluaciones del estudiante")
        Double promedioMaximo,
        @JsonPropertyDescription("Fecha minima de inscripcion del estudiante")
        LocalDate fechaInscripcionDesde,
        @JsonPropertyDescription("Fecha maxima de inscripcion del estudiante")
        LocalDate fechaInscripcionHasta
) {}

