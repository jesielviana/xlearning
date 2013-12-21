package br.com.xlearning.post.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.post.dao.PostRepository;
import br.com.xlearning.post.entidade.Post;
import br.com.xlearning.post.service.PostService;

@Stateless
@LocalBean
@Local(PostService.class)
public class PostServiceBean implements PostService{
	@Inject
	private PostRepository postRepository;

	@Override
	public void adiciona(Post post)
	{
		postRepository.adiciona(post);
	}

	@Override
	public void remove(Post post)
	{
		postRepository.remove(post);
	}

	@Override
	public void atualiza(Post post)
	{
		postRepository.atualiza(post);
	}

	@Override
	public Post buscaPorId(Long id)
	{
		return postRepository.buscaPorId(id);
	}

	@Override
	public List<Post> getPostsPorTipoStatusData(TipoPost tipo, StatusPost status, Date data, int maxValue)
	{
		return postRepository.getPostsPorTipoStatusData(tipo, status, data, maxValue);
	}

	@Override
	public List<Post> getPostPorTipo(TipoPost tipo)
	{
		return postRepository.getPostPorTipo(tipo);
	}

	@Override
	public List<Post> listaTodos()
	{
		return postRepository.listaTodos();
	}
	
	

}
