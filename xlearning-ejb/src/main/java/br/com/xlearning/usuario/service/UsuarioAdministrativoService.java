package br.com.xlearning.usuario.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;

public interface UsuarioAdministrativoService {
	
	public void adcionarUsuarioAministrativo(UsuarioAdministrativo usuarioAdministrativo)throws BusinessException, PersistenceException;
	
   public void atualizarUsuarioAdm(UsuarioAdministrativo usuarioAdministrativo) throws PersistenceException, EJBException;
   
   public UsuarioAdministrativo buscaUsuarioAdmPorId(Long id) throws PersistenceException, EJBException;
      
   public List<UsuarioAdministrativo> getTodosUsuariosAdms()  throws PersistenceException, EJBException;

}
