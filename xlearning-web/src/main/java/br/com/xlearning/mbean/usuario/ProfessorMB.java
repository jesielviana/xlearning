/**
 * TCC BSI 2013
 * xlearning.com.br
 */

package br.com.xlearning.mbean.usuario;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import br.com.xlearning.constantes.NumeroParametro;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.mbean.model.UsuarioDataModel;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.parametro.service.ParametroService;
import br.com.xlearning.usuario.dto.ConsultaUsuarioDTO;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.ProfessorService;
import br.com.xlearning.util.CriptografaUtil;

/**
 * @author jesiel
 * @Data: 2013
 */
@javax.faces.bean.ManagedBean
@SessionScoped
public class ProfessorMB extends UsuarioMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ProfessorMB.class);
	@EJB
	private DisciplinaService disciplinaService;
	@EJB
	private ProfessorService professorService;
	@EJB
	private ParametroService parametroService;
	private List<Disciplina> disciplinas;
	private Long codigoDisciplina;
	@Inject
	private NavigationMB navigationMB;
	@Inject
	private ConsultaUsuarioDTO consultaUsuarioDTO;
	private Professor professor;
	private List<Professor> professores;
	private UsuarioDataModel<Professor> usuarioDataModel;
	private String PATH_AVATAR;
	private UploadedFile file;

	public ProfessorMB()
	{
		this.professor = new Professor();
	}

	public void inicializa()
	{
		this.disciplinas = disciplinaService.getListaTodas();
	}

	public Professor getProfessor()
	{
		return professor;
	}

	public void setProfessor(Professor professor)
	{
		this.professor = professor;
	}

	public ConsultaUsuarioDTO getConsultaUsuarioDTO()
	{
		return consultaUsuarioDTO;
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinas = disciplinas;
	}

	public void setConsultaUsuarioDTO(ConsultaUsuarioDTO consultaUsuarioDTO)
	{
		this.consultaUsuarioDTO = consultaUsuarioDTO;
	}

	protected void preencheStatus(Professor professor)
	{
		professor.setStatus(StatusUsuario.get(super.getStatusUsuario()));
	}

	protected void preencheUf(Professor professor)
	{
		professor.setUf(UF.get(super.getUf()));
	}

	private void preencheRoleProfessor()
	{
		this.professor.setRole(super.getRoleService().getRole(Permissao.ROLE_PROFESSOR));
	}

	private void preencheSenha()
	{
		this.professor.setSenha(CriptografaUtil.getSenhaCriptografada(getSenha()));
	}

	public String adcionarProfessor()
	{
		if (!validaCPF(professor))
		{
			addErrorMessage("CPF inválido");
			return null;
		}
		if(!validaDados())
		{
			return null;
		}
		preencheStatus(professor);
		preencheUf(professor);
		preencheRoleProfessor();
		preencheSenha();
      if (!carregaParametroAvatar())
      {
         return null;
      }
		fileUpload();
		try
		{
			professorService.adcionaProfessor(this.professor);
		}
		catch (PersistenceException e)
		{
			addErrorMessage("Falha na comunicação com o banco de dados");
			logger.error("Erro ao adcionar professor: ", e);
			return null;
		}
		catch (EJBException e)
		{
			addErrorMessage("Falha na inserção de professor.");
			logger.error("Erro ao adcionar professor: ", e);
			return null;
		}
		catch (BusinessException e)
		{
			addErrorMessage(e);
			e.printStackTrace();
			return null;
		}
		professor = new Professor();
		removeBean(ConstantsMB.BEAN_PROESSOR_MB);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor cadastrado com sucesso", null));
		logger.info("Professor adcionado com sucesso.");
		return null;
	}
	
	private boolean validaDados()
	{
		boolean erro = true;
		if(getStatusUsuario() == null)
		{
			addErrorMessageCampoEspeficico("form:status","Selecione um status");
			erro = false;
		}
		if(getUf() == null)
		{
			addErrorMessageCampoEspeficico("form:uf", "Selecione uma UF");
			erro = false;
		}
		return erro;
	}

