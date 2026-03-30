package com.escala.plantao.repository;

import com.escala.plantao.entity.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlantaoRepository extends JpaRepository<Plantao, Long> {
    // Regra 1: profissional não pode ter dois plantões no mesmo dia e turno
    boolean existsByProfissionalIdAndDataAndTurnoId(Long profissionalId, LocalDate data, Long turnoId);

    // Regra 2: buscar plantões na semana para um profissional específico
    List<Plantao> findByProfissionalIdAndDataBetween (
            Long profissionalId,
            LocalDate inicioSemana,
            LocalDate fimSemana
    );

    //Regra 3: buscar plantões por data
    List<Plantao> findByDataBetween(LocalDate inicio, LocalDate fim);
}
