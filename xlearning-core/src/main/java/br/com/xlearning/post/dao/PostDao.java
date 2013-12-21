package br.com.xlearning.post.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.post.entidade.Post;
import br.com.xlearning.post.repository.PostRepository;

public class PostDao extends GenericDAO<Post> implements PostRepository{

	private static final long serialVersionUID = 1L;
	
	public PostDao()
	{
		super(Post.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsPorTipoStatusData(TipoPost tipo, StatusPost status, Date data, int maxValue)
	{
		Query query = entityManager.createNamedQuery("Post.findByTipoAndStatusAndData");
		query.setParameter("tipo", tipo);
		query.setParameter("status", status);
		query.setParameter("data", data);
		query.setMaxResults(maxValue);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostPorTipo(TipoPost tipo)
	{
		Query query = entityManager.createNamedQuery("Post.findByTipo");
		query.setParameter("tipo", tipo);
		
		return query.getResultList();
	}

}
