package com.escala.plantao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "plantao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantao {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private LocalDate data;

        @ManyToOne
        @JoinColumn(name = "turno_id")
        private Turno turno;

        @ManyToOne
        @JoinColumn(name = "profissional_id")
        private Profissional profissional;

}
