package br.com.xlearning.questionario.entidade;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.xlearning.disciplina.entidade.Disciplina;
import br.com.xlearning.util.XlearningUtil;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "questionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionario.findAll", query = "SELECT q FROM Questionario q"),
    @NamedQuery(name = "Questionario.findByIdquestionario", query = "SELECT q FROM Questionario q WHERE q.idquestionario = :idquestionario"),
    @NamedQuery(name = "Questionario.findByNome", query = "SELECT q FROM Questionario q WHERE q.nome = :nome"),
    @NamedQuery(name = "Questionario.findByDisciplina", query = "SELECT q FROM Questionario q WHERE q.disciplina = :disciplina"),
    @NamedQuery(name = "Questionario.findByAlunoStatus", query = "SELECT q FROM Questionario q INNER JOIN q.questionarioAlunos qa WHERE " +
    	            " q.disciplina.idDisciplina =:disciplina AND qa.questionarioAlunoPK.aluno =:aluno AND qa.status in (:status)"),
    @NamedQuery(name = "Questionario.findByDataInicial", query = "SELECT q FROM Questionario q WHERE q.dataInicial = :dataInicial"),
    @NamedQuery(name = "Questionario.findByDataFinal", query = "SELECT q FROM Questionario q WHERE q.dataFinal = :dataFinal")})
public class Questionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "idquestionario")
    private Long idquestionario;
    @Size(max = 45)
    @Column(name = "nome")
    private String nome;
    @Column(name = "data_inicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicial;
    @Column(name = "data_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFinal;
    @JoinColumn(name = "disciplina", referencedColumnName = "iddisciplina")
    @ManyToOne(optional = false)
    private Disciplina disciplina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionario", fetch=FetchType.EAGER)
    private List<Questao> questoes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionario")
    private List<QuestionarioAluno> questionarioAlunos;


    public Questionario() {
    }

    public Questionario(Long idquestionario) {
        this.idquestionario = idquestionario;
    }

    public Long getIdquestionario() {
        return idquestionario;
    }

    public void setIdquestionario(Long idquestionario) {
        this.idquestionario = idquestionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @XmlTransient
    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquestionario != null ? idquestionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questionario)) {
            return false;
        }
        Questionario other = (Questionario) object;
        if ((this.idquestionario == null && other.idquestionario != null) || (this.idquestionario != null && !this.idquestionario.equals(other.idquestionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Questionario[ nome=" + nome + " ]";
    }
    
    public String getDataInicialFormatada()
    {
       return XlearningUtil.formataData(getDataInicial(), "dd/MM/yyyy");
    }
    
    public String getDataFinalFormatada()
    {
       return XlearningUtil.formataData(getDataFinal(), "dd/MM/yyyy");
    }

	public List<QuestionarioAluno> getQuestionarioAlunos()
	{
		return questionarioAlunos;
	}

	public void setQuestionarioAlunos(List<QuestionarioAluno> questionarioAlunos)
	{
		this.questionarioAlunos = questionarioAlunos;
	}
    
}
