package br.com.xlearning.mbean.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.login.LoginMB;
import br.com.xlearning.mbean.navegacao.ConstantsNavigation;
import br.com.xlearning.notificacao.entidade.Notificacao;
import br.com.xlearning.notificar.service.EnviarNotificacaoService;
import br.com.xlearning.notificar.service.impl.GeradorNotificacaoEmail;
import br.com.xlearning.post.entidade.Post;
import br.com.xlearning.post.service.PostService;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.service.AlunoService;
import br.com.xlearning.util.XlearningUtil;

@ManagedBean
@SessionScoped
public class PostMB extends PageMB {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PostMB.class);

	@EJB
	private PostService postService;
	@EJB
	private EnviarNotificacaoService enviarNotificacaoService;
	@EJB
	private GeradorNotificacaoEmail geradorNotificacaoEmail;
	@EJB
	private AlunoService alunoService;
	private List<Disciplina> disciplinas;
	private Integer tipoPost;
	private Integer statusPost;
	private Post post;
	private List<Post> posts;
	@Inject
	private LoginMB loginMB;
	private boolean enviarNotificacao;

	public PostMB()
	{
		post = new Post();
	}
	
	public void init()
	{
		post = new Post();
	}

	public void adicionarPost()
	{
		if (!validaDatas(post) || !preencheStatus() | !preencheTipo())
		{
			return;
		}
		criaPost();
		try
		{
			postService.adiciona(post);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao adicionar post.");
			logger.error("Erro ao adicionar post.");
			return;
		}
		if (enviarNotificacao)
		{
			try
			{
				enviarNotificacaoService.enviar(criaNotificacao(this.post), geradorNotificacaoEmail);
			}
			catch (EmailException e)
			{
				addWarnMessage("Não foi possível enviar notificação para todos destinatários");
				logger.warn("Não foi possível enviar notificação para todos destinatários", e);
			}
		}
		post = new Post();
		addInfoMessage("Post adicionado com sucesso");
		logger.info("Post adicionado com sucesso");
	}

	private void criaPost()
	{
//		preencheStatus();
//		preencheTipo();
		post.setUsuario(getLoginMB().getUserSession());
	}

	private Notificacao criaNotificacao(Post post)
	{
		Notificacao n = new Notificacao();
		n.setAssunto(post.getTitulo());
		n.setData(new Date());
		n.setGrupoDestino("Alunos");
		n.setMensagem(post.getConteudo());
		n.setUsuario(loginMB.getUserSession());
		n.setDestinatarios(getDestinatarios(post));
		return n;
	}

	private List<Aluno> getDestinatarios(Post post)
	{
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = alunoService.getAlunoPorSituacaoDisciplina(post.getDisciplina().getIdDisciplina(),
				SituacaoAlunoDisciplina.CURSANDO);
		return alunos;
	}

	private boolean validaDatas(Post post)
	{
		boolean valido = true;
		if (post.getDataInicial().compareTo(XlearningUtil.getDataTrucada(new Date())) == -1)
		{
			valido = false;
			addErrorMessage("A data inicial deve ser maior que a data atual.");
		}
		if (post.getDataFinal().compareTo(XlearningUtil.getDataTrucada(new Date())) == -1)
		{
			valido = false;
			addErrorMessage("A data final deve ser maior que a data atual.");
		}
		if (post.getDataInicial().compareTo(post.getDataFinal()) == 1)
		{
			valido = false;
			addErrorMessage("A data inicial deve ser menor ou igual a data final.");
		}
		return valido;
	}

	private boolean preencheTipo()
	{
		if(getTipoPost()== null)
		{
			addErrorMessage("Selecione o tipo do post");
			return false;
		}
		post.setTipo(TipoPost.get(getTipoPost()));
		return true;
	}

	private boolean preencheStatus()
	{
		if(getStatusPost()== null)
		{
			addErrorMessage("Selecione o status");
			return false;
		}
		post.setStatus(StatusPost.get(getStatusPost()));
		return true;
	}

	public List<TipoPost> getTipoPostItens()
	{
		return Arrays.asList(TipoPost.values());
	}

	public List<StatusPost> getStatusPostItens()
	{
		return Arrays.asList(StatusPost.values());
	}

	public String cancelar()
	{
		post = new Post();
		return ConstantsNavigation.RESULTADO_CONSULTA_POST;
	}

	public String consultar()
	{
		if (tipoPost.equals(100))
		{
			consultarTodos();

		}
		else
		{
			consultarPorTipo(TipoPost.get(tipoPost));
		}
		if (posts == null && posts.isEmpty())
		{
			addErrorMessage("Nenhum post encontrado");
			return null;
		}
		return ConstantsNavigation.RESULTADO_CONSULTA_POST;
	}

	public String consultarPorProfessor()
	{
		post = new Post();
		if (tipoPost.equals(100))
		{
			consultarTodos();

		}
		else
		{
			consultarPorTipo(TipoPost.get(tipoPost));
		}
		if (posts == null && posts.isEmpty())
		{
			addErrorMessage("Nenhum post encontrado");
			return null;
		}
		return ConstantsNavigation.RESULTADO_POST;
	}

	private void consultarTodos()
	{
		posts = postService.listaTodos();
	}

	private void consultarPorTipo(TipoPost tipo)
	{
		posts = postService.getPostPorTipo(tipo);
	}

	public String alterarPost()
	{
		if (!validaDatas(post))
		{
			return null;
		}
		criaPost();
		try
		{
			postService.atualiza(post);
		}
		catch (Exception e)
		{
			addErrorMessage("Erro ao alterar post");
			logger.error("Erro ao alterar post", e);
		}
		addInfoMessage("Post alterado com sucesso.");
		post = new Post();
		if (loginMB.getUserSession().isProfessor())
			return ConstantsNavigation.ALTERAR_POST_PROFESSOR;
		return ConstantsNavigation.RESULTADO_CONSULTA_POST;
	}

	public String paginaAlterarPost()
	{
		if (post != null)
		{
			recuperarDadosPost(post);
		}
		return ConstantsNavigation.ALTERAR_POST;
	}
	
	public void excluirPost(Post post)
	{
		try
		{

			postService.remove(postService.buscaPorId(post.getIdPost()));
		}
		catch (Exception e)
		{
			addErrorMessage("Não foi possível remover o Post.");
			return;
		}
		consultar();
		addInfoMessage("Post removido com sucesso.");
	}

	public String paginaAlterarPostProfessor()
	{
		if (post != null)
		{
			recuperarDadosPost(post);
		}
		return ConstantsNavigation.ALTERAR_POST_PROFESSOR;
	}

	private void recuperarDadosPost(Post post)
	{
		setStatusPost(post.getStatus());
		setTipoPost(post.getTipo().getChave());
	}

	public Post getPost()
	{
		return post;
	}

	public void setPost(Post post)
	{
		this.post = post;
	}

	public List<Disciplina> getDisciplinas()
	{
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas)
	{
		this.disciplinas = disciplinas;
	}

	public Integer getStatusPost()
	{
		return statusPost;
	}

	public void setStatusPost(Integer statusPost)
	{
		this.statusPost = statusPost;
	}

	public Integer getTipoPost()
	{
		return tipoPost;
	}

	public void setTipoPost(Integer tipoPost)
	{
		this.tipoPost = tipoPost;
	}

	public LoginMB getLoginMB()
	{
		return loginMB;
	}

	public void setLoginMB(LoginMB loginMB)
	{
		this.loginMB = loginMB;
	}

	public List<Post> getPosts()
	{
		return posts;
	}

	public void setPosts(List<Post> posts)
	{
		this.posts = posts;
	}

	public boolean isEnviarNotificacao()
	{
		return enviarNotificacao;
	}

	public void setEnviarNotificacao(boolean enviarNotificacao)
	{
		this.enviarNotificacao = enviarNotificacao;
	}
}
