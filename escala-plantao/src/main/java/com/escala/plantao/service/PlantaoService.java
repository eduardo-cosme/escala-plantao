package com.escala.plantao.service;

import com.escala.plantao.entity.Plantao;
import com.escala.plantao.entity.Profissional;
import com.escala.plantao.entity.Turno;
import com.escala.plantao.repository.PlantaoRepository;
import com.escala.plantao.repository.ProfissionalRepository;
import com.escala.plantao.repository.TurnoRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Builder
public class PlantaoService {

    private final PlantaoRepository plantaoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final TurnoRepository turnoRepository;

    public Plantao criarPlantao(Long profissionalId, Long turnoId, LocalDate data) {
        //Buscar o profissional e o turno
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno não encontrado"));

        // REGRA 1 — Não pode duplicar plantão
        boolean existe = plantaoRepository
                .existsByProfissionalIdAndDataAndTurnoId(profissionalId, data, turnoId);
        if (existe) {
            throw new RuntimeException("Profissional já possui plantão nesse dia e turno");
        }
        // REGRA 2 — Não pode ultrapassar carga horária
        validarCargaHoraria(profissional, turno, data);

        Plantao plantao = Plantao.builder()
                .profissional(profissional)
                .turno(turno)
                .data(data)
                .build();
        return plantaoRepository.save(plantao);
    }

    public void excluirPlantao(Long id) {
        if (!plantaoRepository.existsById(id)) {
            throw new RuntimeException("Plantão não encontrado");
        }

        plantaoRepository.deleteById(id);
    }

    public List<Plantao> listarEscalaSemanal(LocalDate data) {

        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.SUNDAY);

        return plantaoRepository.findByDataBetween(inicioSemana, fimSemana);
    }

    private void validarCargaHoraria(Profissional profissional, Turno novoTurno, LocalDate data) {
        //Definir semana (segunda a domingo)
        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.SUNDAY);

        //Buscar plantões da semana
        List<Plantao> plantoesSemana = plantaoRepository
                .findByProfissionalIdAndDataBetween(
                        profissional.getId(),
                        inicioSemana,
                        fimSemana
                );

        //Somar horas já existentes
        int totalHoras = plantoesSemana.stream()
                .mapToInt(p -> p.getTurno().getCargaHoraria())
                .sum();

        //Somar novo plantão
        totalHoras += novoTurno.getCargaHoraria();

        //Validar limite
        if (totalHoras > profissional.getCargaHorariaSemanal()) {
            throw new RuntimeException("Carga horária semanal excedida");
        }
    }
}
