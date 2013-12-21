package br.com.xlearning.parametro.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.xlearning.parametro.dao.ParametroRepository;
import br.com.xlearning.parametro.entidade.Parametro;
import br.com.xlearning.parametro.service.ParametroService;
/**
 * 
 * @author jesiel
 *
 */
@Stateless
@LocalBean
@Local(ParametroService.class)
public class ParametroServiceBean implements ParametroService{

	@Inject
	private ParametroRepository parametroRepository;

	@Override
	public void adicionarParametro(Parametro parametro)
	{
		parametroRepository.adiciona(parametro);
	}

	@Override
	public int getParametroInteiro(int parametro)
	{
		return parametroRepository.getParametroInteiro(parametro);
	}

	@Override
	public String getParametroString(int parametro) throws PersistenceException, EJBException
	{
		return parametroRepository.getParametroString(parametro);
	}

	@Override
	public boolean getParametroBooleano(int parametro)
	{
		return parametroRepository.getParametroBooleano(parametro);
	}

	@Override
	public void alterarParametro(Parametro parametro) throws PersistenceException, EJBException
	{
		parametroRepository.atualiza(parametro);
	}

	@Override
	public List<Parametro> getTodosParametros() throws PersistenceException, EJBException
	{
		return parametroRepository.listaTodos();
	}

	@Override
	public double getParametroReal(int parametro) throws PersistenceException, EJBException
	{
		return parametroRepository.getParametroReal(parametro);
	}
}
