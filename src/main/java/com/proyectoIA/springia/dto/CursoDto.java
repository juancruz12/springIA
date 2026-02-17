package com.proyectoIA.springia.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para retornar información del curso sin exponer la entidad.
 */
public record CursoDto(
        Long id,
        String nombre,
        Integer horasTotales,
        String docente,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {}

