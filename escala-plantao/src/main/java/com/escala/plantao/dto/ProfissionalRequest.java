package com.escala.plantao.dto;

import lombok.Data;

@Data
public class ProfissionalRequest {
    private String nome;
    private String registro;
    private Long categoriaId;
    private Integer cargaHorariaSemanal;
}
