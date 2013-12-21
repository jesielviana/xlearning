package br.com.xlearning.turma.entidade;


import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.StatusTurma;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "turma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turma.findAll", query = "SELECT t FROM Turma t"),
    @NamedQuery(name = "Turma.findByIdturma", query = "SELECT t FROM Turma t WHERE t.idturma = :idturma"),
    @NamedQuery(name = "Turma.findByNome", query = "SELECT t FROM Turma t WHERE t.nome = :nome"),
    @NamedQuery(name = "Turma.findByCurso", query = "SELECT t FROM Turma t WHERE t.curso = :curso"),
    @NamedQuery(name = "Turma.findBySemestre", query = "SELECT t FROM Turma t WHERE t.semestre = :semestre"),
    @NamedQuery(name = "Turma.findByStatus", query = "SELECT t FROM Turma t WHERE t.status = :status")})
public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Basic(optional = false)
    @Column(name = "idturma")
    private Long idturma;
    @Size(max = 45)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "semestre")
    private int semestre;
    @Column(name = "status")
    private StatusTurma status;
    @JoinTable(name = "turma_aluno", joinColumns = {
            @JoinColumn(name = "turma", referencedColumnName = "idturma")}, inverseJoinColumns = {
            @JoinColumn(name = "aluno", referencedColumnName = "matricula")})
    @ManyToMany
    private Set<Aluno> alunos;
    @JoinColumn(name = "curso", referencedColumnName = "idcurso")
    @ManyToOne(optional = false)
    private Curso curso;
    @JoinTable(name = "turma_disciplina", joinColumns = {
            @JoinColumn(name = "turma", referencedColumnName = "idturma")}, inverseJoinColumns = {
            @JoinColumn(name = "disciplina", referencedColumnName = "iddisciplina")})
    @ManyToMany
    private Set<Disciplina> disciplinas;

    public Turma() {
    }

    public Turma(Long idturma) {
        this.idturma = idturma;
    }


    public Long getIdturma() {
        return idturma;
    }

    public void setIdturma(Long idturma) {
        this.idturma = idturma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Integer getStatus() {
        return status.getChave();
    }
    
    public StatusTurma getStatusDescricao() {
        return status;
    }

    public void setStatus(StatusTurma status) {
        this.status = status;
    }

    @XmlTransient
    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idturma != null ? idturma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.idturma == null && other.idturma != null) || (this.idturma != null && !this.idturma.equals(other.idturma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Turma[ nome=" + nome + " ]";
    }
    
}
