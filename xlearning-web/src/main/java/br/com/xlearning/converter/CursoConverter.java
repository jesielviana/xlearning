/**
 * Xlearning 2013
 */
package br.com.xlearning.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.CursoService;

/**
 * @author Jesiel Viana
 * Date 13/10/2013
 */
@ManagedBean
public class CursoConverter implements Converter{

	@EJB
	private CursoService cursoService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String key)
	{
		return cursoService.buscaCursoPorId(Long.parseLong(key));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object cursoObject)
	{
		if (cursoObject != null && cursoObject instanceof Curso)
		{
			Curso curso = (Curso) cursoObject;
			return String.valueOf(curso.getIdCurso());
		}
		return "";
	}
}
