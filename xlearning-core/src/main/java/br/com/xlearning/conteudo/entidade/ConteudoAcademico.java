package br.com.xlearning.conteudo.entidade;



import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.TipoConteudo;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "conteudo_academico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConteudoAcademico.findAll", query = "SELECT c FROM ConteudoAcademico c"),
    @NamedQuery(name = "ConteudoAcademico.findByTipo", query = "SELECT c FROM ConteudoAcademico c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "ConteudoAcademico.findByDisciplina", query = "SELECT c FROM ConteudoAcademico c WHERE c.disciplina.idDisciplina = :idDisciplina"),
    @NamedQuery(name = "ConteudoAcademico.findByNome", query = "SELECT c FROM ConteudoAcademico c WHERE c.nome = :nome"),
    @NamedQuery(name = "ConteudoAcademico.findByDataInicial", query = "SELECT c FROM ConteudoAcademico c WHERE c.dataPostagem = :dataInicial")})
public class ConteudoAcademico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idconteudo_academico")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long idconteudoAcademico;
    @Column(name = "tipo")
    private Integer tipo;
    @Size(max = 45)
    @Column(name = "nome")
    private String nome;
    @Size(max = 300)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 200)
    @Column(name = "caminho")
    private String caminho;
    @Column(name = "arquivo")
    private String arquivo;
    @Column(name = "dataPostagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPostagem;
    @JoinColumn(name = "disciplina", referencedColumnName = "iddisciplina")
    @ManyToOne(optional = false)
    private Disciplina disciplina;
    @JoinColumn(name = "curso", referencedColumnName = "idCurso")
    @ManyToOne(optional = false)
    private Curso curso;

    public ConteudoAcademico() {
    }

    public ConteudoAcademico(Long idconteudoAcademico) {
        this.idconteudoAcademico = idconteudoAcademico;
    }

    public Long getIdconteudoAcademico() {
        return idconteudoAcademico;
    }

    public void setIdconteudoAcademico(Long idconteudoAcademico) {
        this.idconteudoAcademico = idconteudoAcademico;
    }

    public Integer getTipo() {
        return tipo;
    }
    
    public TipoConteudo getTipoConteudo()
    {
       return TipoConteudo.get(tipo);
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataInicial) {
        this.dataPostagem = dataInicial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconteudoAcademico != null ? idconteudoAcademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConteudoAcademico)) {
            return false;
        }
        ConteudoAcademico other = (ConteudoAcademico) object;
        if ((this.idconteudoAcademico == null && other.idconteudoAcademico != null) || (this.idconteudoAcademico != null && !this.idconteudoAcademico.equals(other.idconteudoAcademico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConteudoAcademico[ nome =" + nome + " ]";
    }

	public Disciplina getDisciplina()
	{
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina)
	{
		this.disciplina = disciplina;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public String getArquivo()
	{
		return arquivo;
	}

	public void setArquivo(String arquivo)
	{
		this.arquivo = arquivo;
	}
    
}
