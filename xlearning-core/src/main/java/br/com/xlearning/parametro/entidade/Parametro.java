package br.com.xlearning.parametro.entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jesiel
 */
@Entity
@Table(name = "parametro")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p"),
		@NamedQuery(name = "Parametro.findByCodigo", query = "SELECT p FROM Parametro p WHERE p.codigo = :codigo"),
		@NamedQuery(name = "Parametro.findByValor", query = "SELECT p FROM Parametro p WHERE p.valor = :valor"),
		@NamedQuery(name = "Parametro.findByDescricao", query = "SELECT p FROM Parametro p WHERE p.descricao = :descricao"),
		@NamedQuery(name = "Parametro.findByTipo", query = "SELECT p FROM Parametro p WHERE p.tipo = :tipo") })
public class Parametro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "codigo")
	private Integer codigo;
	@Size(max = 120)
	@Column(name = "valor")
	private String valor;
	@Size(max = 45)
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "tipo")
	private Integer tipo;

	public Parametro()
	{
	}

	public Parametro(Integer codigo, String valor, String descricao, int tipo)
	{
		this.codigo = codigo;
		this.valor = valor;
		this.descricao = descricao;
		this.tipo = tipo;
	}

	public Integer getCodigo()
	{
		return codigo;
	}

	public void setCodigo(Integer codigo)
	{
		this.codigo = codigo;
	}

	public String getValor()
	{
		return valor;
	}

	public void setValor(String valor)
	{
		this.valor = valor;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Integer getTipo()
	{
		return tipo;
	}

	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (codigo != null ? codigo.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Parametro))
		{
			return false;
		}
		Parametro other = (Parametro) object;
		if ((this.codigo == null && other.codigo != null)
				|| (this.codigo != null && !this.codigo.equals(other.codigo)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Parametro[ codigo=" + codigo + " ]";
	}

}
