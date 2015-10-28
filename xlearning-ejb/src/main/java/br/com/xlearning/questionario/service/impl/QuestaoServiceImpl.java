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

import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.repository.QuestaoRepository;
import br.com.xlearning.questionario.service.QuestaoService;

/**
 * @author Jesiel Viana Date 03/10/2013
 */

@Stateless
@LocalBean
@Local(QuestaoService.class)
public class QuestaoServiceImpl implements QuestaoService {

	@Inject
	private QuestaoRepository questaoRepository;

	@Override
	public void adicionarQuestao(Questao questao) throws PersistenceException, EJBException
	{
		questaoRepository.adiciona(questao);
	}

	@Override
	public void alterarQuestao(Questao questao) throws PersistenceException, EJBException
	{
		questaoRepository.atualiza(questao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.service.QuestaoService#
	 * getQuestaoPorNumeroEQuestionario(int, long)
	 */
	@Override
	public Questao getQuestaoPorNumeroEQuestionario(int numero, long idQuestionario)
	{
		return questaoRepository.getQuestaoPorNumeroEQuestionario(numero, idQuestionario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.service.QuestaoService#
	 * getQuestoesPorQuestionario(long)
	 */
	@Override
	public List<Questao> getQuestoesPorQuestionario(long idQuestionario)
	{
		return questaoRepository.getQuestoesPorQuestionario(idQuestionario);
	}

	@Override
	public void remove(Questao questao)
	{
		questaoRepository.remove(questao);
	}

	@Override
	public Questao buscaPorId(Long id)
	{
		return questaoRepository.buscaPorId(id);
	}

}
