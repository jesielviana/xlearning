/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.questionario.repository;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.questionario.entidade.Opcao;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
public interface OpcaoRepository
{
	public void adiciona(Opcao opcoes) throws PersistenceException, EJBException; 
	
	public void adicionarOpcoes(List<Opcao> opcoes) throws PersistenceException, EJBException; 
	
	public void atualiza(Opcao opcoes) throws PersistenceException, EJBException; 
	
	public Opcao buscaPorId(Long id);

}
