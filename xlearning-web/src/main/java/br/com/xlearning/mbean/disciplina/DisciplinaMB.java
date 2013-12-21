/**
 * 
 */

package br.com.xlearning.mbean.disciplina;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.impl.CursoServiceBean;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.status.EnumStatus;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.model.DisciplinaDataModel;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.ProfessorService;

/**
 * @author jesiel
 */
@ManagedBean
@SessionScoped
public class DisciplinaMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DisciplinaMB.class);
	@Inject
	private Disciplina disciplina;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private CursoServiceBean cursoService;
	@EJB
	private ProfessorService professorService;
	@Inject
	private NavigationMB navigationMB;
	private List<Curso> cursos = new ArrayList<Curso>();
	private List<Professor> professores = new ArrayList<Professor>();
	private Long matriculaProfessor;
	private List<Disciplina> disciplinas;
	private DisciplinaDataModel disciplinaDataModel;
	private Integer statusDisciplina;
	private Long cursoSelecionado;
	private Curso curso;
	private String nomeDisciplina;

	@PostConstruct
	public void init()
	{
		getNavigationMB().limparSessao();
		this.cursos = cursoService.getListaTodosCursosAtivos();
	}

	public void inicializaAlteracao()
	{
		this.professores = professorService.getTodosProfessores();
	}

	public Disciplina getDisciplina()
	{
		return disciplina;
	}

	public List<Curso> getCursos()
	{
		return cursos;
	}

	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}

	public void setCursos(List<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public String consultarDisciplinas()
	{
		if (nomeDisciplina != null && !nomeDisciplina.isEmpty())
		{
			return consultarDisciplinaPorNome();
		}
		else if (curso != null)
		{
			return consultarDisciplinaPorCurso();
		}
		addErrorMessage("Insira os dados para consulta");
		return null;
	}

	private String consultarDisciplinaPorNome()
	{
		Disciplina disciplina = new Disciplina();
		disciplina = disciplinaService.findByNome(nomeDisciplina);
		if (disciplina == null)
		{
			addErrorMessage("Não foi encontrado disciplina com o nome " + nomeDisciplina);
			nomeDisciplina = null;
			return null;
		}
		nomeDisciplina = null;
		disciplinas = new ArrayList<Disciplina>();
		disciplinas.add(disciplina);
		disciplinaDataModel = new DisciplinaDataModel(disciplinas);
		for (Disciplina d : disciplinas)
		{
			recuperaSatatus(d);
			recuperaProfessor(d);
			recuperaCurso(d);
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_DISCIPLINA;
	}

	private String consultarDisciplinaPorCurso()
	{
		if (!validaConsulta())
		{
			return null;
		}
		try
		{
			this.disciplinas = disciplinaService.listaDisciplinasPorCurso(curso);
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de disciplinas", e);
			addErrorMessage("Erro na consulta de disciplinas");
			return null;
		}
		if (disciplinas == null || disciplinas.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhuma disciplinas no curso informado.");
			return null;
		}
		disciplinaDataModel = new DisciplinaDataModel(disciplinas);
		for (Disciplina d : disciplinas)
		{
			recuperaSatatus(d);
			recuperaProfessor(d);
			recuperaCurso(d);
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_DISCIPLINA;
	}

	/**
 * 
 */
	private boolean validaConsulta()
	{
		if (cursos == null || cursos.isEmpty())
		{
			addErrorMessage("Não tem disciplinas cadastradas");
			return false;
		}
		return true;
	}

	public void buscaDisciplinaPorCurso()
	{
		setDisciplinas(disciplinaService.listaDisciplinasPorCurso(curso));
	}

	private boolean preencheStatus()
	{
		if(getStatusDisciplina() == null)
		{
			addErrorMessage("Selecione o status do usuário");
			return false;
		}
		this.disciplina.setStatus(EnumStatus.get(getStatusDisciplina()));
		return true;
	}

	public String adicionarDisciplina()
	{
		if (matriculaProfessor != null && matriculaProfessor > 0)
		{
			adicionarProfessor();
		}
		if (!validaCursos() || !preencheStatus())
		{
			return null;
		}
		preencheStatus();
		try
		{
			disciplinaService.adciona(disciplina);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao adcionar disciplina");
			return null;
		}
		disciplina = new Disciplina();
		removeBean(ConstantsMB.BEAN_DISCIPLINA_MB);
		addInfoMessage("Disciplina adicionada com sucesso.");
		removeBean(ConstantsMB.BEAN_DISCIPLINA_MB);
		return null;
	}

	private boolean validaCursos()
	{
		if (cursos == null || cursos.isEmpty())
		{
			logger.error("Não existe cursos cadastrados.");
			addErrorMessage("Não existe cursos cadastrados.");
			return false;
		}
		else if (disciplina.getCurso() == null)
		{
			logger.error("selecione pelo menos 1 curso");
			addErrorMessage("selecione pelo menos 1 curso");
			return false;
		}
		return true;
	}

	public String paginaAlterarDisciplina()
	{
		recuperaSatatus(disciplina);
		recuperaProfessor(disciplina);
		recuperaCurso(disciplina);
		return ConstantsNavigation.ALTERAR_DISCIPLINA;
	}

	public void alterarDisciplina(RowEditEvent event)
	{
		try
		{
			disciplina = (Disciplina) event.getObject();
			preencheStatus();
			adicionarProfessor();
			preencheCurso();
			disciplinaService.atualiza(disciplina);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao atualizar disciplina");
			logger.error("Erro ao atualizar disciplina", e);
		}
		disciplina = new Disciplina();
		addInfoMessage("Disciplina alterada com sucesso.");
		logger.info("Disciplina alterada com sucesso.");
	}

	public void cancelaAlterarDisciplina()
	{

	}

	private void adicionarProfessor()
	{
		if (matriculaProfessor != null)
		{
			disciplina.setProfessor(professorService.buscaPorId(matriculaProfessor));
		}
		else
		{
			disciplina.setProfessor(null);
		}
	}

	public Long getMatriculaProfessor()
	{
		return matriculaProfessor;
	}

	public void setMatriculaProfessor(Long matriculaProfessor)
	{
		this.matriculaProfessor = matriculaProfessor;
	}

	public List<Professor> getProfessores()
	{
		return professores;
	}

	public void setProfessores(List<Professor> professores)
	{
		this.professores = professores;
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinas = disciplinas;
	}

	public DisciplinaDataModel getDisciplinaDataModel()
	{
		return disciplinaDataModel;
	}

	public void setDisciplinaDataModel(DisciplinaDataModel disciplinaDataModel)
	{
		this.disciplinaDataModel = disciplinaDataModel;
	}

	public Integer getStatusDisciplina()
	{
		return statusDisciplina;
	}

	public void setStatusDisciplina(Integer statusDisciplina)
	{
		this.statusDisciplina = statusDisciplina;
	}

	public List<EnumStatus> getStatusItens()
	{
		return Arrays.asList(EnumStatus.values());
	}

	private void recuperaSatatus(Disciplina disciplina)
	{
		setStatusDisciplina(disciplina.getStatus());
	}
	
	private void preencheCurso()
	{
		if(cursoSelecionado != null && cursoSelecionado > 0)
		this.disciplina.setCurso(cursoService.buscaCursoPorId(cursoSelecionado));
	}
	
	private void recuperaCurso(Disciplina disciplina)
	{
		Curso curso = disciplina.getCurso();
		if (curso != null)
		{
			setCursoSelecionado(curso.getIdCurso());
		}
	}

	private void recuperaProfessor(Disciplina disciplina)
	{
		Professor professor = disciplina.getProfessor();
		if (professor != null)
		{
			setMatriculaProfessor(professor.getMatricula());
		}
	}

	public Long getCursoSelecionado()
	{
		return cursoSelecionado;
	}

	public void setCursoSelecionado(Long cursoSelecionado)
	{
		this.cursoSelecionado = cursoSelecionado;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
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

	public String getNomeDisciplina()
	{
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina)
	{
		this.nomeDisciplina = nomeDisciplina;
	}

}
