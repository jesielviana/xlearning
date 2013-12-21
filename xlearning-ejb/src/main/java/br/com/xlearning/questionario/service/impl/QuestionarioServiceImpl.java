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

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.dao.QuestionarioRepository;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.service.QuestionarioService;

/**
 * @author Jesiel Viana Date 03/10/2013
 */
@Stateless
@LocalBean
@Local(QuestionarioService.class)
public class QuestionarioServiceImpl implements QuestionarioService {

	@Inject
	private QuestionarioRepository questionarioRepository;

	@Override
	public void adicionarQuestionario(Questionario questionario) throws PersistenceException, EJBException
	{
		questionarioRepository.adiciona(questionario);
	}

	@Override
	public void alterarQuestionario(Questionario questionario) throws PersistenceException, EJBException
	{
		questionarioRepository.atualiza(questionario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.service.QuestionarioService#
	 * getQuestionariosPorDisciplina
	 * (br.com.xlearning.disciplina.entidade.Disciplina)
	 */
	@Override
	public List<Questionario> getQuestionariosPorDisciplina(Disciplina disciplina)
	{
		return questionarioRepository.getQuestionariosPorDisciplina(disciplina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.questionario.service.QuestionarioService#flush()
	 */
	@Override
	public void flush()
	{
		questionarioRepository.flush();
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.QuestionarioService#getQuestionariosAluno(long, br.com.xlearning.enumeracao.status.StatusQuestionarioAluno)
	 */
	@Override
	public List<Questionario> getQuestionariosAluno(long aluno, long disciplina, List<StatusQuestionarioAluno> status)
	{
		return questionarioRepository.getQuestionariosAluno(aluno, disciplina, status);
	}

	@Override
	public void remove(Questionario questionario) 
	{
		questionarioRepository.remove(questionario);
	}

   @Override
   public Questionario buscaPorId(Long id)
   {
      return questionarioRepository.buscaPorId(id);
   }

}
