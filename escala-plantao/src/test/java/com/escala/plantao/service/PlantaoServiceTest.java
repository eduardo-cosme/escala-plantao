package com.escala.plantao.service;

import com.escala.plantao.entity.Plantao;
import com.escala.plantao.entity.Profissional;
import com.escala.plantao.entity.Turno;
import com.escala.plantao.repository.PlantaoRepository;
import com.escala.plantao.repository.ProfissionalRepository;
import com.escala.plantao.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlantaoServiceTest {

    @Mock
    private PlantaoRepository plantaoRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private PlantaoService plantaoService;

    @Test
    void deveLancarErroQuandoProfissionalJaPossuiPlantaoNoMesmoDiaETurno() {
        Long profissionalId = 1L;
        Long turnoId = 2L;
        LocalDate data = LocalDate.of(2026, 3, 30);

        Profissional profissional = Profissional.builder()
                .id(profissionalId)
                .cargaHorariaSemanal(40)
                .build();

        Turno turno = new Turno(2, "Tarde", 6);

        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turno));
        when(plantaoRepository.existsByProfissionalIdAndDataAndTurnoId(profissionalId, data, turnoId)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> plantaoService.criarPlantao(profissionalId, turnoId, data));

        assertTrue(ex.getMessage().contains("já possui plantão"));
        verify(plantaoRepository, never()).save(any(Plantao.class));
    }

    @Test
    void deveLancarErroQuandoExcederCargaHorariaSemanal() {
        Long profissionalId = 1L;
        Long turnoId = 3L;
        LocalDate data = LocalDate.of(2026, 3, 30);

        Profissional profissional = Profissional.builder()
                .id(profissionalId)
                .cargaHorariaSemanal(36)
                .build();

        Turno turnoNoite = new Turno(1, "Noite", 12);

        Plantao plantaoExistente1 = Plantao.builder().turno(new Turno(1, "Noite", 12)).build();
        Plantao plantaoExistente2 = Plantao.builder().turno(new Turno(1, "Noite", 12)).build();
        Plantao plantaoExistente3 = Plantao.builder().turno(new Turno(3, "Tarde", 6)).build();

        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
        when(turnoRepository.findById(turnoId)).thenReturn(Optional.of(turnoNoite));
        when(plantaoRepository.existsByProfissionalIdAndDataAndTurnoId(profissionalId, data, turnoId)).thenReturn(false);
        when(plantaoRepository.findByProfissionalIdAndDataBetween(eq(profissionalId), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(plantaoExistente1, plantaoExistente2, plantaoExistente3));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> plantaoService.criarPlantao(profissionalId, turnoId, data));

        assertEquals("Carga horária semanal excedida", ex.getMessage());
        verify(plantaoRepository, never()).save(any(Plantao.class));
    }
}

