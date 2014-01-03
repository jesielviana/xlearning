/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.conteudo.service.ConteudoAcademicoService;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.TipoConteudo;
import br.com.xlearning.mbean.conteudo.ConteudoAcademicoVO;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.login.LoginMB;
import br.com.xlearning.parametro.service.ParametroService;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 * @author Jesiel Viana
 * 
 *         Date: 16/10/2013
 */
@ManagedBean
@SessionScoped
public class AlunoAcademicoMB extends PageMB {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AlunoAcademicoMB.class);
	@EJB
	private ConteudoAcademicoService conteudoAcademicoService;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	ParametroService parametroService;
	@EJB
	private AlunoDisciplinaService alunoDisciplinaService;
	private List<ConteudoAcademicoVO> conteudoAcademicos;
	private List<Disciplina> disciplinasCursando;
	private List<Disciplina> disciplinasACursar;
	private List<Disciplina> disciplinasCursadas;
	private List<AlunoDisciplina> alunoDisciplinas;
	private Disciplina disciplina;
	private Aluno aluno;
	@Inject
	private LoginMB loginMB;
	private MenuModel menuModel;
	private PieChartModel pieModel;
	private StreamedContent file;  
	private String filePath;

	@PostConstruct
	private void init()
	{
		aluno = new Aluno();
		aluno = (Aluno) loginMB.getUserSession();
		setConteudoAcademicos(new ArrayList<ConteudoAcademicoVO>());
		setDisciplinas(new ArrayList<Disciplina>());
		getDisiciplinasDoAluno();
		criaMenu(disciplinasCursando);
		createPieModel();
		getFilePath();
	}


   /**
    * 
    */
   private void getDisiciplinasDoAluno()
   {
      disciplinasCursando = disciplinaService.getDisciplinasAlunoCursando(aluno.getMatricula(), SituacaoAlunoDisciplina.CURSANDO);
		disciplinasACursar = disciplinaService.getDisciplinasAlunoCursando(aluno.getMatricula(), SituacaoAlunoDisciplina.A_CURSAR);
		disciplinasCursadas = disciplinaService.getDisciplinasAlunoCursando(aluno.getMatricula(), SituacaoAlunoDisciplina.CURSADA);
   }
	
    
    public void criaFile(ConteudoAcademicoVO conteudoAcademico) {  
    	
    	 File file1 = new File(filePath+conteudoAcademico.getCaminho());
    	 String extesao = conteudoAcademico.getCaminho().substring(conteudoAcademico.getCaminho().indexOf("."));
        InputStream stream = null;
		try
		{
			stream = new FileInputStream(file1);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		conteudoAcademico.setFile(new DefaultStreamedContent(stream, "application/"+extesao.replace(".", ""), 
				conteudoAcademico.getNome()+extesao));  
    }  
  
    public StreamedContent getFile() {  
        return file;  
    }    


	public PieChartModel getPieModel()
	{
		return pieModel;
	}

	/**
	 * Cria o gráfico de disciplinas
	 */
	private void createPieModel()
	{
		pieModel = new PieChartModel();
		int sizeCursar = disciplinasACursar.size();
		int sizeCursando = disciplinasCursando.size();
		int sizeAprovadas = disciplinasCursadas.size();
		int total = sizeCursar + sizeCursando + sizeAprovadas;
		float percentagemCursadas = 0;
		float percentagemCursar = (float) sizeCursar / total;
		float percentagemCursando = (float) sizeCursando / total;
		pieModel.set("Cursadas", percentagemCursadas * 1000);
		pieModel.set("Cursando", percentagemCursando * 1000);
		pieModel.set("Cursar", percentagemCursar * 1000);
	}

	/**
	 * Carrega o conteúdo acadêmico das disciplinas que o aluno está cursando
	 */
	public String inicializaConteudoDisciplina()
	{
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String id = req.getParameter("id");
		if (id != null)
		{
			setConteudoAcademicos(new ArrayList<ConteudoAcademicoVO>());
			setDisciplinas(new ArrayList<Disciplina>());
			disciplina = disciplinaService.buscaPorId(new Long(id));
			disciplinasCursando = disciplinaService.getDisciplinasAlunoCursando(aluno.getMatricula(), SituacaoAlunoDisciplina.CURSANDO);
			List<ConteudoAcademico> conteudos = conteudoAcademicoService.listaConteudoPorDisciplina(disciplina);
			if (conteudos != null && !conteudos.isEmpty())
			{
				converte(conteudos);
				for (ConteudoAcademicoVO conteudoAcademicoVO : conteudoAcademicos)
				{
					if(conteudoAcademicoVO.getTipo().equals(TipoConteudo.OUTROS.getChave()) )
					criaFile(conteudoAcademicoVO);
				}
			}
		}
		return "disciplinas.jsf?faces-redirect=true";
	}
	
	/**
	 * Converte "ConteudoAcademico" para "ConteudoAcademicoVO"
	 * @param conteudos
	 */
	private void converte(List<ConteudoAcademico> conteudos)
	{
		ConteudoAcademicoVO vo;
		for (ConteudoAcademico conteudoAcademico : conteudos)
		{
			vo = new ConteudoAcademicoVO();
			vo.setNome(conteudoAcademico.getNome());
			vo.setTipo(conteudoAcademico.getTipo());
			vo.setCaminho(conteudoAcademico.getCaminho());
			vo.setDataInicial(conteudoAcademico.getDataPostagem());
			this.conteudoAcademicos.add(vo);
		}
		Collections.sort(conteudoAcademicos);
	}

	/**
	 * Carrega as notas de todas as disciplinas do aluno
	 */
	public void carregaNotas()
	{
	   List<SituacaoAlunoDisciplina> situacoes = new ArrayList<SituacaoAlunoDisciplina>();
	   situacoes.add(SituacaoAlunoDisciplina.CURSANDO);
	   if(disciplinasCursando == null || disciplinasCursando.isEmpty())
	   {
		   addErrorMessage("Você não está matriculado em nenhuma disciplina.");
		   return;
	   }
	   alunoDisciplinas = alunoDisciplinaService.getListaDeDisciplinasAlunoDisciplinaPorSituacao(aluno.getMatricula(), getIdsDisiciplinas(disciplinasCursando), situacoes);
	}
	
   public List<Long> getIdsDisiciplinas(List<Disciplina> disciplinas)
   {
      List<Long> ids = new ArrayList<Long>();
      for (Disciplina disciplina : disciplinas)
      {
         ids.add(disciplina.getIdDisciplina());
      }
      return ids;
   }
	
	/**
	 * Cria o menu com as disciplinas que o aluno logado no sistema está cursando
	 * @param disciplinas
	 */
	private void criaMenu(List<Disciplina> disciplinas)
	{
		menuModel = new DefaultMenuModel();

		// First submenu
		DefaultSubMenu submenu = new DefaultSubMenu();
		submenu.setLabel("Conteúdo");

		DefaultMenuItem item;
		for (Disciplina disciplina : disciplinas)
		{
			item = new DefaultMenuItem();
			item.setValue(disciplina.getNome());
			item.setCommand("#{alunoAcademicoMB.inicializaConteudoDisciplina}");
			item.setParam("id", disciplina.getIdDisciplina());
			submenu.addElement(item);
		}

		menuModel.addElement(submenu);
	}

	public List<ConteudoAcademicoVO> getConteudoAcademicos()
	{
		return conteudoAcademicos;
	}

	public void setConteudoAcademicos(List<ConteudoAcademicoVO> conteudoAcademicos)
	{
		this.conteudoAcademicos = conteudoAcademicos;
	}

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinasCursando;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinasCursando = disciplinas;
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

	public Disciplina getDisciplina()
	{
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}

	public List<Disciplina> getDisciplinasACursar()
	{
		return disciplinasACursar;
	}

	public void setDisciplinasACursar(List<Disciplina> disciplinasACursar)
	{
		this.disciplinasACursar = disciplinasACursar;
	}

	public List<Disciplina> getDisciplinasAprovadas()
	{
		return disciplinasCursadas;
	}

	public void setDisciplinasAprovadas(List<Disciplina> disciplinasAprovadas)
	{
		this.disciplinasCursadas = disciplinasAprovadas;
	}
	
	private void getFilePath()
	{
		try
		{
			filePath = parametroService.getParametroString(NumeroParametro.PATH_CONTEUDO_ACADEMICO);
		}
		catch (Exception e)
		{
			logger.info("Não existe parametro cadastrado para path de logo.");
		}
	}


   public List<AlunoDisciplina> getAlunoDisciplinas()
   {
      return alunoDisciplinas;
   }

}
