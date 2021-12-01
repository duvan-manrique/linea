package com.duvan.linea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duvan.linea.entity.Editorial;

@Repository
public interface IEditorialRepo extends JpaRepository<Editorial, Integer>{
	Boolean existsByNombre(String nombre);
	Boolean existsByCorreo(String correo);
	Boolean existsByNit(String correo);
	
}
