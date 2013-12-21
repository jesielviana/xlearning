/**
 * Xlearning 2013
 */

package br.com.xlearning.alunodisciplina.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.disciplina.dao.AlunoDisciplinaRepository;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.StatusAprovacao;
import br.com.xlearning.parametro.service.ParametroService;

/**
 * @author Jesiel Viana Date 21/09/2013
 */
@Stateless
@LocalBean
@Local(AlunoDisciplinaService.class)
public class AlunoDisciplinaServiceBean implements AlunoDisciplinaService {

	@Inject
	private AlunoDisciplinaRepository alunoDisciplinaRepository;
	@EJB
	private ParametroService parametroService;

	private double media = 5.0;
	private int peso1 = 5;
	private int peso2 = 5;

	@Override
	public void adicionarDisciplinasParaAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException
	{
		alunoDisciplinaRepository.adiciona(alunoDisciplina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDiscipinaService#
	 * atualizarDisciplinasParaAluno(br.com.xlearning.disciplina.entidade.
	 * AlunoDisciplina)
	 */
	@Override
	public void atualizarDisciplinasParaAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException
	{
		alunoDisciplinaRepository.atualiza(alunoDisciplina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDiscipinaService#
	 * getAlunoDisciplinaCursar(java.lang.Long, java.lang.Long, java.util.List)
	 */
	@Override
	public AlunoDisciplina getAlunoDisciplinaPorSituacao(Long aluno, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException, EJBException
	{
		return alunoDisciplinaRepository.getAlunoDisciplinaPorSituacao(aluno, disciplina, situacaoAlunoDisciplinas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService#
	 * getListaAlunoDisciplinaPorSituacao(java.util.List, java.lang.Long,
	 * java.util.List)
	 */
	@Override
	public List<AlunoDisciplina> getListaAlunoDisciplinaPorSituacao(List<Long> alunos, Long disciplina, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException, EJBException
	{
		return alunoDisciplinaRepository.getListaDeAlunoDisciplinaPorSituacao(alunos, disciplina,
				situacaoAlunoDisciplinas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService#
	 * getListaDeDisciplinasAlunoDisciplinaPorSituacao(java.lang.Long,
	 * java.util.List, java.util.List)
	 */
	@Override
	public List<AlunoDisciplina> getListaDeDisciplinasAlunoDisciplinaPorSituacao(Long aluno, List<Long> disciplinas, List<SituacaoAlunoDisciplina> situacaoAlunoDisciplinas) throws PersistenceException, EJBException
	{
		return alunoDisciplinaRepository.getListaDeDisciplinasAlunoDisciplinaPorSituacao(aluno, disciplinas,
				situacaoAlunoDisciplinas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService#
	 * calculaMediaDoAluno(br.com.xlearning.disciplina.entidade.AlunoDisciplina
	 * )
	 */
	@Override
	public float calculaMediaDoAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException
	{
		carregaParametros();
		Float nota2 = alunoDisciplina.getNota2();
		Float nota1 = alunoDisciplina.getNota1();
		return ((nota1 != null ? nota1 : 0) * peso1 + (nota2 != null ? nota2 : 0) * peso2) / 10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService#
	 * verificaSituaçãoDoAluno(br.com.xlearning.disciplina.entidade.
	 * AlunoDisciplina)
	 */
	@Override
	public StatusAprovacao verificaSituaçãoDoAluno(AlunoDisciplina alunoDisciplina) throws PersistenceException, EJBException
	{
		carregaParametros();
		if (alunoDisciplina.getNota2() != null)
		{
			if (alunoDisciplina.getMedia() < media)
			{
				return StatusAprovacao.REPROVADO;
			}
			else
			{
				return StatusAprovacao.APROVADO;
			}
		}
		else
		{
			return StatusAprovacao.NAO_CURSOU;
		}
	}

	private void carregaParametros()
	{
		double media = parametroService.getParametroReal(NumeroParametro.MEDIA);
		int peso1 = parametroService.getParametroInteiro(NumeroParametro.PESO1);
		int peso2 = parametroService.getParametroInteiro(NumeroParametro.PESO2);
		if (media > 0)
		{
			this.media = media;
		}
		if (peso1 > 0)
		{
			this.peso1 = peso1;
		}
		if (peso2 > 0)
		{
			this.peso2 = peso2;
		}
	}

}
