package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.planeador.modelo.Microcurriculo;


public interface RepositorioMicrocurriculo extends CrudRepository<Microcurriculo, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);
	
	List<Microcurriculo> findAll();
}
