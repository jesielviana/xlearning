package br.com.xlearning.usuario.service;


import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.role.entidade.Roles;

public interface RoleService {
	
	
	public Roles getRole(Permissao role) throws PersistenceException, EJBException;
	
	public void adcionaRole(Roles role) throws PersistenceException, EJBException;
	

}
