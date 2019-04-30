/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.questionario.repository.RespostaRepository;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * 
 *         Date: 02/10/2013
 */
public class RespostaDao extends GenericDAO<Resposta> implements
		RespostaRepository {

	private static final long serialVersionUID = 1L;

	public RespostaDao()
	{
		super(Resposta.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.questionario.dao.RespostaRepository#getRespostasPorQuestao
	 * (br.com.xlearning.questionario.entidade.Questao)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno)
	{
		Query query = entityManager.createNamedQuery("Resposta.findByQuestaoAndAluno");
		query.setParameter("questao", questao);
		query.setParameter("aluno", aluno);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resposta> getRespostasPorQuestao(Questao questao)
	{
		Query query = entityManager.createNamedQuery("Resposta.findByQuestao");
		query.setParameter("questao", questao);
		return query.getResultList();
	}

}
