/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.infra;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.xlearning.error.BusinessException;

/**
 * @author jesiel
 * @Data: 2013
 */
@ManagedBean
public abstract class PageMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String MESSAGE_RESOURCE_FILE = "Messages";

	/**
	 * Adiciona Mensagem de nível Error ao Message
	 */
	protected void addErrorMessage(String summary, String detail) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}
	
	protected void addErrorMessageCampoEspeficico(String campo, String summary) {
		getFacesContext().addMessage(campo,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, summary));
	}

	/**
	 * Adiciona mensagem de erro
	 */
	protected void addErrorMessage(String summary) {
		addErrorMessage(summary, summary);
	}
	
	
	   protected void addErrorMessage(BusinessException exception)
	   {
	      if (exception.isMultipla())
	      {
	         List<String> mensagensErro = exception.getMensagensErro();
	         for (String mensagem : mensagensErro)
	         {
	            addErrorMessage(mensagem, mensagem);
	         }
	      }
	      else
	      {
	         String message = exception.getMessage();
	         addErrorMessage(message, message);
	      }
	   }


	/**
	 * Adiciona Mensagem de nível INFO ao message em conjunto com o detail que
	 * poderá ser exibido
	 */
	protected void addInfoMessage(String summary, String detail) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	/**
	 * Adiciona Mensagem de nível INFO com apenas sumário.
	 */
	protected void addInfoMessage(String summary) {
		addInfoMessage(summary, summary);
	}

	/**
	 * Adiciona Mensagem de nível WARN ao message em conjunto com o detail que
	 * poderá ser exibido
	 */
	protected void addWarnMessage(String summary, String detail) {
		getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	/**
	 * Adiciona Mensagem de nível WARN com apenas sumário
	 */
	protected void addWarnMessage(String summary) {
		addWarnMessage(summary, summary);
	}

	/**
	 * Obtem o mapeamento dos objetos definidos no contexto da aplicaçãoo
	 * @return O mapeamento dos objetos definidos no contexto da aplicaçãoo
	 */
	@SuppressWarnings("rawtypes")
	protected Map getApplicationMap() {
		return getExternalContext().getApplicationMap();
	}

	/**
	 * Obtem o objeto ExternalContext da instância atual do FacesContext
	 */
	protected ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Obtem a instância corrente da classe FacesContext
	 */
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Obtem o resource bundle das mensagens e labels da interface
	 * @return O ResourceBundle com as mensagens e labels da interface
	 */
	protected ResourceBundle getMessageResources() {
		return ResourceBundle.getBundle(PageMB.MESSAGE_RESOURCE_FILE);
	}

	/**
	 * Obtém o valor de um parametro.
	 */
	protected Object getParameter(String nome) {
		return getExternalContext().getRequestParameterMap().get(nome);
	}

	/**
	 * Obtem os valores da requisição
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	/**
	 * Obtém o mapeamento dos objetos definidos no contexto da requisição
	 * @return O mapeamento dos objetos definidos no contexto da requisição
	 */
	@SuppressWarnings("rawtypes")
	protected Map getRequestMap() {
		return getExternalContext().getRequestMap();
	}

	/**
	 * Obtem o mapeamento dos objetos definidos no contexto da requisição
	 * @return O mapeamento dos objetos definidos no contexto da requisição
	 */
	@SuppressWarnings("rawtypes")
	protected Map getSessionMap() {
		return getExternalContext().getSessionMap();
	}
	
	protected void removeBean(String bean){
		getSessionMap().remove(bean);
	}
}
