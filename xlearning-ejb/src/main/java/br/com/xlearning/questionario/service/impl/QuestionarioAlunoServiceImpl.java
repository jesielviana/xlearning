/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.dao.QuestionarioAlunoRepository;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.service.QuestionarioAlunoService;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * Date 09/11/2013
 */
@Stateless
@LocalBean
@Local(QuestionarioAlunoService.class)
public class QuestionarioAlunoServiceImpl implements QuestionarioAlunoService{
	
	@Inject
	private QuestionarioAlunoRepository questionarioAlunoRepository;

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.QuestionarioAlunoService#adicionaQuestionarioService(br.com.xlearning.questionario.entidade.QuestionarioAluno)
	 */
	@Override
	public void adicionaQuestionarioService(List<QuestionarioAluno> questionarioAlunos)
	{
		questionarioAlunoRepository.adicionaQuestionarioService(questionarioAlunos);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.QuestionarioAlunoService#atualizaQuestionarioService(br.com.xlearning.questionario.entidade.QuestionarioAluno)
	 */
	@Override
	public void atualizaQuestionarioService(QuestionarioAluno questionarioAluno)
	{
		questionarioAlunoRepository.atualiza(questionarioAluno);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.questionario.service.QuestionarioAlunoService#getQuestionariosAluno(br.com.xlearning.questionario.entidade.Questionario, br.com.xlearning.usuario.entidade.Aluno, br.com.xlearning.enumeracao.status.StatusQuestionarioAluno)
	 */
	@Override
	public QuestionarioAluno getQuestionariosAluno(Questionario questionario, Aluno aluno, StatusQuestionarioAluno status)
	{
		return questionarioAlunoRepository.getQuestionariosAluno(questionario, aluno, status);
	}

   @Override
   public void atualizaQuestionarioService(List<QuestionarioAluno> questionarioAlunos)
   {
      questionarioAlunoRepository.atualizaQuestionarioService(questionarioAlunos);
   }

}
