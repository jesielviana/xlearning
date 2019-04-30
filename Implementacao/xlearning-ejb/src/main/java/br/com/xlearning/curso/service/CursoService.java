package br.com.xlearning.curso.service;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.Coordenador;
/**
 * 
 * @author jesiel
 *
 */
public interface CursoService {
	
	public void adcionaCurso(Curso curso) throws BusinessException;
	
	public Curso buscaCursoPorId(Long id) throws PersistenceException;
	
	public List<Curso> getListaTodosCursosAtivos() throws PersistenceException;
	
	public List<Curso> getCursosPorDisciplina(Long idDisciplina) throws PersistenceException;
	
	public void atualizaCurso(Curso curso) throws PersistenceException;
	
	public Curso getCursoPorCoordenador(Coordenador coordenador);
	
}
