package com.duvan.linea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duvan.linea.entity.Libro;

@Repository
public interface ILibroRepo extends JpaRepository<Libro, Integer> {

		public Boolean existsByNombre(String nombre);
	
	//SQL Nativa	
		//@Query(value = "select count(*) from libro u where u.id_autor = : id_autor", nativeQuery  = true)
		//Object cantidadlibro(@Param("correo") String id_autor);
	//jpql
		@Query(value = "SELECT u FROM Libro u WHERE u.autor_libro.id = :id_autor  ORDER BY u.nombre")
		Page<Libro> buscarConsultaAutorOrdenada(Pageable page,@Param("id_autor")int id_autor);
		
		@Query(value = "SELECT u FROM Libro u WHERE u.autor_libro.id = :id_autor")
		Page<Libro> buscarConsultaAutor(Pageable page,@Param("id_autor") int id_autor);
}
