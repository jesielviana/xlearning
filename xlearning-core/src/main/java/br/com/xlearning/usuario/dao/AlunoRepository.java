/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dao;

import java.util.List;
import javax.persistence.PersistenceException;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.usuario.entidade.Aluno;


/**
 * @author Jesiel Viana
 *
 * Date: 16/10/2013
 */
public interface AlunoRepository
{
   public void adiciona(Aluno usuario) throws PersistenceException;

   public void atualiza(Aluno usuario) throws PersistenceException;

   public Aluno buscaPorId(Long id) throws PersistenceException;
   
   public List<Aluno> findByStatus(Integer status) throws PersistenceException;
   
   public List<Aluno> buscaPorCurso(Curso curso) throws PersistenceException;
   
   public List<Aluno> buscaPorCursoNotTurma(Curso curso, Long idTurma) throws PersistenceException;
   
   public List<Aluno> listaTodosAlunos() throws PersistenceException;
   
   public List<Aluno> getAlunoPorSituacaoDisciplina(Long disciplina, SituacaoAlunoDisciplina situacao) throws PersistenceException;
   
   public void flush();
   
   public List<Aluno> getAlunoPorQuestionarioStatus(Long questionario, StatusQuestionarioAluno status);

}
