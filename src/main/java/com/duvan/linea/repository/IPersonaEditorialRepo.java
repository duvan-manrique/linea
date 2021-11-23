package com.duvan.linea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.duvan.linea.entity.PersonaEditorial;
import com.duvan.linea.view.IPersonaEditorialView;

@Repository
public interface IPersonaEditorialRepo extends JpaRepository<PersonaEditorial, Integer>{

	@Query(value = "SELECT ae.id, p.nombre AS nombre_autor, p.correo AS correo_autor, e.nombre AS nombre_editorial, e.correo AS correo_editorial"
			+ "  FROM public.autor_editorial ae JOIN public.persona p ON ae.autor_id = p.id JOIN public.editorial e ON ae.editorial_id = e.id", nativeQuery = true)
	Page<IPersonaEditorialView> obtenerRelaciones(Pageable page);
}
