package br.com.xlearning.questionario.entidade;



import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

import br.com.xlearning.usuario.entidade.Aluno;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "resposta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resposta.findAll", query = "SELECT r FROM Resposta r"),
    @NamedQuery(name = "Resposta.findByQuestao", query = "SELECT r FROM Resposta r WHERE r.questao = :questao"),
    @NamedQuery(name = "Resposta.findByQuestaoAndAluno", query = "SELECT r FROM Resposta r WHERE r.questao = :questao AND r.aluno = :aluno"),
    @NamedQuery(name = "Resposta.findByIdresposta", query = "SELECT r FROM Resposta r WHERE r.idresposta = :idresposta")})
public class Resposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idresposta")
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Integer idresposta;
    @JoinColumn(name = "aluno", referencedColumnName = "matricula")
    @ManyToOne(optional = false)
    private Aluno aluno;
    @JoinColumn(name = "opcao", referencedColumnName = "idopcoes")
    @ManyToOne(optional = false)
    private Opcao opcao;
    @JoinColumn(name = "questao", referencedColumnName = "idquestao")
    @ManyToOne(optional = false)
    private Questao questao;

    public Resposta() {
    }

    public Resposta(Integer idresposta) {
        this.idresposta = idresposta;
    }

    public Integer getIdresposta() {
        return idresposta;
    }

    public void setIdresposta(Integer idresposta) {
        this.idresposta = idresposta;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Opcao getOpcao() {
        return opcao;
    }

    public void setOpcao(Opcao opcao) {
        this.opcao = opcao;
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
        hash += (idresposta != null ? idresposta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resposta)) {
            return false;
        }
        Resposta other = (Resposta) object;
        if ((this.idresposta == null && other.idresposta != null) || (this.idresposta != null && !this.idresposta.equals(other.idresposta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Resposta[ opção=" + opcao.getOpcao() + " ]";
    }
    
}
