package com.planeador.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.planeador.generico.GenericService;
import com.planeador.modelo.Docente;
import com.planeador.modelo.Planeador;


public interface PlaneadorServicio extends GenericService<Planeador, Integer> {
	
	public List<Planeador> listaDePlaneadors();
	
	Page<Planeador> paginaDePlaneadores (int pagina, int nroDeElementos);
	
	Page<Planeador> paginaDePlaneadores (Docente docente, int pagina, int nroDeElementos);
	
	Optional<Planeador> findById(Integer id);

}
