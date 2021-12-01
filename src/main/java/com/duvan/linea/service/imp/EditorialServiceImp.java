package com.duvan.linea.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.duvan.linea.entity.Editorial;
import com.duvan.linea.exception.ArgumentRequiredException;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.repository.IEditorialRepo;
import com.duvan.linea.serv.IEditorialService;

@Service
public class EditorialServiceImp implements IEditorialService {

	@Autowired
	private IEditorialRepo editorialRepo;

	@Override
	public Page<Editorial> retornarOrdenadosNombre(int page, int size, int param)
			throws ModelNotFoundException, Exception {
		try {

			if (this.editorialRepo.findAll(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				Page<Editorial> editorial = this.editorialRepo
						.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending())).map(p -> {
							p.setAutor_editorial(null);
							return p;
						});
				return editorial;
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
	public Page<Editorial> retornarOrdenados(String orden, String tipo, int param)
			throws ModelNotFoundException, Exception {
		try {

			if (this.editorialRepo.findAll(PageRequest.of(param, 5)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {

				if (tipo.equals("asc")) {
					return this.editorialRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).ascending())).map(p -> {
						p.setAutor_editorial(null);
						return p;
					});
				}

				if (tipo.equals("des")) {
					return this.editorialRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).descending())).map(p -> {
						p.setAutor_editorial(null);
						return p;
					});
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
	public Page<Editorial> retornarPaginado(int page, int size, int param) throws ModelNotFoundException, Exception {
		try {

			if (this.editorialRepo.findAll(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				Page<Editorial> editorial = this.editorialRepo.findAll(PageRequest.of(page, size)).map(p -> {
					p.setAutor_editorial(null);
					return p;
				});
				return editorial;
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
	public Editorial retonarPorId(Integer id) throws ModelNotFoundException, Exception {
		try {
			Editorial l = this.editorialRepo.findById(id)
					.orElseThrow(() -> new ModelNotFoundException("libro no encontrado"));
			l.setAutor_editorial(null);
			return l;
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public Editorial guardar(Editorial object) throws ConflictException, Exception {
		try {
			if (this.editorialRepo.existsByNombre(object.getNombre())) {
				throw new ConflictException("editorial ya existente");
			} else {
				if (this.editorialRepo.existsByCorreo(object.getCorreo())) {
					throw new ConflictException("correo editorial ya existente");
				} else {
					Editorial ed = this.editorialRepo.save(object);
					ed.setAutor_editorial(null);
					return ed;
				}
			}
		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo editorial nulo no asignado");
		} catch (Exception e) {
			throw new Exception("Error general: " + e.getMessage());
		}
	}

	@Override
	public Editorial editar(Editorial object)
			throws ArgumentRequiredException, ModelNotFoundException, ConflictException, Exception {
		try {

			Editorial l = this.editorialRepo.findById(object.getId())
					.orElseThrow(() -> new ModelNotFoundException("editorial no encontrado"));
			if (!object.getNombre().equals(l.getNombre())) {
				if (this.editorialRepo.existsByNombre(object.getNombre())) {
					throw new ConflictException("nombre ya existente");
				} else
					l.setNombre(object.getNombre());
			}

			if (!object.getNit().equals(l.getNit())) {
				if (this.editorialRepo.existsByNit(object.getNit())) {
					throw new ConflictException("Nit ya existente");
				} else
					l.setNit(object.getNit());
			}

			if (!object.getCorreo().equals(l.getCorreo())) {
				if (this.editorialRepo.existsByCorreo(object.getCorreo())) {
					throw new ConflictException("Correo ya existente");
				} else
					l.setCorreo(object.getCorreo());
			}

			Editorial l2 = this.editorialRepo.save(l);
			l2.setAutor_editorial(null);
			return l2;

		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo libro nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(int object) throws ModelNotFoundException, Exception {
		try {
			if (this.editorialRepo.existsById(object)) {
				this.editorialRepo.deleteById(object);
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
