/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.login;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import br.com.xlearning.mbean.infra.PageMB;


/**
 * @author Jesiel Viana
 *
 * Date: 03/10/2013
 */
@ManagedBean
@RequestScoped
public class ErrorLoginMB extends PageMB
{
   private static final long serialVersionUID = 1L;
   private boolean error;
   
   
  HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
   
   public boolean isError()
   {
      if(getFacesContext().getMessageList().isEmpty() && req.getParameter("error") != null)
      {
         addErrorMessage("Login incorreto");
         error = true;
      }
      return error;
   }

}
