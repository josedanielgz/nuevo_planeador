package com.planeador.servicio.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.planeador.generico.GenericServiceImp;
import com.planeador.modelo.Administrador;
import com.planeador.repositorio.RepositorioAdministrador;
import com.planeador.servicio.AdministradorServicio;


@Service
public class AdministradorServicioImpl extends GenericServiceImp<Administrador, Integer> implements AdministradorServicio{

	@Autowired
	public RepositorioAdministrador repositorioAdministrador;
	
	@Override
	public CrudRepository<Administrador, Integer> getDao(){
		return repositorioAdministrador;
	}
	
	@Override
	public Administrador select(String email, String password) {
		return repositorioAdministrador.findByEmailAndPassword(email, password);
	}

}
