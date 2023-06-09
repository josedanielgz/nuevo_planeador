package com.planeador.servicio;


import java.util.List;

import org.springframework.data.domain.Page;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Docente;

public interface DocenteServicio extends GenericService<Docente, Integer>{

	public Docente select(String email, String password);
	
	public List<Docente> findPendingRequests();
	
	public Page<Docente> paginaDeSolicitudes(int pagina, int cantidad);
}

