/**
 * Xlearning 2013
 */
package br.com.xlearning.conteudo.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.conteudo.dao.ConteudoAcademicoRepository;
import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.conteudo.service.ConteudoAcademicoService;
import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
@Stateless
@LocalBean
@Local(ConteudoAcademicoService.class)
public class ConteudoAcademicoServiceBean  implements ConteudoAcademicoService{
	
	private static Logger logger = Logger.getLogger(ConteudoAcademicoServiceBean.class);
	@Inject
	private ConteudoAcademicoRepository repository;

	@Override
	public void adicionarConteudo(ConteudoAcademico conteudoAcademico) throws PersistenceException, EJBException
	{
		repository.adiciona(conteudoAcademico);
		logger.info("conteudo adicionado com sucesso: " + conteudoAcademico.getNome());
	}

	@Override
	public void alterarConteudo(ConteudoAcademico conteudoAcademico) throws PersistenceException, EJBException
	{
		repository.atualiza(conteudoAcademico);
		logger.info("conteudo alterado com sucesso: " + conteudoAcademico.getNome());
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.conteudo.service.ConteudoAcademicoService#buscaPorId(java.lang.Long)
	 */
	@Override
	public ConteudoAcademico buscaPorId(Long id) throws PersistenceException, EJBException
	{
		return repository.buscaPorId(id);
	}

	/* (non-Javadoc)
	 * @see br.com.xlearning.conteudo.service.ConteudoAcademicoService#listaConteudoParaAlunoPorDisciplina(br.com.xlearning.disciplina.entidade.Disciplina)
	 */
	@Override
	public List<ConteudoAcademico> listaConteudoPorDisciplina(Disciplina disciplina) throws PersistenceException, EJBException
	{
		return repository.listaConteudoPorDisciplina(disciplina);
	}

   @Override
   public void remove(ConteudoAcademico conteudoAcademico)
   {
      repository.remove(conteudoAcademico);
   }

}
