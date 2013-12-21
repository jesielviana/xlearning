/**
 * Xlearning 2013
 */

package br.com.xlearning.mbean.usuario;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.mbean.login.LoginMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.AlunoService;

/**
 * @author Jesiel Viana Date 15/10/2013
 */
@ManagedBean
@SessionScoped
public class ProfessorAcademicoMB extends UsuarioMB
{

   private static final long serialVersionUID = 1L;

   private static Logger logger = Logger.getLogger(ProfessorAcademicoMB.class);
   @EJB
   private DisciplinaService disciplinaService;
   @EJB
   private AlunoDisciplinaService alunoDisciplinaService;
   @EJB
   private AlunoService alunoService;
   @Inject
   private NavigationMB navigationMB;
   private List<Disciplina> disciplinas;
   private Professor professor;
   private Aluno aluno;
   private List<Aluno> alunos;
   private List<AlunoDisciplina> alunosDisciplinas;
   @Inject
   private LoginMB loginMB;
   private MenuModel menuModel;

   @SuppressWarnings("unused")
   @PostConstruct
   private void init()
   {
      professor = new Professor();
      alunosDisciplinas = new ArrayList<AlunoDisciplina>();
      professor = (Professor) getLoginMB().getUserSession();
      carregaDisciplinasDoProfessor();
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
      menuModel = new DefaultMenuModel();

      DefaultSubMenu submenu = new DefaultSubMenu();
      submenu.setLabel("Alunos/Notas");

      DefaultMenuItem item;
      for (Disciplina disciplina : disciplinas)
      {
         item = new DefaultMenuItem();
         item.setValue(disciplina.getNome());
         item.setCommand("#{professorAcademicoMB.carregarAlunosDisciplinasPorDisciplinaCursando}");
         item.setParam("id", disciplina.getIdDisciplina());
         item.setIcon("ui-icon-extlink"); 
         submenu.addElement(item);
      }

      getMenuModel().addElement(submenu);
   }

   public String alterarNotas(RowEditEvent event)
   {
      AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
      try
      {
         alunoDisciplina = (AlunoDisciplina) event.getObject();
         alunoDisciplina = calcularMediaDoAluno(alunoDisciplina);
         alunoDisciplina = adicionaSitaucaoDoAlunoNaDisciplina(alunoDisciplina);
         alunoDisciplinaService.atualizarDisciplinasParaAluno(alunoDisciplina);
      }
      catch (Exception e)
      {
         logger.error("Erro ao alterar alunoDisciplina");
         addErrorMessage("Não foi possível alterar os dados do aluno.");
         return null;
      }
      
      logger.info("ALunoDisciplina alterado com sucesso");
      addInfoMessage("Dados do aluno alterado com sucesso.");
      return null;
   }
   
   public AlunoDisciplina calcularMediaDoAluno(AlunoDisciplina alunoDisciplina)
   {
      alunoDisciplina.setMedia(alunoDisciplinaService.calculaMediaDoAluno(alunoDisciplina));
      return alunoDisciplina;
   }
   
   public AlunoDisciplina adicionaSitaucaoDoAlunoNaDisciplina(AlunoDisciplina alunoDisciplina)
   {
      alunoDisciplina.setStatusAprovacao(alunoDisciplinaService.verificaSituaçãoDoAluno(alunoDisciplina).getChave());
      return alunoDisciplina;
   }

   public void cancelarAlteracaoNotas()
   {
	   
   }

   /**
    * Carrega os alunos que estão cursando a discipĺina(id) passada no parâmetro
    * 
    * @param id
    */
   private void carregarAlunosPorDisciplinaCursando(String id)
   {
      alunos = alunoService.getAlunoPorSituacaoDisciplina(new Long(id), SituacaoAlunoDisciplina.CURSANDO);

   }

   /**
    * Pega as matrículas dos alunos que estão cursnado a disciplina
    * 
    * @param id
    * @return
    */
   public List<Long> getMatriculasAlunos(String id)
   {
      carregarAlunosPorDisciplinaCursando(id);
      List<Long> listaDeMatriculas = new ArrayList<Long>();
      for (Aluno aluno : alunos)
      {
         listaDeMatriculas.add(aluno.getMatricula());
      }
      return listaDeMatriculas;
   }

   /**
    * Carrega o relacionamento alunoDisciplina para os alunos que estão cursando a disciplina A disciplina é buscada no parâmetro "id"
    * passado pelo menu, que é o "id" da disciplina selecionada
    * 
    * @return
    */
   public String carregarAlunosDisciplinasPorDisciplinaCursando()
   {
      String id = getParametroIdDisciplina();
      if (id != null)
      {
         alunosDisciplinas =
            alunoDisciplinaService.getListaAlunoDisciplinaPorSituacao(getMatriculasAlunos(id), new Long(id), situacoesDesejadas());
      }
      return "notas-alunos.jsf?faces-redirect=true";
   }

   /**
    * @return
    */
   private String getParametroIdDisciplina()
   {
      HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      String id = req.getParameter("id");
      return id;
   }

   /**
    * @return
    */
   private List<SituacaoAlunoDisciplina> situacoesDesejadas()
   {
      List<SituacaoAlunoDisciplina> situacoes = new ArrayList<SituacaoAlunoDisciplina>();
      situacoes.add(SituacaoAlunoDisciplina.CURSANDO);
      return situacoes;
   }

   public LoginMB getLoginMB()
   {
      return loginMB;
   }

   public void setLoginMB(LoginMB loginMB)
   {
      this.loginMB = loginMB;
   }

   public Professor getProfessor()
   {
      return professor;
   }

   public List<Disciplina> getDisciplinas()
   {
      return disciplinas;
   }

   public void setDisciplinas(List<Disciplina> disciplinas)
   {
      this.disciplinas = disciplinas;
   }

   public List<Aluno> getAlunos()
   {
      return alunos;
   }

   public void setAlunos(List<Aluno> alunos)
   {
      this.alunos = alunos;
   }

   public Aluno getAluno()
   {
      return aluno;
   }

   public void setAluno(Aluno aluno)
   {
      this.aluno = aluno;
   }

   public MenuModel getMenuModel()
   {
      return menuModel;
   }

   public List<AlunoDisciplina> getAlunosDisciplinas()
   {
      return alunosDisciplinas;
   }

   public void setAlunosDisciplinas(List<AlunoDisciplina> alunosDisciplinas)
   {
      this.alunosDisciplinas = alunosDisciplinas;
   }
   
	public String cancelar()
	{
	   getNavigationMB().limparSessao();
	   return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
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
