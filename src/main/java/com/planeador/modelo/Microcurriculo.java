package com.planeador.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.planeador.modelo.enums.TipoCurso;

@Entity
@Table(name = "microcurriculo")

public class Microcurriculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoCurso tipo_Curso;

	@Column
	private int horas_directas;

	@Column
	private int horas_independientes;

	@Column
	private LocalDate fecha_registro;

	@ManyToOne
	@JoinColumn(name = "materia_id")
	private Materia materia;

//	Por hacer:
//	Arreglo de Clase unidad

	public Microcurriculo() {
		super();
	}

}