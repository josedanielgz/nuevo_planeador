package com.planeador.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.planeador.modelo.Administrador;


public interface RepositorioAdministrador extends CrudRepository<Administrador, Integer> {

	public abstract Administrador findByEmailAndPassword(String email, String password);
}
