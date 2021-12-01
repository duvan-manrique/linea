package com.duvan.linea.service.imp;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.duvan.linea.entity.Persona;
import com.duvan.linea.exception.ArgumentRequiredException;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;
import com.duvan.linea.repository.IPersonaRepo;
import com.duvan.linea.repository.IRolRepo;
import com.duvan.linea.serv.IPersonaService;

@Service
public class PersonaServiceImp implements IPersonaService, UserDetailsService {

	@Autowired
	private IPersonaRepo personaRepo;

	@Autowired
	private IRolRepo rolRepo;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Persona p = this.personaRepo.findOneByCorreo(username);
		if (p == null)
			throw new UsernameNotFoundException("Usuario no encontrado");

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(p.getRol().getRol()));

		UserDetails ud = new User(p.getCorreo(), p.getClave(), roles);

		return ud;
	}

	@Override
	public Page<Persona> retornarOrdenadosNombre(int page, int size, int param)
			throws ModelNotFoundException, Exception {
		try {

			if (this.personaRepo.findAll(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				Page<Persona> autors = this.personaRepo
						.findAll(PageRequest.of(page, size, Sort.by("nombre").ascending())).map(p -> {
							p.setLibro(null);
							p.setAutor_editorial(null);
							p.setRol(null);
							return p;
						});
				return autors;
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
	public Page<Persona> retornarOrdenados(String orden, String tipo, int param)
			throws ModelNotFoundException, Exception {
		try {

			if (this.personaRepo.findAll(PageRequest.of(param, 5)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {

				if (tipo.equals("asc")) {
					return this.personaRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).ascending())).map(p -> {
						p.setLibro(null);
						p.setAutor_editorial(null);
						p.setRol(null);
						return p;
					});
				}

				if (tipo.equals("des")) {
					return this.personaRepo.findAll(PageRequest.of(param, 5, Sort.by(orden).descending())).map(p -> {
						p.setLibro(null);
						p.setAutor_editorial(null);
						p.setRol(null);
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
	public Page<Persona> retornarPaginado(int page, int size, int param) throws ModelNotFoundException, Exception {
		try {

			if (this.personaRepo.findAll(PageRequest.of(page, size)).isEmpty()) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				Page<Persona> autors = this.personaRepo.findAll(PageRequest.of(page, size)).map(p -> {
					p.setAutor_editorial(null);
					p.setLibro(null);
					p.setRol(null);
					return p;
				});
				return autors;
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
	public Persona retonarPorId(Integer id) throws ModelNotFoundException, Exception {
		try {
			return this.personaRepo.findById(id).orElseThrow(() -> new ModelNotFoundException("autor no encontrado"));
		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public Persona guardar(Persona object) throws ConflictException, Exception {

		try {
			if (this.personaRepo.existsByCedula(object.getCedula())) {
				throw new ConflictException("Cedula ya existente");
			}
			if (this.personaRepo.existsByCorreo(object.getCorreo())) {
				throw new ConflictException("Correo ya existente");
			}

			if (!this.rolRepo.existsById(object.getRol().getId())) {
				throw new ModelNotFoundException("lista vacia");
			} else {
				String clave = this.bcrypt.encode(object.getClave());
				object.setClave(clave);

				return this.personaRepo.save(object);
			}

		} catch (NullPointerException e) {
			throw new NullPointerException("Objeo nulo no asignado");
		} catch (ModelNotFoundException e) {
			throw new ModelNotFoundException(e.getMessage());
		} catch (ConflictException e) {
			throw new ConflictException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error general");
		}
	}

	@Override
	public Persona editar(Persona object)
			throws ArgumentRequiredException, ModelNotFoundException, ConflictException, Exception {
		try {
			
			Persona l = this.personaRepo.findById(object.getId())
					.orElseThrow(() -> new ModelNotFoundException("autor no encontrado"));

			if (!l.getCorreo().equals(object.getCorreo())) {
				if (this.personaRepo.cantidadCorreo(object.getCorreo()) == 1) {
					throw new ConflictException("correo ya existente");
				} else {
					l.setCorreo(object.getCorreo());
				}
			}

			if (!object.getCedula().equals(l.getCedula())) {
				if (this.personaRepo.cantidadCedula(object.getCedula()) == 1) {
					throw new ConflictException("Cedula ya existente");
				} else {
					l.setCedula(object.getCedula());
				}
			}

			if (!object.getNombre().equals(l.getNombre())) {
				l.setNombre(object.getNombre());
			}

			if (!object.getApellido().equals(l.getApellido())) {
				l.setApellido(object.getApellido());
			}

			if (!object.getClave().equals(object.getClave())) {
				String clave = this.bcrypt.encode(object.getClave());
				l.setClave(clave);
			}

			Persona l2 = this.personaRepo.save(l);
			l2.setLibro(null);
			l2.setAutor_editorial(null);
			return l2;

		} catch (

		ConflictException e) {
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
		// TODO Auto-generated method stub
		try {
			if (this.personaRepo.existsById(object)) {
				this.personaRepo.deleteById(object);
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
