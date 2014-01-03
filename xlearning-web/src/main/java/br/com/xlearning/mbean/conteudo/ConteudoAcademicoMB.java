/**
 * Xlearning 2013
 */

package br.com.xlearning.mbean.conteudo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.conteudo.service.ConteudoAcademicoService;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.TipoConteudo;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.login.LoginMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.notificacao.entidade.Notificacao;
import br.com.xlearning.notificar.service.EnviarNotificacaoService;
import br.com.xlearning.notificar.service.impl.GeradorNotificacaoEmail;
import br.com.xlearning.parametro.service.ParametroService;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.AlunoService;

/**
 * @author Jesiel Viana Date 03/10/2013
 */
@ManagedBean
@SessionScoped
public class ConteudoAcademicoMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ConteudoAcademicoMB.class);
	@EJB
	private ConteudoAcademicoService conteudoAcademicoService;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private ParametroService parametroService;
	@EJB
	private AlunoService alunoService;
	// @EJB
	// private CursoService cursoService;
	@EJB
	private EnviarNotificacaoService enviarNotificacaoService;
	@EJB
	private GeradorNotificacaoEmail geradorNotificacaoEmail;
	@Inject
	private ConteudoAcademico conteudoAcademico;
	@Inject
	private NavigationMB navigationMB;
	private Professor professor;
	private Integer tipo;
	private Curso curso;
	private Disciplina disciplina;
	private List<Disciplina> disciplinas;
	private List<ConteudoAcademico> conteudoAcademicos;
	private Turma turma;
	private List<Turma> turmas;
	private String PathConteudo;
	private UploadedFile file;
	@Inject
	private LoginMB loginMB;
	private boolean enviarNotificacao;
	private String pathConteudo;
	private MenuModel menuModel;

	@PostConstruct
	private void init()
	{
		professor = new Professor();
		professor = (Professor) loginMB.getUserSession();
		carregaDisciplinas();
	}

	/**
	 * Carrega as discplinas do professor e seus cursos
	 */
	public void carregaDisciplinas()
	{
		disciplinas = disciplinaService.getDisciplinasPorProfessor(professor.getMatricula());
		criaMenu(disciplinas);
	}

	private void criaMenu(List<Disciplina> disciplinas)
	{
		menuModel = new DefaultMenuModel();

		DefaultSubMenu submenu = new DefaultSubMenu();
		submenu.setLabel("Conteúdo");

		DefaultMenuItem item;
		for (Disciplina disciplina : disciplinas)
		{
			item = new DefaultMenuItem();
			item.setValue(disciplina.getNome());
			item.setCommand("#{conteudoAcademicoMB.disciplinaSelecionada}");
			item.setParam("id", disciplina.getIdDisciplina());
			item.setIcon("ui-icon-extlink");
			submenu.addElement(item);
		}

		getMenuModel().addElement(submenu);
	}

	public String disciplinaSelecionada()
	{
		String id = getParametroIdDisciplina();
		if (id != null)
		{
			disciplina = disciplinaService.buscaPorId(new Long(id));
			carregaConteudosDaDisciplina(disciplina);
		}
		return "adicionar-conteudo.jsf?faces-redirect=true";
	}

	private void carregaConteudosDaDisciplina(Disciplina disciplina)
	{
		conteudoAcademicos = conteudoAcademicoService.listaConteudoPorDisciplina(disciplina);
	}

	private String getParametroIdDisciplina()
	{
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String id = req.getParameter("id");
		return id;
	}

	public String adicionarConteudo()
	{
		adicionaDataAtual(conteudoAcademico);
		conteudoAcademico = adicionarCursoEDisciplina(conteudoAcademico);
		if (!carregaParametoDeConteudo() || !isSelecionouArquivo() | !preencheTipo(conteudoAcademico))
		{
			return null;
		}
		fileUpload();
		try
		{
			conteudoAcademicoService.adicionarConteudo(conteudoAcademico);
		}
		catch (PersistenceException e)
		{
			addErrorMessage("Não foi possível adicionar conteúdo.");
			logger.error("Não foi possível adicionar conteúdo.", e);
			return null;
		}
		catch (Exception e)
		{
			addErrorMessage("Não foi possível adicionar conteúdo.");
			logger.error("Não foi possível adicionar conteúdo.", e);
			return null;
		}
		if (enviarNotificacao)
		{
			try
			{
				enviarNotificacaoService.enviar(criaNotificacao(conteudoAcademico), geradorNotificacaoEmail);
			}
			catch (EmailException e)
			{
				addWarnMessage("Não foi possível enviar notificação para todos destinatários");
				logger.warn("Não foi possível enviar notificação para todos destinatários", e);
			}
		}
		conteudoAcademico = new ConteudoAcademico();
		carregaConteudosDaDisciplina(disciplina);
		addInfoMessage("Conteúdo adicionado com sucesso");
		logger.info("Conteúdo adicionado com sucesso, nome: " + conteudoAcademico.getNome());
		return null;
	}
	
	public String alterarConteudo(ConteudoAcademico academico)
	{
		preencheTipo(academico);
		try
		{
			conteudoAcademicoService.alterarConteudo(academico);
		}
		catch (Exception e)
		{
			addErrorMessage("erro ao alterar conteúdo.");
		}
		addInfoMessage("Conteúdo alterado com sucesso.");
		return null;
	}

	public void excluirConteudo(ConteudoAcademico conteudoAcademico)
	{
		try
		{

			conteudoAcademicoService.remove(conteudoAcademicoService.buscaPorId(conteudoAcademico
					.getIdconteudoAcademico()));
		}
		catch (Exception e)
		{
			addErrorMessage("Não foi possível remover o conteúdo.");
			return;
		}
		carregaConteudosDaDisciplina(disciplina);
		addInfoMessage("Conteúdo removido com sucesso.");
	}

	public void cancelarAlteracao()
	{}

	private ConteudoAcademico adicionarCursoEDisciplina(ConteudoAcademico conteudoAcademico)
	{
		conteudoAcademico.setDisciplina(disciplina);
		conteudoAcademico.setCurso(disciplina.getCurso());
		return conteudoAcademico;
	}

	private void adicionaDataAtual(ConteudoAcademico academico)
	{
		academico.setDataPostagem(new Date());
	}

	private List<Aluno> getDestinatarios(ConteudoAcademico conteudoAcademico)
	{
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = alunoService.getAlunoPorSituacaoDisciplina(conteudoAcademico.getDisciplina().getIdDisciplina(),
				SituacaoAlunoDisciplina.CURSANDO);
		return alunos;
	}

	private Notificacao criaNotificacao(ConteudoAcademico conteudoAcademico)
	{
		Notificacao n = new Notificacao();
		n.setAssunto(conteudoAcademico.getNome());
		n.setData(new Date());
		n.setGrupoDestino("Alunos");
		n.setMensagem(conteudoAcademico.getDescricao());
		n.setUsuario(professor);
		n.setDestinatarios(getDestinatarios(conteudoAcademico));
		return n;
	}

	/**
	 * Carrega o arquivo enviado pelo professor para o path carregado do
	 * parâmetro 3.
	 */
	public void fileUpload()
	{
		FileOutputStream fout = null;
		try
		{

			// Cria um arquivo UploadFile, para receber o arquivo do evento
			UploadedFile arq = getFile();

			InputStream in = new BufferedInputStream(arq.getInputstream());

			String separador = formataSeparador();
			PathConteudo = pathConteudo + conteudoAcademico.getCurso().getNome() + separador
					+ conteudoAcademico.getDisciplina().getNome() + separador;
			File diretorio = new File(PathConteudo);
			diretorio.mkdirs();
			// String extensao = arq.getFileName().substring(
			// arq.getFileName().indexOf("."));
			String nomeArquivo = arq.getFileName();
			File file = new File(PathConteudo + nomeArquivo);

			// O método file.getAbsolutePath() fornece o caminho do arquivo
			// criado

			// Pode ser usado para ligar algum objeto do banco ao arquivo
			// enviado

			String caminho = conteudoAcademico.getCurso().getNome() + separador
					+ conteudoAcademico.getDisciplina().getNome() + separador + arq.getFileName();
			logger.info("O arquivo" + arq.getFileName() + "] foi salvo em: [" + PathConteudo + "]");
			this.conteudoAcademico.setCaminho(caminho);
			this.conteudoAcademico.setArquivo(arq.getFileName());

			fout = new FileOutputStream(file);

			while (in.available() != 0)
			{

				fout.write(in.read());

			}
			fout.close();

			FacesMessage msg = new FacesMessage("O Arquivo ", file.getName() + " salvo.");

			FacesContext.getCurrentInstance().addMessage("msgUpdate", msg);

		}

		catch (Exception ex)
		{

			ex.printStackTrace();

		}
		finally
		{
			try
			{
				fout.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private String formataSeparador()
	{
		String sistema = System.getProperty("os.name");
		if (sistema.equals("Linux"))
		{
			return "/";
		}
		return "\\";
	}

	/**
	 * @return
	 */
	private boolean carregaParametoDeConteudo()
	{
		try
		{
			pathConteudo = parametroService.getParametroString(NumeroParametro.PATH_CONTEUDO_ACADEMICO);
			if (pathConteudo == null)
			{
				addErrorMessage("Não existe parâmetro cadastrado para o conteúdo.");
				logger.error("Não existe parâmetro cadastrado para o conteúdo.");
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			logger.error("Não existe parâmetro cadastrado para o conteúdo.", e);
			addErrorMessage("Não existe parâmetro cadastrado para o conteúdo.");
			return false;
		}
	}

	private boolean isSelecionouArquivo()
	{
		if (getFile().getFileName().isEmpty())
		{
			addErrorMessage("Selecione um aquivo.");
			return false;
		}
		return true;
	}

	public List<TipoConteudo> getTiposItens()
	{
		return Arrays.asList(TipoConteudo.values());
	}

	protected boolean preencheTipo(ConteudoAcademico conteudoAcademico)
	{
		if(getTipo()== null)
		{
			addErrorMessage("Informe o tipo de conteúdo");
			return false;
		}
		conteudoAcademico.setTipo(TipoConteudo.get(this.getTipo()).getChave());
		return true;
	}

	public Disciplina getDisciplina()
	{
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinas = disciplinas;
	}

	public List<Turma> getTurmas()
	{
		return turmas;
	}

	public void setTurmas(List<Turma> turmas)
	{
		this.turmas = turmas;
	}

	public Turma getTurma()
	{
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public ConteudoAcademico getConteudoAcademico()
	{
		return conteudoAcademico;
	}

	public void setConteudoAcademico(ConteudoAcademico conteudoAcademico)
	{
		this.conteudoAcademico = conteudoAcademico;
	}

	public Integer getTipo()
	{
		return tipo;
	}

	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}

	public UploadedFile getFile()
	{
		return file;
	}

	public void setFile(UploadedFile file)
	{
		this.file = file;
	}

	public Professor getProfessor()
	{
		return professor;
	}

	public void setProfessor(Professor professor)
	{
		this.professor = professor;
	}

	public LoginMB getLoginMB()
	{
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB)
	{
		this.loginMB = loginMB;
	}

	public boolean isEnviarNotificacao()
	{
		return enviarNotificacao;
	}

	public void setEnviarNotificacao(boolean enviarNotificacao)
	{
		this.enviarNotificacao = enviarNotificacao;
	}

	public String cancelar()
	{
		getNavigationMB().limparSessao();
		return ConstantsNavigation.PAGINA_INDEX_PROFESSOR;
	}

	public NavigationMB getNavigationMB()
	{
		return navigationMB;
	}

	public void setNavigationMB(NavigationMB navigationMB)
	{
		this.navigationMB = navigationMB;
	}

	public MenuModel getMenuModel()
	{
		return menuModel;
	}

	public void setMenuModel(MenuModel menuModel)
	{
		this.menuModel = menuModel;
	}

	public List<ConteudoAcademico> getConteudoAcademicos()
	{
		return conteudoAcademicos;
	}

	public void setConteudoAcademicos(List<ConteudoAcademico> conteudoAcademicos)
	{
		this.conteudoAcademicos = conteudoAcademicos;
	}

}
