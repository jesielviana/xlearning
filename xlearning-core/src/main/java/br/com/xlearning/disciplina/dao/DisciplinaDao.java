/**
 * 
 */
package br.com.xlearning.disciplina.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.repository.DisciplinaRepository;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.EnumStatus;

/**
 * @author jesiel
 * 
 */
public class DisciplinaDao extends GenericDAO<Disciplina> implements
		DisciplinaRepository {

	private static final long serialVersionUID = 1L;

	public DisciplinaDao()
	{
		super(Disciplina.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> getDisciplinasDoCursoNotTurma(Long idTurma, Curso curso) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Disciplina.findByNotTurma");
		query.setParameter("idturma", idTurma);
		query.setParameter("curso", curso);
		query.setParameter("status", EnumStatus.ATIVO);
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = query.getResultList();

		return disciplinas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.disciplina.dao.DisciplinaRepository#
	 * getDisciplinasPorProfessor(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> getDisciplinasPorProfessor(Long matricula) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Disciplina.findByProfessor");
		query.setParameter("matricula", matricula);
		query.setParameter("status", EnumStatus.ATIVO);
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = query.getResultList();

		return disciplinas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.disciplina.dao.DisciplinaRepository#
	 * getDisciplinasAlunoCursando(java.lang.Long,
	 * br.com.xlearning.enumeracao.SituacaoAlunoDisciplina)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> getDisciplinasAlunoCursando(Long aluno, SituacaoAlunoDisciplina situacao) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Disciplina.findByAlunoDisciplinaCursando");
		query.setParameter("aluno", aluno);
		query.setParameter("situacao", situacao);
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = query.getResultList();

		return disciplinas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.disciplina.dao.DisciplinaRepository#listaDisciplinasPorCurso
	 * (br.com.xlearning.curso.entidade.Curso)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listaDisciplinasPorCurso(Curso curso) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Disciplina.findByCurso");
		query.setParameter("curso", curso);
		query.setParameter("status", EnumStatus.ATIVO);
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = query.getResultList();

		return disciplinas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.disciplina.dao.DisciplinaRepository#findByNome(java.
	 * lang.String)
	 */
	@Override
	public Disciplina findByNome(String nome) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Disciplina.findByNome");
		query.setParameter("nome", nome);
		if(!query.getResultList().isEmpty())
		{
			return (Disciplina)  query.getResultList().get(0);
		}
		return null;
	}

   /* (non-Javadoc)
    * @see br.com.xlearning.disciplina.dao.DisciplinaRepository#findByStatus(br.com.xlearning.enumeracao.status.EnumStatus)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<Disciplina> findByStatus(EnumStatus status) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("Disciplina.findByStatus");
      query.setParameter("status", status);
      List<Disciplina> disciplinas = new ArrayList<Disciplina>();
      disciplinas = query.getResultList();

      return disciplinas;
   }

}
