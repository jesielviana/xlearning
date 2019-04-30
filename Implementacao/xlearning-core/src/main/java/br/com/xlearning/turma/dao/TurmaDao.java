/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.turma.dao;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.turma.repository.TurmaRepository;


/**
 * @author jesiel
 * @Data: 2013
 */
public class TurmaDao extends GenericDAO<Turma> implements TurmaRepository{
	private static final long serialVersionUID = 1L;

	public TurmaDao() {
		super(Turma.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Turma> getTurmasPorCurso(Curso curso) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Turma.findByCurso", Turma.class);
		query.setParameter("curso", curso);
		List<Turma> turmas = query.getResultList();
		return turmas;
	}
	
}
