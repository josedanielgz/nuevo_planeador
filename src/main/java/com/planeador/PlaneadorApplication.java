package com.planeador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.planeador.modelo.Administrador;
import com.planeador.modelo.Materia;
import com.planeador.repositorio.RepositorioAdministrador;
import com.planeador.repositorio.RepositorioMateria;

@SpringBootApplication
public class PlaneadorApplication implements CommandLineRunner {

	// Objetos de prueba para verificar cierta funcionalidad
	@Autowired
	private RepositorioMateria materias;

	@Autowired
	private RepositorioAdministrador admins;
	
	public static void main(String[] args) {
		SpringApplication.run(PlaneadorApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		

		List<Materia> nuevosMaterias = new ArrayList<>();
		nuevosMaterias.add(new Materia("Análisis de Algoritmos",4,4));
		nuevosMaterias.add(new Materia("Análisis de Datos",3,8));
		nuevosMaterias.add(new Materia("Ingeniería de Datos",4,4));
		nuevosMaterias.add(new Materia("Análisis de Operaciones",4,5));


		for (Materia m : nuevosMaterias) {
			this.materias.save(m);
		}

		admins.save(new Administrador("ElJajas","prueba@123.com","123456789"));

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
