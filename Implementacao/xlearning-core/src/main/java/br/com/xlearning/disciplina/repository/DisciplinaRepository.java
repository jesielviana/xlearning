/**
 * 
 */
package br.com.xlearning.disciplina.repository;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.EnumStatus;

/**
 * @author jesiel
 *
 */
public interface DisciplinaRepository {

	public void adiciona(Disciplina disciplina) throws PersistenceException; 

	public Disciplina buscaPorId(Long id) throws PersistenceException; 
	
	public Disciplina findByNome(String nome) throws PersistenceException;
	
	public List<Disciplina> listaTodos() throws PersistenceException; 
	
	public List<Disciplina> findByStatus(EnumStatus status) throws PersistenceException; 
	
	public void atualiza(Disciplina disciplina) throws PersistenceException;
	
	 public List<Disciplina> listaDisciplinasPorCurso(Curso curso) throws PersistenceException;
	
	/**
	 * Busca as disciplinas do curso selecionado que ainda não estão na turma selecionada
	 * @param idTurma
	 * @param idcurso
	 * @return
	 * @throws PersistenceException
	 */
	public List<Disciplina> getDisciplinasDoCursoNotTurma(Long idTurma, Curso curs) throws PersistenceException;
	
	public List<Disciplina> getDisciplinasPorProfessor(Long matricula) throws PersistenceException;
	
	public List<Disciplina> getDisciplinasAlunoCursando(Long aluno, SituacaoAlunoDisciplina situacao) throws PersistenceException;

}
