package br.com.xlearning.disciplina.entidade;



import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.xlearning.enumeracao.SituacaoAlunoDisciplina;
import br.com.xlearning.enumeracao.StatusAprovacao;
import br.com.xlearning.usuario.entidade.Aluno;

/**
 *
 * @author jesiel
 */
@Entity
@Table(name = "aluno_disciplina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlunoDisciplina.findAll", query = "SELECT a FROM AlunoDisciplina a"),
    @NamedQuery(name = "AlunoDisciplina.findByAluno", query = "SELECT a FROM AlunoDisciplina a WHERE a.alunoDisciplinaPK.aluno = :aluno"),
    @NamedQuery(name = "AlunoDisciplina.findByDisciplina", query = "SELECT a FROM AlunoDisciplina a WHERE a.alunoDisciplinaPK.disciplina = :disciplina"),
    @NamedQuery(name = "AlunoDisciplina.findByAlunoDisciplinaSituacao", query = "SELECT ad FROM AlunoDisciplina ad WHERE ad.alunoDisciplinaPK.disciplina = :disciplina" +
    		" AND ad.alunoDisciplinaPK.aluno =:aluno AND ad.situacao in (:situacao)"),
    @NamedQuery(name = "AlunoDisciplina.findByListAlunoDisciplinaSituacao", query = "SELECT ad FROM AlunoDisciplina ad WHERE ad.alunoDisciplinaPK.disciplina = :disciplina" +
    	            " AND ad.alunoDisciplinaPK.aluno in (:alunos) AND ad.situacao in (:situacao)"),
    @NamedQuery(name = "AlunoDisciplina.findByListDisciplinasAlunoDisciplinaSituacao", query = "SELECT ad FROM AlunoDisciplina ad WHERE ad.alunoDisciplinaPK.disciplina in " +
    		"(:disciplinas) AND ad.alunoDisciplinaPK.aluno = :aluno AND ad.situacao in (:situacao)"),
    @NamedQuery(name = "AlunoDisciplina.findByNota1", query = "SELECT a FROM AlunoDisciplina a WHERE a.nota1 = :nota1"),
    @NamedQuery(name = "AlunoDisciplina.findByNota2", query = "SELECT a FROM AlunoDisciplina a WHERE a.nota2 = :nota2"),
    @NamedQuery(name = "AlunoDisciplina.findByMedia", query = "SELECT a FROM AlunoDisciplina a WHERE a.media = :media"),
    @NamedQuery(name = "AlunoDisciplina.findByFrequecia", query = "SELECT a FROM AlunoDisciplina a WHERE a.frequecia = :frequecia")})
public class AlunoDisciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlunoDisciplinaPK alunoDisciplinaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota1")
    private Float nota1;
    @Column(name = "nota2")
    private Float nota2;
    @Column(name = "media")
    private Float media;
    @Column(name = "frequecia")
    private Float frequecia;
    @Column(name = "situacao")
    @NotNull
    private SituacaoAlunoDisciplina situacao;
    @JoinColumn(name = "disciplina", referencedColumnName = "iddisciplina", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Disciplina disciplina;
    @JoinColumn(name = "aluno", referencedColumnName = "matricula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aluno aluno;
    @Column(name = "status_aprovacao")
    private StatusAprovacao statusAprovacao;

    public AlunoDisciplina() {
    }

    public AlunoDisciplina(AlunoDisciplinaPK alunoDisciplinaPK) {
        this.alunoDisciplinaPK = alunoDisciplinaPK;
    }

    public AlunoDisciplina(int aluno, int disciplina) {
        this.alunoDisciplinaPK = new AlunoDisciplinaPK(aluno, disciplina);
    }

    public AlunoDisciplinaPK getAlunoDisciplinaPK() {
        return alunoDisciplinaPK;
    }

    public void setAlunoDisciplinaPK(AlunoDisciplinaPK alunoDisciplinaPK) {
        this.alunoDisciplinaPK = alunoDisciplinaPK;
    }

    public Float getNota1() {
        return nota1;
    }

    public void setNota1(Float nota1) {
        this.nota1 = nota1;
    }

    public Float getNota2() {
        return nota2;
    }

    public void setNota2(Float nota2) {
        this.nota2 = nota2;
    }

    public Float getMedia() {
        return media;
    }

    public void setMedia(Float media) {
        this.media = media;
    }

    public Float getFrequecia() {
        return frequecia;
    }

    public void setFrequecia(Float frequecia) {
        this.frequecia = frequecia;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina1(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno1(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alunoDisciplinaPK != null ? alunoDisciplinaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlunoDisciplina)) {
            return false;
        }
        AlunoDisciplina other = (AlunoDisciplina) object;
        if ((this.alunoDisciplinaPK == null && other.alunoDisciplinaPK != null) || (this.alunoDisciplinaPK != null && !this.alunoDisciplinaPK.equals(other.alunoDisciplinaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AlunoDisciplina[ alunoDisciplinaPK=" + alunoDisciplinaPK + " ]";
    }

	public int getSituacao()
	{
		return situacao.getChave();
	}
	
	public SituacaoAlunoDisciplina getSituacaoDescricao()
	{
		return situacao;
	}

	public void setSituacao(int situacao)
	{
		this.situacao = SituacaoAlunoDisciplina.get(situacao);
	}

   public StatusAprovacao getStatusAprovacao()
   {
      return statusAprovacao;
   }

   public void setStatusAprovacao(int statusAprovacao)
   {
      this.statusAprovacao = StatusAprovacao.get(statusAprovacao);
   }
    
}
