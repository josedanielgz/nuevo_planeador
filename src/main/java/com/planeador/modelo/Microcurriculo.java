package com.planeador.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "curso")

public class Microcurriculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String codigoCurso;
	private Boolean tipoCurso;
	private int numeroCreditos;
	private int semestre;

//	Por hacer:
//	Arreglo de Clase unidad

	public Microcurriculo() {
		super();
	}

	public Microcurriculo(String nombre, String codigoCurso, Boolean tipoCurso, int numeroCreditos, int semestre) {
		super();
		this.nombre = nombre;
		this.codigoCurso = codigoCurso;
		this.tipoCurso = tipoCurso;
		this.numeroCreditos = numeroCreditos;
		this.semestre = semestre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoCurso() {
		return codigoCurso;
	}

	public void setCodigoCurso(String codigoCurso) {
		this.codigoCurso = codigoCurso;
	}

	public Boolean getTipoCurso() {
		return tipoCurso;
	}

	public void setTipoCurso(Boolean tipoCurso) {
		this.tipoCurso = tipoCurso;
	}

	public int getNumeroCreditos() {
		return numeroCreditos;
	}

	public void setNumeroCreditos(int numeroCreditos) {
		this.numeroCreditos = numeroCreditos;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

}
