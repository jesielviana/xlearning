/**
 * Xlearning 2013
 */
package br.com.xlearning.login.service;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author Jesiel Viana
 * Date 29/09/2013
 */
public interface LoginService {

	public Usuario getUserSession(Long id) throws PersistenceException, EJBException;
	
	public Usuario getUsuarioPorCPF(String cpf);
	
	 public void atualiza(Usuario usuario);
	
}