//	private boolean validaAvatar()
//	{
//		if (getFile().getFileName().isEmpty())
//		{
//			addErrorMessage("Selecione seu avatar");
//			return false;
//		}
//		return true;
//	}

	public String consultarProfesor()
	{
		preencheDisciplinaConsulta();
		if (consultaUsuarioDTO.getMatricula() != null
				&& consultaUsuarioDTO.getMatricula() > 0)
		{
			return consultarProfessorPorMatricula();
		}
		else if (consultaUsuarioDTO.getDisciplina() != null)
		{
			return consultarProfessorPorDisciplina();
		}
		else if(getStatusUsuario() != null)
		{
			return consultarProfessorPorStatus();
		}
		else
		{
			addErrorMessage("Informe os dados para a consulta.");
			return null;
		}
	}

	private String consultarProfessorPorMatricula()
	{
		professores = new ArrayList<Professor>();
		try
		{
			professor = professorService.buscaPorId(consultaUsuarioDTO.getMatricula());
			if (professor != null)
			{
				this.professores.add(professor);
			}
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de professor");
			addErrorMessage("Erro na consulta de professor");
			return null;
		}
		if (professores == null || professores.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum professor com a matrícula informada.");
			return null;
		}
		usuarioDataModel = new UsuarioDataModel<Professor>(professores);
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
		return ConstantsNavigation.RESULTADO_CONSULTA_PROFESSOR;
	}

	private String consultarProfessorPorDisciplina()
	{
		professores = new ArrayList<Professor>();
		Disciplina disciplina = consultaUsuarioDTO.getDisciplina();
		if (disciplina.getProfessor() == null)
		{
			logger.error("Disciplina sem professor");
			addErrorMessage("Disciplina sem professor");
			return null;
		}
		try
		{
			professor = professorService.getProfessorPorMatriculaFethJoinDisciplina(disciplina.getIdDisciplina());
			if (professor != null)
			{
				this.professores.add(professor);
			}
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de professor");
			addErrorMessage("Erro na consulta de professor");
			return null;
		}
		if (professores == null || professores.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum professor na disciplina informada.");
			return null;
		}
		usuarioDataModel = new UsuarioDataModel<Professor>(professores);
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
		return ConstantsNavigation.RESULTADO_CONSULTA_PROFESSOR;
	}
	
	private String consultarProfessorPorStatus()
	{
		professores = new ArrayList<Professor>();
		try
		{
			professores = professorService.findByStatus(StatusUsuario.get(getStatusUsuario()));
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de professor");
			addErrorMessage("Erro na consulta de professor");
			return null;
		}
		if (professores == null || professores.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum professor "+StatusUsuario.get(getStatusUsuario()).getValor());
			return null;
		}
		usuarioDataModel = new UsuarioDataModel<Professor>(professores);
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
		return ConstantsNavigation.RESULTADO_CONSULTA_PROFESSOR;
	}


	private void preencheDisciplinaConsulta()
	{
		if (codigoDisciplina != null && codigoDisciplina > 0)
			consultaUsuarioDTO.setDisciplina(disciplinaService.buscaPorId(codigoDisciplina));
	}

	public String paginaAlterarProfessor()
	{
		if (professor != null)
		{
			recuperaUFeSatatus(professor);
		}
		return ConstantsNavigation.ALTERAR_PROFESSOR;
	}

	public String alterarProfessor()
	{
		preencheStatus(this.professor);
		preencheUf(this.professor);
		fileUpload();
		// adcionaDisciplinaAoProfessor();
		try
		{
			professorService.alterarProfessor(this.professor);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro  ao alterar professor");
			logger.info("Erro  ao alterar professor.");
			return null;
		}
		removeBean(ConstantsMB.BEAN_PROESSOR_MB);
		addInfoMessage("Professor alterado com sucesso");
		logger.info("Professor alterado com sucesso.");
		return ConstantsNavigation.CONSULTAR_PROFESSOR;
	}

	public String cancelar()
	{
		getNavigationMB().limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
	}

	public Long getCodigoDisciplina()
	{
		return codigoDisciplina;
	}

	public void setCodigoDisciplina(Long codigoDisciplina)
	{
		this.codigoDisciplina = codigoDisciplina;
	}

	public List<Professor> getProfessores()
	{
		return professores;
	}

	public void setProfessores(List<Professor> professores)
	{
		this.professores = professores;
	}

	public UsuarioDataModel<Professor> getUsuarioDataModel()
	{
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(UsuarioDataModel<Professor> usuarioDataModel)
	{
		this.usuarioDataModel = usuarioDataModel;
	}
	
	   private boolean carregaParametroAvatar()
	   {
	      try
	      {
	         PATH_AVATAR = parametroService.getParametroString(NumeroParametro.PATH_AVATAR_USUARIO);
	         if(PATH_AVATAR == null)
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
		if (getFile().getFileName().isEmpty())
		{
			return;
		}
		FileOutputStream fout = null;
		try
		{

			// Cria um arquivo UploadFile, para receber o arquivo do evento

			UploadedFile arq = getFile();

			InputStream in = new BufferedInputStream(arq.getInputstream());

			// copiar para pasta do projeto

			File diretorio = new File(PATH_AVATAR);
			diretorio.mkdirs();
			String extesao = arq.getFileName().substring(arq.getFileName().indexOf("."));
			String nomeImage = professor.getCpf() + extesao;
			File file = new File(PATH_AVATAR + nomeImage);

			// O método file.getAbsolutePath() fornece o caminho do arquivo
			// criado

			// Pode ser usado para ligar algum objeto do banco ao arquivo
			// enviado

			String caminho = file.getAbsolutePath();
			logger.info("A imagem [" + arq.getFileName() + "] foi salva em: ["
					+ caminho + "]");
			this.professor.setAvatar(nomeImage);

			fout = new FileOutputStream(file);

			while (in.available() != 0)
			{

				fout.write(in.read());

			}

			fout.close();

			FacesMessage msg = new FacesMessage("O Arquivo ", file.getName()
					+ " salvo.");

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public UploadedFile getFile()
	{
		return file;
	}

	public void setFile(UploadedFile file)
	{
		this.file = file;
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
