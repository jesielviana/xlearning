package br.com.xlearning.mbean.curso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.CursoService;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.EnumStatus;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.model.CursoDataModel;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.parametro.service.ParametroService;
import br.com.xlearning.usuario.entidade.Coordenador;
import br.com.xlearning.usuario.service.CoordenadorService;

/**
 * @author jesiel
 */
@ManagedBean
@SessionScoped
public class CursoMB extends PageMB {

	private String PATH_LOGO_CURSOS;
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CursoMB.class);
	@EJB
	private CursoService cursoService;
	@EJB
	private ParametroService parametroService;
	@EJB
	private CoordenadorService coordenadorService;
	@Inject
	private Curso curso;
	@Inject
	private NavigationMB navigationMB;
	private List<Curso> cursos;
	private Integer statusCurso;
	private CursoDataModel cursoDataModel;
	private List<Disciplina> disciplinas;
	private List<String> codigoDisciplinas;
	private Long matriculaCoordanador;
	private List<Coordenador> coordenadores;
	private UploadedFile file;
	private boolean cursosCadastrados;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		getNavigationMB().limparSessao();
	}

	public Curso getCurso() {
		return curso;
	}

	public void inicializaCadastro() {
		coordenadores = coordenadorService.getTodosCoordenadores();
	}

	public void inicializaConsulta() {
		cursos = cursoService.getListaTodosCursosAtivos();
		cursoDataModel = new CursoDataModel(cursos);
		existeCursosCadastrados();
	}

	private void existeCursosCadastrados() {
		if (cursos.size() < 1) {
			addErrorMessage("Não existe cursos cadastrados");
			setCursosCadastrados(true);
		}
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	/**
	 * Faz o upload da imagem do curso para algum diretório que está descrito no
	 * parâmetro 1;
	 * 
	 * @throws IOException
	 */
	public void fileUpload() throws IOException {
		if (file.getFileName().isEmpty()) {
			return;
		}
		FileOutputStream fout = null;
		try {

			// Cria um arquivo UploadFile, para receber o arquivo do evento

			UploadedFile arq = file;

			InputStream in = new BufferedInputStream(arq.getInputstream());

			// copiar para pasta do projeto

			File diretorio = new File(PATH_LOGO_CURSOS);
			diretorio.mkdirs();
			String extesao = arq.getFileName().substring(
					arq.getFileName().indexOf("."));
			String nomeImage = curso.getNome() + extesao;
			File file = new File(PATH_LOGO_CURSOS + nomeImage);

			// O método file.getAbsolutePath() fornece o caminho do arquivo
			// criado

			// Pode ser usado para ligar algum objeto do banco ao arquivo
			// enviado

			String caminho = file.getAbsolutePath();
			logger.info("A imagem [" + arq.getFileName() + "] foi salva em: ["
					+ caminho + "]");

			this.curso.setNomeLogo(nomeImage);

			fout = new FileOutputStream(file);

			while (in.available() != 0) {
				fout.write(in.read());
			}
			fout.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			fout.close();
		}
	}

	/**
    * 
    */
	private boolean carregaParametroLogoCurso() {
		try {
			PATH_LOGO_CURSOS = parametroService
					.getParametroString(NumeroParametro.PATH_LOGO_CURSOS);
			if (PATH_LOGO_CURSOS == null) {
				logger.error("não existe parâmetro cadastrado.");
				addErrorMessage("Não existe parâmetro cadastrado para logo de cursos");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("não existe parâmetro cadastrado.");
			addErrorMessage("Não existe parâmetro cadastrado para logo de cursos");
			return false;
		}
	}

	private boolean preencheStatus() {
		if (getStatusCurso() == null) {
			addErrorMessage("Selecione o status do curso");
			return false;
		}
		this.curso.setStatus(EnumStatus.get(getStatusCurso()));
		return true;
	}

	private void preencheCoordenador() {
		if (matriculaCoordanador != null && matriculaCoordanador > 0)
			this.curso.setCoordenador(coordenadorService
					.buscaCoordenadorPorId(matriculaCoordanador));
	}

	public String adicionarCurso() {
		try {
			if (!validaNome() || !carregaParametroLogoCurso() | !validaLogo()
					| !preencheStatus()) {
				return null;
			}
			fileUpload();

			if (matriculaCoordanador != null && matriculaCoordanador > 0) {
				preencheCoordenador();
			}
			cursoService.adcionaCurso(this.curso);
		} catch (Exception e) {
			addErrorMessage("Erro ao adcionar curso");
			return null;
		}
		curso = new Curso();
		removeBean(ConstantsMB.BEAN_CURSO_MB);
		addInfoMessage(getMessageResources().getString("message.curso.sucesso"));
		return null;
	}

	private boolean validaNome() {
		if (cursoService.getCursoPorNome(this.curso.getNome()) != null) {
			addErrorMessage("Curso já cadastrado");
			return false;
		}
		return true;
	}

	/**
	 * Verifica se o usuário selecionou seu avatar
	 * 
	 * @return
	 */
	private boolean validaLogo() {
		if (file.getFileName().isEmpty()) {
			addErrorMessage("Selecione a logo do curso");
			return false;
		}
		return true;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public String paginaAlterarCurso() {
		recuperaSatatus(curso);
		recuperaCoordenador(curso);
		return ConstantsNavigation.ALTERAR_CURSO;
	}

	public String alterarCurso() {
		try {
			preencheStatus();
			preencheCoordenador();
			fileUpload();
			cursoService.atualizaCurso(curso);
		} catch (Exception e) {
			addErrorMessage("Erro ao atualizar curso");
			return null;
		}
		removeBean(ConstantsMB.BEAN_CURSO_MB);
		addInfoMessage("Curso alterada com sucesso.");
		return ConstantsNavigation.CONSULTAR_CURSO;
	}

	public Integer getStatusCurso() {
		return statusCurso;
	}

	public void setStatusCurso(Integer statusCurso) {
		this.statusCurso = statusCurso;
	}

	public List<EnumStatus> getStatusItens() {
		return Arrays.asList(EnumStatus.values());
	}

	private void recuperaSatatus(Curso curso) {
		setStatusCurso(curso.getStatus());
	}

	private void recuperaCoordenador(Curso curso) {
		if (curso.getCoordenador() != null) {
			setMatriculaCoordanador(curso.getCoordenador().getMatricula());
		}
	}

	public CursoDataModel getCursoDataModel() {
		return cursoDataModel;
	}

	public void setCursoDataModel(CursoDataModel cursoDataModel) {
		this.cursoDataModel = cursoDataModel;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<String> getCodigoDisciplinas() {
		return codigoDisciplinas;
	}

	public void setCodigoDisciplinas(List<String> codigoDisciplinas) {
		this.codigoDisciplinas = codigoDisciplinas;
	}

	public Long getMatriculaCoordanador() {
		return matriculaCoordanador;
	}

	public void setMatriculaCoordanador(Long matriculaCoordanador) {
		this.matriculaCoordanador = matriculaCoordanador;
	}

	public List<Coordenador> getCoordenadores() {
		return coordenadores;
	}

	public void setCoordenadores(List<Coordenador> coordenadores) {
		this.coordenadores = coordenadores;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isCursosCadastrados() {
		return cursosCadastrados;
	}

	public void setCursosCadastrados(boolean cursosCadastrados) {
		this.cursosCadastrados = cursosCadastrados;
	}

	public NavigationMB getNavigationMB() {
		return navigationMB;
	}

	public void setNavigationMB(NavigationMB navigationMB) {
		this.navigationMB = navigationMB;
	}

	public String cancelar() {
		navigationMB.limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
	}

	public String cancelarALteracao() {
		curso = new Curso();
		return ConstantsNavigation.RESULTADO_CONSULTA_CURSO;
	}

}
