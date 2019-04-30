package br.com.xlearning.mbean.util;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.mbean.usuario.AlunoMB;
import br.com.xlearning.usuario.service.impl.RoleServiceImpl;

@ManagedBean
@RequestScoped
public class RoleMB extends PageMB{
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AlunoMB.class);

	@EJB
	private RoleServiceImpl roleService;

	public String adcionaRoles(){
		try {
			roleService.adcionaRole(null);
		} catch (PersistenceException e) {
			addErrorMessage("Erro de persitencia.");
			logger.error("Roles já adcionadas", e);
			return null;
		}catch (EJBException e) {
			logger.error("Roles já adcionadas", e);
			addErrorMessage("Erro de EJB.");
			return null;
		}
		return ConstantsNavigation.PAGINA_INICIAL;
	}
	
}
