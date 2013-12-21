/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author Jesiel Viana
 * 
 *         Date: 16/10/2013
 */
public class AlunoDao extends GenericDAO<Aluno> implements AlunoRepository {
	private static final long serialVersionUID = 1L;

	public AlunoDao()
	{
		super(Aluno.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> buscaPorCurso(Curso curso) throws PersistenceException
	{

		Query query = entityManager.createNamedQuery("Aluno.findByCurso", Aluno.class);
		query.setParameter("curso", curso);
		query.setParameter("status", StatusUsuario.ATIVO);
		List<Aluno> listaDeAlunos = query.getResultList();
		return listaDeAlunos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.usuario.dao.AlunoDao#listaTodosAlunos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> listaTodosAlunos() throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Aluno.findAll", Aluno.class);
		List<Aluno> listaDeAlunos = query.getResultList();
		return listaDeAlunos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.usuario.dao.UsuarioRepository#buscaPorCursoNotTurma(
	 * br.com.xlearning.curso.entidade.Curso)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> buscaPorCursoNotTurma(Curso curso, Long idTurma) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Aluno.findByNotTurma", Aluno.class);
		query.setParameter("curso", curso);
		query.setParameter("idturma", idTurma);
		query.setParameter("status", StatusUsuario.ATIVO);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Aluno> findByStatus(Integer status) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Usuario.findByStatus", Usuario.class);
		query.setParameter("status", status);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> getAlunoPorSituacaoDisciplina(Long disciplina, SituacaoAlunoDisciplina situacao) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("Aluno.findByAlunoDisciplinaCursando");
		query.setParameter("disciplina", disciplina);
		query.setParameter("situacao", situacao);
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = query.getResultList();

		return alunos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> getAlunoPorQuestionarioStatus(Long questionario, StatusQuestionarioAluno status)
	{
		Query query = entityManager.createNamedQuery("Aluno.findByQuestionarioStatus");
		query.setParameter("questionario", questionario);
		query.setParameter("status", status);
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = query.getResultList();

		return alunos;
	}

}
