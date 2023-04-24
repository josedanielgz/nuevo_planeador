package com.planeador.servicio;

import java.util.List;

import org.springframework.data.domain.Page;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Materia;

public interface MateriaServicio extends GenericService<Materia, Integer> {

	public List<Materia> listaDeMaterias();

	Page<Materia> paginaDeMaterias(int pagina, int nroDeElementos);

}
