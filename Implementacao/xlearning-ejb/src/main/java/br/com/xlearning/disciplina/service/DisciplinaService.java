package br.com.xlearning.disciplina.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;

public interface DisciplinaService {

	public List<Disciplina> getListaTodas() throws PersistenceException, EJBException;
	
	public void adciona(Disciplina disciplina) throws PersistenceException, EJBException;
	
	public Disciplina buscaPorId(Long id) throws PersistenceException, EJBException;
	
	public Disciplina findByNome(String nome) throws PersistenceException, EJBException;
	
	public void atualiza(Disciplina disciplina) throws PersistenceException, EJBException;
	
	 public List<Disciplina> getDisciplinasDoCursoNotTurma(Long idTurma, Curso curso) throws PersistenceException, EJBException;
	 
	 public List<Disciplina> getDisciplinasPorProfessor(Long matricula) throws PersistenceException, EJBException;
	 
	 public List<Disciplina> listaDisciplinasPorCurso(Curso curso) throws PersistenceException, EJBException;
	 
	 public List<Disciplina> getDisciplinasAlunoCursando(Long aluno, SituacaoAlunoDisciplina situacao) throws PersistenceException;
	
}
