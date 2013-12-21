/**
 * Xlearning 2013
 */
package br.com.xlearning.mbean.conteudo;

import java.util.Date;


import org.primefaces.model.StreamedContent;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 * @author Jesiel Viana
 * Date 20/10/2013
 */
public class ConteudoAcademicoVO implements Comparable<ConteudoAcademicoVO> {

	    private Integer tipo;
	    private String nome;
	    private String descricao;
	    private String caminho;
	    private Date dataInicial;
	    private Date dataFinal;
	    private Disciplina disciplina;
	    private Curso curso;
	    private StreamedContent file;
		/**
		 * @return the tipo
		 */
		public Integer getTipo()
		{
			return tipo;
		}
		/**
		 * @param tipo the tipo to set
		 */
		public void setTipo(Integer tipo)
		{
			this.tipo = tipo;
		}
		/**
		 * @return the nome
		 */
		public String getNome()
		{
			return nome;
		}
		/**
		 * @param nome the nome to set
		 */
		public void setNome(String nome)
		{
			this.nome = nome;
		}
		/**
		 * @return the descricao
		 */
		public String getDescricao()
		{
			return descricao;
		}
		/**
		 * @param descricao the descricao to set
		 */
		public void setDescricao(String descricao)
		{
			this.descricao = descricao;
		}
		/**
		 * @return the caminho
		 */
		public String getCaminho()
		{
			return caminho;
		}
		/**
		 * @param caminho the caminho to set
		 */
		public void setCaminho(String caminho)
		{
			this.caminho = caminho;
		}
		/**
		 * @return the dataInicial
		 */
		public Date getDataInicial()
		{
			return dataInicial;
		}
		/**
		 * @param dataInicial the dataInicial to set
		 */
		public void setDataInicial(Date dataInicial)
		{
			this.dataInicial = dataInicial;
		}
		/**
		 * @return the dataFinal
		 */
		public Date getDataFinal()
		{
			return dataFinal;
		}
		/**
		 * @param dataFinal the dataFinal to set
		 */
		public void setDataFinal(Date dataFinal)
		{
			this.dataFinal = dataFinal;
		}
		/**
		 * @return the disciplina
		 */
		public Disciplina getDisciplina()
		{
			return disciplina;
		}
		/**
		 * @param disciplina the disciplina to set
		 */
		public void setDisciplina(Disciplina disciplina)
		{
			this.disciplina = disciplina;
		}
		/**
		 * @return the curso
		 */
		public Curso getCurso()
		{
			return curso;
		}
		/**
		 * @param curso the curso to set
		 */
		public void setCurso(Curso curso)
		{
			this.curso = curso;
		}
		/**
		 * @return the file
		 */
		public StreamedContent getFile()
		{
			return file;
		}
		/**
		 * @param file the file to set
		 */
		public void setFile(StreamedContent file)
		{
			this.file = file;
		}
		@Override
		public int compareTo(ConteudoAcademicoVO o)
		{
			return this.getTipo().compareTo(o.getTipo());
		}  
	    
	    
}
