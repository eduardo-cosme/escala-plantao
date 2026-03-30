package com.escala.plantao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Turno {
    @Id
    private int id;
    private String descricao;
    // Adiciona o campo cargaHoraria para armazenar a carga horária de cada turno
    //Manhã = 6h; Tarde = 6h; Noite = 12h
    private Integer cargaHoraria;

    public Turno(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

}
