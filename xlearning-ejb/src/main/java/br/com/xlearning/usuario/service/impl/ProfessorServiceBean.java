/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.service.impl;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.error.ErrorCode;
import br.com.xlearning.usuario.dao.ProfessorRepository;
import br.com.xlearning.usuario.dao.UsuarioRepository;
import br.com.xlearning.usuario.entidade.Professor;
import br.com.xlearning.usuario.service.ProfessorService;

/**
 * @author jesiel
 * @Data: 2013
 */
@Stateless
@LocalBean
@Local(ProfessorService.class)
public class ProfessorServiceBean implements ProfessorService{
	
	private static Logger logger = Logger.getLogger(ProfessorServiceBean.class);
	@Inject
	private ProfessorRepository professorRepository;
   @Inject
   private UsuarioRepository usuarioRepository;

	public void adcionaProfessor(Professor professor) throws BusinessException, PersistenceException, EJBException{
		try {
		   isCPFCadastrado(professor.getCpf());
			professorRepository.atualiza(professor);
			logger.info("Professor adcionado com sucesso. nome: "+professor.getNome());
			
		} catch (PersistenceException e) {
			logger.error("Erro na persistência de professor");
			throw e;
		} catch (EJBException e) {
			throw e;
		}
		
	}
	

	@Override
	public List<Professor> getTodosProfessores()
			throws PersistenceException, EJBException {
		return professorRepository.listaTodosProfessores();
	}



	@Override
	public Professor buscaPorId(Long id) throws PersistenceException {
		return (Professor) professorRepository.buscaPorId(id);
	}


	@Override
	public void alterarProfessor(Professor professor) throws PersistenceException,
			EJBException {
		professorRepository.atualiza(professor);
	}


	@Override
	public Professor getProfessorPorMatriculaFethJoinDisciplina(Long matricula)
			throws PersistenceException {
		return professorRepository.getProfessorPorMatriculaFethJoinDisciplina(matricula);
	}
	
	  private void isCPFCadastrado(String cpf) throws PersistenceException, BusinessException {
	      if (usuarioRepository.getUsuarioPorCpf(cpf) != null) {
	         throw new BusinessException(ErrorCode.CPF_JA_CADASTRADO);
	      }
	   }


	/* (non-Javadoc)
	 * @see br.com.xlearning.usuario.service.ProfessorService#findByStatus(br.com.xlearning.enumeracao.status.StatusUsuario)
	 */
	@Override
	public List<Professor> findByStatus(StatusUsuario status) throws PersistenceException, EJBException
	{
		// TODO Auto-generated method stub
		return professorRepository.findByStatus(status);
	}
	
}
