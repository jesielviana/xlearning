/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.usuario.entidade.Coordenador;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.repository.CoordenadorRepository;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public class CoordenadorDao extends GenericDAO<Coordenador> implements CoordenadorRepository
{
   private static final long serialVersionUID = 1L;

   public CoordenadorDao()
   {
      super(Coordenador.class);
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Coordenador> listaTodosCoordenadores() throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Coordenador.findAll", Coordenador.class);
      List<Coordenador> listaDeCoordenadores = query.getResultList();
      return listaDeCoordenadores;
   }
   
   @SuppressWarnings("unchecked")
   public List<Coordenador> findByStatus(Integer status) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Usuario.findByStatus", Usuario.class);
      query.setParameter("status", status);
      return  query.getResultList();
      
   }
}
