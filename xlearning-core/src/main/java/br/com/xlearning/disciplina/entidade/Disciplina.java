package br.com.xlearning.disciplina.entidade;



import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.conteudo.entidade.ConteudoAcademico;
import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.enumeracao.status.EnumStatus;
import br.com.xlearning.questionario.entidade.Questionario;
import br.com.xlearning.turma.entidade.Turma;
import br.com.xlearning.usuario.entidade.Professor;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "disciplina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplina.findAll", query = "SELECT d FROM Disciplina d"),
    @NamedQuery(name = "Disciplina.findByIddisciplina", query = "SELECT d FROM Disciplina d WHERE d.idDisciplina = :idDisciplina"),
    @NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome"),
    @NamedQuery(name = "Disciplina.findByCurso", query = "SELECT d FROM Disciplina d WHERE d.curso =:curso AND d.status = :status"),
    @NamedQuery(name = "Disciplina.findByAlunoDisciplinaCursando", query = "SELECT d FROM Disciplina d INNER JOIN d.alunoDisciplinas ad WHERE " +
    		" ad.alunoDisciplinaPK.aluno =:aluno AND ad.situacao =:situacao"),
    @NamedQuery(name = "Disciplina.findByProfessor", query = "SELECT d FROM Disciplina d WHERE d.professor.matricula = :matricula" +
    		" AND d.status =:status"),
    @NamedQuery(name = "Disciplina.findByNotTurma", query = "SELECT d FROM Disciplina d " +
    		"WHERE d.curso =:curso AND :idturma NOT MEMBER OF d.turmas AND d.status =:status"),
    @NamedQuery(name = "Disciplina.findByStatus", query = "SELECT d FROM Disciplina d WHERE d.status = :status")})
public class Disciplina implements Serializable, Comparable<Disciplina> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Basic(optional = false)
    @Column(name = "iddisciplina")
    private Long idDisciplina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    @Column(name = "status")
    private EnumStatus status;
    @JoinColumn(name = "curso", referencedColumnName = "idcurso")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Curso curso;
    @JoinColumn(name = "professor", referencedColumnName = "matricula")
    @ManyToOne
    private Professor professor;
    @ManyToMany(mappedBy = "disciplinas")
    private List<Turma> turmas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Set<AlunoDisciplina> alunoDisciplinas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Set<ConteudoAcademico> conteudoAcademicos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Set<Questionario> questionarios;

    public Disciplina() {
    }

    public Disciplina(Long iddisciplina) {
        this.idDisciplina = iddisciplina;
    }

    public Disciplina(Long iddisciplina, String nome, int horas) {
        this.idDisciplina = iddisciplina;
        this.nome = nome;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long iddisciplina) {
        this.idDisciplina = iddisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status.getChave();
    }
    
    public EnumStatus getStatusDescricao() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @XmlTransient
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurma(List<Turma> turmas) {
        this.turmas = turmas;
    }

    @XmlTransient
    public Set<AlunoDisciplina> getAlunoDisciplinas() {
        return alunoDisciplinas;
    }

    public void setAlunoDisciplinas(Set<AlunoDisciplina> alunoDisciplinas) {
        this.alunoDisciplinas = alunoDisciplinas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Disciplina[ nome =" + nome + " ]";
    }

	public Set<ConteudoAcademico> getConteudoAcademicos()
	{
		return conteudoAcademicos;
	}

	public void setConteudoAcademicos(Set<ConteudoAcademico> conteudoAcademicos)
	{
		this.conteudoAcademicos = conteudoAcademicos;
	}

	public Set<Questionario> getQuestionarios()
	{
		return questionarios;
	}

	public void setQuestionarios(Set<Questionario> questionarios)
	{
		this.questionarios = questionarios;
	}

	@Override
	public int compareTo(Disciplina o)
	{
		return this.getNome().compareTo(o.getNome());
	}
    
}
