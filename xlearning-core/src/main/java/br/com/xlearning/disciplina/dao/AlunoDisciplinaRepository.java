/**
 * Xlearning 2013
 */
package br.com.xlearning.disciplina.dao;

import java.util.List;
import javax.persistence.PersistenceException;

import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;

/**
 * @author Jesiel Viana
 * Date 21/09/2013
 */
public interface AlunoDisciplinaRepository {
	
	public void atualiza(AlunoDisciplina alunoDisciplina) throws PersistenceException;
	
	public void adiciona(AlunoDisciplina alunoDisciplina) throws PersistenceException;
	
	public AlunoDisciplina getAlunoDisciplinaPorSituacao(Long aluno, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas)  
	         throws PersistenceException;
	
   public List<AlunoDisciplina> getListaDeAlunoDisciplinaPorSituacao(List<Long> alunos, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) 
            throws PersistenceException;
   
   public List<AlunoDisciplina> getListaDeDisciplinasAlunoDisciplinaPorSituacao(Long aluno, List<Long> disciplinas, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) 
            throws PersistenceException;

}
