package br.com.xlearning.usuario.entidade;



import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.curso.entidade.Curso;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "coordenador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordenador.findAll", query = "SELECT c FROM Coordenador c"),
    @NamedQuery(name = "Coordenador.findByMatricula", query = "SELECT c FROM Coordenador c WHERE c.matricula = :matricula")})
public class Coordenador extends Usuario {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "coordenador")
    private List<Curso> cursos;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public Coordenador() {
    }

    public Coordenador(Long matricula) {
        super.setMatricula(matricula);
    }

    @XmlTransient
    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
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
        hash += (super.getMatricula() != null ? super.getMatricula().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordenador)) {
            return false;
        }
        Coordenador other = (Coordenador) object;
        if ((super.getMatricula() == null && other.getMatricula() != null) || (super.getMatricula() != null && !super.getMatricula().equals(other.getMatricula()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coordenador[ matricula=" + super.getMatricula() + " ]";
    }
    
}
