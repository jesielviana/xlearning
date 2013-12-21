package br.com.xlearning.usuario.repository;

import java.util.List;
import javax.persistence.PersistenceException;
import br.com.xlearning.usuario.entidade.Usuario;

public interface UsuarioRepository {

   
   public Usuario getUserSession(Long id) throws PersistenceException;
   
	public List<Usuario> listaTodos() throws PersistenceException;
	
   public Usuario getUsuarioPorCpf(String cpf) throws PersistenceException;
   
   public void atualiza(Usuario usuario);

}
