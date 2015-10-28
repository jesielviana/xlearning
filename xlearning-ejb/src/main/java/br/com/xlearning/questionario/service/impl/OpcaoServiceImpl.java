/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Opcao;
import br.com.xlearning.questionario.repository.OpcaoRepository;
import br.com.xlearning.questionario.service.OpcaoService;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
@Stateless
@LocalBean
@Local(OpcaoService.class)
public class OpcaoServiceImpl implements OpcaoService{
	
	@Inject
	private OpcaoRepository opcaoRepository;

	@Override
	public void adicionarOpcao(Opcao opcoes) throws PersistenceException, EJBException
	{
		opcaoRepository.adiciona(opcoes);
	}

	@Override
	public void alterarOpcao(Opcao opcoes) throws PersistenceException, EJBException
	{
		opcaoRepository.atualiza(opcoes);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.OpcaoService#adicionarOpoes(java.util.List)
	 */
	@Override
	public void adicionarOpoes(List<Opcao> opcoes) throws PersistenceException, EJBException
	{
		opcaoRepository.adicionarOpcoes(opcoes);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.OpcaoService#buscaOpcaoPorId(long)
	 */
	@Override
	public Opcao buscaOpcaoPorId(long id)
	{
		return opcaoRepository.buscaPorId(id);
	}

}
