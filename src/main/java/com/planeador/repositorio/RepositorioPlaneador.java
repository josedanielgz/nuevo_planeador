package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.planeador.modelo.Docente;
import com.planeador.modelo.Planeador;

public interface RepositorioPlaneador extends CrudRepository<Planeador, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);

	Page<Planeador> findAll(Pageable request);

	List<Planeador> findAll();
	
	@Query("SELECT p FROM Planeador p WHERE p.docente = :docente")
	List<Planeador> findByDocente(@Param("docente") Docente docente);
	
	@Query("SELECT p FROM Planeador p WHERE p.docente = :docente")
	Page<Planeador> findByDocente(@Param("docente") Docente docente, Pageable request);
}
