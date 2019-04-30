/**
 * 
 */
package br.com.xlearning.disciplina.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.repository.DisciplinaRepository;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;

/**
 * @author jesiel
 * 
 */
@Stateless
@LocalBean
@Local(DisciplinaService.class)
public class DisciplinaServiceImpl implements DisciplinaService {

	@Inject
	private DisciplinaRepository disciplinaRepository;

	public void adciona(Disciplina disciplina) throws PersistenceException, EJBException
	{
		try
		{
			disciplinaRepository.adiciona(disciplina);
		}
		catch (PersistenceException e)
		{
			throw e;
		}
		catch (EJBException e)
		{
			throw e;
		}
	}

	public Disciplina buscaPorId(Long id) throws PersistenceException, EJBException
	{
		return disciplinaRepository.buscaPorId(id);
	}

	public List<Disciplina> getListaTodas() throws PersistenceException, EJBException
	{
		return disciplinaRepository.listaTodos();
	}

	@Override
	public void atualiza(Disciplina disciplina) throws PersistenceException, EJBException
	{
		disciplinaRepository.atualiza(disciplina);
	}

	@Override
	public List<Disciplina> getDisciplinasDoCursoNotTurma(Long idTurma, Curso curso) throws PersistenceException, EJBException
	{
		return disciplinaRepository.getDisciplinasDoCursoNotTurma(idTurma, curso);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.disciplina.service.DisciplinaService#
	 * getDisciplinasPorProfessor(java.lang.Long)
	 */
	@Override
	public List<Disciplina> getDisciplinasPorProfessor(Long matricula) throws PersistenceException, EJBException
	{
		return disciplinaRepository.getDisciplinasPorProfessor(matricula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.disciplina.service.DisciplinaService#
	 * getDisciplinasAlunoCursando(java.lang.Long,
	 * br.com.xlearning.enumeracao.SituacaoAlunoDisciplina)
	 */
	@Override
	public List<Disciplina> getDisciplinasAlunoCursando(Long aluno, SituacaoAlunoDisciplina situacao) throws PersistenceException
	{
		return disciplinaRepository.getDisciplinasAlunoCursando(aluno, situacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.disciplina.service.DisciplinaService#
	 * listaDisciplinasPorCurso(br.com.xlearning.curso.entidade.Curso)
	 */
	@Override
	public List<Disciplina> listaDisciplinasPorCurso(Curso curso) throws PersistenceException, EJBException
	{
		return disciplinaRepository.listaDisciplinasPorCurso(curso);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.disciplina.service.DisciplinaService#findByNome(java
	 * .lang.String)
	 */
	@Override
	public Disciplina findByNome(String nome) throws PersistenceException, EJBException
	{
		return disciplinaRepository.findByNome(nome);
	}

}
