/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.entidade.Questionario;

/**
 * @author Jesiel Viana
 * 
 *         Date: 02/10/2013
 */
public class QuestionarioDao extends GenericDAO<Questionario> implements
		QuestionarioRepository {
	private static final long serialVersionUID = 1L;

	public QuestionarioDao()
	{
		super(Questionario.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestionarioRepository#
	 * getQuestionariosPorDisciplina
	 * (br.com.xlearning.disciplina.entidade.Disciplina)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Questionario> getQuestionariosPorDisciplina(Disciplina disciplina)
	{
		Query query = entityManager.createNamedQuery("Questionario.findByDisciplina");
		query.setParameter("disciplina", disciplina);
		List<Questionario> questionarios = new ArrayList<Questionario>();
		questionarios = query.getResultList();
		return questionarios;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.dao.QuestionarioRepository#
	 * getQuestionariosAluno(long,
	 * br.com.xlearning.enumeracao.status.StatusQuestionarioAluno)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Questionario> getQuestionariosAluno(long aluno, long disciplina, List<StatusQuestionarioAluno> status)
	{
		Query query = entityManager.createNamedQuery("Questionario.findByAlunoStatus");
		query.setParameter("aluno", aluno);
		query.setParameter("disciplina", disciplina);
		query.setParameter("status", status);
		List<Questionario> questionarios = new ArrayList<Questionario>();
		questionarios = query.getResultList();
		return questionarios;
	}

}
