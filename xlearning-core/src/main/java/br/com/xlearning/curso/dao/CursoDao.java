package br.com.xlearning.curso.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.enumeracao.status.EnumStatus;
import br.com.xlearning.usuario.entidade.Coordenador;

public class CursoDao extends GenericDAO<Curso> implements CursoRepository{
	private static final long serialVersionUID = 1L;
	

	public CursoDao() {
		super(Curso.class);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.curso.dao.CursoRepository#getCursosPorDisciplina(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> getCursosPorDisciplina(Long idDisciplina) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Curso.findByDisciplinas");
		query.setParameter("idDisciplina", idDisciplina);
		query.setParameter("status", EnumStatus.ATIVO);
		List<Curso> cursos = new ArrayList<Curso>();
		cursos = query.getResultList();
		
		return cursos;
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.curso.dao.CursoRepository#getListaTodosCursosAtivos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> getListaTodosCursosAtivos() throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Curso.findByStatus");
		query.setParameter("status", EnumStatus.ATIVO);
		List<Curso> cursos = new ArrayList<Curso>();
		cursos = query.getResultList();
		
		return cursos;
	}

	@Override
	public Curso getCursoPorCoordenador(Coordenador coordenador)
	{
		Query query = entityManager.createNamedQuery("Curso.findByCoordenador");
		query.setParameter("coordenador", coordenador);
		if(!query.getResultList().isEmpty())
		{
			return (Curso) query.getResultList().get(0);
		}
		return null;		
	}

	@Override
	public Curso getCursoPorNome(String nome) {
		Query query = entityManager.createNamedQuery("Curso.findByNome");
		query.setParameter("nome", nome);
		if(!query.getResultList().isEmpty())
		{
			return (Curso) query.getResultList().get(0);
		}
		return null;	
	}

}
