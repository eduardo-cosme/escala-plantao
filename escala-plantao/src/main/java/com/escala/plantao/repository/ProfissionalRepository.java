package com.escala.plantao.repository;

import com.escala.plantao.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    //Filtro por categoria
    List<Profissional> findByCategoriaId(Long categoriaId);



}
