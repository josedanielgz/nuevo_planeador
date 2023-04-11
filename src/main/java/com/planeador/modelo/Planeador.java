package com.planeador.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "curso")

public class Planeador {

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

	public Planeador() {
		super();
	}

}