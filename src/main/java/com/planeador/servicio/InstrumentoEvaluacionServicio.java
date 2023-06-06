package com.planeador.servicio;

import java.util.List;

import org.springframework.data.domain.Page;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Planeador;
import com.planeador.modelo.InstrumentoEvaluacion;

public interface InstrumentoEvaluacionServicio extends GenericService<InstrumentoEvaluacion, Integer> {
	
	public List<InstrumentoEvaluacion> listaDeInstrumentoEvaluacions();
	
	Page<InstrumentoEvaluacion> paginaDeInstrumentoEvaluacion (int pagina, int nroDeElementos);
	
	Page<InstrumentoEvaluacion> paginaDeInstrumentoEvaluacion (Planeador planeador, int pagina, int nroDeElementos);

}
