package br.com.xlearning.curso.entidade;



import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.status.EnumStatus;
import br.com.xlearning.usuario.entidade.Aluno;
import br.com.xlearning.usuario.entidade.Coordenador;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByCoordenador", query = "SELECT c FROM Curso c WHERE c.coordenador = :coordenador"),
    @NamedQuery(name = "Curso.findByDisciplinas", query = "SELECT c FROM Curso c INNER JOIN " +
    		"c.disciplinas d WHERE d.idDisciplina =:idDisciplina AND c.status =:status"),
    @NamedQuery(name = "Curso.findByStatus", query = "SELECT c FROM Curso c WHERE c.status = :status"),
    @NamedQuery(name = "Curso.findByNome", query = "SELECT c FROM Curso c WHERE c.nome = :nome")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @NotNull
    @Column(name = "idCurso")
    private Long idCurso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    @Size(min = 1, max = 120)
    @Column(name = "nomeLogo")
    private String nomeLogo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private List<Disciplina> disciplinas;
    @JoinColumn(name = "coordenador", referencedColumnName = "matricula")
    @ManyToOne
    private Coordenador coordenador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private List<Aluno> alunos;
    @NotNull
    @Column(name = "status")
    @Enumerated
    private EnumStatus status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private Set<ConteudoAcademico> conteudoAcademicos;
    @Size(min = 1, max = 2000)
    @Column(name = "descricao")
    private String descricao;

    public Curso() {
    }

    public Curso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Curso(Long idCurso, String nome) {
        this.idCurso = idCurso;
        this.nome = nome;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Coordenador getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
    }

    @XmlTransient
    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    
    public int getStatus() {
		return status.getChave();
	}
    
    public EnumStatus getStatusDescricao() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurso != null ? idCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.idCurso == null && other.idCurso != null) || (this.idCurso != null && !this.idCurso.equals(other.idCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Curso[ nome=" + nome + "]";
    }

	public String getNomeLogo() {
		return nomeLogo;
	}

	public void setNomeLogo(String nomeLogo) {
		this.nomeLogo = nomeLogo;
	}

	public Set<ConteudoAcademico> getConteudoAcademicos()
	{
		return conteudoAcademicos;
	}

	public void setConteudoAcademicos(Set<ConteudoAcademico> conteudoAcademicos)
	{
		this.conteudoAcademicos = conteudoAcademicos;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
    
}
