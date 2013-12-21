package br.com.xlearning.questionario.entidade;



import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "opcoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcoes.findAll", query = "SELECT o FROM Opcao o"),
    @NamedQuery(name = "Opcoes.findByIdopcoes", query = "SELECT o FROM Opcao o WHERE o.idopcoes = :idopcoes"),
    @NamedQuery(name = "Opcoes.findByOpcao", query = "SELECT o FROM Opcao o WHERE o.opcao = :opcao"),
    @NamedQuery(name = "Opcoes.findByOpcaoCorreta", query = "SELECT o FROM Opcao o WHERE o.opcaoCorreta = :opcaoCorreta"),
})
public class Opcao implements Serializable, Comparable<Opcao> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "idopcoes")
    private Long idopcoes;
    @Size(max = 45)
    @NotNull
    @Column(name = "opcao")
    private String opcao;
    @Column(name = "opcao_correta")
    private boolean opcaoCorreta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opcao")
    private List<Resposta> respostas;
    @JoinColumn(name = "questao", referencedColumnName = "idquestao")
    @ManyToOne(optional = true)
    private Questao questao;

    public Opcao() {
    }

    public Opcao(Long idopcoes) {
        this.idopcoes = idopcoes;
    }

    public Long getIdopcoes() {
        return idopcoes;
    }

    public void setIdopcoes(Long idopcoes) {
        this.idopcoes = idopcoes;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public boolean getOpcaoCorreta() {
        return opcaoCorreta;
    }

    public void setOpcaoCorreta(boolean opcaoCorreta) {
        this.opcaoCorreta = opcaoCorreta;
    }

    @XmlTransient
    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idopcoes != null ? idopcoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcao)) {
            return false;
        }
        Opcao other = (Opcao) object;
        if ((this.idopcoes == null && other.idopcoes != null) || (this.idopcoes != null && !this.idopcoes.equals(other.idopcoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Opcao[ nome=" + opcao + " ]";
    }

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Opcao o)
	{
		return this.getIdopcoes().compareTo(o.getIdopcoes());
	}
    
}
