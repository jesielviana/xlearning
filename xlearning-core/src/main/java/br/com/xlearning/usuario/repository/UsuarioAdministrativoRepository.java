/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.repository;

import java.util.List;
import javax.persistence.PersistenceException;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public interface UsuarioAdministrativoRepository
{
   public void adiciona(UsuarioAdministrativo usuario) throws PersistenceException;

   public void atualiza(UsuarioAdministrativo usuario) throws PersistenceException;

   public UsuarioAdministrativo buscaPorId(Long id) throws PersistenceException;
   
   public List<UsuarioAdministrativo> findByStatus(Integer status) throws PersistenceException;
   
   public void flush();
   
   public List<UsuarioAdministrativo> listaTodosUsuariosAdm() throws PersistenceException;

}
