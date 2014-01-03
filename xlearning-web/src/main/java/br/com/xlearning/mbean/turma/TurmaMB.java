/**
 * TCC BSI 2013
 * xlearning.com.br
 */

package br.com.xlearning.mbean.turma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.model.DualListModel;
import br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.CursoService;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.StatusAprovacao;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.enumeracao.status.StatusTurma;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.model.TurmaDataModel;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.entidade.QuestionarioAlunoPK;
import br.com.xlearning.questionario.service.QuestionarioAlunoService;
import br.com.xlearning.questionario.service.QuestionarioService;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.turma.service.TurmaService;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.service.AlunoService;

/**
 * @author jesiel
 * @Data: 2013
 */
@ManagedBean
@SessionScoped
public class TurmaMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TurmaMB.class);
	@Inject
	private Turma turma;
	@EJB
	private TurmaService turmaService;
	@EJB
	private CursoService cursoService;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private QuestionarioService questionarioService;
	@EJB
	private AlunoDisciplinaService alunoDisciplinaService;
	@EJB
	private QuestionarioAlunoService questionarioAlunoService;
	@EJB
	private AlunoService alunoService;
	@Inject
	private NavigationMB navigationMB;
	private List<Turma> turmas;
	private List<Curso> cursos;
	private List<Aluno> alunos;
	private List<String> matriculas;
	private Integer statusTurma;
	private Long idCurso;
	private Curso curso;
	private TurmaDataModel turmaDataModel;

	private DualListModel<Disciplina> disciplinasPickList;
	private List<Disciplina> disciplinasSource;
	private List<Disciplina> disciplinasTarget;

	private DualListModel<Aluno> alunosPickList;
	private List<Aluno> alunosSource;
	private List<Aluno> alunosTarget;
	private List<Aluno> alunosRemovidos;

	@PostConstruct
	private void init()
	{
		getNavigationMB().limparSessao();
	}

	public void inicializaConsulta()
	{
		cursos = cursoService.getListaTodosCursosAtivos();
	}

	public void inicializaDisicplinas()
	{
		disciplinasSource = disciplinaService.listaDisciplinasPorCurso(getCurso());
		if (disciplinasTarget == null)
			disciplinasTarget = new ArrayList<Disciplina>();
		disciplinasPickList = new DualListModel<Disciplina>(disciplinasSource, disciplinasTarget);
	}

	public void inicializaAlunos()
	{
		alunosSource = alunoService.buscaAlunoPorCurso(getCurso());
		if (alunosTarget == null)
			alunosTarget = new ArrayList<Aluno>();
		alunosPickList = new DualListModel<Aluno>(alunosSource, alunosTarget);
	}

	private void preencheStatus()
	{
		this.turma.setStatus(StatusTurma.get(getStatusTurma()));
	}

	public String paginaAlterarTurma()
	{
		setCurso(turma.getCurso());
		recuperaSatatus(turma);
		recuperaAlunos(turma);
		recuperaDisciplinas(turma);
		return ConstantsNavigation.ALTERAR_TURMA;
	}

	public String consultarTurmaPorCurso()
	{
		if (!validaConsulta())
		{
			return null;
		}
		try
		{
			this.turmas = turmaService.getTurmasPorCurso(cursoService.buscaCursoPorId(idCurso));
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de turmas");
			addErrorMessage("Erro na consulta de turmas");
			return null;
		}
		if (turmas == null || turmas.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhuma turma no curso informado.");
			return null;
		}
		turmaDataModel = new TurmaDataModel(turmas);
		return ConstantsNavigation.RESULTADO_CONSULTA_TURMA;
	}

	public String voltarDaAlteracao()
	{
		disciplinasPickList = new DualListModel<Disciplina>();
		alunosPickList = new DualListModel<Aluno>();
		alunosTarget = new ArrayList<Aluno>();
		disciplinasTarget = new ArrayList<Disciplina>();
		return ConstantsNavigation.RESULTADO_CONSULTA_TURMA;
	}

	private boolean validaConsulta()
	{
		if (cursos == null || cursos.isEmpty())
		{
			addErrorMessage("Não tem turmas cadastradas");
			return false;
		}
		else
			if (idCurso == null || idCurso < 1)
			{
				addErrorMessage("Selecione um curso");
				return false;
			}
		return true;
	}

	public String adicionarTurma()
	{
		try
		{
			adicionaDisciplinaATurma();
			adicionaAlunosATurma();
			adicionaCursosaATurma();
			preencheStatus();
			turmaService.adicionaTurma(this.turma);
			alteraAlunoDisciplinaTurmaAberta(this.turma.getAlunos(), this.turma.getDisciplinas());
			questionarioAlunoService.adicionaQuestionarioService(getQuestionariosAlunos(turma.getAlunos(), turma
					.getDisciplinas()));

		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao adicionar turma");
			e.printStackTrace();
			return null;
		}
		removeBean(ConstantsMB.BEAN_TURMA_MB);
		addInfoMessage("Turma adicionada com sucesso.");
		return ConstantsNavigation.CONSULTAR_CURSO;
	}

	/**
	 * 
	 * @param alunos
	 * @param disciplinas
	 * @return
	 */
	private List<QuestionarioAluno> getQuestionariosAlunos(Set<Aluno> alunos, Set<Disciplina> disciplinas)
	{
		List<QuestionarioAluno> questionarioAlunos = new ArrayList<QuestionarioAluno>();
		for (Disciplina disciplina : disciplinas)
		{
			List<Questionario> questionarios = questionarioService.getQuestionariosPorDisciplina(disciplina);
			List<QuestionarioAluno> qas = new ArrayList<QuestionarioAluno>();
			List<QuestionarioAluno> set = getQuestionariosPorDisciplina(alunos, questionarios);
			if (set != null)
				qas.addAll(set);
			if (qas != null && !qas.isEmpty())
			{
				for (QuestionarioAluno questionarioAluno : qas)
				{
					questionarioAlunos.add(questionarioAluno);
				}
			}
		}
		return questionarioAlunos;
	}

	private List<QuestionarioAluno> getQuestionariosPorDisciplina(Set<Aluno> alunos, List<Questionario> questionarios)
	{
		List<QuestionarioAluno> questionarioAlunos = new ArrayList<QuestionarioAluno>();
		for (Questionario questionario : questionarios)
		{
			List<QuestionarioAluno> qas = criaQuestionarioAluno(alunos, questionario);
			if (qas != null && !qas.isEmpty())
			{
				for (QuestionarioAluno questionarioAluno : qas)
				{
					questionarioAlunos.add(questionarioAluno);
				}
			}
		}
		return questionarioAlunos;
	}

	/**
	 * Cria o relacionamento dentre aluno x questionário
	 * 
	 * @param alunos
	 * @param questionario
	 * @return
	 */
	private List<QuestionarioAluno> criaQuestionarioAluno(Set<Aluno> alunos, Questionario questionario)
	{
		List<QuestionarioAluno> questionarioAlunos = new ArrayList<QuestionarioAluno>();
		for (Aluno aluno : alunos)
		{
			QuestionarioAluno questionarioAluno = new QuestionarioAluno();
			QuestionarioAlunoPK pk = new QuestionarioAlunoPK(questionario.getIdquestionario(), aluno.getMatricula());
			questionarioAluno.setQuestionarioAlunoPK(pk);
			questionarioAluno.setStatus(StatusQuestionarioAluno.A_FAZER);
			questionarioAlunos.add(questionarioAluno);
		}
		return questionarioAlunos;
	}

	public String alterarTurma()
	{
		try
		{
			adicionaDisciplinaATurma();
			adicionaAlunosATurma();
			preencheStatus();
			turmaService.alterarTurma(this.turma);
			verificaStatusTurma(turma);
			questionarioAlunoService.atualizaQuestionarioService(getQuestionariosAlunos(turma.getAlunos(), turma
					.getDisciplinas()));

		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao alterar turma");
			e.printStackTrace();
			return null;
		}
		removeBean(ConstantsMB.BEAN_TURMA_MB);
		addInfoMessage("Turma alterada com sucesso.");
		return ConstantsNavigation.CONSULTAR_TURMA;
	}

	private void verificaStatusTurma(Turma turma)
	{
		if (turma.getStatus().equals(StatusTurma.CONCLUIDA.getChave()))
		{
			alteraAlunoDisciplinaTurmaConcluida(turma.getAlunos(), turma.getDisciplinas());
		}
		else
			if (turma.getStatus().equals(StatusTurma.ABERTA.getChave()))
			{
				alteraAlunoDisciplinaTurmaAberta(turma.getAlunos(), turma.getDisciplinas());
			}
			else
			{
				alteraAlunoDisciplinaTurmaCancelada(turma.getAlunos(), turma.getDisciplinas());
			}
	}

	/**
	 * Adiciona as disciplinas selecionadas à turma
	 */
	private void adicionaDisciplinaATurma()
	{

		Set<Disciplina> lista = new HashSet<Disciplina>();
		lista.addAll(disciplinasPickList.getTarget());
		turma.setDisciplinas(lista);
	}

	/**
	 * Adiciona os alunos selecionados à turma
	 */
	private void adicionaAlunosATurma()
	{

		Set<Aluno> lista = new HashSet<Aluno>();
		lista.addAll(alunosPickList.getTarget());
		turma.setAlunos(lista);
	}

	private void alteraAlunoDisciplinaTurmaAberta(Set<Aluno> alunos, Set<Disciplina> disciplinas)
	{
		List<SituacaoAlunoDisciplina> sitList = new ArrayList<SituacaoAlunoDisciplina>();
		sitList.add(SituacaoAlunoDisciplina.A_CURSAR);
		for (Aluno aluno : alunos)
		{
			for (Disciplina disciplina : disciplinas)
			{
				atualizaSituacaoALunoDisciplinaParaCursando(sitList, aluno, disciplina);
			}
		}
		
		for (Aluno aluno : alunosRemovidos)
		{
			if(!alunos.contains(aluno))
			{
			for (Disciplina disciplina : disciplinas)
			{
				atualizaSituacaoALunoDisciplinaParaAcursar(aluno, disciplina);
			}
			}
		}
		

	}

	private void atualizaSituacaoALunoDisciplinaParaCursando(List<SituacaoAlunoDisciplina> sitList, Aluno aluno, Disciplina disciplina)
	{
		AlunoDisciplina alunoDisciplina = alunoDisciplinaService.getAlunoDisciplinaPorSituacao(aluno.getMatricula(), disciplina
				.getIdDisciplina(), sitList);
		if (alunoDisciplina != null)
		{
			alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.CURSANDO.getChave());
			alunoDisciplina.setStatusAprovacao(StatusAprovacao.NAO_CURSOU.getChave());
			logger.info(aluno.getMatricula() + " cursando " + disciplina.getIdDisciplina());
			alunoDisciplinaService.atualizarDisciplinasParaAluno(alunoDisciplina);
		}
	}
	
	private void atualizaSituacaoALunoDisciplinaParaAcursar(Aluno aluno, Disciplina disciplina)
	{
		List<SituacaoAlunoDisciplina> sitList = new ArrayList<SituacaoAlunoDisciplina>();
		sitList.add(SituacaoAlunoDisciplina.CURSANDO);
		AlunoDisciplina alunoDisciplina = alunoDisciplinaService.getAlunoDisciplinaPorSituacao(aluno.getMatricula(), disciplina
				.getIdDisciplina(), sitList);
		if (alunoDisciplina != null)
		{
			alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.A_CURSAR.getChave());
			alunoDisciplina.setStatusAprovacao(StatusAprovacao.NAO_CURSOU.getChave());
			logger.info(aluno.getMatricula() + " a cursar " + disciplina.getIdDisciplina());
			alunoDisciplinaService.atualizarDisciplinasParaAluno(alunoDisciplina);
		}
	}

	private void alteraAlunoDisciplinaTurmaConcluida(Set<Aluno> alunos, Set<Disciplina> disciplinas)
	{
		List<SituacaoAlunoDisciplina> sitList = new ArrayList<SituacaoAlunoDisciplina>();
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		sitList.add(SituacaoAlunoDisciplina.CURSANDO);
		for (Aluno aluno : alunos)
		{
			for (Disciplina disciplina : disciplinas)
			{
				alunoDisciplina = alunoDisciplinaService.getAlunoDisciplinaPorSituacao(aluno.getMatricula(), disciplina
						.getIdDisciplina(), sitList);
				if (alunoDisciplina != null)
				{
					if (alunoDisciplina.getStatusAprovacao() == StatusAprovacao.APROVADO)
					{
						alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.CURSADA.getChave());
						logger.info("Aluno " + aluno.getMatricula() + " Aprovado na disciplina "
								+ disciplina.getIdDisciplina());
					}
					else
					{
						alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.A_CURSAR.getChave());
						logger.info("Aluno " + aluno.getMatricula() + " Não curso com sucesso a disciplina "
								+ disciplina.getIdDisciplina());
					}
					alunoDisciplinaService.atualizarDisciplinasParaAluno(alunoDisciplina);
				}
			}
		}

	}

	private void alteraAlunoDisciplinaTurmaCancelada(Set<Aluno> alunos, Set<Disciplina> disciplinas)
	{
		List<SituacaoAlunoDisciplina> sitList = new ArrayList<SituacaoAlunoDisciplina>();
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		sitList.add(SituacaoAlunoDisciplina.CURSANDO);
		for (Aluno aluno : alunos)
		{
			for (Disciplina disciplina : disciplinas)
			{
				alunoDisciplina = alunoDisciplinaService.getAlunoDisciplinaPorSituacao(aluno.getMatricula(), disciplina
						.getIdDisciplina(), sitList);
				if (alunoDisciplina != null)
				{
					alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.A_CURSAR.getChave());
					alunoDisciplina.setStatusAprovacao(StatusAprovacao.NAO_CURSOU.getChave());
					logger.info(aluno.getMatricula() + " cursando " + disciplina.getIdDisciplina());
					alunoDisciplinaService.atualizarDisciplinasParaAluno(alunoDisciplina);
				}
			}
		}

	}

	private void adicionaCursosaATurma()
	{

		turma.setCurso(getCurso());
	}

	private void recuperaDisciplinas(Turma turma)
	{
		disciplinasSource = disciplinaService.getDisciplinasDoCursoNotTurma(turma.getIdturma(), cursoService
				.buscaCursoPorId(idCurso));
		if (disciplinasTarget == null)
			disciplinasTarget = new ArrayList<Disciplina>();
		this.disciplinasTarget.addAll(turma.getDisciplinas());
		disciplinasPickList = new DualListModel<Disciplina>(disciplinasSource, disciplinasTarget);
	}

	/**
	 * Método responsável por recuperar os alunos ja cadastrados para a turma e
	 * buscar os alunos do curso que ainda não estão cadastrados
	 * 
	 * @param turma
	 */
	private void recuperaAlunos(Turma turma)
	{
		alunosSource = alunoService.buscaPorCursoNotTurma(getCurso(), turma.getIdturma());
		if (alunosTarget == null)
		{
			alunosTarget = new ArrayList<Aluno>();
			alunosRemovidos = new ArrayList<Aluno>();
		}
		this.alunosTarget.addAll(turma.getAlunos());
		this.alunosRemovidos.addAll(turma.getAlunos());
		alunosPickList = new DualListModel<Aluno>(alunosSource, alunosTarget);
	}

	public Turma getTurma()
	{
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public Integer getStatusTurma()
	{
		return statusTurma;
	}

	public void setStatusTurma(Integer statusTurma)
	{
		this.statusTurma = statusTurma;
	}

	public List<StatusTurma> getStatusItens()
	{
		return Arrays.asList(StatusTurma.values());
	}

	private void recuperaSatatus(Turma turma)
	{
		setStatusTurma(turma.getStatus());
	}

	public List<Aluno> getAlunos()
	{
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos)
	{
		this.alunos = alunos;
	}

	public List<String> getMatriculas()
	{
		return matriculas;
	}

	public void setMatriculas(List<String> matriculas)
	{
		this.matriculas = matriculas;
	}

	public Long getIdCurso()
	{
		return idCurso;
	}

	public String paginaCadastrarTurma()
	{
		inicializaDisicplinas();
		inicializaAlunos();
		return ConstantsNavigation.ADICIONAR_TURMA;
	}

	public void setIdCurso(Long idCurso)
	{
		this.idCurso = idCurso;
	}

	public List<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(List<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public List<Turma> getTurmas()
	{
		return turmas;
	}

	public void setTurmas(List<Turma> turmas)
	{
		this.turmas = turmas;
	}

	public TurmaDataModel getTurmaDataModel()
	{
		return turmaDataModel;
	}

	public void setTurmaDataModel(TurmaDataModel turmaDataModel)
	{
		this.turmaDataModel = turmaDataModel;
	}

	public DualListModel<Disciplina> getDisciplinasPickList()
	{
		return disciplinasPickList;
	}

	public void setDisciplinasPickList(DualListModel<Disciplina> disciplinasPickList)
	{
		this.disciplinasPickList = disciplinasPickList;
	}

	public DualListModel<Aluno> getAlunosPickList()
	{
		return alunosPickList;
	}

	public void setAlunosPickList(DualListModel<Aluno> alunosPickList)
	{
		this.alunosPickList = alunosPickList;
	}

	public List<Aluno> getAlunosSource()
	{
		return alunosSource;
	}

	public void setAlunosSource(List<Aluno> alunosSource)
	{
		this.alunosSource = alunosSource;
	}

	public List<Aluno> getAlunosTarget()
	{
		return alunosTarget;
	}

	public void setAlunosTarget(List<Aluno> alunosTarget)
	{
		this.alunosTarget = alunosTarget;
	}

	public NavigationMB getNavigationMB()
	{
		return navigationMB;
	}

	public void setNavigationMB(NavigationMB navigationMB)
	{
		this.navigationMB = navigationMB;
	}

	public String cancelar()
	{
		navigationMB.limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
	}

}
