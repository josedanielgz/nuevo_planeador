package com.planeador.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Microcurriculo;
import com.planeador.repositorio.RepositorioMicrocurriculo;

public class MicrocurriculoServicioImpl extends GenericServiceImp<Microcurriculo, Integer>
		implements MicrocurriculoServicio {

	@Autowired
	private RepositorioMicrocurriculo repositorioMicrocurriculo;

	@Override
	public CrudRepository<Microcurriculo, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioMicrocurriculo;
	}

}
