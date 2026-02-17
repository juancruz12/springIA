package com.proyectoIA.springia.dto;

import java.time.LocalDate;
import java.util.List;

public record CrearCursoRequest(
    String nombre,
    Integer horasTotales,
    String docente,
    String descripcion,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    List<String> evaluaciones
) {}


