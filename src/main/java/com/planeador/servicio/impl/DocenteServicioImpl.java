package com.planeador.servicio.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Docente;
import com.planeador.repositorio.RepositorioDocente;
import com.planeador.servicio.DocenteServicio;

@Service
public class DocenteServicioImpl extends GenericServiceImp<Docente, Integer> implements DocenteServicio {

	@Autowired
	public RepositorioDocente repositorioDocente;

	@Override
	public CrudRepository<Docente, Integer> getDao() {
		return repositorioDocente;
	}

	@Override
	public Docente select(String email, String password) {
		return repositorioDocente.findByEmailAndPassword(email, password);
	}

	@Override
	public List<Docente> findPendingRequests() {
		// TODO Auto-generated method stub
		return repositorioDocente.findPendingRequests();
	}

	@Override
	public Page<Docente> paginaDeSolicitudes(int pagina, int nroDeElementos) {
		Pageable request = PageRequest.of(pagina - 1, nroDeElementos);
		return this.repositorioDocente.pageOfPendingRequests(request);
	}

}
