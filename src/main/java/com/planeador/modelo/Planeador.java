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

import com.planeador.modelo.enums.TipoCurso;

@Entity
@Table(name = "planeador")
public class Planeador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "fecha_registro")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name = "microcurriculo_id")
	private Microcurriculo microcurriculo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_curso")
	private TipoCurso tipoCurso;
	
    @ManyToOne
	@JoinColumn(name = "docente_id")
	private Docente docente;
	
	@OneToMany(mappedBy = "planeador")
	List<InstrumentoEvaluacion> instrumentosEvaluacion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Microcurriculo getMicrocurriculo() {
		return microcurriculo;
	}

	public void setMicrocurriculo(Microcurriculo microcurriculo) {
		this.microcurriculo = microcurriculo;
	}
	
	public TipoCurso getTipoCurso() {
		return tipoCurso;
	}

	public void setTipoCurso(TipoCurso tipoCurso) {
		this.tipoCurso = tipoCurso;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<InstrumentoEvaluacion> getInstrumentosEvaluacion() {
		return instrumentosEvaluacion;
	}

	public void setInstrumentosEvaluacion(List<InstrumentoEvaluacion> instrumentosEvaluacion) {
		this.instrumentosEvaluacion = instrumentosEvaluacion;
	}


	public Planeador() {
		super();
	}

	public Planeador(LocalDate fechaRegistro, Microcurriculo microcurriculo, TipoCurso tipoCurso, Docente docente) {
		super();
		this.fechaRegistro = fechaRegistro;
		this.microcurriculo = microcurriculo;
		this.tipoCurso = tipoCurso;
		this.docente = docente;
	}
		
}
