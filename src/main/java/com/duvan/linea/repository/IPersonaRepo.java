package com.duvan.linea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.duvan.linea.entity.Persona;

@Repository
public interface IPersonaRepo extends JpaRepository<Persona, Integer>{
	public Persona findOneByCorreo(String correo);
	public Persona findByCedula(String cedula);
	public Persona findByCorreo(String correo);
	public Boolean existsByCedula(String cedula);
	public Boolean existsByCorreo(String correo);
	
	//SQL Nativa	
		
	@Query(value = "SELECT  count(correo) from persona where correo = ?1", nativeQuery  = true)
	Integer cantidadCorreo(String correo);
	
	@Query(value = "SELECT  count(cedula) from persona where cedula = ?1", nativeQuery  = true)
	Integer cantidadCedula(String cedula);
}
