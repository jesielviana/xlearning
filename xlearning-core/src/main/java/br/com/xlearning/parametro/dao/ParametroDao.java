package br.com.xlearning.parametro.dao;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.parametro.entidade.Parametro;

/**
 * @author jesiel
 */
public class ParametroDao extends GenericDAO<Parametro> implements ParametroRepository {

	private static final long serialVersionUID = 1L;

	public ParametroDao()
	{
		super(Parametro.class);
	}

	private Parametro buscaPorId(Integer id) throws PersistenceException
	{
		return entityManager.find(Parametro.class, id);
	}

	@Override
	public int getParametroInteiro(int parametro) throws PersistenceException
	{
		Parametro p = buscaPorId(parametro);
		if (p == null)
		{
			return 0;
		}
		int valor = Integer.parseInt(p.getValor());
		return valor;
	}

	@Override
	public String getParametroString(int parametro) throws PersistenceException
	{
		Parametro p = buscaPorId(parametro);
		if (p == null)
		{
			return null;
		}
		return p.getValor();
	}

	@Override
	public boolean getParametroBooleano(int parametro) throws PersistenceException
	{
		Parametro p = buscaPorId(parametro);
		if (p == null)
		{
			throw new PersistenceException();
		}
		boolean valor = Boolean.parseBoolean(p.getValor());
		return valor;
	}

	@Override
	public double getParametroReal(int parametro) throws PersistenceException, EJBException
	{
		Parametro p = buscaPorId(parametro);
		if (p == null)
		{
			return 0;
		}
		double valor = Double.parseDouble(p.getValor());
		return valor;
	}

}
