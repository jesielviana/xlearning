/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;
import br.com.xlearning.usuario.repository.UsuarioAdministrativoRepository;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public class UsuarioAdministrativoDao extends GenericDAO<UsuarioAdministrativo> implements UsuarioAdministrativoRepository
{
   private static final long serialVersionUID = 1L;

   public UsuarioAdministrativoDao()
   {
      super(UsuarioAdministrativo.class);
   }
   
   @SuppressWarnings("unchecked")
   @Override
   public List<UsuarioAdministrativo> listaTodosUsuariosAdm() throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("UsuarioAdministrativo.findAll", UsuarioAdministrativo.class);
      List<UsuarioAdministrativo> listaDeAdms = query.getResultList();
      return listaDeAdms;
   }
   
   @SuppressWarnings("unchecked")
   public List<UsuarioAdministrativo> findByStatus(Integer status) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Usuario.findByStatus", Usuario.class);
      query.setParameter("status", status);
      return  query.getResultList();
      
   }

}
