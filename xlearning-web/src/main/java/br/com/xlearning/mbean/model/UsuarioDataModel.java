package br.com.xlearning.mbean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.xlearning.usuario.entidade.Usuario;


/**
 * @author jesiel
 * @param <T>
 * @Data: 2013
 */
public class UsuarioDataModel<T extends Usuario> extends ListDataModel<T> implements SelectableDataModel<T> {    
  
    public UsuarioDataModel() {  
    }  
  
    public UsuarioDataModel(List<T> data) {  
        super(data);  
    }  
      
    @Override  
    public T getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        @SuppressWarnings("unchecked")
		List<T> usuarios = (List<T>) getWrappedData();  
          
        for(T usuario : usuarios) {  
            if(usuario.getMatricula().equals(rowKey))  
                return usuario;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(T usuario) {  
        return usuario.getMatricula();  
    }  
}
