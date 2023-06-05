package com.planeador.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Planeador;
import com.planeador.repositorio.RepositorioPlaneador;
import com.planeador.servicio.PlaneadorServicio;

@Service
public class PlaneadorServicioImp extends GenericServiceImp<Planeador, Integer>
		implements PlaneadorServicio {

	@Autowired
	private RepositorioPlaneador repositorioPlaneador;

	@Override
	public CrudRepository<Planeador, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioPlaneador;
	}

	@Override
	public List<Planeador> listaDePlaneadors() {
		return this.repositorioPlaneador.findAll();
	}
	
	@Override
	public Page<Planeador> paginaDePlaneadores(int pagina, int nroDeElementos){
		 Pageable request = PageRequest.of(pagina - 1, nroDeElementos);
		 return repositorioPlaneador.findAll(request);
	}

}
