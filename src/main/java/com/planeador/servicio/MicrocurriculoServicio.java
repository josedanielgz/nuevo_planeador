package com.planeador.servicio;

import java.util.List;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Microcurriculo;


public interface MicrocurriculoServicio extends GenericService<Microcurriculo, Integer> {
	
	public List<Microcurriculo> listaDeMicrocurriculos();

}
