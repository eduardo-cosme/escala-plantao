package com.escala.plantao;

import com.escala.plantao.repository.CategoriaRepository;
import com.escala.plantao.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EscalaPlantaoApplicationTests {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private TurnoRepository turnoRepository;

	@Test
	void contextLoads() {
		assertTrue(categoriaRepository.count() >= 4);
		assertTrue(turnoRepository.count() >= 3);
		assertEquals(6, turnoRepository.findAll().stream()
				.filter(turno -> turno.getId() == 1)
				.findFirst()
				.orElseThrow()
				.getCargaHoraria());
	}

}
