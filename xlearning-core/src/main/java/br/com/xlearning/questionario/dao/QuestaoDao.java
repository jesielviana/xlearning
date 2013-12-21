/**
 * TCC BSI 2013
 * xlearning.com.br
 */

package br.com.xlearning.questionario.dao;

import java.util.List;
import javax.persistence.Query;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.questionario.entidade.Questao;

/**
 * @author Jesiel Viana Date: 02/10/2013
 */
public class QuestaoDao extends GenericDAO<Questao> implements QuestaoRepository {

	private static final long serialVersionUID = 1L;

	public QuestaoDao()
	{
		super(Questao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestaoRepository#
	 * getQuestaoPorNumeroEQuestionario(int, long)
	 */
	@Override
	public Questao getQuestaoPorNumeroEQuestionario(int numero, long idQuestionario)
	{
		Query query = entityManager.createNamedQuery("Questao.findByNumeroAndQuestionario");
		query.setParameter("numero", numero);
		query.setParameter("idQuestionario", idQuestionario);
		if (query.getResultList() != null && !query.getResultList().isEmpty())
		{
			return (Questao) query.getResultList().get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestaoRepository#
	 * getQuestoesPorQuestionario(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Questao> getQuestoesPorQuestionario(long idQuestionario)
	{

		Query query = entityManager.createNamedQuery("Questao.findByQuestionario");
		query.setParameter("idQuestionario", idQuestionario);
		return query.getResultList();
	}

}
