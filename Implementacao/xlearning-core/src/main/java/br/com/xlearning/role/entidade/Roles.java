package br.com.xlearning.role.entidade;


import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRole", query = "SELECT r FROM Roles r WHERE r.role = :role")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role")
    private String role;
    @Size(max = 45)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<Usuario> usuario;

    public Roles() {
    }

    public Roles(Permissao role, String descricao) {
        this.role = role.toString();
        this.descricao = descricao;
    }

    public String getRole() {
        return role;
    }

    public void setRole(Permissao role) {
        this.role = role.toString();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (role != null ? role.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.role == null && other.role != null) || (this.role != null && !this.role.equals(other.role))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Roles[ role=" + role + " ]";
    }
    
}
