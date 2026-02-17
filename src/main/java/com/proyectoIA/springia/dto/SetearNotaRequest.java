package com.proyectoIA.springia.dto;

import java.time.LocalDate;

public record SetearNotaRequest(
    Long cursoId,
    Long estudianteId,
    Double nota,
    LocalDate fechaEvaluacion,
    String observaciones
) {}


