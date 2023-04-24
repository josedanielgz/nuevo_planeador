package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;


public interface RepositorioMateria extends CrudRepository<Materia, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);
	
	List<Materia> findAll();
	// @Query("select m from microcurriculo m join m.materia n where n.id = :materiaId")
	// List<Microcurriculo> microcurriculoByMateriaID(@Param("materiaId") Integer materiaId);

	Page<Materia> findAll(PageRequest request);
}
