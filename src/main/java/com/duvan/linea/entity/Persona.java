package com.duvan.linea.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "persona")
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Cedula es obligatorio")
	@Size(min = 7, max = 12, message = "El Cedula debe estar entre 7 y 12 caracteres")
	@Column(name = "cedula", length = 12, nullable = false, unique = true)
	private String cedula;	
	
	@NotNull(message = "Nombre es obligatorio")
	@Size(min = 3, max = 15, message = "El nombre debe estar entre 3 y 15 caracteres")
	@Column(name = "nombre", length = 15, nullable = false)
	private String nombre;
	
	@NotNull(message = "Apellido es obligatorio")
	@Size(min = 3, max = 15, message = "El apellido debe estar entre 3 y 15 caracteres")	
	@Column(name = "apellido", length = 15, nullable = false)
	private String apellido;
	
	@NotNull(message = "correo es obligatorio")
	@Email(message = "Email incorrecto")
	@Column(name = "correo", length = 60, nullable = false, unique = true,columnDefinition = "text")
	private String correo;
	
	@NotNull(message = "el campo es obligatorio")
	@Column(name = "clave", length = 60, nullable = false, unique = true,columnDefinition = "text")
	private String clave;
	
	@ManyToOne
	@JoinColumn(name = "rol_id", foreignKey = @ForeignKey(name = "FK_rol"))
    private Rol rol;

	@JsonIgnoreProperties(value={"autor_libro", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(mappedBy = "autor_libro", cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Libro> libro;
	
	@JsonIgnoreProperties(value={"autor", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(mappedBy="autor", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<PersonaEditorial> autor_editorial;
	
	public Persona() {
		super();
	}

	public Persona(String cedula, String nombre, String apellido, String correo) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public List<Libro> getLibro() {
		return libro;
	}

	public void setLibro(List<Libro> libro) {
		this.libro = libro;
	}

	public List<PersonaEditorial> getAutor_editorial() {
		return autor_editorial;
	}

	public void setAutor_editorial(List<PersonaEditorial> autor_editorial) {
		this.autor_editorial = autor_editorial;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}		

}
