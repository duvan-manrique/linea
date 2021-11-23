package com.duvan.linea.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class UsuarioDto {

	
	@NotNull (message = "Nombre vacio")
	@NotNull
	@Size(min = 3, max = 15, message = "longitud de la caden de 3 a 15 caracteres")
	private String nombre;
	@Min(value = 18 ,message = "debe ser mayor de edad (18)") 
	private int edad;
	@Size(min = 9, max = 10, message = "longitud de la caden de 9 a 10 caracteres numericos ")
	private String cedula;
	@Email
	private String correo;
	
	public UsuarioDto(String nombre, int edad, String cedula, String correo) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.cedula = cedula;
		this.correo = correo;
	}
	
	
	public UsuarioDto() {
		
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	
	
	
}
