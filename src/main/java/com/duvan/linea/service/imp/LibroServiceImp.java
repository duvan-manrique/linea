package com.duvan.linea.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.duvan.linea.entity.Libro;
import com.duvan.linea.entity.Persona;
import com.duvan.linea.exception.ArgumentRequiredException;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.repository.ILibroRepo;
import com.duvan.linea.repository.IPersonaRepo;
import com.duvan.linea.serv.ILibroService;

@Service
public class LibroServiceImp implements ILibroService {

	@Autowired
	private ILibroRepo libroRepo;

	@Autowired
	private IPersonaRepo personaRepo;

	@Override
	public Page<Libro> retornarOrdenadosNombre(int page, int size, int param) throws ModelNotFoundException, Exception {

		try {
			if (!this.personaRepo.existsById(param)) {
				throw new ModelNotFoundException("autor no existente");
			}
			Page<Libro> Libs = this.libroRepo
					.buscarConsultaAutor(PageRequest.of(page, size, Sort.by("nombre").descending()), param).map(p -> {
						p.setAutor_libro(null);
						return p;
					});
			if (Libs.equals(null)) {
				throw new ModelNotFoundException("no hay resultados de la busqueda");
			}
			return Libs;

		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public Page<Libro> retornarOrdenados(String orden, String tipo, int param)
			throws ModelNotFoundException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Libro> retornarPaginado(int page, int size, int param) throws ModelNotFoundException, Exception {
		try {
			if (!this.personaRepo.existsById(param)) {
				throw new ModelNotFoundException("autor no existente");
			}
			if (this.libroRepo.buscarConsultaAutorOrdenada(PageRequest.of(page, size), param).isEmpty()) {
				throw new ModelNotFoundException("lista de libros vacia");
			} else {
				Page<Libro> libs = this.libroRepo.buscarConsultaAutorOrdenada(PageRequest.of(page, size), param)
						.map(p -> {
							p.setAutor_libro(null);
							return p;
						});
				return libs;
			}

		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Libro retonarPorId(Integer id) throws ModelNotFoundException, Exception {
		try {
			Libro l = this.libroRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("libro no encontrado"));
			Persona per = new Persona();
			per.setApellido(l.getAutor_libro().getApellido());
			per.setNombre(l.getAutor_libro().getNombre());
			per.setId(l.getAutor_libro().getId());
			l.setAutor_libro(per);
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
	public Libro guardar(Libro object) throws ConflictException, Exception {
		//System.out.println(object.getAutor_libro().getId());
		try {
			if (this.libroRepo.existsByNombre(object.getNombre())) {
				throw new ConflictException("libro ya existente");
			} else {
				Persona p = this.personaRepo.findById(object.getAutor_libro().getId())
						.orElseThrow(() -> new ModelNotFoundException("autor no encontrado"));
				object.setAutor_libro(p);
				Libro l = this.libroRepo.save(object);
				l.setAutor_libro(null);
				return l;
			}
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
	public Libro editar(Libro object)
			throws ArgumentRequiredException, ModelNotFoundException, ConflictException, Exception {
		try {
			if (this.libroRepo.existsByNombre(object.getNombre())) {
				throw new ConflictException("libro ya existente");
			} else {
				Libro l = this.libroRepo.findById(object.getId())
						.orElseThrow(() -> new ModelNotFoundException("libro no encontrado"));
				if (object.getNombre() != l.getNombre()) {
					l.setNombre(object.getNombre());
				}

				if (object.getDescripcion() != l.getDescripcion()) {
					l.setDescripcion(object.getDescripcion());
				}

				if (object.getNumeroPaginas() != l.getNumeroPaginas()) {
					l.setNumeroPaginas(object.getNumeroPaginas());
				}

				Libro l2 = this.libroRepo.save(l);
				l2.setAutor_libro(null);
				return l2;

			}
		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo libro nulo no asignado");
		} catch (Exception e) {
			throw new Exception("Error general: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(int object) throws ModelNotFoundException, Exception {
		try {
			if (this.libroRepo.existsById(object)) {
				this.libroRepo.deleteById(object);
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
