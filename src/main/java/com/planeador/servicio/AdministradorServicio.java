package com.planeador.servicio;


import com.planeador.generico.GenericService;
import com.planeador.modelo.Administrador;

public interface AdministradorServicio extends GenericService<Administrador, Integer>{

	public Administrador select(String email, String password);
}

