package com.escala.plantao.controller;

import com.escala.plantao.dto.ProfissionalRequest;
import com.escala.plantao.entity.Categoria;
import com.escala.plantao.entity.Plantao;
import com.escala.plantao.entity.Profissional;
import com.escala.plantao.repository.CategoriaRepository;
import com.escala.plantao.repository.ProfissionalRepository;
import com.escala.plantao.service.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProfissionalController {

    private final ProfissionalService  profissionalService;

    @PostMapping
    public Profissional criarProfissional(@RequestBody ProfissionalRequest request)
    {
          return profissionalService.criarProfissional(request.getNome(), request.getRegistro(),
                    request.getCategoriaId(), request.getCargaHorariaSemanal());
    }

    //Listar todos
    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalService.listarTodos();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Profissional> listarPorCategoria(@PathVariable Long categoriaId) {
        return profissionalService.listarPorCategoria(categoriaId);
    }
}
