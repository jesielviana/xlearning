/**
 * Xlearning 2013
 */
package br.com.xlearning.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.service.AlunoService;

/**
 * @author Jesiel Viana Date 12/10/2013
 */
@ManagedBean
public class AlunoConverter implements Converter{

	@EJB
	private AlunoService alunoService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String key)
	{
		return alunoService.buscaAlunoPorMatricula(Long.parseLong(key));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object alunoObject)
	{
		if (alunoObject != null && alunoObject instanceof Aluno)
		{
			Aluno aluno = (Aluno) alunoObject;
			return String.valueOf(aluno.getMatricula());
		}
		return "";
	}

}
