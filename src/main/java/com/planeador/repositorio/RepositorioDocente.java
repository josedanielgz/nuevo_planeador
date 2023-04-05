package com.planeador.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.planeador.modelo.Docente;


public interface RepositorioDocente extends CrudRepository<Docente, Integer> {

	public abstract Docente findByEmailAndPassword(String email, String password);
}
