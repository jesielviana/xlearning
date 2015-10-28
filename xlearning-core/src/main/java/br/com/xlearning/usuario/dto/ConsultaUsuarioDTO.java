/**
 * 
 */
package br.com.xlearning.usuario.dto;

import java.io.Serializable;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusUsuario;

/**
 * @author jesiel
 *
 */
public class ConsultaUsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long matricula;
	private Curso curso;
	private Disciplina disciplina;
	private String cpf;
	private StatusUsuario status;
	
	public Long getMatricula() {
		return matricula;
	}
	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public void clear(ConsultaUsuarioDTO consultaUsuarioDTO){
		consultaUsuarioDTO = new ConsultaUsuarioDTO();
	}
	public String getCpf()
	{
		return cpf;
	}
	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}
	public StatusUsuario getStatus()
	{
		return status;
	}
	public void setStatus(StatusUsuario status)
	{
		this.status = status;
	}
	
	
}
