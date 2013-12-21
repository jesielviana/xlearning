package br.com.xlearning.curso.repository;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.usuario.entidade.Coordenador;

public interface CursoRepository {

	public void adiciona(Curso curso) throws ConstraintViolationException;

	public Curso buscaPorId(Long id) throws PersistenceException; 
	
	public List<Curso> getListaTodosCursosAtivos() throws PersistenceException; 
	
	public void atualiza(Curso curso) throws PersistenceException; 
	
	public List<Curso> getCursosPorDisciplina(Long idDisciplina) throws PersistenceException; 
	
	public Curso getCursoPorCoordenador(Coordenador coordenador);
	
	public Curso getCursoPorNome(String nome);

}