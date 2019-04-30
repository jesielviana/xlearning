package br.com.xlearning.post.repository;

import java.util.Date;
import java.util.List;

import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.post.entidade.Post;

public interface PostRepository {

	public void adiciona(Post post);

	public void remove(Post post);

	public void atualiza(Post post);

	public Post buscaPorId(Long id);

	public List<Post> getPostsPorTipoStatusData(TipoPost tipoPost, StatusPost statusPost, Date data, int maxValue);

	public List<Post> getPostPorTipo(TipoPost tipo);

	public List<Post> listaTodos();

}
