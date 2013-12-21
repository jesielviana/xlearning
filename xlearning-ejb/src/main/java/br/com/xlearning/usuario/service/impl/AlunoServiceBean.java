package br.com.xlearning.usuario.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.error.ErrorCode;
import br.com.xlearning.usuario.dao.AlunoRepository;
import br.com.xlearning.usuario.dao.UsuarioRepository;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.service.AlunoService;

/**
 * 
 * @author jesiel
 * 
 */
@Stateless
@LocalBean
@Local(AlunoService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlunoServiceBean implements AlunoService {
	private static Logger logger = Logger.getLogger(AlunoServiceBean.class);
	@Inject
	private AlunoRepository alunoRepository;
	@Inject
	private UsuarioRepository usuarioRepository;

	public void adcionaAluno(Aluno aluno) throws BusinessException, PersistenceException, EJBException
	{
		isCPFCadastrado(aluno.getCpf());
		alunoRepository.adiciona(aluno);
		logger.info("aluno adicionado com sucesso. nome: " + aluno.getNome());
	}

	public Aluno buscaAlunoPorMatricula(Long id) throws PersistenceException, EJBException
	{
		return (Aluno) alunoRepository.buscaPorId(id);
	}

	public List<Aluno> buscaAlunoPorCurso(Curso curso) throws PersistenceException, EJBException
	{
		return alunoRepository.buscaPorCurso(curso);
	}

	public List<Aluno> listaTodosAlunos() throws PersistenceException, EJBException
	{
		return alunoRepository.listaTodosAlunos();
	}

	public void alterarAluno(Aluno aluno) throws PersistenceException, EJBException
	{
		alunoRepository.atualiza(aluno);
	}

	private void isCPFCadastrado(String cpf) throws PersistenceException, BusinessException
	{
		if (usuarioRepository.getUsuarioPorCpf(cpf) != null)
		{
			throw new BusinessException(ErrorCode.CPF_JA_CADASTRADO);
		}
	}

	@Override
	public Aluno buscaAlunoPorCPF(String cpf) throws PersistenceException, EJBException
	{
		return (Aluno) usuarioRepository.getUsuarioPorCpf(cpf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.usuario.service.AlunoService#flush()
	 */
	@Override
	public void flush()
	{
		alunoRepository.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.usuario.service.AlunoService#buscaPorCursoNotTurma(br
	 * .com.xlearning.curso.entidade.Curso,
	 * br.com.xlearning.turma.entidade.Turma)
	 */
	@Override
	public List<Aluno> buscaPorCursoNotTurma(Curso curso, Long idTurma) throws PersistenceException
	{
		// TODO Auto-generated method stub
		return alunoRepository.buscaPorCursoNotTurma(curso, idTurma);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.xlearning.usuario.service.AlunoService#getAlunoPorSituacaoDisciplina
	 * (java.lang.Long, br.com.xlearning.enumeracao.SituacaoAlunoDisciplina)
	 */
	@Override
	public List<Aluno> getAlunoPorSituacaoDisciplina(Long disciplina, SituacaoAlunoDisciplina situacao) throws PersistenceException, EJBException
	{
		return alunoRepository.getAlunoPorSituacaoDisciplina(disciplina, situacao);
	}

	@Override
	public List<Aluno> getAlunoPorQuestionarioStatus(Long questionario, StatusQuestionarioAluno status)
	{
		return alunoRepository.getAlunoPorQuestionarioStatus(questionario, status);
	}

}
