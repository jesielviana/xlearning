/**
 * Xlearning 2013
 */

package br.com.xlearning.login.service.impl;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import br.com.xlearning.login.service.LoginService;
import br.com.xlearning.usuario.dao.UsuarioRepository;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author Jesiel Viana Date 29/09/2013
 */

@Stateless
@LocalBean
@Local(LoginService.class)
public class LoginServiceBean implements LoginService {

	@Inject
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario getUserSession(Long id) throws PersistenceException, EJBException, IllegalArgumentException
	{
		return usuarioRepository.getUserSession(id);
	}

	@Override
	public Usuario getUsuarioPorCPF(String cpf)
	{
		return usuarioRepository.getUsuarioPorCpf(cpf);
	}

	@Override
	public void atualiza(Usuario usuario)
	{
		usuarioRepository.atualiza(usuario);
	}
}
