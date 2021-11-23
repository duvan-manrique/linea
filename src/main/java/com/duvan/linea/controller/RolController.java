package com.duvan.linea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duvan.linea.entity.Rol;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.serv.IRolService;

@RestController
@RequestMapping("/roles")
public class RolController {

	@Autowired
	private IRolService rolService;
	
	@PostMapping("/guardar")
	public ResponseEntity<Rol> registrarPersona(@RequestBody Rol rol) throws ConflictException, Exception{
		Rol p = this.rolService.guardar(rol);
		return new ResponseEntity<Rol>(p, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/obtenerOrden/{orden}/{tipo}/{page}" ,produces = "application/json")
	public ResponseEntity<Page<Rol>> retonarOrdenado(@PathVariable("orden")  String orden,@PathVariable("tipo")  String tipo,@PathVariable("page") int page) throws ModelNotFoundException, Exception {
		Page<Rol> users = this.rolService.retornarOrdenados(orden, tipo, page);
		return new ResponseEntity<Page<Rol>>(users, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/obtenerPaginadoNombre/{page}/{size}/{id_autor}" ,produces = "application/json")
	public ResponseEntity<Page<Rol>> retonarNombre(@PathVariable("page")  int page,@PathVariable("size")  int size,@PathVariable("id_autor")  int id_autor) throws ModelNotFoundException, Exception {
		Page<Rol> users = this.rolService.retornarOrdenadosNombre(page,size,id_autor);
		return new ResponseEntity<Page<Rol>>(users, HttpStatus.OK);
	}


	@DeleteMapping(value = "/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id)throws ModelNotFoundException , Exception {
		this.rolService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
