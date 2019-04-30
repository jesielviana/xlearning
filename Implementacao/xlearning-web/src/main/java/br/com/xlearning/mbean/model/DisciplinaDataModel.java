package br.com.xlearning.mbean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.xlearning.disciplina.entidade.Disciplina;

public class DisciplinaDataModel extends ListDataModel<Disciplina> implements SelectableDataModel<Disciplina> {

	  public DisciplinaDataModel() {  
	    }  
	  
	    public DisciplinaDataModel(List<Disciplina> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public Disciplina getRowData(String rowKey) {  
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
	          
	        @SuppressWarnings("unchecked")
			List<Disciplina> disciplinas = (List<Disciplina>) getWrappedData();  
	          
	        for(Disciplina disciplina : disciplinas) {  
	            if(disciplina.getIdDisciplina().equals(rowKey))  
	                return disciplina;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(Disciplina disciplina) {  
	        return disciplina.getIdDisciplina();  
	    }  
}
