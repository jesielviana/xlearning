/**
 * Xlearning 2013
 */
package br.com.xlearning.mbean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.xlearning.turma.entidade.Turma;


/**
 * @author Jesiel Viana
 * Date 22/09/2013
 */
public class TurmaDataModel extends ListDataModel<Turma> implements SelectableDataModel<Turma> {
	
	  public TurmaDataModel() {  
	    }  
	  
	    public TurmaDataModel(List<Turma> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public Turma getRowData(String rowKey) {  
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
	          
	        @SuppressWarnings("unchecked")
			List<Turma> Turmas = (List<Turma>) getWrappedData();  
	          
	        for(Turma Turma : Turmas) {  
	            if(Turma.getIdturma().equals(rowKey))  
	                return Turma;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(Turma Turma) {  
	        return Turma.getIdturma();  
	    }  

}
