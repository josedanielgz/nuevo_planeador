package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.planeador.modelo.Docente;


public interface RepositorioDocente extends CrudRepository<Docente, Integer> {

	public abstract Docente findByEmailAndPassword(String email, String password);
	
	@Query(value="SELECT * FROM docente WHERE docente.aprobado = FALSE", nativeQuery = true)
	List<Docente> findPendingRequests();
	
	@Query(value="SELECT * FROM docente WHERE docente.aprobado = FALSE", nativeQuery = true)
	Page<Docente> pageOfPendingRequests(Pageable pageable);
	}
