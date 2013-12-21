package br.com.xlearning.infra.servelet;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.UsuarioAdministrativo;
import br.com.xlearning.usuario.service.RoleService;
import br.com.xlearning.usuario.service.UsuarioAdministrativoService;
import br.com.xlearning.util.CriptografaUtil;

/**
 * 
 * @author Jesiel Viana
 *
 * Date: 01/10/2013
 */
@WebServlet(name="criaUsuarioAdministrador", urlPatterns="/criaUsuarioAdministrador/*")
public class CriaUsuarioAdministrador extends HttpServlet
{
   private static final long serialVersionUID = 1L;
   private static Logger logger = Logger.getLogger(CriaUsuarioAdministrador.class);
   @EJB
   private UsuarioAdministrativoService administrativoService;
   @EJB
   private RoleService roleService;
   private List<UsuarioAdministrativo> lista;
      
   @Override
   public void init() throws ServletException
   {
      super.init();
      logger.info(".................Inicializando a aplicação pela primeira vez.............");
      try
      {
         lista = administrativoService.getTodosUsuariosAdms();
         
      }
      catch (Exception e)
      {
         logger.error("não existe usuários adms cadastrados");
      }
      
      if(lista == null || lista.isEmpty())
      {
         try
         {
            adicionaRoles();
            logger.error("Roles adicionadas com sucesso...");
            
         }
         catch (Exception e)
         {
            logger.error("Roles já adicionadas...");
         }
        try
      {
         administrativoService.adcionarUsuarioAministrativo(criaSuperUser());
         logger.error("Usuario adicionado com sucesso...");
      }
      catch (PersistenceException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (BusinessException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      }
      
   }
   
   private void adicionaRoles()
   {
      roleService.adcionaRole(null);
   }
   
   private UsuarioAdministrativo criaSuperUser()
   {
      UsuarioAdministrativo admin = new UsuarioAdministrativo();
      admin.setNome("admin");
      admin.setCpf("000000");
      admin.setSenha(CriptografaUtil.getSenhaCriptografada("123"));
      admin.setStatus(StatusUsuario.ATIVO);
      admin.setEmail("admin@email.com");
      admin.setRole(roleService.getRole(Permissao.ROLE_ADMIN));
      admin.setEndereco("endereco");
      admin.setCidade("cidade");
      admin.setUf(UF.get("DF"));
      admin.setTelefone("(00) 1234 - 1234");
      return admin;
   }
   
   @Override
   protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
      super.doGet(arg0, arg1);
   }
   
   @Override
   public void destroy()
   {
      super.destroy();
   }

}
