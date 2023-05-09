package com.planeador.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "materia")
public class Materia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String nombre;
	
	@Column
	private Integer creditos;
	
	@Column
	private Integer semestre;
	
	@OneToMany(mappedBy = "materia")
	List<Microcurriculo> microcurriculos;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public List<Microcurriculo> getMicrocurriculos() {
		return microcurriculos;
	}

	public void setMicrocurriculos(List<Microcurriculo> microcurriculos) {
		this.microcurriculos = microcurriculos;
	}

	public Materia(Integer id, String nombre, Integer creditos, Integer semestre,
			List<Microcurriculo> microcurriculos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.creditos = creditos;
		this.semestre = semestre;
		this.microcurriculos = microcurriculos;
	}

	public Materia(String nombre, Integer creditos, Integer semestre) {
		super();
		this.nombre = nombre;
		this.creditos = creditos;
		this.semestre = semestre;
	}

	public Materia() {
		super();
	}
	
	
}
