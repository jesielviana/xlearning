/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.repository;

import java.util.List;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Questao;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
public interface QuestaoRepository
{

	public void adiciona(Questao questao) throws PersistenceException; 
	
	public void atualiza(Questao questao) throws PersistenceException; 
	
	public Questao getQuestaoPorNumeroEQuestionario(int numero, long idQuestionario);
	
	public List<Questao> getQuestoesPorQuestionario(long idQuestionario);
	
	public void remove(Questao questao);
	
	public Questao buscaPorId(Long id);
}

