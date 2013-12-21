/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service;

import java.util.List;

import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * Date 09/11/2013
 */
public interface QuestionarioAlunoService {
	
	public void adicionaQuestionarioService(List<QuestionarioAluno> questionarioAlunos);
	
	public void atualizaQuestionarioService(List<QuestionarioAluno> questionarioAlunos);
	
	public void atualizaQuestionarioService(QuestionarioAluno questionarioAluno);
	
	public QuestionarioAluno getQuestionariosAluno(Questionario questionario, Aluno aluno, 
			StatusQuestionarioAluno status);

}
