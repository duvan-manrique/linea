package com.duvan.linea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duvan.linea.entity.Libro;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.serv.ILibroService;

@RestController
@RequestMapping("/libros")
public class LibroController {
	
	@Autowired
	private ILibroService libroService;

	@PostMapping("/libro/guardar")
	public ResponseEntity<Libro> guardarLibro(@RequestBody Libro libro) throws ConflictException, Exception{
		System.out.println(libro.getAutor_libro().getId());
		Libro l = this.libroService.guardar(libro);
		return new ResponseEntity<Libro>(l, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/obtenerPaginado/{page}/{size}/{id_autor}" ,produces = "application/json")
	public ResponseEntity<?> retonar(@PathVariable("page")  int page,@PathVariable("size")  int size,@PathVariable("id_autor") int id_autor) throws ModelNotFoundException, Exception {
		Page<Libro> users = this.libroService.retornarPaginado(page,size,id_autor);
		return new ResponseEntity<Page<Libro>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/obtenerPaginadoNombre/{page}/{size}/{id_autor}" ,produces = "application/json")
	public ResponseEntity<?> retonarNombre(@PathVariable("page")  int page,@PathVariable("size")  int size,@PathVariable("id_autor")  int id_autor) throws ModelNotFoundException, Exception {
		Page<Libro> users = this.libroService.retornarOrdenadosNombre(page,size,id_autor);
		return new ResponseEntity<Page<Libro>>(users, HttpStatus.OK);
	}	
	
	@GetMapping(value = "/obtener/{id}" ,produces = "application/json")
	public ResponseEntity<Libro> retonar(@PathVariable("id")  int id) throws ModelNotFoundException, Exception {
		Libro user = this.libroService.retonarPorId(id);
		return new ResponseEntity<Libro>(user, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/editar", consumes = "application/json")
	public ResponseEntity<Libro> editar(@Validated @RequestBody Libro au)throws ModelNotFoundException , Exception {
		Libro l = this.libroService.editar(au);
		return new ResponseEntity<Libro>(l, HttpStatus.OK);
	}	 
	

	@DeleteMapping(value = "/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id)throws ModelNotFoundException , Exception {
		this.libroService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}	
	
}
