package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.planeador.modelo.Planeador;

public interface RepositorioPlaneador extends CrudRepository<Planeador, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);

	Page<Planeador> findAll(Pageable request);

	List<Planeador> findAll();
}
