package br.com.xlearning.post.entidade;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

import br.com.xlearning.curso.entidade.Curso;
import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.enumeracao.TipoPost;
import br.com.xlearning.enumeracao.status.StatusPost;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findByTitulo", query = "SELECT a FROM Post a WHERE a.titulo = :titulo"),
    @NamedQuery(name = "Post.findByTipo", query = "SELECT p FROM Post p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Post.findByTipoAndData", query = "SELECT p FROM Post p WHERE p.tipo = :tipo AND" +
    		" p.dataInicial <= :data AND p.dataFinal >= :data order by p.dataInicial desc"),
    @NamedQuery(name = "Post.findByDataInicial", query = "SELECT a FROM Post a WHERE a.dataInicial = :dataInicial"),
    @NamedQuery(name = "Post.findByDataFinal", query = "SELECT a FROM Post a WHERE a.dataFinal = :dataFinal"),
    @NamedQuery(name = "Post.findByTipoAndStatusAndData", query = "SELECT p FROM Post p WHERE p.tipo = :tipo AND p.status = :status " +
    		" AND p.dataInicial <= :data AND p.dataFinal >= :data order by p.dataInicial desc")})
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @NotNull
    @Column(name = "idPost")
    private Long idPost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 2000)
    @Column(name = "conteudo")
    private String conteudo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inicial")
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Column(name = "data_final")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    @Type(type = "br.com.xlearning.enumeracao.GenericEnumPersistenceType",  
		parameters = { @org.hibernate.annotations.Parameter(name = "enumClass", 
		value = "br.com.xlearning.enumeracao.TipoPost") 
        })
    private TipoPost tipo;
    @NotNull
    @Column(name = "status")
    @Enumerated
    private StatusPost status;
    @JoinColumn(name = "usuario", referencedColumnName = "matricula")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "disciplina", referencedColumnName = "iddisciplina")
    @ManyToOne(optional = true)
    private Disciplina disciplina;
    @JoinColumn(name = "curso", referencedColumnName = "idCurso")
    @ManyToOne(optional = true)
    private Curso curso;

    public Post() {
    }

    public Post(Long idPost) {
        this.idPost = idPost;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public TipoPost getTipo() {
        return tipo;
    }
    
    public TipoPost getTipoDescricao() {
        return tipo;
    }

    public void setTipo(TipoPost tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public int getStatus() {
		return status.getChave();
	}
    
    @Transient
    public StatusPost getStatusDescricao() {
		return status;
	}

	public void setStatus(StatusPost status) {
		this.status = status;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPost != null ? idPost.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.idPost == null && other.idPost != null) || (this.idPost != null && !this.idPost.equals(other.idPost))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post[ titulo=" + titulo + " ]";
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
    
}
