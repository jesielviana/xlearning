/**
 * Xlearning 2013
 */
package br.com.xlearning.mbean.questionario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.login.LoginMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.questionario.entidade.Opcao;
import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.entidade.QuestionarioAlunoPK;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.questionario.service.OpcaoService;
import br.com.xlearning.questionario.service.QuestaoService;
import br.com.xlearning.questionario.service.QuestionarioAlunoService;
import br.com.xlearning.questionario.service.QuestionarioService;
import br.com.xlearning.questionario.service.RespostaService;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.AlunoService;
import br.com.xlearning.util.XlearningUtil;

/**
 * @author Jesiel Viana Date 27/10/2013
 */

@ManagedBean
@SessionScoped
public class QuestionarioMB extends PageMB {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QuestionarioMB.class);

	@EJB
	private QuestionarioService questionarioService;
	@EJB
	private QuestaoService questaoService;
	@EJB
	private AlunoService alunoService;
	@EJB
	private OpcaoService opcaoService;
	@EJB
	private RespostaService respostaService;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private QuestionarioAlunoService questionarioAlunoService;
	@Inject
	private LoginMB loginMB;
	private List<Disciplina> disciplinas;
	private Professor professor;
	private Disciplina disciplina;
	private Questionario questionario;
	private List<Questionario> questionarios;
	private Questao questao;
	private List<Questao> questoes;
	private List<Opcao> opcoes;
	private MenuModel menuModel;
	private boolean novo;
	private boolean btnAlterar;
	@Inject
	private NavigationMB navigationMB;
	
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init()
	{
		setProfessor(new Professor());
		setProfessor((Professor) getLoginMB().getUserSession());
		carregaDisciplinasDoProfessor();
		questoes = new ArrayList<Questao>();
	}

	/**
	 * Inicia um novo questionário, usado para abrir o form de cadastro
	 */
	public void novoQuestionario()
	{
		novo = true;
		questionario = new Questionario();

	}

	/**
	 * Cancela questionário, usado para fechar o form de cadastro
	 */
	public void cancelarQuestionario()
	{
		novo = false;
		questionario = new Questionario();

	}

	public void carregaQuestionarios(Disciplina disciplina)
	{
		questionarios = questionarioService.getQuestionariosPorDisciplina(disciplina);
	}

	/**
	 * Carrega as discplinas do professor.
	 */
	public void carregaDisciplinasDoProfessor()
	{
		try
		{
			disciplinas = disciplinaService.getDisciplinasPorProfessor(professor.getMatricula());
			logger.info("disciplinas carregadas com sucesso");
			criaMenu(disciplinas);

		}
		catch (Exception e)
		{
			logger.info("disciplinas carregadas com sucesso", e);
		}
	}

	/**
	 * Gera o menu de disciplinas
	 * 
	 * @param disciplinas
	 */
	private void criaMenu(List<Disciplina> disciplinas)
	{
		setMenuModel(new DefaultMenuModel());

		DefaultSubMenu submenu = new DefaultSubMenu();
		submenu.setLabel("Questionário");

		DefaultMenuItem item;
		for (Disciplina disciplina : disciplinas)
		{
			item = new DefaultMenuItem();
			item.setValue(disciplina.getNome());
			item.setCommand("#{questionarioMB.criarQuestionario}");
			item.setIcon("ui-icon-extlink");
			item.setParam("id", disciplina.getIdDisciplina());
			submenu.addElement(item);
		}

		getMenuModel().addElement(submenu);
	}

	public String criarQuestionario()
	{

		String id = getParametroIdDisciplina();
		disciplina = disciplinaService.buscaPorId(new Long(id));
		carregaQuestionarios(disciplina);
		return "questionarios.jsf?faces-redirect=true";
	}

	private String getParametroIdDisciplina()
	{
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String id = req.getParameter("id");
		return id;
	}

	public void adicionarQuestionario()
	{
		questionario.setDisciplina(disciplina);
		if (!validaDatas(questionario))
		{
			return ;
		}
		try
		{
			questionarioService.adicionarQuestionario(questionario);
			questionarioService.flush();
			questionarioAlunoService.adicionaQuestionarioService(getQuestionarioAlunos(questionario));

		}
		catch (Exception e)
		{
			logger.error("Não foi possível criar o questionário", e);
			addErrorMessage("Não foi possível criar o questionário");
			return;
		}
		logger.info("questionário " + questionario.getNome() + " adcionado com sucesso");
		addInfoMessage("Questionário adcionado com sucesso");
		cancelarQuestionario();
		carregaQuestionarios(disciplina);
	}

	public void excluirQuestionario(Questionario questionario)
	{
		try
		{

			questionarioService.remove(questionarioService.buscaPorId(questionario.getIdquestionario()));
		}
		catch (Exception e)
		{
			addErrorMessage("Não foi possível remover o questionário.");
			return;
		}
		carregaQuestionarios(disciplina);
		addInfoMessage("Questionário removido com sucesso.");
	}

	public void excluirQuestao(Questao questao)
	{
		try
		{

			questaoService.remove(questaoService.buscaPorId(questao.getIdquestao()));
		}
		catch (Exception e)
		{
			addErrorMessage("Não foi possível remover o questão.");
			return;
		}
		iniciaQuestoes();
		addInfoMessage("Questão removido com sucesso.");
	}

	private List<Aluno> carregarAlunosPorDisciplinaCursando(Long id)
	{

		return alunoService.getAlunoPorSituacaoDisciplina(id, SituacaoAlunoDisciplina.CURSANDO);

	}

	private List<QuestionarioAluno> getQuestionarioAlunos(Questionario questionario)
	{
		List<Aluno> alunos = carregarAlunosPorDisciplinaCursando(questionario.getDisciplina().getIdDisciplina());
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

	public void alterarQuestionario(RowEditEvent event)
	{
		if (!validaDatas((Questionario) event.getObject()))
		{
			return ;
		}
		try
		{
			questionarioService.alterarQuestionario((Questionario) event.getObject());
		}
		catch (Exception e)
		{
			logger.error("Não foi possível alterar o questionário", e);
			addErrorMessage("Não foi possível alterar o questionário");
		}
		logger.info("questionário alterado com sucesso");
		addInfoMessage("Questionário alterado com sucesso");
	}

	public void cancelarAlterarQuestionario()
	{

	}

	public String paginaAdicionarQuestao()
	{
		iniciaQuestoes();
		return ConstantsNavigation.PAGINA_ADD_QUESTAO;
	}

	/**
	 * 
	 */
	private void iniciaQuestoes()
	{
		questao = new Questao();
		opcoes = new ArrayList<Opcao>();
		questoes = questaoService.getQuestoesPorQuestionario(questionario.getIdquestionario());

	}

	public void adicionarQuestao()
	{
		questao.setQuestionario(questionario);
		if (!validaNumero(questao) || !validaOpcoes(opcoes))
		{
			return;
		}
		try
		{
			questaoService.adicionarQuestao(questao);
			opcaoService.adicionarOpoes(opcoes);

		}
		catch (Exception e)
		{
			logger.error("Não foi possível adicionar a questão.");
			addErrorMessage("Não foi possível adicionar a questão.");
			return;
		}
		logger.info("Questão adicionada com sucesso");
		addInfoMessage("Questão adicionada com sucesso");
		iniciaQuestoes();
	}
	
	private boolean validaDatas(Questionario questionario)
	{
		boolean valido = true;
		if (questionario.getDataInicial().compareTo(XlearningUtil.getDataTrucada(new Date())) == -1)
		{
			valido = false;
			addErrorMessage("A data inicial deve ser maior que a data atual.");
		}
		if (questionario.getDataFinal().compareTo(XlearningUtil.getDataTrucada(new Date())) == -1)
		{
			valido = false;
			addErrorMessage("A data final deve ser maior que a data atual.");
		}
		if (questionario.getDataInicial().compareTo(questionario.getDataFinal()) == 1)
		{
			valido = false;
			addErrorMessage("A data inicial deve ser menor ou igual a data final.");
		}
		return valido;
	}
	
	public void alterarQuestao(RowEditEvent event)
	{
		Questao questao = (Questao) event.getObject();
		if (!validaOpcoes(converteOpcaosParaList(questao.getOpcoes())))
		{
			return;
		}
		try
		{
			questaoService.alterarQuestao(questao);
		}
		catch (Exception e)
		{
			logger.error("Não foi possível alterar a questão", e);
			addErrorMessage("Não foi possível alterar a questão");
		}
		logger.info("questão alterado com sucesso");
		addInfoMessage("questão alterado com sucesso");
	}

	/**
	 * Verifica se já existe uma questão com o mesmo número para o questionário
	 * 
	 * @param questao
	 * @return
	 */
	private boolean validaNumero(Questao questao)
	{
		if (questaoService.getQuestaoPorNumeroEQuestionario(questao.getNumero(), questao.getQuestionario()
				.getIdquestionario()) != null)
		{
			addErrorMessage("Já existe uma questão com o número " + questao.getNumero());
			logger.error("Já existe uma questão com o número " + questao.getNumero());
			return false;
		}
		return true;
	}

	/**
	 * Verifica se existe somente uma opção correta, é obrigatório ter uma só.
	 * 
	 * @param opcoes
	 * @return
	 */
	private boolean validaOpcoes(List<Opcao> opcoes)
	{
		boolean valido = false;
		int corretas = 0;
		for (Opcao opcao : opcoes)
		{
			if (opcao.getOpcaoCorreta())
			{
				corretas++;
				valido = true;
			}
		}
		if (corretas > 1)
		{
			addErrorMessage("Marque somente uma opção como correta.");
			return false;
		}
		else
			if (corretas == 0)
			{
				addErrorMessage("Selecione uma opção como correta.");
			}
		return valido;
	}

	public void addOpcao()
	{
		Opcao opcao = new Opcao();
		opcao.setQuestao(questao);
		opcoes.add(opcao);
	}

	public void removeOpcao(Opcao opcao)
	{
		opcoes.remove(opcao);
	}

	public String cancelarQuestao()
	{
		return "questionarios.jsf?faces-redirect=true";
	}

	public List<Opcao> converteOpcaosParaList(Set<Opcao> opcoesSet)
	{
		List<Opcao> opcoes = new ArrayList<Opcao>();
		if (opcoesSet != null)
		{
			opcoes.addAll(opcoesSet);
			Collections.sort(opcoes);
		}
		return opcoes;
	}

	public PieChartModel gerarGrafico(Questionario questionario)
	{
		List<Aluno> alunosCursandoDisciplina = new ArrayList<Aluno>();
		List<Aluno> alunosEmAndamento = new ArrayList<Aluno>();
		List<Aluno> alunosConcluido = new ArrayList<Aluno>();

		alunosCursandoDisciplina = getTodosAlunosCursandosDisciplina();
		alunosEmAndamento = getAlunosFazendoQuestionario(questionario);
		alunosConcluido = getAlunosConcluiramQuestionario(questionario);
		return criaGrafico(alunosCursandoDisciplina.size(), alunosEmAndamento.size(), alunosConcluido.size());
	}

	/**
	 * Consulta alunos que concluiram o questionário passado no parâmetro
	 * 
	 * @param questionario
	 * @return
	 */
	public List<Aluno> getAlunosConcluiramQuestionario(Questionario questionario)
	{
		List<Aluno> alunosConcluido;
		alunosConcluido = alunoService.getAlunoPorQuestionarioStatus(questionario.getIdquestionario(),
				StatusQuestionarioAluno.CONCLUIDO);
		return alunosConcluido;
	}

	/**
	 * Consulta alunos que começaram fazer o questionário passado no parâmetro,
	 * más ainda não concluiram
	 * 
	 * @param questionario
	 * @return
	 */
	public List<Aluno> getAlunosFazendoQuestionario(Questionario questionario)
	{
		List<Aluno> alunosEmAndamento;
		alunosEmAndamento = alunoService.getAlunoPorQuestionarioStatus(questionario.getIdquestionario(),
				StatusQuestionarioAluno.EM_ANDAMENTO);
		return alunosEmAndamento;
	}

	/**
	 * Consulta todos alunos que estão cursando a disciplina passada no
	 * parâmetro
	 * 
	 * @param disciplina
	 */
	public List<Aluno> getTodosAlunosCursandosDisciplina()
	{
		List<Aluno> alunosCursandoDisciplina;
		alunosCursandoDisciplina = alunoService.getAlunoPorSituacaoDisciplina(disciplina.getIdDisciplina(),
				SituacaoAlunoDisciplina.CURSANDO);
		return alunosCursandoDisciplina;
	}

	/**
	 * Gera gráfico em forma de pizza.
	 * 
	 * @param total
	 * @param andamento
	 * @param concluido
	 * @return
	 */
	private PieChartModel criaGrafico(int total, int andamento, int concluido)
	{
		PieChartModel pieModel = new PieChartModel();
		int semfazer = total - (andamento + concluido);
		float percentagemAndamento = (float) andamento / total;
		float percentagemConcluido = (float) concluido / total;
		float percentagemSemFazer = (float) semfazer / total;
		pieModel.set("Sem responder", percentagemSemFazer * 1000);
		pieModel.set("Em andamento", percentagemAndamento * 1000);
		pieModel.set("Concluído", percentagemConcluido * 1000);
		return pieModel;
	}

	public int qtdQuestoesCertas(Questionario questionario, Aluno aluno)
	{
		int respostaCertas = 0;

		for (Questao questao : questionario.getQuestoes())
		{
			List<Resposta> respostas = getRespostasPorQuestao(questao, aluno);
			rotuloQuestao: for (Resposta resposta : respostas)
			{
				for (Opcao opcao : questao.getOpcoes())
				{
					if (opcao.getOpcaoCorreta() && resposta.getOpcao().equals(opcao))
					{
						respostaCertas = respostaCertas + 1;
						continue rotuloQuestao;
					}

				}

			}
		}
		return respostaCertas;
	}

	public int qtdQuestoesErradas(Questionario questionario, Aluno aluno)
	{
		int qtdRespostasErradas = 0;
		int qtdRespostas = 0;
		int respostaCertas = 0;
		for (Questao questao : questionario.getQuestoes())
		{
			List<Resposta> respostas = getRespostasPorQuestao(questao, aluno);
			rotuloQuestao:
			for (Resposta resposta : respostas)
			{
				qtdRespostas++;
					for (Opcao opcao : questao.getOpcoes())
					{
						if (opcao.getOpcaoCorreta()
								&& resposta.getOpcao().equals(opcao))
						{
							respostaCertas = respostaCertas + 1;
							continue rotuloQuestao;
						}
						
					}

				}
			qtdRespostasErradas = qtdRespostas - respostaCertas;
		}
		return qtdRespostasErradas;
	}

	public int qtdQuestoesSemRespostas(Questionario questionario, Aluno aluno)
	{
		int total = questionario.getQuestoes().size();
		int qtdSemRespostas = total - (qtdQuestoesErradas(questionario, aluno) + qtdQuestoesCertas(questionario, aluno));
		
		return qtdSemRespostas;
	}

	private List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno)
	{
		List<Resposta> respostas = respostaService.getRespostasPorQuestao(questao, aluno);
		return respostas;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	public List<Questionario> getQuestionarios()
	{
		return questionarios;
	}

	public void setQuestionarios(List<Questionario> questionarios)
	{
		this.questionarios = questionarios;
	}

	public Questao getQuestao()
	{
		return questao;
	}

	public void setQuestao(Questao questao)
	{
		this.questao = questao;
	}

	public Professor getProfessor()
	{
		return professor;
	}

	public void setProfessor(Professor professor)
	{
		this.professor = professor;
	}

	public Disciplina getDisciplina()
	{
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}

	public LoginMB getLoginMB()
	{
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB)
	{
		this.loginMB = loginMB;
	}

	public MenuModel getMenuModel()
	{
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel)
	{
		this.menuModel = menuModel;
	}

	public boolean isNovo()
	{
		return novo;
	}

	public void setNovo(boolean novo)
	{
		this.novo = novo;
	}

	public List<Opcao> getOpcoes()
	{
		return opcoes;
	}

	public void setOpcoes(List<Opcao> opcoes)
	{
		this.opcoes = opcoes;
	}

	public List<Questao> getQuestoes()
	{
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes)
	{
		this.questoes = questoes;
	}

	public boolean isBtnAlterar()
	{
		return btnAlterar;
	}

	public void setBtnAlterar(boolean btnAlterar)
	{
		this.btnAlterar = btnAlterar;
	}

	public NavigationMB getNavigationMB()
	{
		return navigationMB;
	}

	public void setNavigationMB(NavigationMB navigationMB)
	{
		this.navigationMB = navigationMB;
	}
}
