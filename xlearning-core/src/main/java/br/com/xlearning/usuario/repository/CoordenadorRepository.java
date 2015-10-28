/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.repository;

import java.util.List;
import javax.persistence.PersistenceException;
import br.com.xlearning.usuario.entidade.Coordenador;
import br.com.xlearning.usuario.entidade.Usuario;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public interface CoordenadorRepository
{
   public void adiciona(Coordenador usuario) throws PersistenceException;

   public void atualiza(Coordenador usuario) throws PersistenceException;

   public Usuario buscaPorId(Long id) throws PersistenceException;
   
   public List<Coordenador> findByStatus(Integer status) throws PersistenceException;
   
   public void flush();
   
   public List<Coordenador> listaTodosCoordenadores() throws PersistenceException;

}
