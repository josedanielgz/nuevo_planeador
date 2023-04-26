package com.planeador.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "docente", uniqueConstraints = { @UniqueConstraint(name = "unique_email", columnNames = { "email" }) })
public class Docente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String nombre;

	@Column(unique = true)
	private String email;

	@Column
	private String password;

	@Column
	private Boolean aprobado;

	@OneToMany(mappedBy = "docente")
	private List<Curso> cursos;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAprobado() {
		return aprobado;
	}

	public void setAprobado(Boolean aprovado) {
		this.aprobado = aprovado;
	}

	public Docente() {
	};

	public Docente(String email, String password) {
		this.email = email;
		this.password = password;
		this.aprobado = false;
	}

	public Boolean isEmpty() {
		return this.id == 0;
	}

}
