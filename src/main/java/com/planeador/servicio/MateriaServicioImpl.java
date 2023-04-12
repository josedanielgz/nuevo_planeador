package com.planeador.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;
import com.planeador.repositorio.RepositorioMateria;
import com.planeador.servicio.MateriaServicio;

@Service
public class MateriaServicioImpl extends GenericServiceImp<Materia, Integer> implements MateriaServicio {

	@Autowired
	private RepositorioMateria repositorioMateria;

	@Override
	public CrudRepository<Materia, Integer> getDao() {
		// TODO Auto-generated method stub
		return repositorioMateria;
	}

	@Override
	public List<Materia> listaDeMaterias() {
		return repositorioMateria.findAll();
	}
	
	public List<Microcurriculo> microcurriculosPorMateria(Integer idMicrocurriculo){
		return repositorioMateria.microcurriculoByMateriaID(idMicrocurriculo);
	}

}
