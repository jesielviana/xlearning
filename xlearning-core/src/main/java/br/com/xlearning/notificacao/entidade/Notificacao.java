package br.com.xlearning.notificacao.entidade;



import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import br.com.xlearning.usuario.entidade.Usuario;

/**
 *
 * @author jesiel
 * @param <T>
 */
@Entity
@Table(name = "notificacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacao.findAll", query = "SELECT n FROM Notificacao n"),
    @NamedQuery(name = "Notificacao.findByIdnotificacao", query = "SELECT n FROM Notificacao n WHERE n.idnotificacao = :idnotificacao"),
    @NamedQuery(name = "Notificacao.findByData", query = "SELECT n FROM Notificacao n WHERE n.data = :data"),
    @NamedQuery(name = "Notificacao.findByAssunto", query = "SELECT n FROM Notificacao n WHERE n.assunto = :assunto"),
    @NamedQuery(name = "Notificacao.findByDescricao", query = "SELECT n FROM Notificacao n WHERE n.mensagem = :descricao"),
    @NamedQuery(name = "Notificacao.findByGrupoDestino", query = "SELECT n FROM Notificacao n WHERE n.grupoDestino = :grupoDestino")})
public class Notificacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idnotificacao")
    private Integer idnotificacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "assunto")
    private String assunto;
    @Size(max = 500)
    @Column(name = "mensagem")
    private String mensagem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grupo_destino")
    private String grupoDestino;
    @Transient
    private List<? extends Usuario> destinatarios;
    @JoinColumn(name = "usuario", referencedColumnName = "matricula")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Notificacao() {
    }

    public Notificacao(Integer idnotificacao) {
        this.idnotificacao = idnotificacao;
    }

    public Notificacao(Integer idnotificacao, Date data, String assunto, String grupoDestino) {
        this.idnotificacao = idnotificacao;
        this.data = data;
        this.assunto = assunto;
        this.grupoDestino = grupoDestino;
    }

    public Integer getIdnotificacao() {
        return idnotificacao;
    }

    public void setIdnotificacao(Integer idnotificacao) {
        this.idnotificacao = idnotificacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getGrupoDestino() {
        return grupoDestino;
    }

    public void setGrupoDestino(String grupoDestino) {
        this.grupoDestino = grupoDestino;
    }

    public List<? extends Usuario> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<? extends Usuario> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacao != null ? idnotificacao.hashCode() : 0);
        return hash;
    }

   @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.idnotificacao == null && other.idnotificacao != null) || (this.idnotificacao != null && !this.idnotificacao.equals(other.idnotificacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notificacao[ assunto=" + assunto + " ]";
    }
    
}
