package com.planeador.servicio;


import java.util.List;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Docente;

public interface DocenteServicio extends GenericService<Docente, Integer>{

	public Docente select(String email, String password);
	
	public List<Docente> findPendingRequests();
}

