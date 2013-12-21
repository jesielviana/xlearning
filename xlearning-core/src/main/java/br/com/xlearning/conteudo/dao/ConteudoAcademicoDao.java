package br.com.xlearning.conteudo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.dao.util.GenericDAO;
import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 * 
 * @author Jesiel Viana
 * 
 *         Date: 02/10/2013
 */
public class ConteudoAcademicoDao extends GenericDAO<ConteudoAcademico> implements ConteudoAcademicoRepository {
	private static final long serialVersionUID = 1L;

	public ConteudoAcademicoDao()
	{
		super(ConteudoAcademico.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.xlearning.conteudo.dao.ConteudoAcademicoRepository#
	 * listaConteudoParaAlunoPorDisciplina
	 * (br.com.xlearning.disciplina.entidade.Disciplina)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConteudoAcademico> listaConteudoPorDisciplina(Disciplina disciplina) throws PersistenceException
	{
		Query query = entityManager.createNamedQuery("ConteudoAcademico.findByDisciplina");
		query.setParameter("idDisciplina", disciplina.getIdDisciplina());
		List<ConteudoAcademico> conteudos = new ArrayList<ConteudoAcademico>();
		conteudos = query.getResultList();
		
		return conteudos;
	}

}
