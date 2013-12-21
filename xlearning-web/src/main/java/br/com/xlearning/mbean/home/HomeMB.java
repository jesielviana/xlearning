
package br.com.xlearning.mbean.home;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.curso.service.CursoService;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.disciplina.service.DisciplinaService;
import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.login.service.LoginService;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.post.entidade.Post;
import br.com.xlearning.post.service.PostService;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.util.CriptografaUtil;
import br.com.xlearning.util.XlearningUtil;

@ManagedBean
@SessionScoped
public class HomeMB extends PageMB
{

   private static final long serialVersionUID = 1L;
   @EJB
   private CursoService cursoService;
   @EJB
   private PostService postService;
   @EJB
   private LoginService loginService;
   @EJB
   private DisciplinaService disciplinaService;
   private String cpf;
   private Usuario usuario;
   private boolean cpfEncontrado;
   private String novaSenha;
   private String repeteSenha;
   private Curso curso;
   private Post post;

   public List<Curso> getTodosCursos()
   {
      return cursoService.getListaTodosCursosAtivos();
   }

   public List<Post> getUltimosPostsRodape()
   {
      return postService.getPostsPorTipoStatusData(TipoPost.PAGINA_INICIAL, StatusPost.HABILITADO, new Date(), 5);
   }

   public List<Post> getUltimosPostsParaAlunos()
   {
      return postService.getPostsPorTipoStatusData(TipoPost.ALUNOS, StatusPost.HABILITADO, new Date(), 10);
   }

   public List<Post> getUltimosPostsParaProfessores()
   {
      return postService.getPostsPorTipoStatusData(TipoPost.PROFESSOR, StatusPost.HABILITADO, new Date(), 10);
   }

   public List<Post> getUltimosPostsPaginaInicial()
   {
      return postService.getPostsPorTipoStatusData(TipoPost.PAGINA_INICIAL, StatusPost.HABILITADO, new Date(), 2);
   }

   private boolean validaCPF(String cpf)
   {
      return XlearningUtil.isCPFValido(cpf);
   }

   public String buscarUsuarioPorCpf()
   {
      if (!validaCPF(cpf))
      {
         addErrorMessage("CPF inválido");
         // return null;
      }
      try
      {
         usuario = loginService.getUsuarioPorCPF(cpf);
      }
      catch (Exception e)
      {
         addErrorMessage("Erro ao consultar usuário");
         cpfEncontrado = false;
         return null;
      }
      if (usuario == null)
      {
         addErrorMessage("Usuário não encontrado");
         cpfEncontrado = false;
         return null;
      }
      addInfoMessage("Usuário encontrado");
      cpfEncontrado = true;
      return null;

   }

   private boolean validaSenha()
   {
      if (getNovaSenha() != null && !getNovaSenha().isEmpty() && getNovaSenha().equals(getRepeteSenha()))
      {
         return true;
      }
      return false;
   }

   private void adicionarSenha()
   {
      usuario.setSenha(CriptografaUtil.getSenhaCriptografada(getNovaSenha()));
   }

   public String alterarSenha()
   {
      if (!validaSenha())
      {
         addErrorMessage("Senha inválida");
         return null;
      }
      adicionarSenha();
      try
      {
         loginService.atualiza(usuario);
      }
      catch (Exception e)
      {
         addErrorMessage("Não foi possível alterar a senha");
         return null;
      }
      addInfoMessage("Senha alterada com sucesso.");
      cpfEncontrado = false;
      return null;
   }

   public Usuario getUsuario()
   {
      return usuario;
   }

   public void setUsuario(Usuario usuario)
   {
      this.usuario = usuario;
   }

   public String getCpf()
   {
      return cpf;
   }

   public void setCpf(String cpf)
   {
      this.cpf = cpf;
   }

   public boolean isCpfEncontrado()
   {
      return cpfEncontrado;
   }

   public void setCpfEncontrado(boolean cpfEncontrado)
   {
      this.cpfEncontrado = cpfEncontrado;
   }

   public String getNovaSenha()
   {
      return novaSenha;
   }

   public void setNovaSenha(String novaSenha)
   {
      this.novaSenha = novaSenha;
   }

   public String getRepeteSenha()
   {
      return repeteSenha;
   }

   public void setRepeteSenha(String repeteSenha)
   {
      this.repeteSenha = repeteSenha;
   }

   public String paginaCurso()
   {
      return "/curso.jsf?faces-redirect=true";
   }

   public String paginaNoticias()
   {
      return "/noticias.jsf?faces-redirect=true";
   }

   public List<Disciplina> getDisciplinasPorCurso()
   {
      List<Disciplina> disciplinas = disciplinaService.listaDisciplinasPorCurso(curso);
      Collections.sort(disciplinas);
      return disciplinas;
   }

   public Curso getCurso()
   {
      return curso;
   }

   public void setCurso(Curso curso)
   {
      this.curso = curso;
   }

   public Post getPost()
   {
      return post;
   }

   public void setPost(Post post)
   {
      this.post = post;
   }

}
