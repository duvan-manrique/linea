package com.duvan.linea.serv;

import org.springframework.data.domain.Page;

import com.duvan.linea.entity.PersonaEditorial;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.view.IPersonaEditorialView;

public interface IPersonaEditorialService{
	PersonaEditorial guardarNuevos(PersonaEditorial pe) throws ConflictException,Exception;
	PersonaEditorial relacionarExistentes(int idPersona, int idEditorial) throws ConflictException,Exception;
	Page<IPersonaEditorialView> listarPersonaEditorialPage(int page, int size) throws ModelNotFoundException, Exception ;
	Page<IPersonaEditorialView> listarPersonaEditorialPageSort(int page, int size, String option, String tipo)throws ModelNotFoundException, Exception ;
	void eliminar(int id)throws ModelNotFoundException,Exception;
}
