/**
 * Xlearning 2013
 */
package br.com.xlearning.mbean.parametro;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.mbean.util.ConstantsMB;
import br.com.xlearning.parametro.entidade.Parametro;
import br.com.xlearning.parametro.service.ParametroService;

/**
 * @author Jesiel Viana Date 19/09/2013
 */
@ManagedBean
@ViewScoped
public class ParametroMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ParametroMB.class);
	@EJB
	private ParametroService parametroService;
	@Inject
	private Parametro parametro;
	@Inject
	private NavigationMB navigationMB;
	private List<Parametro> parametros;
	private boolean parametrosCadastrados;

	public void inicializa()
	{
		parametros = parametroService.getTodosParametros();
		existeParametrosCadastrados();
	}
	
	private void existeParametrosCadastrados()
	{
		if(parametros.size() < 1)
		{
		 addErrorMessage("Não existe cursos cadastrados");
		 setParametrosCadastrados(true);
		}
	}

	
	public String adcionaParametro()
	{
		// preecnheParametro();
		try
		{
		   formataPathWindows(this.parametro);
			parametroService.adicionarParametro(this.parametro);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao adicionar parametro.");
		}
		parametro = new Parametro();
		removeBean(ConstantsMB.BEAN_PARAMETRO);
		logger.info("parametro"+parametro.getCodigo()+" adicionado com sucesso, valor: " + parametro.getValor());
		addInfoMessage("Parâmetro cadastrado com sucesso.");
		return null;

	}
	
	private void formataPathWindows(Parametro  parametro)
	{
	   String sistema = System.getProperty("os.name");
      if (!sistema.equals("Linux"))
      {
	   this.parametro.setValor(parametro.getValor().replace("\\", "\\"));
      }
	}
	

	public void alterarParametro(RowEditEvent event)
	{
		try
		{
			this.parametro = (Parametro) event.getObject();
			parametroService.alterarParametro(this.parametro);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao atualizar disciplina");
			return;
		}
		addInfoMessage("Parametro alterado com sucesso.");
		return;
	}

	public void cancelaAlterarParametro()
	{
		addInfoMessage("O parâmetro não foi alterada.");
	}

	public Parametro getParametro()
	{
		return parametro;
	}

	public void setParametro(Parametro parametro)
	{
		this.parametro = parametro;
	}

	public List<Parametro> getParametros()
	{
		return parametros;
	}

	public void setParametros(List<Parametro> parametros)
	{
		this.parametros = parametros;
	}

	public boolean isParametrosCadastrados()
	{
		return parametrosCadastrados;
	}

	public void setParametrosCadastrados(boolean parametrosCadastrados)
	{
		this.parametrosCadastrados = parametrosCadastrados;
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
