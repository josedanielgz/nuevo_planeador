package com.planeador.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;
import com.planeador.repositorio.RepositorioMateria;
import com.planeador.repositorio.RepositorioMicrocurriculo;
import com.planeador.servicio.MateriaServicio;
import com.planeador.servicio.MicrocurriculoServicio;

@Service
public class InstrumentoServicioImpl extends GenericServiceImp<Microcurriculo, Integer>
		implements MicrocurriculoServicio {

	@Autowired
	private RepositorioMicrocurriculo repositorioMicrocurriculo;

	@Override
	public CrudRepository<Microcurriculo, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioMicrocurriculo;
	}

	@Override
	public List<Microcurriculo> listaDeMicrocurriculos() {
		return this.repositorioMicrocurriculo.findAll();
	}
	
	@Override
	public Page<Microcurriculo> paginaDeMicrocurriculos(int pagina, int nroDeElementos){
		 Pageable request = PageRequest.of(pagina - 1, nroDeElementos);
		 return repositorioMicrocurriculo.findAll(request);
	}

}
