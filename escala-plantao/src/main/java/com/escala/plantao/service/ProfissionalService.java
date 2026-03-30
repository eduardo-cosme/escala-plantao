package com.escala.plantao.service;

import com.escala.plantao.entity.Categoria;
import com.escala.plantao.entity.Profissional;
import com.escala.plantao.repository.CategoriaRepository;
import com.escala.plantao.repository.ProfissionalRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;
    private final CategoriaRepository categoriaRepository;

    public Profissional criarProfissional(String nome, String registro, Long categoriaId, Integer cargaHorariaSemanal) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Profissional profissional = Profissional.builder()
                .nome(nome)
                .registro(registro)
                .categoria(categoria)
                .cargaHorariaSemanal(cargaHorariaSemanal)
                .build();

        return profissionalRepository.save(profissional);
    }

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    public List<Profissional> listarPorCategoria(Long categoriaId) {
        return profissionalRepository.findByCategoriaId(categoriaId);
    }
}
