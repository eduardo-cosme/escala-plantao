package com.escala.plantao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profissional")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Profissional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String registro; // CRM/COREN

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private Integer cargaHorariaSemanal;

}
