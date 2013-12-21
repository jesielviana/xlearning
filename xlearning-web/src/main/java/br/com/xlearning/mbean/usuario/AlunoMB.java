package br.com.xlearning.mbean.usuario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import br.com.xlearning.alunodisciplina.service.AlunoDisciplinaService;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.CursoService;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.disciplina.entidade.AlunoDisciplinaPK;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.StatusAprovacao;
import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.mbean.model.UsuarioDataModel;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.parametro.service.ParametroService;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.turma.service.TurmaService;
import br.com.xlearning.usuario.dto.ConsultaUsuarioDTO;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.service.AlunoService;
import br.com.xlearning.util.CriptografaUtil;

/**
 * @author jesiel
 * @Data: 2013
 */
@ManagedBean
@SessionScoped
public class AlunoMB extends UsuarioMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AlunoMB.class);
	@EJB
	private AlunoService alunoService;
	@EJB
	private CursoService cursoService;
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private AlunoDisciplinaService alunoDiscipinaService;
	@EJB
	private ParametroService parametroService;
	@EJB
	private TurmaService turmaService;

	@Inject
	private NavigationMB navigationMB;
	private Aluno aluno;
	private List<Disciplina> disciplinas;
	private List<Turma> turmas;
	private List<Aluno> alunos;
	private Long idTurma;
	private Curso curso;
	private List<Curso> cursos;
	@Inject
	private ConsultaUsuarioDTO consultaUsuarioDTO;
	private Aluno alunoSelecionado;
	private UsuarioDataModel<Aluno> usuarioDataModel;
	private List<AlunoDisciplina> alunoDisciplinas;
	private UploadedFile file;
	private String PATH_AVATAR;

	public AlunoMB()
	{
		this.aluno = new Aluno();
	}

	@PostConstruct
	private void init()
	{
		getNavigationMB().limparSessao();
	}

	public void inicializa()
	{
		this.cursos = cursoService.getListaTodosCursosAtivos();
		buscaTurmasPorCurso();
	}

	public String paginaAlterarAluno()
	{
		if (alunoSelecionado != null)
		{
			recuperaUFeSatatus(alunoSelecionado);
		}
		return ConstantsNavigation.ALTERAR_ALUNO;
	}

	/**
	 * Cria o aluno e adiciona o mesmo no banco de dados
	 * 
	 * @return
	 */
	public String adicionarAluno()
	{

		if (!validaCPF(aluno) || !preencheUf(aluno) | !preencheStatus(aluno))
		{
			addErrorMessage("CPF inválido");
			return null;
		}

		if (!validaDados(aluno) || !validaCurso(aluno))
		{
			return null;
		}
		criarAluno(this.aluno, true);
		fileUpload(); // Carrega o avatar de aluno para o local especificado no
						// parâmetro 2.
		try
		{
			alunoService.adcionaAluno(aluno);
			// sincroniza a entidade cadastrada com o cache do hibernate para
			// que o mesmo possa usar sei id(gerado pelo sgbd)
			alunoService.flush();
			// Adiciona turma ao aluno
			persistTurmaAluno(this.aluno);
			// Adiciona as disciplinas para o aluno
			adcionaDisciplinaAoAluno(aluno);
			persistDisciplinasAluno();

		}
		catch (PersistenceException e)
		{
			e.printStackTrace();
			addErrorMessage("Falha na comunicação com o banco de dados");
			return null;
		}
		catch (EJBException e)
		{
			addErrorMessage("Falha na inserção de aluno");
			e.printStackTrace();
			return null;
		}
		catch (BusinessException e)
		{
			addErrorMessage(e);
			e.printStackTrace();
			return null;
		}
		aluno = new Aluno();
		removeBean(ConstantsMB.BEAN_ALUNO_MB); // retira o bean da sessão
		addInfoMessage("Aluno cadastrado com sucesso");
		logger.info("Aluno adcionado com sucesso. " + aluno.getNome());
		return null;
	}

	private boolean validaDados(Aluno aluno)
	{
		boolean erro = true;
		if (getStatusUsuario() == null)
		{
			addErrorMessageCampoEspeficico("form:status", "Selecione um status");
			erro = false;
		}
		if (getUf() == null)
		{
			addErrorMessageCampoEspeficico("form:uf", "Selecione uma UF");
			erro = false;
		}
		return erro;
	}

	/**
	 * Insere as disciplinas relacionadas ao aluno
	 */
	private void persistDisciplinasAluno()
	{
		if (alunoDisciplinas != null && !alunoDisciplinas.isEmpty())
		{
			for (AlunoDisciplina alunoDisciplina : alunoDisciplinas)
			{
				alunoDiscipinaService.adicionarDisciplinasParaAluno(alunoDisciplina);
			}
		}
	}

	/**
	 * Adiciona aluno a turma
	 */
	private void persistTurmaAluno(Aluno aluno)
	{
		if (aluno.getTurmas() != null && !aluno.getTurmas().isEmpty())
		{
			Set<Aluno> lista = new HashSet<Aluno>();
			lista.add(aluno);
			for (Turma turma : aluno.getTurmas())
			{
				turma.setAlunos(lista);
				turmaService.alterarTurma(turma);

			}
		}
	}

	/**
	 * Verifica se foi selecionado algum curso
	 * 
	 * @param aluno
	 * @return
	 */
	private boolean validaCurso(Aluno aluno)
	{
		if (cursos == null || cursos.isEmpty())
		{
			addErrorMessage("Não existe cursos cadastrados");
			logger.error("Não existe cursos cadastrados");
			return false;
		}
		else
			if (aluno.getCurso() == null)
			{
				addErrorMessage("Selecione um curso");
				logger.error("Selecione um curso");
				return false;
			}
		return true;
	}

	/**
	 * 
	 * @param aluno
	 * @param cadastro
	 */
	private void criarAluno(Aluno aluno, boolean cadastro)
	{
		preencheStatus(aluno);
		preencheUf(aluno);
		preencheTurma(aluno);
		if (cadastro)
		{
			preencheRole();
			preencheSenha();
		}
	}

	public String alterarAluno()
	{
		if (!validaCurso(alunoSelecionado))
		{
			return null;
		}
		criarAluno(this.alunoSelecionado, false);
		fileUpload();
		try
		{
			alunoService.alterarAluno(alunoSelecionado);
			alunoService.flush();
			persistTurmaAluno(alunoSelecionado);
		}
		catch (Exception e)
		{
			addInfoMessage("Erro  ao alterar aluno");
			logger.error("Erro  ao alterar aluno.", e);
			return null;
		}
		aluno = new Aluno();
		alunoSelecionado = new Aluno();
		addInfoMessage("Aluno alterado com sucesso");
		logger.info("Aluno alterado com sucesso.");
		return ConstantsNavigation.RESULTADO_CONSULTA_ALUNO;
	}

	public String consultarAluno()
	{
		preencheCursoParaConsulta();
		if (consultaUsuarioDTO.getMatricula() != null && consultaUsuarioDTO.getMatricula() > 0)
		{
			return consultarAlunoPorMatricula();
		}
		else
			if (consultaUsuarioDTO.getCpf() != null && !consultaUsuarioDTO.getCpf().isEmpty())
			{
				return consultarAlunoPorCpf();
			}
			else
				if (consultaUsuarioDTO.getCurso() != null)
				{
					return consultarAlunoPorCurso();
				}
				else
				{
					addErrorMessage("Informe os dados para consulta");
					return null;
				}
	}

	private String consultarAlunoPorMatricula()
	{
		alunos = new ArrayList<>();
		Aluno aluno = new Aluno();
		try
		{
			aluno = alunoService.buscaAlunoPorMatricula(consultaUsuarioDTO.getMatricula());
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de aluno", e);
			addErrorMessage("Erro na consulta de aluno");
			return null;
		}
		if (aluno == null)
		{
			addErrorMessage("Não foi encontrado nenhuma aluno com a matricula inforamda.");
			return null;
		}
		alunos.add(aluno);
		usuarioDataModel = new UsuarioDataModel<Aluno>(alunos);
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
		return ConstantsNavigation.RESULTADO_CONSULTA_ALUNO;
	}
	
	public String voltarConsulta()
	{
		return ConstantsNavigation.RESULTADO_CONSULTA_ALUNO;
	}

	private String consultarAlunoPorCpf()
	{
		alunos = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		try
		{
			aluno = alunoService.buscaAlunoPorCPF(consultaUsuarioDTO.getCpf());
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de aluno", e);
			addErrorMessage("Erro na consulta de aluno");
			return null;
		}
		if (aluno == null)
		{
			addErrorMessage("Não foi encontrado nenhuma aluno com o cpf inforamda.");
			return null;
		}
		alunos.add(aluno);
		usuarioDataModel = new UsuarioDataModel<Aluno>(alunos);
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
		return ConstantsNavigation.RESULTADO_CONSULTA_ALUNO;
	}

	private String consultarAlunoPorCurso()
	{
		try
		{
			this.alunos = alunoService.buscaAlunoPorCurso(consultaUsuarioDTO.getCurso());
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de aluno", e);
			addErrorMessage("Erro na consulta de aluno");
			return null;
		}
		if (alunos == null || alunos.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhuma aluno no curso informado.");
			return null;
		}
		usuarioDataModel = new UsuarioDataModel<Aluno>(alunos);
		return ConstantsNavigation.RESULTADO_CONSULTA_ALUNO;
	}

	private void preencheCursoParaConsulta()
	{
		if (this.getCurso() != null)
		{
			consultaUsuarioDTO.setCurso(this.getCurso());
		}
	}

	/**
	 * Adciona as disciplinas selecionadas ao Aluno no momento do cadastro ou
	 * alteração do mesmo
	 */
	private void adcionaDisciplinaAoAluno(Aluno aluno)
	{
		buscaDisciplinasPorCurso(aluno);
		alunoDisciplinas = new ArrayList<AlunoDisciplina>();
		for (Disciplina disciplina : disciplinas)
		{
			alunoDisciplinas.add(preencheAlunoDisciplina(disciplina, aluno));
		}
	}

	private void buscaDisciplinasPorCurso(Aluno aluno)
	{
		if (aluno.getCurso() != null)
		{
			this.disciplinas = disciplinaService.listaDisciplinasPorCurso(aluno.getCurso());
		}
	}

	private AlunoDisciplina preencheAlunoDisciplina(Disciplina disciplina, Aluno aluno)
	{
		AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
		AlunoDisciplinaPK pk = new AlunoDisciplinaPK(aluno.getMatricula(), disciplina.getIdDisciplina());
		alunoDisciplina.setAlunoDisciplinaPK(pk);
		if (aluno.getTurmas() != null && !aluno.getTurmas().isEmpty())
		{
			for (Turma turma : aluno.getTurmas())
			{
				if (turma.getDisciplinas().contains(disciplina))
				{
					alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.CURSANDO.getChave());
					alunoDisciplina.setStatusAprovacao(StatusAprovacao.NAO_CURSOU.getChave());
				}
				else
				{
					alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.A_CURSAR.getChave());
				}
			}
		}
		else
		{
			alunoDisciplina.setSituacao(SituacaoAlunoDisciplina.A_CURSAR.getChave());
		}

		return alunoDisciplina;
	}

	protected boolean preencheStatus(Aluno aluno)
	{
		if(super.getStatusUsuario()== null)
		{
			addErrorMessage("Selecione o status do usuário");
			return false;
		}
		aluno.setStatus(StatusUsuario.get(super.getStatusUsuario()));
		return true;
	}

	protected boolean preencheUf(Aluno aluno)
	{
		if(super.getUf()== null)
		{
			addErrorMessage("Selecione a UF");
			return false;
		}
		
		aluno.setUf(UF.get(super.getUf()));
		return true;
	}

	private void preencheTurma(Aluno aluno)
	{
		if (this.getIdTurma() != null && this.getIdTurma() > 0)
		{
			Set<Turma> turmas = new HashSet<Turma>();
			turmas.add(turmaService.buscaTurmaPorId(this.getIdTurma()));
			aluno.setTurmas(turmas);
		}
	}

	private void preencheRole()
	{
		this.aluno.setRole(super.getRoleService().getRole(Permissao.ROLE_ALUNO));
	}

	private void preencheSenha()
	{
		this.aluno.setSenha(CriptografaUtil.getSenhaCriptografada(getSenha()));
	}

	public List<Aluno> getAlunos()
	{
		return alunos;
	}

	public List<Curso> getCursos()
	{
		return cursos;
	}

	public ConsultaUsuarioDTO getConsultaUsuarioDTO()
	{
		return consultaUsuarioDTO;
	}

	private void buscaTurmasPorCurso()
	{
		if (aluno.getCurso() != null)
		{
			this.turmas = turmaService.getTurmasPorCurso(aluno.getCurso());
		}
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinas = disciplinas;
	}

	public void setAlunos(List<Aluno> alunos)
	{
		this.alunos = alunos;
	}

	public void setCursos(List<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public void setConsultaUsuarioDTO(ConsultaUsuarioDTO consultaUsuarioDTO)
	{
		this.consultaUsuarioDTO = consultaUsuarioDTO;
	}

	public Aluno getAlunoSelecionado()
	{
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado)
	{
		this.alunoSelecionado = alunoSelecionado;
	}

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
	}

	public UsuarioDataModel<Aluno> getUsuarioDataModel()
	{
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(UsuarioDataModel<Aluno> usuarioDataModel)
	{
		this.usuarioDataModel = usuarioDataModel;
	}

	public UploadedFile getFile()
	{
		return file;
	}

	public void setFile(UploadedFile file)
	{
		this.file = file;
	}

	/**
	 * 
	 * @return
	 */
	private boolean carregaParametroAvatar()
	{
		try
		{
			PATH_AVATAR = parametroService.getParametroString(NumeroParametro.PATH_LOGO_CURSOS);
			if (PATH_AVATAR == null)
			{
				logger.error("não existe parâmetro cadastrado.");
				addErrorMessage("Não existe parâmetro cadastrado para logo de cursos");
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			logger.error("não existe parâmetro cadastrado.", e);
			addErrorMessage("Não existe parâmetro cadastrado para logo de cursos");
			return false;
		}
	}

	public void fileUpload()
	{
		if (file.getFileName().isEmpty())
		{
			return;
		}
		if (!carregaParametroAvatar())
		{
			return;
		}
		FileOutputStream fout = null;
		try
		{

			// Cria um arquivo UploadFile, para receber o arquivo do evento

			UploadedFile arq = file;

			InputStream in = new BufferedInputStream(arq.getInputstream());

			// copiar para pasta do projeto

			File diretorio = new File(PATH_AVATAR);
			diretorio.mkdirs();
			String extesao = arq.getFileName().substring(arq.getFileName().indexOf("."));
			String nomeImage = aluno.getCpf() + extesao;
			File file = new File(PATH_AVATAR + nomeImage);

			// O método file.getAbsolutePath() fornece o caminho do arquivo
			// criado

			// Pode ser usado para ligar algum objeto do banco ao arquivo
			// enviado

			String caminho = file.getAbsolutePath();
			logger.info("A imagem [" + arq.getFileName() + "] foi salva em: [" + caminho + "]");
			this.aluno.setAvatar(nomeImage);

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

	public static void main(String args[])
	{
		String sistema = System.getProperty("os.name");
		System.out.println("Voce esta usando: " + sistema);
	}

	public List<Turma> getTurmas()
	{
		return turmas;
	}

	public void setTurmas(List<Turma> turmas)
	{
		this.turmas = turmas;
	}

	public Long getIdTurma()
	{
		return idTurma;
	}

	public void setIdTurma(Long idTurma)
	{
		this.idTurma = idTurma;
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

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}
	
	
	
	public List<Turma> converteOpcaosParaList(Set<Turma> opcoesSet)
	{
		List<Turma> opcoes = new ArrayList<Turma>();
		if (opcoesSet != null)
		{
			opcoes.addAll(opcoesSet);
		}
		return opcoes;
	}

}
