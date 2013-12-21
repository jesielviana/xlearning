/**
 * Xlearning 2013
 */
package br.com.xlearning.questionario.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Opcao;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
public interface OpcaoService {
	
	public void adicionarOpcao(Opcao opcoes) throws PersistenceException, EJBException; 
	
	public void adicionarOpoes(List<Opcao> opcoes) throws PersistenceException, EJBException; 
	
	public Opcao buscaOpcaoPorId(long id);
	
	public void alterarOpcao(Opcao opcoes) throws PersistenceException, EJBException; 

}
