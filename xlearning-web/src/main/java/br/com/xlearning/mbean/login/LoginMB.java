package br.com.xlearning.mbean.login;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import br.com.xlearning.login.service.LoginService;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author jesiel
 * @Data: 2013
 */
@ManagedBean
@SessionScoped
public class LoginMB extends PageMB {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginMB.class);
	private Usuario userSession;
	private String matriculaUser;
	@EJB
	private LoginService loginService;
	@Inject
	private NavigationMB navigationMB;

	public LoginMB()
	{
		carregaUsuarioLogado();

	}

	private void init()
	{
		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext)
		{
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication)
			{
				if (!authentication.getPrincipal().equals("anonymousUser"))
				{
					this.setMatriculaUser(((User) authentication.getPrincipal()).getUsername());

					logger.info("Login: " + this.getMatriculaUser());
				}
			}
		}
	}

	private void carregaUsuarioLogado()
	{
		init();
		try
		{
			setUserSession(loginService.getUserSession(new Long(getMatriculaUser())));
		}
		catch (Exception e)
		{
			logger.error("****************Não tem usuário logado*************");
		}
	}

	public String logout()
	{
		SecurityContextHolder.clearContext();
		setUserSession(null);
		navigationMB.limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL;
	}

	public String getMatriculaUser()
	{
		return matriculaUser;
	}

	public void setMatriculaUser(String userLogin)
	{
		this.matriculaUser = userLogin;
	}

	public Usuario getUserSession()
	{
		if (userSession == null)
		{
			carregaUsuarioLogado();
		}
		return userSession;
	}

	public void setUserSession(Usuario userSession)
	{
		this.userSession = userSession;
	}

}
