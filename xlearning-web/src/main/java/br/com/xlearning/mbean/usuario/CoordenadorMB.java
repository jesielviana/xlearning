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
import br.com.xlearning.usuario.entidade.Coordenador;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.service.CoordenadorService;
import br.com.xlearning.util.CriptografaUtil;

@ManagedBean
@SessionScoped
public class CoordenadorMB extends UsuarioMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CoordenadorMB.class);
	@EJB
	private CoordenadorService coordenadorService;
	private Coordenador coordenador;
	@Inject
	private NavigationMB navigationMB;
	private Long matricula;
	private boolean todos;
	private List<Coordenador> coordenadores;

	public CoordenadorMB()
	{
		setCoordenador(new Coordenador());
	}

	public String adcionarCoordenador()
	{
		if (!validaCPF(coordenador))
		{
			addErrorMessage("CPF inválido");
			return null;
		}
		if (!validaDados())
		{
			return null;
		}
		criarCoordenador();
		try
		{
			coordenadorService.adicionarCoordenador(this.coordenador);

		}
		catch (PersistenceException e)
		{
			addErrorMessage("Falha na comunicação com o banco de dados");
			logger.error("Erro ao adcionar coordenador: ", e);
			return null;
		}
		catch (EJBException e)
		{
			addErrorMessage("Falha na comunicação com o banco de dados");
			logger.error("Erro ao adcionar coordenador: ", e);
			return null;
		}
		catch (BusinessException e)
		{
			addErrorMessage(e);
			e.printStackTrace();
			return null;
		}
		removeBean(ConstantsMB.BEAN_COORDENADOR_MB);
		addInfoMessage("Coordenador cadastrado com sucesso");
		logger.info("Coordenador adcionado com sucesso.");
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
	
	public String consultarCoordenador()
	{
		if (matricula != null && matricula > 0)
		{
			return consultarCoordenadorPorMatricula();
		}
		else if (todos)
		{
			return consultarTodosUsuarioaCoordenadores();
		}
		else
		{
			addErrorMessage("Preencha uma opção.");
			return null;
		}
	}

	private String consultarCoordenadorPorMatricula()
	{
		coordenadores = new ArrayList<Coordenador>();
		Coordenador coordenador = new Coordenador();
		try
		{
			coordenador = coordenadorService.buscaCoordenadorPorId(getMatricula());
			if (coordenador != null)
			{
				this.coordenadores.add(coordenador);
			}
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de usuário administrativo");
			addErrorMessage("Erro na consulta de usuário administrativo");
			return null;
		}
		if (coordenadores == null || coordenadores.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum coordenador com a matrícula informada.");
			return null;
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_COORDENADOR;
	}

	private String consultarTodosUsuarioaCoordenadores()
	{
		try
		{
			coordenadores = coordenadorService.getTodosCoordenadores();
		}
		catch (Exception e)
		{
			logger.error("Erro na consulta de usuário administrativo");
			addErrorMessage("Erro na consulta de usuário administrativo");
			return null;
		}
		if (coordenadores == null || coordenadores.isEmpty())
		{
			addErrorMessage("Não foi encontrado nenhum coordenador.");
			return null;
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_COORDENADOR;
	}

	private void criarCoordenador()
	{
		preencheStatus(this.coordenador);
		preencheUf(this.coordenador);
		preencheRole();
		preencheSenha();
	}
	
	public String paginaAlterarCoordenador()
	{
		if (coordenador != null)
		{
			recuperaUFeSatatus(coordenador);
		}
		return ConstantsNavigation.ALTERAR_COORDENADOR;
	}
	
	public String alterarCoordenador()
	{
		preencheStatus(this.coordenador);
		preencheUf(this.coordenador);
		try
		{
			coordenadorService.atualizarCoordenador(coordenador);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro  ao alterar coordenador");
			logger.info("Erro  ao alterar coordenador.");
			return null;
		}
		removeBean(ConstantsMB.BEAN_COORDENADOR_MB);
		addInfoMessage("Coordenador alterado com sucesso");
		logger.info("Coordenador alterado com sucesso.");
		return ConstantsNavigation.CONSULTAR_COORDENADOR;
	}

	private void preencheStatus(Usuario usuario)
	{
		coordenador.setStatus(StatusUsuario.get(super.getStatusUsuario()));
	}

	private void preencheUf(Usuario usuario)
	{
		coordenador.setUf(UF.get(super.getUf()));
	}

	private void preencheRole()
	{
		this.coordenador.setRole(super.getRoleService().getRole(Permissao.ROLE_COORDENADOR));
	}

	private void preencheSenha()
	{
		this.coordenador.setSenha(CriptografaUtil.getSenhaCriptografada(getSenha()));
	}

	public Coordenador getCoordenador()
	{
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador)
	{
		this.coordenador = coordenador;
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

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public List<Coordenador> getCoordenadores() {
		return coordenadores;
	}

	public void setCoordenadores(List<Coordenador> coordenadores) {
		this.coordenadores = coordenadores;
	}

}
