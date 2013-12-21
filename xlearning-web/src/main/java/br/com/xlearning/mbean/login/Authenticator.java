/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.login;

import javax.faces.bean.ManagedBean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import br.com.xlearning.mbean.infra.PageMB;


/**
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
@Controller
@Scope("request")
@ManagedBean
public class Authenticator extends PageMB implements AuthenticationProvider
{
   private static final long serialVersionUID = 1L;
//   @EJB
//   private LoginService service;
//   @Inject
//   private UserSession session;
//
//   private String username;
//   private String password;
//
//   public String login() {
//      try {
//         Usuario user = service.login(username, password);
//         loginSpringSecurity(user);
//         session.setUser(user);
//         return "home";
//      } catch (Exception ex) {
//         message(ex.getMessage());
//      }
//      return null;
//   }
//
//   private void loginSpringSecurity(Usuario user) {
//      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getNome(), null, user.getAuthorities());
//      token.setDetails(user);
//
//      SecurityContextHolder.createEmptyContext();
//      SecurityContextHolder.getContext().setAuthentication(token);
//   }
//
//   public String logout() {
//      SecurityContextHolder.clearContext();
//      session.setUser(null);
//      return "index";
//   }
//
//   private void message(String message) {
//      addErrorMessage(message);
//   }
//
//   public String getUsername() {
//      return username;
//   }
//
//   public void setUsername(String username) {
//      this.username = username;
//   }
//
//   public String getPassword() {
//      return password;
//   }
//
//   public void setPassword(String password) {
//      this.password = password;
//   }

   @Override
   public Authentication authenticate(Authentication arg0)
         throws AuthenticationException {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean supports(Class<?> arg0) {
      // TODO Auto-generated method stub
      return false;
   }
}
