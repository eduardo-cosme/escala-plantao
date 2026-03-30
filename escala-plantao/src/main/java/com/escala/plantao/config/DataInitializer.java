package com.escala.plantao.config;

import com.escala.plantao.entity.Categoria;
import com.escala.plantao.entity.Turno;
import com.escala.plantao.repository.CategoriaRepository;
import com.escala.plantao.repository.TurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final TurnoRepository turnoRepository;

    @Override
    public void run(String... args) {
        categoriaRepository.saveAll(List.of(
                new Categoria(1, "Médico"),
                new Categoria(2, "Enfermeiro"),
                new Categoria(3, "Técnico de Enfermagem")
        ));

        turnoRepository.saveAll(List.of(
                new Turno(1, "Manhã", 6),
                new Turno(2, "Tarde", 6),
                new Turno(3, "Noite", 12)
        ));
    }
}
