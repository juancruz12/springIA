package com.proyectoIA.springia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroEstudiante;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(length = 100)
    private String direccion;

    @Column(length = 50)
    private String ciudad;

    @Column(length = 50)
    private String pais;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEstudiante estado;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones;
}
