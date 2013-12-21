/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
public interface RespostaService {
	
	public void adicionarResposta(Resposta resposta) throws PersistenceException, EJBException; 
	
	public void alterarResposta(Resposta resposta) throws PersistenceException, EJBException; 
	
	public Resposta buscaRespostaPorId(long id);
	
	public List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno);
	public List<Resposta> getRespostasPorQuestao(Questao questao);

}
