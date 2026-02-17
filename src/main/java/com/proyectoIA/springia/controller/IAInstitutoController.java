package com.proyectoIA.springia.controller;


import com.proyectoIA.springia.service.InstitutoService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/institutoIA")
public class IAInstitutoController {

    private final ChatClient ai;

    private String prompt = "Eres un experto en educación e institutos en Argentina. " +
            "Responde a las siguientes preguntas con información precisa y actualizada " +
            "sobre el sistema educativo argentino, los institutos disponibles, sus programas académicos, " +
            "requisitos de admisión, y cualquier otra información relevante relacionada con la educación en Argentina. " +
            "Proporciona respuestas claras y concisas para ayudar a los usuarios a entender mejor las opciones educativas en el país. " +
            "Si el usuario solicita explícitamente datos de la base de datos (por ejemplo 'buscar estudiantes' o 'listar cursos'), " +
            "usa la herramienta 'obtenerEstudiantes' y realiza únicamente esa llamada, devolviendo los resultados en formato JSON. " +
            "En caso contrario, responde directamente sin invocar herramientas.";


    public IAInstitutoController(ChatClient.Builder ai, InstitutoService institutoService) {
        this.ai = ai
                .defaultToolCallbacks(ToolCallbacks.from(institutoService))
                .defaultSystem("Eres un asistente administrativo del colegio. " +
                        "Tienes acceso a datos de alumnos y notas. Usa herramientas solo cuando el usuario lo solicite explícitamente.")
                .build();
    }

    @GetMapping("/consultar")
    public String consultar(@RequestParam String question) {
        return ai
                .prompt()
                .user(prompt + " " + question)
                .call()
                .content();
    }

}
