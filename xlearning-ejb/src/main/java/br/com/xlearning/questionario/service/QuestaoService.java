/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Questao;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
public interface QuestaoService {
	
	public void adicionarQuestao(Questao questao) throws PersistenceException, EJBException; 
	
	public void alterarQuestao(Questao questao) throws PersistenceException, EJBException; 
	
	public Questao getQuestaoPorNumeroEQuestionario(int numero, long idQuestionario);
	
	public List<Questao> getQuestoesPorQuestionario(long idQuestionario);
	
	public void remove(Questao questao);
	
	public Questao buscaPorId(Long id);

}
