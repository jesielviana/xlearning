/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;

/**
 * @author Jesiel Viana
 * 
 *         Date: 09/10/2013
 */
@ManagedBean
public class DisciplinaConverter implements Converter {
	@EJB
	private DisciplinaService disciplinaService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String key)
	{
		return disciplinaService.buscaPorId(Long.parseLong(key));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object disciplinaObject)
	{
		if (disciplinaObject != null && disciplinaObject instanceof Disciplina)
		{
			Disciplina disciplina = (Disciplina) disciplinaObject;
			return String.valueOf(disciplina.getIdDisciplina());
		}
		return "";
	}
}
