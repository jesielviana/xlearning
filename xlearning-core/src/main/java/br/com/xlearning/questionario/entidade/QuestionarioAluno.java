package br.com.xlearning.questionario.entidade;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.xlearning.enumeracao.status.StatusQuestionarioAluno;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "questionario_aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionarioAluno.findAll", query = "SELECT q FROM QuestionarioAluno q"),
    @NamedQuery(name = "QuestionarioAluno.findByQuestionario", query = "SELECT q FROM QuestionarioAluno q WHERE q.questionarioAlunoPK.questionario = :questionario"),
    @NamedQuery(name = "QuestionarioAluno.findByAluno", query = "SELECT q FROM QuestionarioAluno q WHERE q.questionarioAlunoPK.aluno = :aluno"),
    @NamedQuery(name = "QuestionarioAluno.findByAlunoAndQuestionario", query = "SELECT q FROM QuestionarioAluno q WHERE q.questionarioAlunoPK.aluno = :aluno" +
    		" AND q.questionarioAlunoPK.questionario = :questionario AND q.status = :status"),
    @NamedQuery(name = "QuestionarioAluno.findByStatus", query = "SELECT q FROM QuestionarioAluno q WHERE q.status = :status"),
    @NamedQuery(name = "QuestionarioAluno.findByDataInicial", query = "SELECT q FROM QuestionarioAluno q WHERE q.dataInicial = :dataInicial"),
    @NamedQuery(name = "QuestionarioAluno.findByDataFinal", query = "SELECT q FROM QuestionarioAluno q WHERE q.dataFinal = :dataFinal"),
    @NamedQuery(name = "QuestionarioAluno.findByAvaliacao", query = "SELECT q FROM QuestionarioAluno q WHERE q.avaliacao = :avaliacao")})
public class QuestionarioAluno implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QuestionarioAlunoPK questionarioAlunoPK;
    @Column(name = "status")
    @Enumerated
    private StatusQuestionarioAluno status;
    @Column(name = "data_inicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicial;
    @Column(name = "data_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "avaliacao")
    private Float avaliacao;
    @JoinColumn(name = "aluno", referencedColumnName = "matricula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aluno aluno;
    @JoinColumn(name = "questionario", referencedColumnName = "idquestionario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Questionario questionario;

    public QuestionarioAluno() {
    }

    public QuestionarioAluno(QuestionarioAlunoPK questionarioAlunoPK) {
        this.questionarioAlunoPK = questionarioAlunoPK;
    }

    public QuestionarioAluno(int questionario, int aluno) {
        this.questionarioAlunoPK = new QuestionarioAlunoPK(questionario, aluno);
    }

    public QuestionarioAlunoPK getQuestionarioAlunoPK() {
        return questionarioAlunoPK;
    }

    public void setQuestionarioAlunoPK(QuestionarioAlunoPK questionarioAlunoPK) {
        this.questionarioAlunoPK = questionarioAlunoPK;
    }

    public int getStatus() {
        return status.getChave();
    }

    public void setStatus(StatusQuestionarioAluno status) {
        this.status = status;
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

    public Float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Questionario getQuestionario1() {
        return questionario;
    }

    public void setQuestionario(Questionario questionario) {
        this.questionario = questionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionarioAlunoPK != null ? questionarioAlunoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionarioAluno)) {
            return false;
        }
        QuestionarioAluno other = (QuestionarioAluno) object;
        if ((this.questionarioAlunoPK == null && other.questionarioAlunoPK != null) || (this.questionarioAlunoPK != null && !this.questionarioAlunoPK.equals(other.questionarioAlunoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "reversa.QuestionarioAluno[ questionarioAlunoPK=" + questionarioAlunoPK + " ]";
    }
    
}
