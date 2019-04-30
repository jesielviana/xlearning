package br.com.xlearning.mbean.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;
import br.com.xlearning.usuario.service.impl.UsuarioAdministrativoServiceImpl;
import br.com.xlearning.util.CriptografaUtil;

@ManagedBean
@SessionScoped
public class UsuarioAdministrativoMB extends UsuarioMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UsuarioAdministrativoMB.class);
	@EJB
	private UsuarioAdministrativoServiceImpl usuarioAdministrativoService;
	@Inject
	private NavigationMB navigationMB;
	private UsuarioAdministrativo usuarioAdm;
	private Long matricula;
	private boolean todos;
	private List<UsuarioAdministrativo> usuarioAdministrativos;

	public UsuarioAdministrativoMB()
	{
		setUsuarioAdm(new UsuarioAdministrativo());
	}

	public String adcionarUsuarioAdministrativo()
	{
		if (!validaCPF(usuarioAdm))
		{
			addErrorMessage("CPF inválido");
			return null;
		}
		if (!validaDados())
		{
			return null;
		}
		criarUsuarioAministrativo();
		try
		{
			usuarioAdministrativoService.adcionarUsuarioAministrativo(this.usuarioAdm);
		}
		catch (PersistenceException e)
		{
			addErrorMessage("Falha na comunicação com o banco de dados");
			logger.error("Erro ao adcionar usuário administrativo: ", e);
			return null;
		}
		catch (EJBException e)
		{
			addErrorMessage("Falha na inserção de usuário");
			logger.error("Erro ao adcionar usuário administrativo: ", e);
			return null;
		}
		catch (BusinessException e)
		{
			addErrorMessage(e);
			e.printStackTrace();
			return null;
		}

		removeBean(ConstantsMB.BEAN_ADM_MB);
		addInfoMessage("Administrador cadastrado com sucesso");
		logger.info("Usuário adcionado com sucesso.");
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


	private void criarUsuarioAministrativo()
	{
		preencheStatus(this.usuarioAdm);
		preencheUf(this.usuarioAdm);
		preencheRole();
		preencheSenha();
	}

	public String consultarUsuarioAdministrativo()
	{
		if (matricula != null && matricula > 0)
		{
			return consultarUsuarioAdmPorMatricula();
		}
		else if (todos)
		{
			return consultarTodosUsuarioaAdms();
		}
		else
		{
			addErrorMessage("Preencha uma opção.");
			return null;
		}
	}

	private String consultarUsuarioAdmPorMatricula()
	{
		usuarioAdministrativos = new ArrayList<UsuarioAdministrativo>();
		try
		{
			usuarioAdm = usuarioAdministrativoService.buscaUsuarioAdmPorId(getMatricula());
			if (usuarioAdm != null)
			{
				this.usuarioAdministrativos.add(usuarioAdm);
			}
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de usuário administrativo");
			addErrorMessage("Erro na consulta de usuário administrativo");
			return null;
		}
		if (usuarioAdministrativos == null || usuarioAdministrativos.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum usuário administrativo com a matrícula informada.");
			return null;
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_USUARIO_ADM;
	}

	private String consultarTodosUsuarioaAdms()
	{
		try
		{
			List<UsuarioAdministrativo> todosUsuariosAdms = usuarioAdministrativoService.getTodosUsuariosAdms();
			usuarioAdministrativos = todosUsuariosAdms != null ? todosUsuariosAdms
					: null;
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de usuário administrativo");
			addErrorMessage("Erro na consulta de usuário administrativo");
			return null;
		}
		if (usuarioAdministrativos == null || usuarioAdministrativos.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum usuário administrativo.");
			return null;
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_USUARIO_ADM;
	}

	public String paginaAlterarAdm()
	{
		if (usuarioAdm != null)
		{
			recuperaUFeSatatus(usuarioAdm);
		}
		return ConstantsNavigation.ALTERAR_ADM;
	}

	public String alterarAdm()
	{
		preencheStatus(this.usuarioAdm);
		preencheUf(this.usuarioAdm);
		try
		{
			usuarioAdministrativoService.atualizarUsuarioAdm(usuarioAdm);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro  ao alterar usuário");
			logger.info("Erro  ao alterar usuário adm.");
			return null;
		}
		removeBean(ConstantsMB.BEAN_ADM_MB);
		addInfoMessage("Usuário alterado com sucesso");
		logger.info("Usuário alterado com sucesso.");
		return ConstantsNavigation.CONSULTAR_ADM;
	}

	private void preencheStatus(Usuario usuario)
	{
		usuarioAdm.setStatus(StatusUsuario.get(super.getStatusUsuario()));
	}

	private void preencheUf(Usuario usuario)
	{
		usuarioAdm.setUf(UF.get(super.getUf()));
	}

	private void preencheRole()
	{
		this.usuarioAdm.setRole(super.getRoleService().getRole(Permissao.ROLE_ADMIN));
	}

	private void preencheSenha()
	{
		this.usuarioAdm.setSenha(CriptografaUtil.getSenhaCriptografada(getSenha()));
	}

	public UsuarioAdministrativo getUsuarioAdm()
	{
		return usuarioAdm;
	}

	public void setUsuarioAdm(UsuarioAdministrativo usuarioAdm)
	{
		this.usuarioAdm = usuarioAdm;
	}

	public boolean isTodos()
	{
		return todos;
	}

	public void setTodos(boolean todos)
	{
		this.todos = todos;
	}

	public Long getMatricula()
	{
		return matricula;
	}

	public void setMatricula(Long matricula)
	{
		this.matricula = matricula;
	}

	public List<UsuarioAdministrativo> getUsuarioAdministrativos()
	{
		return usuarioAdministrativos;
	}

	public void setUsuarioAdministrativos(List<UsuarioAdministrativo> usuarioAdministrativos)
	{
		this.usuarioAdministrativos = usuarioAdministrativos;
	}

	public String cancelar()
	{
		navigationMB.limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
	}

}
