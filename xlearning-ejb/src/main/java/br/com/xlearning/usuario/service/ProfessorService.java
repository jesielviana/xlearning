package br.com.xlearning.usuario.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.Professor;

public interface ProfessorService {
	
	public void adcionaProfessor(Professor professor) throws BusinessException, PersistenceException, EJBException;
	
	public List<Professor> getTodosProfessores() throws PersistenceException, EJBException;
	
	public Professor buscaPorId(Long id) throws PersistenceException; 
	
	public void alterarProfessor(Professor professor) throws PersistenceException, EJBException;
	
	/**
	 * Consulta professor pela discilina
	 * @param matricula
	 * @return
	 * @throws PersistenceException
	 */
	public Professor getProfessorPorMatriculaFethJoinDisciplina(Long matricula)  throws PersistenceException;
	
	public List<Professor> findByStatus(StatusUsuario status) throws PersistenceException, EJBException;
}
