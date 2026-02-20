package com.proyectoIA.springia.service;

import com.proyectoIA.springia.dto.*;
import com.proyectoIA.springia.entities.Curso;
import com.proyectoIA.springia.entities.Estudiante;
import com.proyectoIA.springia.entities.Evaluacion;

import java.util.List;

public interface InstitutoService {
    Estudiante crearEstudiante(CrearEstudianteRequest request);
    Curso crearCurso(CrearCursoRequest request);
    EvaluacionRequest setearNota(SetearNotaRequest request);
    List<EstudianteDto> obtenerEstudiantesFiltrados(FiltroEstudianteRequest filtro);
}

