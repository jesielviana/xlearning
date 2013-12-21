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

import br.com.xlearning.error.BusinessException;
import br.com.xlearning.error.ErrorCode;
import br.com.xlearning.usuario.entidade.Coordenador;
import br.com.xlearning.usuario.repository.CoordenadorRepository;
import br.com.xlearning.usuario.repository.UsuarioRepository;
import br.com.xlearning.usuario.service.CoordenadorService;

/**
 * @author jesiel
 * @Data: 2013
 */
@Stateless
@LocalBean
@Local(CoordenadorService.class)
public class CoordenadorServiceImpl implements CoordenadorService{
   private static Logger logger = Logger.getLogger(CoordenadorServiceImpl.class);
   @Inject
   private CoordenadorRepository coordenadorRepository;
   @Inject
   private UsuarioRepository usuarioRepository;
   
   @Override
   public void adicionarCoordenador(Coordenador coordenador) throws BusinessException, PersistenceException, EJBException
   {
      isCPFCadastrado(coordenador.getCpf());
      coordenadorRepository.adiciona(coordenador);
      logger.info("coordenador adicionado com sucesso. nome: "+coordenador.getNome());
   }

   @Override
   public List<Coordenador> getTodosCoordenadores() throws PersistenceException, EJBException
   {
      return coordenadorRepository.listaTodosCoordenadores();
   }

   @Override
   public Coordenador buscaCoordenadorPorId(Long id) throws PersistenceException, EJBException
   {
      return (Coordenador) coordenadorRepository.buscaPorId(id);
   }
   
   private void isCPFCadastrado(String cpf) throws PersistenceException, BusinessException {
      if (usuarioRepository.getUsuarioPorCpf(cpf) != null) {
         throw new BusinessException(ErrorCode.CPF_JA_CADASTRADO);
      }
   }

   @Override
   public void atualizarCoordenador(Coordenador coordenador) throws PersistenceException, EJBException
   {
      coordenadorRepository.atualiza(coordenador);
   }

}
