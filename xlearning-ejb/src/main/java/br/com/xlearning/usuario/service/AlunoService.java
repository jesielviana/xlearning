package br.com.xlearning.usuario.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.Aluno;

public interface AlunoService {

   /**
    * Adiciona novo aluno
    * @param aluno
    * @throws BusinessException
    * @throws PersistenceException
    * @throws EJBException
    */
	public void adcionaAluno(Aluno aluno) throws BusinessException, PersistenceException,
			EJBException;
	
	/**
	 * Consulta aluno por matrícula
	 * @param id
	 * @return
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	public Aluno buscaAlunoPorMatricula(Long id) throws PersistenceException, EJBException;
	
	/**
	 * Consulta aluno por cpf
	 * @param cpf
	 * @return
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	public Aluno buscaAlunoPorCPF(String cpf) throws PersistenceException, EJBException;

	/**
	 * Consulta alunos por curso
	 * @param curso
	 * @return
	 * @throws PersistenceException
	 * @throws EJBException
	 */
	public List<Aluno> buscaAlunoPorCurso(Curso curso) throws PersistenceException, EJBException;

	/**
	 */
	public List<Aluno> listaTodosAlunos() throws PersistenceException, EJBException;

	public void alterarAluno(Aluno aluno) throws PersistenceException, EJBException;
	
	/**
	 * Consulta alunos por turma
	 * @param curso
	 * @param idTurma
	 * @return
	 * @throws PersistenceException
	 */
	public List<Aluno> buscaPorCursoNotTurma(Curso curso, Long idTurma) throws PersistenceException;
	
   public List<Aluno> getAlunoPorSituacaoDisciplina(Long disciplina, SituacaoAlunoDisciplina situacao) 
            throws PersistenceException, EJBException;
   
   public List<Aluno> getAlunoPorQuestionarioStatus(Long questionario, StatusQuestionarioAluno status);
	
   /**
    * Sincroniza o objeto que está na aplicação com o que está no banco de dados
    */
	public void flush();
	
}
