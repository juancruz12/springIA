package com.proyectoIA.springia.dto;

import java.time.LocalDate;

public record CrearEstudianteRequest(
    String nombre,
    String apellido,
    String numeroEstudiante,
    String email,
    String telefono,
    LocalDate fechaNacimiento,
    String direccion,
    String ciudad,
    String pais
) {}


