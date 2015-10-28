package br.com.xlearning.mbean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.xlearning.curso.entidade.Curso;

public class CursoDataModel extends ListDataModel<Curso> implements SelectableDataModel<Curso>{
	
	  public CursoDataModel() {  
	    }  
	  
	    public CursoDataModel(List<Curso> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public Curso getRowData(String rowKey) {  
	        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
	          
	        @SuppressWarnings("unchecked")
			List<Curso> cursos = (List<Curso>) getWrappedData();  
	          
	        for(Curso curso : cursos) {  
	            if(curso.getIdCurso().equals(rowKey))  
	                return curso;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(Curso curso) {  
	        return curso.getIdCurso();  
	    }  

}
