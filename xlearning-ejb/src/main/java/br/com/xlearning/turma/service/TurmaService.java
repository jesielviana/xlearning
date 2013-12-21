package br.com.xlearning.turma.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.turma.entidade.Turma;

public interface TurmaService {
	
	public void adicionaTurma(Turma turma) throws PersistenceException, EJBException;
	
	public Turma buscaTurmaPorId(Long id) throws PersistenceException, EJBException;
	
	public void alterarTurma(Turma turma) throws PersistenceException, EJBException;
	
	public List<Turma> getTodasTurmas() throws PersistenceException, EJBException;
	
	public List<Turma> getTurmasPorCurso(Curso curso) throws PersistenceException, EJBException;

}
