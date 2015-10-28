/**
 * Xlearning 2013
 */
package br.com.xlearning.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.xlearning.questionario.entidade.Opcao;
import br.com.xlearning.questionario.service.OpcaoService;

/**
 * @author Jesiel Viana Date 12/10/2013
 */
@ManagedBean
public class OpcaoConverter implements Converter{

	@EJB
	private OpcaoService opcaoService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String key)
	{
		return opcaoService.buscaOpcaoPorId(Long.parseLong(key));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object opcaoObject)
	{
		if (opcaoObject != null && opcaoObject instanceof Opcao)
		{
			Opcao opcao = (Opcao) opcaoObject;
			return String.valueOf(opcao.getIdopcoes());
		}
		return "";
	}

}
