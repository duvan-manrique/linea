package com.duvan.linea.serv;

import org.springframework.data.domain.Page;
import com.duvan.linea.exception.ArgumentRequiredException;
import com.duvan.linea.exception.ConflictException;
import com.duvan.linea.exception.ModelNotFoundException;




public interface ICrud<T, ID> {
	
	public Page<T> retornarOrdenadosNombre(int page, int size, int param) throws ModelNotFoundException, Exception;
	
	public Page<T> retornarOrdenados(String orden, String tipo, int param) throws ModelNotFoundException, Exception ;
	
	public Page<T> retornarPaginado(int page, int size, int param)throws ModelNotFoundException,Exception;
	
	public T retonarPorId(ID id) throws ModelNotFoundException,Exception;
		
	public T guardar(T object)  throws ConflictException,Exception;
	
	public T editar(T object)  throws ArgumentRequiredException, ModelNotFoundException, ConflictException,Exception;
	
	public void eliminar(int object) throws ModelNotFoundException,Exception;	

}
