/**
 * Xlearning 2013
 */
package br.com.xlearning.disciplina.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;

/**
 * @author Jesiel Viana
 * Date 21/09/2013
 */
public class AlunoDisciplinaDAO extends GenericDAO<AlunoDisciplina> implements AlunoDisciplinaRepository{

	private static final long serialVersionUID = 1L;
	public AlunoDisciplinaDAO()
	{
		super(AlunoDisciplina.class);
	}
   /* (non-Javadoc)
    * @see br.com.xlearning.disciplina.dao.AlunoDisciplinaRepository#getAlunoDisciplinaCursar(br.com.xlearning.usuario.entidade.Aluno, br.com.xlearning.disciplina.entidade.Disciplina, java.util.List)
    */
   @Override
   public AlunoDisciplina getAlunoDisciplinaPorSituacao(Long aluno, Long disciplina,
      List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("AlunoDisciplina.findByAlunoDisciplinaSituacao");
      query.setParameter("disciplina", disciplina);
      query.setParameter("aluno", aluno);
      query.setParameter("situacao", situacaoAlunoDisciplinas);
      AlunoDisciplina singleResult = (AlunoDisciplina) (!query.getResultList().isEmpty() ? query.getResultList().get(0) : null);
      return singleResult ;
   }
   /* (non-Javadoc)
    * @see br.com.xlearning.disciplina.dao.AlunoDisciplinaRepository#getListaDeAlunoDisciplinaPorSituacao(java.util.List, java.lang.Long, java.util.List)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<AlunoDisciplina> getListaDeAlunoDisciplinaPorSituacao(List<Long> alunos, Long disciplina,
      List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("AlunoDisciplina.findByListAlunoDisciplinaSituacao");
      query.setParameter("disciplina", disciplina);
      query.setParameter("alunos", alunos);
      query.setParameter("situacao", situacaoAlunoDisciplinas);
      List<AlunoDisciplina> alunoDisciplinas = new ArrayList<AlunoDisciplina>();
      alunoDisciplinas = query.getResultList();
      return alunoDisciplinas;
   }
   /* (non-Javadoc)
    * @see br.com.xlearning.disciplina.dao.AlunoDisciplinaRepository#getListaDeDisciplinasAlunoDisciplinaPorSituacao(java.lang.Long, java.util.List, java.util.List)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<AlunoDisciplina> getListaDeDisciplinasAlunoDisciplinaPorSituacao(Long aluno, List<Long> disciplinas,
      List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException
   {
      Query query = entityManager.createNamedQuery("AlunoDisciplina.findByListDisciplinasAlunoDisciplinaSituacao");
      query.setParameter("disciplinas", disciplinas);
      query.setParameter("aluno", aluno);
      query.setParameter("situacao", situacaoAlunoDisciplinas);
      List<AlunoDisciplina> alunoDisciplinas = new ArrayList<AlunoDisciplina>();
      alunoDisciplinas = query.getResultList();
      return alunoDisciplinas;
   }


}
