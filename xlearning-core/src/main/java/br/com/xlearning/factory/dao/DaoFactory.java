/**
 * 
 */
package br.com.xlearning.factory.dao;

import java.lang.reflect.ParameterizedType;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.xlearning.dao.util.GenericDAO;

/**
 * @author jesiel
 *
 */
@SuppressWarnings("rawtypes")
public class DaoFactory {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Produces
	  public GenericDAO create(InjectionPoint injectionPoint) {
	    ParameterizedType type = (ParameterizedType) injectionPoint.getType();
	    Class classe = (Class) type.getActualTypeArguments()[0];
	    return new GenericDAO(classe, entityManager);
	  }
}
