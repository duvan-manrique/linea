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
		
	@Query(value = "SELECT  count(correo) from autor where correo = ?1 and id !=  ?2 ", nativeQuery  = true)
	Integer cantidadCorreo(String correo,int id);
}
