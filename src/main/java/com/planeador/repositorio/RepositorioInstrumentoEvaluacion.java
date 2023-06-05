package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.planeador.modelo.Materia;
import com.planeador.modelo.Microcurriculo;


public interface RepositorioInstrumento extends CrudRepository<Microcurriculo, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);
	
	Page<Microcurriculo> findAll(Pageable request);
	
	List<Microcurriculo> findAll();
}
