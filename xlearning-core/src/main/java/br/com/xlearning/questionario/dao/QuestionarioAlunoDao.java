/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.repository.QuestionarioAlunoRepository;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana Date 09/11/2013
 */
public class QuestionarioAlunoDao extends GenericDAO<QuestionarioAluno> implements QuestionarioAlunoRepository {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QuestionarioAlunoDao.class);

	public QuestionarioAlunoDao()
	{
		super(QuestionarioAluno.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestionarioAlunoRepository#
	 * getQuestionariosAluno
	 * (br.com.xlearning.questionario.entidade.Questionario,
	 * br.com.xlearning.usuario.entidade.Aluno)
	 */
	@Override
	public QuestionarioAluno getQuestionariosAluno(Questionario questionario, Aluno aluno, StatusQuestionarioAluno status)
	{
		Query query = entityManager.createNamedQuery("QuestionarioAluno.findByAlunoAndQuestionario");
		query.setParameter("questionario", questionario.getIdquestionario());
		query.setParameter("aluno", aluno.getMatricula());
		query.setParameter("status", status);

		if (!query.getResultList().isEmpty())
		{
			return (QuestionarioAluno) query.getResultList().get(0);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestionarioAlunoRepository#
	 * adicionaQuestionarioService(java.util.List)
	 */
	@Override
	public void adicionaQuestionarioService(List<QuestionarioAluno> questionarioAlunos)
	{
		for (QuestionarioAluno questionarioAluno : questionarioAlunos)
		{
			try
			{
				adiciona(questionarioAluno);
				
			}
			catch (Exception e)
			{
				logger.error(questionarioAluno.getQuestionario1().getNome() +" já está adicionado para o aluno "+
						questionarioAluno.getAluno().getNome());
			}
		}
	}

	@Override
	public void atualizaQuestionarioService(List<QuestionarioAluno> questionarioAlunos)
	{
		for (QuestionarioAluno questionarioAluno : questionarioAlunos)
		{
			atualiza(questionarioAluno);
		}
	}

}
