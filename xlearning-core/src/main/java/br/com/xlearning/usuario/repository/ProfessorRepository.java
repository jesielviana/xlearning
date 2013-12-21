/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.repository;

import java.util.List;
import javax.persistence.PersistenceException;

import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.usuario.entidade.Professor;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public interface ProfessorRepository
{

   public void adiciona(Professor usuario) throws PersistenceException;

   public void atualiza(Professor usuario) throws PersistenceException;

   public Professor buscaPorId(Long id) throws PersistenceException;
   
   public List<Professor> findByStatus(StatusUsuario status) throws PersistenceException;
   
   public void flush();
   
   public List<Professor> listaTodosProfessores() throws PersistenceException;

   public Professor getProfessorPorMatriculaFethJoinDisciplina(Long matricula) throws PersistenceException;

}
