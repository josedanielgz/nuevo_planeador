package com.planeador.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "microcurriculo")

public class Microcurriculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String codigoCurso;
	private Boolean tipoCurso;

	@ManyToOne
	@JoinColumn(name = "materia_id")
	private Materia materia;

//	Por hacer:
//	Arreglo de Clase unidad

	public Microcurriculo() {
		super();
	}

}