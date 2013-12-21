package br.com.xlearning.parametro.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.parametro.entidade.Parametro;

public interface ParametroRepository {
	
	public void adiciona(Parametro parametro) throws PersistenceException;

	public void remove(Parametro parametro) throws PersistenceException;

	public void atualiza(Parametro parametro) throws PersistenceException;

	public Parametro buscaPorId(Long id) throws PersistenceException;
	
	public List<Parametro> listaTodos() throws PersistenceException;
	
	public int getParametroInteiro(int parametro);
	
	public String getParametroString(int parametro);
	
	public boolean getParametroBooleano(int parametro);
	
	public double getParametroReal(int parametro) throws PersistenceException, EJBException;

}
