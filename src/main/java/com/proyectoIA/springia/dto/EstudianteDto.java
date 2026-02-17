package com.proyectoIA.springia.dto;

import java.time.LocalDate;

/**
 * DTO para retornar información del estudiante sin exponer la entidad.
 */
public record EstudianteDto(
        Long id,
        String nombre,
        String apellido,
        String numeroEstudiante,
        String email,
        String telefono,
        LocalDate fechaNacimiento,
        String direccion,
        String ciudad,
        String pais,
        String estado,
        LocalDate fechaInscripcion
) {}

