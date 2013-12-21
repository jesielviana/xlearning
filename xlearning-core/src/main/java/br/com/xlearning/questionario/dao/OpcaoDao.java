/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.questionario.entidade.Opcao;

/**
 * @author Jesiel Viana
 * 
 *         Date: 02/10/2013
 */
public class OpcaoDao extends GenericDAO<Opcao> implements OpcaoRepository {

	private static final long serialVersionUID = 1L;

	public OpcaoDao()
	{
		super(Opcao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.questionario.dao.OpcaoRepository#adicionarOpcoes(java
	 * .util.List)
	 */
	@Override
	public void adicionarOpcoes(List<Opcao> opcoes) throws PersistenceException, EJBException
	{
		for (Opcao opcao : opcoes)
		{
			adiciona(opcao);
		}

	}

}
