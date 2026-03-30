package com.escala.plantao.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlantaoRequest {
    private Long profissionalId;
    private Long turnoId;
    private LocalDate data;
}
