package br.com.xlearning.questionario.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author jesiel
 */
@Entity
@Table(name = "questao", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"numero", "questionario" }) })
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Questao.findAll", query = "SELECT q FROM Questao q"),
		@NamedQuery(name = "Questao.findByIdquestao", query = "SELECT q FROM Questao q WHERE q.idquestao = :idquestao"),
		@NamedQuery(name = "Questao.findByTipo", query = "SELECT q FROM Questao q WHERE q.tipo = :tipo"),
		@NamedQuery(name = "Questao.findByQuestionario", query = "SELECT q FROM Questao q WHERE q.questionario.idquestionario = :idQuestionario"),
		@NamedQuery(name = "Questao.findByNumeroAndQuestionario", query = "SELECT q FROM Questao q WHERE q.numero = :numero" +
				" AND q.questionario.idquestionario = :idQuestionario"),
		@NamedQuery(name = "Questao.findByPergunta", query = "SELECT q FROM Questao q WHERE q.pergunta = :pergunta"),
		@NamedQuery(name = "Questao.findByStatus", query = "SELECT q FROM Questao q WHERE q.status = :status") })
public class Questao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "idquestao")
	private Long idquestao;
	@Basic(optional = false)
	@Column(name = "tipo")
	private int tipo;
	@NotNull
	@Column(name = "numero")
	private Integer numero;
	@Size(max = 200)
	@NotNull
	@Column(name = "pergunta")
	private String pergunta;
	@Column(name = "status")
	private Integer status;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "questao")
	private List<Resposta> respostas;
	@JoinColumn(name = "questionario", referencedColumnName = "idquestionario")
	@ManyToOne(optional = false)
	private Questionario questionario;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "questao",  fetch=FetchType.EAGER)
	private Set<Opcao> opcoes;
	@Transient
	private Opcao opcaoMarcada;
	

	public Questao()
	{
	}

	public Questao(Long idquestao)
	{
		this.idquestao = idquestao;
	}

	public Long getIdquestao()
	{
		return idquestao;
	}

	public void setIdquestao(Long idquestao)
	{
		this.idquestao = idquestao;
	}

	public int getTipo()
	{
		return tipo;
	}

	public void setTipo(int tipo)
	{
		this.tipo = tipo;
	}

	public String getPergunta()
	{
		return pergunta;
	}

	public void setPergunta(String pergunta)
	{
		this.pergunta = pergunta;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@XmlTransient
	public List<Resposta> getRespostas()
	{
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas)
	{
		this.respostas = respostas;
	}

	public Questionario getQuestionario()
	{
		return questionario;
	}

	public void setQuestionario(Questionario questionario)
	{
		this.questionario = questionario;
	}

	@XmlTransient
	public Set<Opcao> getOpcoes()
	{
		return opcoes;
	}

	public void setOpcoes(Set<Opcao> opcoes)
	{
		this.opcoes = opcoes;
	}

	public Integer getNumero()
	{
		return numero;
	}

	public void setNumero(Integer numero)
	{
		this.numero = numero;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (idquestao != null ? idquestao.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Questao))
		{
			return false;
		}
		Questao other = (Questao) object;
		if ((this.idquestao == null && other.idquestao != null)
				|| (this.idquestao != null && !this.idquestao.equals(other.idquestao)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Questao[ pergunta=" + pergunta + " ]";
	}

	public Opcao getOpcaoMarcada()
	{
		return opcaoMarcada;
	}

	public void setOpcaoMarcada(Opcao opcaoMarcada)
	{
		this.opcaoMarcada = opcaoMarcada;
	}

	public List<Opcao> getTodasOpcoes() {
		
		List<Opcao> todasOpcoes = new ArrayList<Opcao>();
		todasOpcoes.addAll(opcoes);
		return todasOpcoes;
	}

}
