package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.planeador.modelo.Docente;


public interface RepositorioDocente extends CrudRepository<Docente, Integer> {

	public abstract Docente findByEmailAndPassword(String email, String password);
	
	@Query(value="SELECT * FROM docente WHERE docente.aprovado = FALSE", nativeQuery = true)
	List<Docente> findPendingRequests();
	
	}
