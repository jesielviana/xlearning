/**
 * 
 */

package br.com.xlearning.usuario.dao;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.repository.UsuarioRepository;

/**
 * @author jesiel
 */


public class UsuarioDao extends GenericDAO<Usuario> implements
		UsuarioRepository {

	private static final long serialVersionUID = 1L;

   public UsuarioDao()
	{
		super(Usuario.class);
	}
   
	/**
	 * Busca usuário pelo CPF
	 */
	public Usuario getUsuarioPorCpf(String cpf) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Usuario.findByCpf", Usuario.class);
		query.setParameter("cpf", cpf);

		if (query.getResultList() != null && !query.getResultList().isEmpty())
		{
			return (Usuario) query.getSingleResult();
		}
		return null;
	}

   @Override
   public Usuario getUserSession(Long id) throws PersistenceException
   {
	  return entityManager.find(Usuario.class, id);
   }
}
