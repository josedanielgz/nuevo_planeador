package com.planeador.servicio;

import java.util.List;

import org.springframework.data.domain.Page;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;


public interface InstrumentoServicio extends GenericService<Microcurriculo, Integer> {
	
	public List<Microcurriculo> listaDeMicrocurriculos();
	
	Page<Microcurriculo> paginaDeMicrocurriculos (int pagina, int nroDeElementos);

}
