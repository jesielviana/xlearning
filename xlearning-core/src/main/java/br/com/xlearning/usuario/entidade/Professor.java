package br.com.xlearning.usuario.entidade;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.disciplina.entidade.Disciplina;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "professor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor p"),
    @NamedQuery(name = "Professor.findByMatriculaJoinFethDisciplina", query = "SELECT p FROM Professor p join fetch p.disciplinas WHERE p.matricula = :matricula"),
    @NamedQuery(name = "Professor.findByDisciplinaId", query = "SELECT p FROM Professor p join fetch p.disciplinas INNER JOIN p.disciplinas dc " +
    		"WHERE dc = (Select d FROM Disciplina d WHERE d.idDisciplina = :id)"),
    @NamedQuery(name = "Professor.findByStatus", query = "SELECT p FROM Professor p WHERE p.status = :status"),
    @NamedQuery(name = "Professor.findByMatricula", query = "SELECT p FROM Professor p WHERE p.matricula = :matricula")})
public class Professor extends Usuario {
    private static final long serialVersionUID = 1L;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor", fetch=FetchType.EAGER)
    private List<Disciplina> disciplinas;

    public Professor() {
    }

    public Professor(Long matricula) {
        super.setMatricula(matricula);
    }

    @XmlTransient
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getMatricula() != null ? super.getMatricula().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((super.getMatricula() == null && other.getMatricula() != null) || (super.getMatricula() != null && !super.getMatricula().equals(other.getMatricula()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Professor[ matricula=" + super.getMatricula() + " ]";
    }
    
}
