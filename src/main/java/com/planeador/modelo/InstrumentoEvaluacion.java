package com.planeador.modelo;

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

import com.planeador.modelo.enums.TipoInstrumentoEvaluacion;

@Entity
@Table(name = "instrumento_evaluacion")
public class InstrumentoEvaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "planeador_id")
	private Planeador planeador;

	@JoinColumn(name = "corte")
	private int corte;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_instrumento")
	private TipoInstrumentoEvaluacion tipoInstrumento;
	
	@Column(name = "porcentaje_calificacion")
	private float porcentajeCalificacion;
	
	@Column(name = "enlace_recurso")
	private String enlaceRecurso;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Planeador getPlaneador() {
		return planeador;
	}

	public void setPlaneador(Planeador planeador) {
		this.planeador = planeador;
	}

	public int getCorte() {
		return corte;
	}

	public void setCorte(int corte) {
		this.corte = corte;
	}

	public TipoInstrumentoEvaluacion getTipoInstrumento() {
		return tipoInstrumento;
	}

	public void setTipoInstrumento(TipoInstrumentoEvaluacion tipoInstrumento) {
		this.tipoInstrumento = tipoInstrumento;
	}

	public float getPorcentajeCalificacion() {
		return porcentajeCalificacion;
	}

	public void setPorcentajeCalificacion(float porcentajeCalificacion) {
		this.porcentajeCalificacion = porcentajeCalificacion;
	}

	public String getEnlaceRecurso() {
		return enlaceRecurso;
	}

	public void setEnlaceRecurso(String enlaceRecurso) {
		this.enlaceRecurso = enlaceRecurso;
	}
	
	public InstrumentoEvaluacion() {
		super();
	}

	public InstrumentoEvaluacion(String descripcion, Planeador planeador, int corte,
			TipoInstrumentoEvaluacion tipoInstrumento, float porcentajeCalificacion, String enlaceRecurso) {
		super();
		this.descripcion = descripcion;
		this.planeador = planeador;
		this.corte = corte;
		this.tipoInstrumento = tipoInstrumento;
		this.porcentajeCalificacion = porcentajeCalificacion;
		this.enlaceRecurso = enlaceRecurso;
	}
	
	
	
}
