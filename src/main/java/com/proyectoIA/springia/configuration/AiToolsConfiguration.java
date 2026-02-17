package com.proyectoIA.springia.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AiToolsConfiguration {
/*
    @Bean
    @Description("Busca alumnos de un curso específico que tengan un promedio minimo y/o maximo a un valor dado")
    public Function<AlumnoRequest, AlumnoResponse> buscarAlumnosPorRendimiento(InstitutoService service) {
        return request -> {
            var data = service.obtenerEstudiantesFiltrados(
                    FiltroEstudianteRequest.builder()
                            .cursoId(request.cursoId())
                            .promedioMinimo(request.promedioMinimo())
                            .promedioMaximo(request.promedioMaximo())
                            .build());
            return new AlumnoResponse(data);
        };
    }*/
}
