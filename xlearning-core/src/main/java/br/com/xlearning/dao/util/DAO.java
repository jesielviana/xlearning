package br.com.xlearning.dao.util;

import java.io.Serializable;

import javax.persistence.PersistenceException;

/**
 * 
 * @author jesiel
 * @Data: 2013
 * @param <T>
 */
public interface DAO<T extends Serializable> extends Serializable {

	public void adiciona(T obj) throws PersistenceException;

	public void remove(T obj) throws PersistenceException;

	public void atualiza(T obj) throws PersistenceException;

	public T buscaPorId(Long id) throws PersistenceException;
	
	public T buscaPorIdVarchar(String id) throws PersistenceException;
}
