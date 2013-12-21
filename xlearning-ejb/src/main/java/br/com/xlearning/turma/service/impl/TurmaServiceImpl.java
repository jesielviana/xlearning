/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.turma.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.turma.repository.TurmaRepository;
import br.com.xlearning.turma.service.TurmaService;

/**
 * @author jesiel
 * @Data: 2013
 */
@Stateless
@LocalBean
@Local(TurmaService.class)
public class TurmaServiceImpl implements TurmaService{

	@Inject
	private TurmaRepository turmaRepository;

	public void adicionaTurma(Turma turma) throws PersistenceException,
			EJBException {
		try {
			turmaRepository.adiciona(turma);
		} catch (PersistenceException e) {
			throw e;
		} catch (EJBException e) {
			throw e;
		}
	}

	public List<Turma> getTodasTurmas() throws PersistenceException,
			EJBException {
		try {
			return turmaRepository.listaTodos();
		} catch (PersistenceException e) {
			throw e;
		} catch (EJBException e) {
			throw e;
		}
	}

	@Override
	public List<Turma> getTurmasPorCurso(Curso curso) throws PersistenceException, EJBException
	{
		return turmaRepository.getTurmasPorCurso(curso);
	}

	@Override
	public void alterarTurma(Turma turma) throws PersistenceException, EJBException
	{
		turmaRepository.atualiza(turma);
		
	}

	@Override
	public Turma buscaTurmaPorId(Long id) throws PersistenceException, EJBException
	{
		return turmaRepository.buscaPorId(id);
	}
}
