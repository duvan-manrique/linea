package com.duvan.linea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "libro")
public class Libro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Nombre es obligatorio")
	@Size(min = 3, max = 50, message = "El nombre debe estar entre 3 y 50 caracteres")
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;	
	
	@NotNull(message = "descripcion es obligatorio")
	@Size(min = 3, max = 50, message = "la descripcion debe estar entre 3 y 50 caracteres")
	@Column(name = "descripcion", length = 50, nullable = false)
	private String descripcion;	
	
	@NotNull(message = "numero es obligatorio")
	@Column(name = "numero_paginas", nullable = false)
	private Integer numeroPaginas;
	/*
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha", nullable = false)
	private Thymeleaf fecha;
	*/
	
	@ManyToOne
	@JoinColumn(name = "id_autor", nullable = false, foreignKey = @ForeignKey(name = "FK_Autor_Libro"))
	private Persona autor_libro;
	
	
	
	public Libro() {
		
	}

	public Libro(String nombre, String descripcion, Integer numeroPaginas) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.numeroPaginas = numeroPaginas;
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public Persona getAutor_libro() {
		return autor_libro;
	}

	public void setAutor_libro(Persona autor_libro) {
		this.autor_libro = autor_libro;
	}
	
}
