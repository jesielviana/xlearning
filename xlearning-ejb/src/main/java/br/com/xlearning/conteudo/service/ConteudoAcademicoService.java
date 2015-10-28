/**
 * Xlearning 2013
 */
package br.com.xlearning.conteudo.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;

import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 * @author Jesiel Viana
 * Date 03/10/2013
 */
public interface ConteudoAcademicoService {
	
	public void adicionarConteudo(ConteudoAcademico conteudoAcademico) throws PersistenceException, EJBException;
	
	public void alterarConteudo(ConteudoAcademico conteudoAcademico) throws PersistenceException, EJBException;
	
	public ConteudoAcademico buscaPorId(Long id) throws PersistenceException, EJBException;
	
	public List<ConteudoAcademico> listaConteudoPorDisciplina(Disciplina disciplina) throws PersistenceException, EJBException;
	
	public void remove(ConteudoAcademico conteudoAcademico);

}
