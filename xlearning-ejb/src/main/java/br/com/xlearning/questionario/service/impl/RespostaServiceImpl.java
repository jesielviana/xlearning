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
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.questionario.repository.RespostaRepository;
import br.com.xlearning.questionario.service.RespostaService;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
@Stateless
@LocalBean
@Local(RespostaService.class)
public class RespostaServiceImpl implements RespostaService {

	@Inject
	private RespostaRepository respostaRepository;
	
	@Override
	public void adicionarResposta(Resposta resposta) throws PersistenceException, EJBException
	{
		respostaRepository.adiciona(resposta);
	}

	@Override
	public void alterarResposta(Resposta resposta) throws PersistenceException, EJBException
	{
		respostaRepository.atualiza(resposta);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.RespostaService#buscaRespostaPorId(long)
	 */
	@Override
	public Resposta buscaRespostaPorId(long id)
	{
		return respostaRepository.buscaPorId(id);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.RespostaService#getRespostasPorQuestao(br.com.xlearning.questionario.entidade.Questao)
	 */
	@Override
	public List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno)
	{
		return respostaRepository.getRespostasPorQuestao(questao, aluno);
	}

	@Override
	public List<Resposta> getRespostasPorQuestao(Questao questao)
	{
		return respostaRepository.getRespostasPorQuestao(questao);
	}

}
