/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.turma.repository;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.turma.entidade.Turma;

/**
 * @author jesiel
 * @Data: 2013
 */
public interface TurmaRepository {
	
	public void adiciona(Turma turma) throws PersistenceException;
	
	public void atualiza(Turma turma) throws PersistenceException;

	public Turma buscaPorId(Long id) throws PersistenceException;
	
	public List<Turma> listaTodos() throws PersistenceException; 
	
	public List<Turma> getTurmasPorCurso(Curso curso) throws PersistenceException; 

}
