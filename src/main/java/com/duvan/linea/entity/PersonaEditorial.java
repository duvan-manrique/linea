package com.duvan.linea.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "autor_editorial")
public class PersonaEditorial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="autor_id", foreignKey = @ForeignKey(name = "FK_autor"), nullable = false)
	@JsonIgnoreProperties(value = {"autor_editorial", "hibernateLazyInitializer"}, allowSetters = true)
	private Persona autor;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="editorial_id", foreignKey = @ForeignKey(name = "FK_editorial"), nullable = false)
	@JsonIgnoreProperties(value = {"autor_editorial", "hibernateLazyInitializer"}, allowSetters = true)
	private Editorial editorial;
	
	
	public PersonaEditorial() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Persona getAutor() {
		return autor;
	}

	public void setAutor(Persona autor) {
		this.autor = autor;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

}
