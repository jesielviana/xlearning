package br.com.xlearning.usuario.entidade;



import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.AlunoDisciplina;
import br.com.xlearning.questionario.entidade.QuestionarioAluno;
import br.com.xlearning.questionario.entidade.Resposta;
import br.com.xlearning.turma.entidade.Turma;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findByCurso", query = "SELECT a FROM Aluno a WHERE a.curso = :curso AND a.status =:status"),
    @NamedQuery(name = "Aluno.findByNotTurma", query = "SELECT a FROM Aluno a WHERE a.curso =:curso AND :idturma NOT MEMBER OF a.turmas" +
    		" AND a.status =:status"),
    @NamedQuery(name = "Aluno.findByQuestionarioStatus", query = "SELECT a FROM Aluno a INNER JOIN a.questionarioAlunos qa WHERE " +
      	            " qa.questionarioAlunoPK.questionario =:questionario AND qa.status = :status"),
    @NamedQuery(name = "Aluno.findByAlunoDisciplinaCursando", query = "SELECT a FROM Aluno a INNER JOIN a.alunoDisciplinas ad WHERE " +
    	            " ad.alunoDisciplinaPK.disciplina =:disciplina AND ad.situacao =:situacao"),
    @NamedQuery(name = "Aluno.findByMatricula", query = "SELECT a FROM Aluno a WHERE a.matricula = :matricula")})
public class Aluno extends Usuario {
    private static final long serialVersionUID = 1L;
    
    @ManyToMany(mappedBy = "alunos", fetch=FetchType.EAGER) 
    private Set<Turma> turmas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private Set<Resposta> respostas;
    @JoinColumn(name = "curso", referencedColumnName = "idcurso")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Curso curso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<AlunoDisciplina> alunoDisciplinas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<QuestionarioAluno> questionarioAlunos;

    public Aluno() {
    }

    public Aluno(Long matricula) {
        super.setMatricula(matricula);
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    @XmlTransient
    public Set<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostaSet(Set<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @XmlTransient
    public List<AlunoDisciplina> getAlunoDisciplinas() {
        return alunoDisciplinas;
    }

    public void setAlunoDisciplinas(List<AlunoDisciplina> alunoDisciplinas) {
        this.alunoDisciplinas = alunoDisciplinas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getMatricula() != null ? super.getMatricula().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((super.getMatricula() == null && other.getMatricula() != null) || (super.getMatricula() != null && !super.getMatricula().equals(other.getMatricula()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Aluno[ nome=" + getNome() + " ]";
    }

	public List<QuestionarioAluno> getQuestionarioAlunos()
	{
		return questionarioAlunos;
	}

	public void setQuestionarioAlunos(List<QuestionarioAluno> questionarioAlunos)
	{
		this.questionarioAlunos = questionarioAlunos;
	}
    
}
