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

import com.duvan.linea.entity.Editorial;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.serv.IEditorialService;

@RestController
@RequestMapping("/editoriales")
public class EditorialController {
	
	@Autowired
	private IEditorialService editorialService;
	
	@PostMapping("/guardar")
	public ResponseEntity<Editorial> registrarPersona(@RequestBody Editorial persona) throws ConflictException, Exception{
		Editorial p = this.editorialService.guardar(persona);
		return new ResponseEntity<Editorial>(p, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/obtenerOrden/{orden}/{tipo}/{page}" ,produces = "application/json")
	public ResponseEntity<Page<Editorial>> retonarOrdenado(@PathVariable("orden")  String orden,@PathVariable("tipo")  String tipo,@PathVariable("page") int page) throws ModelNotFoundException, Exception {
		Page<Editorial> users = this.editorialService.retornarOrdenados(orden, tipo, page);
		return new ResponseEntity<Page<Editorial>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	public ResponseEntity<Page<Editorial>> retonar(@PathVariable("page")  int page,@PathVariable("size")  int size) throws ModelNotFoundException, Exception {
		Page<Editorial> users = this.editorialService.retornarPaginado(page,size,0);
		return new ResponseEntity<Page<Editorial>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/obtenerPaginadoNombre/{page}/{size}" ,produces = "application/json")
	public ResponseEntity<Page<Editorial>> retonarNombre(@PathVariable("page")  int page,@PathVariable("size")  int size) throws ModelNotFoundException, Exception {
		Page<Editorial> users = this.editorialService.retornarPaginado(page,size,0);
		return new ResponseEntity<Page<Editorial>>(users, HttpStatus.OK);
		
	}	
	
	@GetMapping(value = "/obtener/{id}" ,produces = "application/json")
	public ResponseEntity<Editorial> retonar(@PathVariable("id")  int id) throws ModelNotFoundException, Exception {
		Editorial user = this.editorialService.retonarPorId(id);
		return new ResponseEntity<Editorial>(user, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/editar", consumes = "application/json")
	public ResponseEntity<Editorial> editar(@Validated @RequestBody Editorial au)throws ModelNotFoundException , Exception {
		Editorial p = this.editorialService.editar(au);
		return new ResponseEntity<Editorial>(p, HttpStatus.OK);
	}	 
	

	@DeleteMapping(value = "/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id)throws ModelNotFoundException , Exception {
		this.editorialService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
