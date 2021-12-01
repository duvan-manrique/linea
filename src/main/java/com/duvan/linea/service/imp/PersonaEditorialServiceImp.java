package com.duvan.linea.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.duvan.linea.entity.Editorial;
import com.duvan.linea.entity.Persona;
import com.duvan.linea.entity.PersonaEditorial;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.repository.IPersonaEditorialRepo;
import com.duvan.linea.serv.IEditorialService;
import com.duvan.linea.serv.IPersonaEditorialService;
import com.duvan.linea.serv.IPersonaService;
import com.duvan.linea.view.IPersonaEditorialView;

@Service
public class PersonaEditorialServiceImp implements IPersonaEditorialService {

	@Autowired
	private IPersonaEditorialRepo personaEditorialRepo;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IEditorialService editorialService;
	
	@Override
	public PersonaEditorial guardarNuevos(PersonaEditorial pe) throws ConflictException, Exception {
		try {
			
			Persona p = this.personaService.guardar(pe.getAutor());
			Editorial e = this.editorialService.guardar(pe.getEditorial());
			
			PersonaEditorial rpe = new PersonaEditorial();
			rpe.setAutor(p);
			rpe.setEditorial(e);
			
			return this.personaEditorialRepo.save(rpe);
		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo libro nulo no asignado");
		} catch (Exception e) {
			throw new Exception("Error general: " + e.getMessage());
		}

	}

	@Override
	public PersonaEditorial relacionarExistentes(int idPersona, int idEditorial) throws ConflictException, Exception {
		try {
			
			System.out.println(idPersona);
			Persona p = this.personaService.retonarPorId(idPersona);
			Editorial e = this.editorialService.retonarPorId(idEditorial);
			
			PersonaEditorial rpe = new PersonaEditorial();
			rpe.setAutor(p);
			rpe.setEditorial(e);
			
			return this.personaEditorialRepo.save(rpe);
			
		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo libro nulo no asignado");
		} catch (Exception e) {
			throw new Exception("Error general: " + e.getMessage());
		}
	}

	@Override
	public Page<IPersonaEditorialView> listarPersonaEditorialPage(int page, int size)
			throws ModelNotFoundException, Exception {
		try {

			if (this.personaEditorialRepo.obtenerRelaciones(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				
				return this.personaEditorialRepo.obtenerRelaciones(PageRequest.of(page, size, Sort.by("id").descending()));

			}

		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public Page<IPersonaEditorialView> listarPersonaEditorialPageSort(int page, int size, String option, String tipo)
			throws ModelNotFoundException, Exception {
		// TODO Auto-generated method stub
		try {

			if (this.personaEditorialRepo.obtenerRelaciones(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				
				if(tipo.equals("asc")) {
					return this.personaEditorialRepo.obtenerRelaciones(PageRequest.of(page, size, Sort.by(option).ascending()));
				}
				
				if(tipo.equals("des")) {
					return this.personaEditorialRepo.obtenerRelaciones(PageRequest.of(page, size, Sort.by(option).descending()));
				}
				
				return null;

			}

		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public void eliminar(int id) throws ModelNotFoundException, Exception {
		try {
			if (this.personaEditorialRepo.existsById(id)) {
				this.personaEditorialRepo.deleteById(id);
			} else {
				throw new ModelNotFoundException("id no existente");
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}
	
}
