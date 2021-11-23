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

import com.duvan.linea.dto.PersonaEditorialDTO;
import com.duvan.linea.entity.PersonaEditorial;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.serv.IPersonaEditorialService;
import com.duvan.linea.view.IPersonaEditorialView;

@RestController
@RequestMapping("/relacion")
public class PersonaEditorialController {

	@Autowired
	private IPersonaEditorialService editorialService;
	
	@PostMapping("/guardarNuevos")
	public ResponseEntity<PersonaEditorial> registrarNuevos(@RequestBody PersonaEditorial pe) throws ConflictException, Exception{
		PersonaEditorial p = this.editorialService.guardarNuevos(pe);
		return new ResponseEntity<PersonaEditorial>(p, HttpStatus.CREATED);
	}
	
	@PostMapping("/guardarExistentes")
	public ResponseEntity<PersonaEditorial> registrarExistentes(@RequestBody PersonaEditorialDTO pe) throws ConflictException, Exception{
		System.out.println(pe.getIdAutor());
		PersonaEditorial p = this.editorialService.relacionarExistentes(pe.getIdAutor(), pe.getIdEditorial());
		return new ResponseEntity<PersonaEditorial>(p, HttpStatus.CREATED);
	}
	
	@GetMapping("/listar/{page}/{size}")
	public ResponseEntity<Page<IPersonaEditorialView>> listarPaginado(@PathVariable("page") int page, @PathVariable("size") int size) throws ModelNotFoundException, Exception{
		Page<IPersonaEditorialView> editoriales = this.editorialService.listarPersonaEditorialPage(page, size);
		return new ResponseEntity<Page<IPersonaEditorialView>>(editoriales, HttpStatus.OK);
	}
	
	@GetMapping("/listarSort/{page}/{size}/{option}/{tipo}")
	public ResponseEntity<Page<IPersonaEditorialView>> listarPaginado(@PathVariable("page") int page, @PathVariable("size") int size,@PathVariable("option") String option, @PathVariable("tipo") String tipo) throws ModelNotFoundException, Exception{
		Page<IPersonaEditorialView> editoriales = this.editorialService.listarPersonaEditorialPageSort(page, size, option, tipo);
		return new ResponseEntity<Page<IPersonaEditorialView>>(editoriales, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id)throws ModelNotFoundException , Exception {
		this.editorialService.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
}
