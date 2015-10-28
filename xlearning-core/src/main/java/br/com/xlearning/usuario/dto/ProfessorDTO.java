/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dto;

import java.io.Serializable;
import java.util.List;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author jesiel
 * @Data: 2013
 */
public class ProfessorDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List<Disciplina> disciplinas;
	private List<Turma> turmas;
	
	public ProfessorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfessorDTO(Usuario usuario, List<Disciplina> disciplinas, List<Turma> turmas) {
		this.usuario = usuario;
		this.disciplinas = disciplinas;
		this.turmas = turmas;
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
}
