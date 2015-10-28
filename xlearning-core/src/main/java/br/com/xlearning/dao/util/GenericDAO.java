/**
 * 
 */
package br.com.xlearning.dao.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;


/**
 * @author jesiel
 * 
 */
public class GenericDAO<T extends Serializable> implements DAO<T> {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	private final Class<T> classe;

	public GenericDAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public GenericDAO(Class<T> classe, EntityManager entityManager) {
		this.classe = classe;
		this.entityManager = entityManager;
	}

	public void adiciona(T obj) throws PersistenceException{
			entityManager.persist(obj);
	}

	public void remove(T obj) throws PersistenceException{
		entityManager.remove(obj);
	}

	public void atualiza(T obj) throws PersistenceException{
		entityManager.merge(obj);
	}

	public T buscaPorId(Long id) throws PersistenceException{
		return entityManager.find(classe, id);
	}
	
	public T buscaPorIdVarchar(String id) throws PersistenceException{
		return entityManager.find(classe, id);
	}
	
	 public List<T> listaTodos() throws PersistenceException{
		    CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
		    query.from(classe);
		    return entityManager.createQuery(query).getResultList();
		  }
	 
	 public void flush()
	 {
		 entityManager.flush();
	 }

}
