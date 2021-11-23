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

import com.duvan.linea.entity.Persona;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.serv.IPersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService personaService;
	
	@PostMapping("/registro")
	public ResponseEntity<Persona> registrarPersona(@RequestBody Persona persona) throws ConflictException, Exception{
		Persona p = this.personaService.guardar(persona);
		return new ResponseEntity<Persona>(p, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/persona/obtenerOrden/{orden}/{tipo}/{page}" ,produces = "application/json")
	public ResponseEntity<Page<Persona>> retonarOrdenado(@PathVariable("orden")  String orden,@PathVariable("tipo")  String tipo,@PathVariable("page") int page) throws ModelNotFoundException, Exception {
		Page<Persona> users = this.personaService.retornarOrdenados(orden, tipo, page);
		return new ResponseEntity<Page<Persona>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/persona/obtenerPaginado/{page}/{size}" ,produces = "application/json")
	public ResponseEntity<Page<Persona>> retonar(@PathVariable("page")  int page,@PathVariable("size")  int size) throws ModelNotFoundException, Exception {
		Page<Persona> users = this.personaService.retornarPaginado(page,size,0);
		return new ResponseEntity<Page<Persona>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/persona/obtenerPaginadoNombre/{page}/{size}" ,produces = "application/json")
	public ResponseEntity<Page<Persona>> retonarNombre(@PathVariable("page")  int page,@PathVariable("size")  int size) throws ModelNotFoundException, Exception {
		Page<Persona> users = this.personaService.retornarPaginado(page,size,0);
		return new ResponseEntity<Page<Persona>>(users, HttpStatus.OK);
		
	}	
	
	@GetMapping(value = "/persona/obtener/{id}" ,produces = "application/json")
	public ResponseEntity<Persona> retonar(@PathVariable("id")  int id) throws ModelNotFoundException, Exception {
		Persona user = this.personaService.retonarPorId(id);
		return new ResponseEntity<Persona>(user, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/persona/editar", consumes = "application/json")
	public ResponseEntity<Persona> editar(@Validated @RequestBody Persona au)throws ModelNotFoundException , Exception {
		Persona p = this.personaService.editar(au);
		return new ResponseEntity<Persona>(p, HttpStatus.OK);
	}	 
	

	@DeleteMapping(value = "/persona/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id)throws ModelNotFoundException , Exception {
		this.personaService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
