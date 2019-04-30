/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.usuario.dto;

import java.io.Serializable;
import java.util.List;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author jesiel
 * @Data: 2013
 */
public class AlunoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Curso curso;
	private Turma turma;
	private List<Disciplina> disciplinas;

	public AlunoDTO() {
		// TODO Auto-generated constructor stub
	}

	public AlunoDTO(Usuario usuario, Curso curso, List<Disciplina> disciplinas) {
		this.setUsuario(usuario);
		this.setCurso(curso);
		this.setDisciplinas(disciplinas);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

}
