package com.planeador.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.planeador.modelo.InstrumentoEvaluacion;


public interface RepositorioInstrumentoEvaluacion extends CrudRepository<InstrumentoEvaluacion, Integer> {

//	public abstract Curso findByEmailAndPassword(String email, String password);
	
	Page<InstrumentoEvaluacion> findAll(Pageable request);
	
	List<InstrumentoEvaluacion> findAll();
}
