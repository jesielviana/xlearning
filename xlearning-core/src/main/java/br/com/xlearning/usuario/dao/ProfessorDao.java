/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.repository.ProfessorRepository;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public class ProfessorDao extends GenericDAO<Professor> implements ProfessorRepository
{
   private static final long serialVersionUID = 1L;

   public ProfessorDao()
   {
      super(Professor.class);
   }
   
   public Professor getProfessorPorMatriculaFethJoinDisciplina(Long id) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Professor.findByDisciplinaId", Professor.class);
      query.setParameter("id", id);
      return (Professor) query.getSingleResult();
   }
   
   @SuppressWarnings("unchecked")
   @Override
   public List<Professor> listaTodosProfessores() throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Professor.findAll", Professor.class);
      List<Professor> listaDeProfessores = query.getResultList();
      return listaDeProfessores;
   }
   
   @SuppressWarnings("unchecked")
   public List<Professor> findByStatus(StatusUsuario status) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Professor.findByStatus", Usuario.class);
      query.setParameter("status", status);
      return  query.getResultList();
      
   }
}
