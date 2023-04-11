package com.planeador.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Microcurriculo;
import com.planeador.repositorio.RepositorioMateria;

@Service
public class MicrocurriculoServicioImpl extends GenericServiceImp<Microcurriculo, Integer>
		implements MicrocurriculoServicio {

	@Autowired
	private RepositorioMateria repositorioMicrocurriculo;

	@Override
	public CrudRepository<Microcurriculo, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioMicrocurriculo;
	}

//	https://stackoverflow.com/q/56499565
	@Override
	public List<Microcurriculo> listaDeMicrocurriculos() {
		// TODO Auto-generated method stub
		return this.repositorioMicrocurriculo.findAll();
	}

}
