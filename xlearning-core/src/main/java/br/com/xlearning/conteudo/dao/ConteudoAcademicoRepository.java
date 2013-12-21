package br.com.xlearning.conteudo.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 * 
 * @author Jesiel Viana
 *
 * Date: 02/10/2013
 */
public interface ConteudoAcademicoRepository
{
	public void adiciona(ConteudoAcademico conteudoAcademico) throws PersistenceException;
	
	public void atualiza(ConteudoAcademico conteudoAcademico) throws PersistenceException;
	
	public ConteudoAcademico buscaPorId(Long id);
	
	public List<ConteudoAcademico> listaConteudoPorDisciplina(Disciplina disciplina) throws PersistenceException;

	public void remove(ConteudoAcademico conteudoAcademico);
	

}
