package com.planeador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.planeador.modelo.Microcurriculo;
import com.planeador.repositorio.RepositorioMicrocurriculo;

@SpringBootApplication
public class PlaneadorApplication implements CommandLineRunner {

	// Objetos de prueba para verificar cierta funcionalidad
	@Autowired
	private RepositorioMicrocurriculo microcurriculos;
	
	public static void main(String[] args) {
		SpringApplication.run(PlaneadorApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
//		List<Microcurriculo> nuevosMicrocurriculos = new ArrayList<>();
//		nuevosMicrocurriculos.add(new Microcurriculo("ANALISIS DE ALGORITMOS","1151404",false,120,4));
//		nuevosMicrocurriculos.add(new Microcurriculo("ANALISIS DE DATOS CON PYTHON","1151817",false,100,8));
//		nuevosMicrocurriculos.add(new Microcurriculo("INFORMATICA EDUCATIVA","1151501",false,80,8));
//		for (Microcurriculo m : nuevosMicrocurriculos) {
//			this.microcurriculos.save(m);
//		}
//		
//		
	}

}
