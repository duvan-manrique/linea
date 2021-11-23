package com.duvan.linea.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.duvan.linea.entity.Rol;
import com.duvan.linea.exception.ArgumentRequiredException;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.repository.IRolRepo;
import com.duvan.linea.serv.IRolService;

@Service
public class RolServiceImp implements IRolService{

	@Autowired
	private IRolRepo rolRepo;
	
	@Override
	public Page<Rol> retornarOrdenadosNombre(int page, int size, int param) throws ModelNotFoundException, Exception {
		try {

			if (this.rolRepo.findAll(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				Page<Rol> rol = this.rolRepo.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending()));
				return rol;
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
	public Page<Rol> retornarOrdenados(String orden,String tipo, int param) throws ModelNotFoundException, Exception {
		try {

			if (this.rolRepo.findAll(PageRequest.of(param, 5)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {

				if(tipo.equals("asc")) {
					return this.rolRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).ascending()));
				}
				
				if(tipo.equals("des")) {
					return this.rolRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).descending()));
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
	public Page<Rol> retornarPaginado(int page, int size, int param) throws ModelNotFoundException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rol retonarPorId(Integer id) throws ModelNotFoundException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rol guardar(Rol object) throws ConflictException, Exception {
		try {
			if (this.rolRepo.existsByRol(object.getRol())) {
				throw new ConflictException("libro ya existente");
			} else {
				return this.rolRepo.save(object);
			}
	} catch (ConflictException e) {
		throw new ConflictException(e.getMessage());
	} catch (NullPointerException e) {
		throw new NullPointerException("Objeo libro nulo no asignado");
	} catch (Exception e) {
		throw new Exception("Error general: " + e.getMessage());
	}
	}

	@Override
	public Rol editar(Rol object)
			throws ArgumentRequiredException, ModelNotFoundException, ConflictException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(int object) throws ModelNotFoundException, Exception {
		try {
			if (this.rolRepo.existsById(object)) {
				this.rolRepo.deleteById(object);
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
