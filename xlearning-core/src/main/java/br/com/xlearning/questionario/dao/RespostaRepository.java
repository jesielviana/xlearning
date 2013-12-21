/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.dao;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.usuario.entidade.Aluno;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
public interface RespostaRepository
{
	
	public void adiciona(Resposta resposta) throws PersistenceException, EJBException; 
	
	public void atualiza(Resposta resposta) throws PersistenceException, EJBException; 
	
	public Resposta buscaPorId(Long id);
	
	public List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno);
	
	public List<Resposta> getRespostasPorQuestao(Questao questao);

}
