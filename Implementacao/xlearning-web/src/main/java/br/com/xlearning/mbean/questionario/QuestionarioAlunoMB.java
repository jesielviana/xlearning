package br.com.xlearning.mbean.questionario;

import java.util.ArrayList;
import java.util.Collections;
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
import br.com.xlearning.questionario.entidade.Opcao;
import br.com.xlearning.questionario.entidade.Questao;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.questionario.service.QuestionarioAlunoService;
import br.com.xlearning.questionario.service.QuestionarioService;
import br.com.xlearning.questionario.service.RespostaService;
import br.com.xlearning.usuario.entidade.Aluno;

@ManagedBean
@SessionScoped
public class QuestionarioAlunoMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QuestionarioAlunoMB.class);
	@EJB
	private QuestionarioService questionarioService;
	@EJB
	private RespostaService respostaService;
	private List<Questionario> questionarios;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private QuestionarioAlunoService questionarioAlunoService;
	private List<Disciplina> disciplinasCursando;
	private List<Questao> questoes;
	private Resposta resposta;
	private Aluno aluno;
	@Inject
	private LoginMB loginMB;
	private MenuModel menuModel;

	@PostConstruct
	private void init()
	{
		aluno = new Aluno();
		aluno = (Aluno) loginMB.getUserSession();
		getDisiciplinasDoAluno();
		criaMenu(getDisciplinasCursando());
	}

	/**
	 * Cria o menu com as disciplinas que o aluno logado no sistema está
	 * cursando
	 * 
	 * @param disciplinas
	 */
	private void criaMenu(List<Disciplina> disciplinas)
	{
		setMenuModel(new DefaultMenuModel());

		// First submenu
		DefaultSubMenu submenu = new DefaultSubMenu();
		submenu.setLabel("Questionários");

		DefaultMenuItem item;
		for (Disciplina disciplina : disciplinas)
		{
			item = new DefaultMenuItem();
			item.setValue(disciplina.getNome());
			item.setCommand("#{questionarioAlunoMB.inicializaQuestionariosDisciplina}");
			item.setParam("id", disciplina.getIdDisciplina());
			submenu.addElement(item);
		}

		getMenuModel().addElement(submenu);
	}

	private Resposta criaResposta(Opcao opcao)
	{
		Resposta resposta = new Resposta();
		resposta.setAluno(aluno);
		resposta.setQuestao(opcao.getQuestao());
		resposta.setOpcao(opcao);
		return resposta;
	}

	public void adicionaResposta(Questao questao)
	{
		if (questao.getOpcaoMarcada() == null)
		{
			addErrorMessage("Selecione uma opção");
			return;
		}
		try
		{
			respostaService.adicionarResposta(criaResposta(questao.getOpcaoMarcada()));
			QuestionarioAluno questionarioAluno = getQuestionarioAluno(questao.getOpcaoMarcada());
			if(questionarioAluno != null)
			{
				questionarioAlunoService.atualizaQuestionarioService(questionarioAluno);
			}
			
		}
		catch (Exception e)
		{
			logger.error("Erro ao responder");
			addErrorMessage("Erro ao responder");
			return;
		}
		addInfoMessage("Resposta salva.");
	}
	
	
	public void finalizarQuestionario(Questionario questionario)
	{
		try
		{
			QuestionarioAluno questionarioAluno = getQuestionarioFinalizadoPeloAluno(questionario);
			if(questionarioAluno != null)
			{
				questionarioAlunoService.atualizaQuestionarioService(questionarioAluno);
			}else{
				logger.error("O aluno ainda não iniciou o questionário.");
				addErrorMessage("O aluno ainda não iniciou o questionário.");
				return;
			}
			
		}
		catch (Exception e)
		{
			logger.error("O aluno ainda não iniciou o questionário.");
			addErrorMessage("O aluno ainda não iniciou o questionário.");
			return;
		}
		addInfoMessage("Questionário finalizado com sucesso.");
	}

	/**
	 * 
	 */
	private QuestionarioAluno getQuestionarioAluno(Opcao opcao)
	{
		QuestionarioAluno questionarioAluno = new QuestionarioAluno();
		questionarioAluno =  questionarioAlunoService.getQuestionariosAluno(opcao.getQuestao().getQuestionario(),
				aluno, StatusQuestionarioAluno.A_FAZER);
		if(questionarioAluno != null)
		{
			questionarioAluno.setStatus(StatusQuestionarioAluno.EM_ANDAMENTO);
		}
		return questionarioAluno;
	}
	
	private QuestionarioAluno getQuestionarioFinalizadoPeloAluno(Questionario questionario)
	{
		QuestionarioAluno questionarioAluno = new QuestionarioAluno();
		questionarioAluno =  questionarioAlunoService.getQuestionariosAluno(questionario,
				aluno, StatusQuestionarioAluno.EM_ANDAMENTO);
		if(questionarioAluno != null)
		{
			questionarioAluno.setStatus(StatusQuestionarioAluno.CONCLUIDO);
			return questionarioAluno;
		}
		return null;
	}

	/**
	 * Carrega os questionários da disciplina selecionada
	 * 
	 * @return
	 */
	public String inicializaQuestionariosDisciplina()
	{
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = req.getParameter("id");
		questionarios = questionarioService.getQuestionariosAluno(aluno.getMatricula(), new Long(id), getStatusQuestionariosAluno());
		verificaQuestionarioVazio();
		for (Questionario questionario : questionarios)
		{
			for (Questao questao : questionario.getQuestoes())
			{
				List<Resposta> respostas = getRespostasPorQuestao(questao, this.aluno);
				for (Resposta resposta : respostas)
				{
						questao.setOpcaoMarcada(resposta.getOpcao());
				}
			}
		}
		return "questionarios-aluno.jsf?faces-redirect=true";
	}
	
	private void verificaQuestionarioVazio()
	{
		List<Questionario> ques = new ArrayList<Questionario>();
		for (Questionario q : questionarios)
		{
			if(q.getQuestoes() == null || q.getQuestoes().isEmpty())
			{
				ques.add(q);
			}
		}
		
		removeQuestionario(ques);
		
	}

	private void removeQuestionario(List<Questionario> ques)
	{
		for (Questionario qt : ques)
		{
			questionarios.remove(qt);
		}
	}

	private List<StatusQuestionarioAluno> getStatusQuestionariosAluno() {
		List<StatusQuestionarioAluno> status = new ArrayList<StatusQuestionarioAluno>();
		status.add(StatusQuestionarioAluno.A_FAZER);
		status.add(StatusQuestionarioAluno.EM_ANDAMENTO);
		return status;
	}

	/**
	 * Carrega as disciplinas que o aluno logado no sistema está cursando
	 */
	private void getDisiciplinasDoAluno()
	{
		setDisciplinasCursando(disciplinaService.getDisciplinasAlunoCursando(aluno.getMatricula(), SituacaoAlunoDisciplina.CURSANDO));
	}

	public boolean isQuestaoAResponderPorALunoNaSessao(Questao questao)
	{
		List<Resposta> respostas = getRespostasPorQuestao(questao, this.aluno);
		return respostas.isEmpty();
	}

	public boolean isRespostaCorreta(Questao questao)
	{
		List<Resposta> respostas = getRespostasPorQuestao(questao, this.aluno);
		for (Resposta resposta : respostas)
		{
			if (resposta.getAluno().equals(this.aluno))
			{
				for (Opcao opcao : questao.getOpcoes())
				{
					if (opcao.getOpcaoCorreta()
							&& resposta.getOpcao().equals(opcao))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public String icon(Questao questao)

	{
		if (isRespostaCorreta(questao))
		{
			return "tick_32.png";
		}
		return "delete_32.png";
	}

	public LoginMB getLoginMB()
	{
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB)
	{
		this.loginMB = loginMB;
	}

	public List<Questionario> getQuestionarios()
	{
		return questionarios;
	}

	public void setQuestionarios(List<Questionario> questionarios)
	{
		this.questionarios = questionarios;
	}

	public List<Questao> getQuestoes()
	{
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes)
	{
		this.questoes = questoes;
	}

	public Resposta getResposta()
	{
		return resposta;
	}

	public void setResposta(Resposta resposta)
	{
		this.resposta = resposta;
	}

	public List<Disciplina> getDisciplinasCursando()
	{
		return disciplinasCursando;
	}

	public void setDisciplinasCursando(List<Disciplina> disciplinasCursando)
	{
		this.disciplinasCursando = disciplinasCursando;
	}

	public MenuModel getMenuModel()
	{
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel)
	{
		this.menuModel = menuModel;
	}

	public List<Opcao> converteOpcaosParaList(Set<Opcao> opcoesSet)
	{
		List<Opcao> opcoes = new ArrayList<Opcao>();
		opcoes.addAll(opcoesSet);
		Collections.sort(opcoes);
		return opcoes;
	}

//	/**
//	 * @param opcoes
//	 */
//	private void ordenarOpcoesPorId(List<Opcao> opcoes)
//	{
//		Collections.sort(opcoes, new Comparator<Opcao>() {
//
//			@Override
//			public int compare(Opcao o1, Opcao o2)
//			{
//				return o1.getIdopcoes().compareTo(o2.getIdopcoes());
//			}
//
//		});
//	}

	public Opcao teste(Questao questao)
	{
		List<Resposta> respostas = getRespostasPorQuestao(questao, this.aluno);
		for (Resposta resposta : respostas)
		{
			if (resposta.getAluno().equals(this.aluno))
			{
				return resposta.getOpcao();
			}
		}
		return null;
	}

	public PieChartModel getPieModelQuestionario(Questionario questionario)
	{
		int respostaCertas = 0;
		int qtdRespostasErradas = 0;
		int qtdRespostas = 0;
		int total = questionario.getQuestoes().size();
		for (Questao questao : questionario.getQuestoes())
		{
			List<Resposta> respostas = getRespostasPorQuestao(questao, this.aluno);
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
		

		PieChartModel pieModel = criaGrafico(total, respostaCertas, qtdRespostasErradas);

		return pieModel;
	}

	/**
	 * @param qtdQ
	 * @param respostaCertas
	 * @param qtdSemRespostas
	 * @return
	 */
	private PieChartModel criaGrafico(int total, int respostaCertas, int qtdRespostasErradas)
	{
		int qtdSemResposta;
		PieChartModel pieModel = new PieChartModel();
		qtdSemResposta = total - (qtdRespostasErradas + respostaCertas);
		float percentagemCorreta = (float) respostaCertas / total;
		float percentagemErrada = (float) qtdRespostasErradas / total;
		float percentagemSemFazer = (float) qtdSemResposta / total;
		pieModel.set("Corretas", percentagemCorreta * 1000);
		pieModel.set("Erradas", percentagemErrada * 1000);
		pieModel.set("Sem responder", percentagemSemFazer * 1000);
		return pieModel;
	}

	/**
	 * @param questao
	 * @return
	 */
	private List<Resposta> getRespostasPorQuestao(Questao questao, Aluno aluno)
	{
		List<Resposta> respostas = respostaService.getRespostasPorQuestao(questao,aluno);
		return respostas;
	}

}
