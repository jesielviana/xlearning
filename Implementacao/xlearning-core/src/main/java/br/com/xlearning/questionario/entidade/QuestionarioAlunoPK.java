package br.com.xlearning.questionario.entidade;


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
public class QuestionarioAlunoPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "questionario")
    private long questionario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aluno")
    private long aluno;

    public QuestionarioAlunoPK() {
    }

    public QuestionarioAlunoPK(long questionario, long aluno) {
        this.questionario = questionario;
        this.aluno = aluno;
    }

    public long getQuestionario() {
        return questionario;
    }

    public void setQuestionario(long questionario) {
        this.questionario = questionario;
    }

    public long getAluno() {
        return aluno;
    }

    public void setAluno(long aluno) {
        this.aluno = aluno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) questionario;
        hash += (int) aluno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionarioAlunoPK)) {
            return false;
        }
        QuestionarioAlunoPK other = (QuestionarioAlunoPK) object;
        if (this.questionario != other.questionario) {
            return false;
        }
        if (this.aluno != other.aluno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reversa.QuestionarioAlunoPK[ questionario=" + questionario + ", aluno=" + aluno + " ]";
    }
    
}
