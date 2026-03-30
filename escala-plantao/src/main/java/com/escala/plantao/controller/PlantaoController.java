package com.escala.plantao.controller;

import com.escala.plantao.dto.PlantaoRequest;
import com.escala.plantao.entity.Plantao;
import com.escala.plantao.service.PlantaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/plantoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PlantaoController {
    //TODO: Implementar os endpoints para criar, listar, atualizar e deletar plantões

    private final PlantaoService plantaoService;

    @PostMapping
    public Plantao criarPlantao(@RequestBody PlantaoRequest request)
    {
        return plantaoService.criarPlantao(request.getProfissionalId(), request.getTurnoId(), request.getData());
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        plantaoService.excluirPlantao(id);
    }

    @GetMapping("/semana")
    public List<Plantao> listarPorSemana(@RequestParam String data) {

        LocalDate dataParam = LocalDate.parse(data);

        return plantaoService.listarEscalaSemanal(dataParam);
    }
}
