package br.com.xlearning.disciplina.entidade;



import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jesiel
 */
@Embeddable
public class AlunoDisciplinaPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "aluno")
    private long aluno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disciplina")
    private long disciplina;

    public AlunoDisciplinaPK() {
    }

    public AlunoDisciplinaPK(long aluno, long disciplina) {
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public long getAluno() {
        return aluno;
    }

    public void setAluno(long aluno) {
        this.aluno = aluno;
    }

    public long getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(long disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) aluno;
        hash += (int) disciplina;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlunoDisciplinaPK)) {
            return false;
        }
        AlunoDisciplinaPK other = (AlunoDisciplinaPK) object;
        if (this.aluno != other.aluno) {
            return false;
        }
        if (this.disciplina != other.disciplina) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AlunoDisciplinaPK[ aluno=" + aluno + ", disciplina=" + disciplina + " ]";
    }
    
}
