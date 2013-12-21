package br.com.xlearning.usuario.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;
import br.com.xlearning.error.BusinessException;
import br.com.xlearning.usuario.entidade.Coordenador;

public interface CoordenadorService {
   
   public void adicionarCoordenador(Coordenador coordenador) throws BusinessException, PersistenceException, EJBException;
   
   public void atualizarCoordenador(Coordenador coordenador) throws PersistenceException, EJBException;
   
   public Coordenador buscaCoordenadorPorId(Long id) throws PersistenceException, EJBException;
      
   public List<Coordenador> getTodosCoordenadores()  throws PersistenceException, EJBException;
   
}
