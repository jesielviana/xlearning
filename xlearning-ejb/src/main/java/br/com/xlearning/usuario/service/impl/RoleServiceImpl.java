package br.com.xlearning.usuario.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.xlearning.dao.util.DAO;
import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.role.entidade.Roles;
import br.com.xlearning.usuario.service.RoleService;

/**
 * 
 * @author jesiel
 * @Data: 2013
 */
@Stateless
@LocalBean
@Local(RoleService.class)
public class RoleServiceImpl implements RoleService{
	@Inject
	private DAO<Roles> dao;
	
	
	public Roles getRole(Permissao role) throws PersistenceException, EJBException
	{
		return dao.buscaPorIdVarchar(role.toString());
	}
	
	public void adcionaRole(Roles role) throws PersistenceException, EJBException{
		if(role != null){
		dao.adiciona(role);
		}else{
			for(Roles role2 : getRoles()){
				try {
					dao.adiciona(role2);
				} catch (PersistenceException e) {
					throw e;
				}
			}
		}
	}
	
	private List<Roles> getRoles() throws PersistenceException, EJBException{
		List<Roles> listaRoles = new ArrayList<Roles>();
		for(Permissao permissao : Permissao.values()){
			listaRoles.add(new Roles(permissao, permissao.getDescricao()));
		}
		return listaRoles;
	}
	
}
