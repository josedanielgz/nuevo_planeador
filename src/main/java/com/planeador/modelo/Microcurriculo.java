package com.planeador.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "microcurriculo")

public class Microcurriculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "horas_directas")
	int horasDirectas;

	@Column(name = "horas_independientes")
	private int horasIndependientes;

	@Column(name = "fecha_registro")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaRegistro;

	@ManyToOne
	@JoinColumn(name = "materia_id")
	private Materia materia;

	@OneToMany(mappedBy = "microcurriculo")
	List<Planeador> planeadores;

	public Microcurriculo() {
		super();
	}

	public Microcurriculo(String nombre, int horasDirectas, int horasIndependientes,
			Materia materia) {
		super();
		this.nombre = nombre;
		this.horasDirectas = horasDirectas;
		this.horasIndependientes = horasIndependientes;
		this.materia = materia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getHorasDirectas() {
		return horasDirectas;
	}

	public void setHorasDirectas(int horasDirectas) {
		this.horasDirectas = horasDirectas;
	}

	public int getHorasIndependientes() {
		return horasIndependientes;
	}

	public void setHorasIndependientes(int horasIndependientes) {
		this.horasIndependientes = horasIndependientes;
	}

	public List<Planeador> getPlaneadores() {
		return planeadores;
	}

	public void setPlaneadores(List<Planeador> planeadores) {
		this.planeadores = planeadores;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	

}