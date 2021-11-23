package com.duvan.linea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duvan.linea.entity.Rol;

@Repository
public interface IRolRepo extends JpaRepository<Rol, Integer>{
	Boolean existsByRol(String rol);
}
