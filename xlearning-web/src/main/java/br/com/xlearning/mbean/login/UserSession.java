/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.login;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.xlearning.usuario.entidade.Usuario;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
@ManagedBean
@SessionScoped
public class UserSession
{

   private Usuario user;

   public Usuario getUser() {
      return user;
   }

   public void setUser(Usuario user) {
      this.user = user;
   }
   
   public boolean isLoggedIn(){
      return user != null;
   }
}
