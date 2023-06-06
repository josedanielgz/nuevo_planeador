package com.planeador.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.InstrumentoEvaluacion;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;
import com.planeador.modelo.Planeador;
import com.planeador.repositorio.RepositorioMateria;
import com.planeador.repositorio.RepositorioInstrumentoEvaluacion;
import com.planeador.servicio.MateriaServicio;
import com.planeador.servicio.InstrumentoEvaluacionServicio;

@Service
public class InstrumentoEvaluacionServicioImpl extends GenericServiceImp<InstrumentoEvaluacion, Integer>
		implements InstrumentoEvaluacionServicio {

	@Autowired
	private RepositorioInstrumentoEvaluacion repositorioInstrumentoEvaluacion;

	@Override
	public CrudRepository<InstrumentoEvaluacion, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioInstrumentoEvaluacion;
	}

	
	@Override
	public Page<InstrumentoEvaluacion> paginaDeInstrumentoEvaluacion(int pagina, int nroDeElementos){
		 Pageable request = PageRequest.of(pagina - 1, nroDeElementos);
		 return repositorioInstrumentoEvaluacion.findAll(request);
	}


	@Override
	public List<InstrumentoEvaluacion> listaDeInstrumentoEvaluacions() {
		// TODO Auto-generated method stub
		return this.repositorioInstrumentoEvaluacion.findAll();
	}


	@Override
	public Page<InstrumentoEvaluacion> paginaDeInstrumentoEvaluacion(Planeador planeador, int pagina, int nroDeElementos) {
		 Pageable request = PageRequest.of(pagina - 1, nroDeElementos);
		 return repositorioInstrumentoEvaluacion.findByPlaneador(planeador, request);
	}



}
