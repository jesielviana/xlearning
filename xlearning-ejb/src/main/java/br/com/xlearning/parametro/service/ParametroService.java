package br.com.xlearning.parametro.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.parametro.entidade.Parametro;

/**
 * 
 * @author jesiel
 *
 */
public interface ParametroService {
	
	public void adicionarParametro(Parametro parametro) throws PersistenceException, EJBException;
	
	public void alterarParametro(Parametro parametro) throws PersistenceException, EJBException;
	
	public int getParametroInteiro(int parametro) throws PersistenceException, EJBException;
	
	public double getParametroReal(int parametro) throws PersistenceException, EJBException;
	
	public String getParametroString(int parametro) throws PersistenceException, EJBException;
	
	public boolean getParametroBooleano(int parametro) throws PersistenceException, EJBException;
	
	public List<Parametro> getTodosParametros() throws PersistenceException, EJBException;

}
