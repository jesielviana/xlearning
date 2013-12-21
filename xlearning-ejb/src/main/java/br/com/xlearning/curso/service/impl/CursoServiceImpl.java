package br.com.xlearning.curso.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.repository.CursoRepository;
import br.com.xlearning.curso.service.CursoService;
import br.com.xlearning.usuario.entidade.Coordenador;

@Stateless
@LocalBean
@Local(CursoService.class)
public class CursoServiceImpl implements CursoService {

	private static Logger logger = Logger.getLogger(CursoServiceImpl.class);

	@Inject
	private CursoRepository cursoRepository;

	public void adcionaCurso(Curso curso) throws PersistenceException, EJBException
	{
		try
		{
			cursoRepository.adiciona(curso);

		}
		catch (PersistenceException e)
		{
			logger.error("ID Duplicado");
			throw e;
		}
	}

	public Curso buscaCursoPorId(Long id) throws PersistenceException, EJBException
	{
		return cursoRepository.buscaPorId(id);
	}

	public List<Curso> getListaTodosCursosAtivos() throws PersistenceException, EJBException
	{
		return cursoRepository.getListaTodosCursosAtivos();
	}

	@Override
	public void atualizaCurso(Curso curso) throws PersistenceException
	{
		cursoRepository.atualiza(curso);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.curso.service.CursoService#getCursosPorDisciplina(java
	 * .lang.Long)
	 */
	@Override
	public List<Curso> getCursosPorDisciplina(Long idDisciplina) throws PersistenceException
	{
		return cursoRepository.getCursosPorDisciplina(idDisciplina);
	}

	@Override
	public Curso getCursoPorCoordenador(Coordenador coordenador)
	{
		return cursoRepository.getCursoPorCoordenador(coordenador);
	}

	@Override
	public Curso getCursoPorNome(String nome) {
		return cursoRepository.getCursoPorNome(nome);
	}
}
