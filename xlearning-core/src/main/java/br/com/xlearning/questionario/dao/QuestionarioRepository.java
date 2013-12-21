/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.dao;

import java.util.List;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.entidade.Questionario;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
public interface QuestionarioRepository
{
   public void adiciona(Questionario questionario);
   
   public void atualiza(Questionario questionario);
   
   public List<Questionario> getQuestionariosPorDisciplina(Disciplina disciplina);
   
   public List<Questionario> getQuestionariosAluno(long aluno, long disciplina, List<StatusQuestionarioAluno> status);
   
   public void flush();
   
   public void remove(Questionario questionario);
   
   public Questionario buscaPorId(Long id);

}
