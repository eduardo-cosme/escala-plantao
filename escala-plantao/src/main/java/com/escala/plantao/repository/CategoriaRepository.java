package com.escala.plantao.repository;

import com.escala.plantao.entity.Categoria;
import com.escala.plantao.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
