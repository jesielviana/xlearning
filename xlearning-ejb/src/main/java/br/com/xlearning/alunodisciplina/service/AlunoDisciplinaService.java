/**
 * Xlearning 2013
 */
package br.com.xlearning.alunodisciplina.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.StatusAprovacao;

/**
 * @author Jesiel Viana
 * Date 21/09/2013
 */
public interface AlunoDisciplinaService {

   /**
    * Adiciona um novo relacionamento de aluno x disciplina
    * @param alunoDisciplina
    * @throws PersistenceException
    * @throws EJBException
    */
	public void adicionarDisciplinasParaAluno(AlunoDisciplina alunoDisciplina) 
	         throws PersistenceException, EJBException;

	/**
	 * Alterar o relacionamento de aluno x disciplina
	 * @param alunoDisciplina
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	public void atualizarDisciplinasParaAluno(AlunoDisciplina alunoDisciplina) 
	         throws PersistenceException, EJBException;
	
	/**
	 * Consulta aluno x disciplina por situação
	 * @param aluno
	 * @param disciplina
	 * @param situacaoAlunoDisciplinas
	 * @return AlunoDisciplina
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	public AlunoDisciplina getAlunoDisciplinaPorSituacao(Long aluno, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas)  
	         throws PersistenceException, EJBException;
	
	/**
	 * Consulta alunos por disciplina e situação
	 * @param alunos
	 * @param disciplina
	 * @param situacaoAlunoDisciplinas
	 * @return List<AlunoDisciplina>
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	  public List<AlunoDisciplina> getListaAlunoDisciplinaPorSituacao(List<Long> alunos, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas)  
	            throws PersistenceException, EJBException;
	  
	  /**
	   * Calcula a média do aluno
	   * @param alunoDisciplina
	   * @return média do aluno
	   * @throws PersistenceException
	   * @throws EJBException
	   */
	  public float calculaMediaDoAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException;
	  
	  /**
	   * Verifica se o aluno está aprovado ou reprovado
	   * @param alunoDisciplina
	   * @return StatusAprovacao
	   * @throws PersistenceException
	   * @throws EJBException
	   */
	  public StatusAprovacao verificaSituaçãoDoAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException;
	  
	  /**
	   * Consulta disciplinas por aluno e situação
	   * @param aluno
	   * @param disciplinas
	   * @param situacaoAlunoDisciplinas
	   * @return
	   * @throws PersistenceException
	   * @throws EJBException
	   */
	  public List<AlunoDisciplina> getListaDeDisciplinasAlunoDisciplinaPorSituacao(Long aluno, List<Long> disciplinas, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas)  
              throws PersistenceException, EJBException;
}
