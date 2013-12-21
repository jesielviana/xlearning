/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.error.BusinessException;
import br.com.xlearning.error.ErrorCode;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;
import br.com.xlearning.usuario.repository.UsuarioAdministrativoRepository;
import br.com.xlearning.usuario.repository.UsuarioRepository;
import br.com.xlearning.usuario.service.UsuarioAdministrativoService;

/**
 * @author jesiel
 * @Data: 2013
 */
@Stateless
@LocalBean
@Local(UsuarioAdministrativoService.class)
public class UsuarioAdministrativoServiceImpl implements UsuarioAdministrativoService{

   private static Logger logger = Logger.getLogger(UsuarioAdministrativoServiceImpl.class);
	@Inject
	private UsuarioAdministrativoRepository usuarioAdministrativoRepository;
   @Inject
   UsuarioRepository usuarioRepository;

	public void adcionarUsuarioAministrativo(UsuarioAdministrativo usuarioAdministrativo) throws BusinessException, PersistenceException, EJBException 
	{
	      isCPFCadastrado(usuarioAdministrativo.getCpf());
	      usuarioAdministrativoRepository.adiciona(usuarioAdministrativo);
			logger.info("Usuário adicionado com sucesso. nome: "+usuarioAdministrativo.getNome());
		
	}
	
	  private void isCPFCadastrado(String cpf) throws PersistenceException, BusinessException {
	      if (usuarioRepository.getUsuarioPorCpf(cpf) != null) {
	         throw new BusinessException(ErrorCode.CPF_JA_CADASTRADO);
	      }
	   }

   @Override
   public void atualizarUsuarioAdm(UsuarioAdministrativo usuarioAdministrativo) throws PersistenceException, EJBException
   {
      usuarioAdministrativoRepository.atualiza(usuarioAdministrativo);
   }

   @Override
   public UsuarioAdministrativo buscaUsuarioAdmPorId(Long id) throws PersistenceException, EJBException
   {
      return usuarioAdministrativoRepository.buscaPorId(id);
   }

   @Override
   public List<UsuarioAdministrativo> getTodosUsuariosAdms() throws PersistenceException, EJBException
   {
      return usuarioAdministrativoRepository.listaTodosUsuariosAdm();
   }

}
