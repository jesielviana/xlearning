/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.questionario.entidade.Questionario;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
public interface QuestionarioService {

	public void adicionarQuestionario(Questionario questionario) throws PersistenceException, EJBException; 
	
	public void alterarQuestionario(Questionario questionario) throws PersistenceException, EJBException; 
	
	public List<Questionario> getQuestionariosPorDisciplina(Disciplina disciplina);
	
	public List<Questionario> getQuestionariosAluno(long aluno, long disciplina, List<StatusQuestionarioAluno> status);
	
	public void flush();
	
	public void remove(Questionario questionario);
	
	public Questionario buscaPorId(Long id);
}
