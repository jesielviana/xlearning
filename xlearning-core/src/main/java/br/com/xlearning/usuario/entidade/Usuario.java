package br.com.xlearning.usuario.entidade;



import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.xlearning.enumeracao.Permissao;
import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.role.entidade.Roles;

/**
 *
 * @author jesiel
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByCpf", query = "SELECT u FROM Usuario u WHERE u.cpf = :cpf"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email") })
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @NotNull
    @Column(name = "matricula")
    private Long matricula;
    @NotNull(message="{validation.usuario.nome}")
    @Size(min = 1, max = 60)
    @Column(name = "nome")
    private String nome;
    @Size(min = 2, max = 200)
    @Column(name = "avatar")
    private String avatar;
    @NotNull(message = "Informe o CPF")
    @Size(max = 14)
    @Column(name = "cpf")
    private String cpf;
    //if the field contains email address consider using this annotation to enforce field validation
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")
    @NotNull(message = "Informe o email")
    @Size(min = 5, max = 50)
    @Column(name = "email")
    private String email;
    @NotNull(message = "Informe o telefone")
   // @Pattern(regexp = "\\(?\\b([0-9]{2})\\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})\\b", message="Telefone em formato incorreto")
    @Column(name = "telefone")
    private String telefone;
    @Basic(optional = false)
    @NotNull(message = "Informe o endereço")
    @Size(min = 1, max = 100)
    @Column(name = "endereco")
    private String endereco;
    @NotNull(message = "Informe a UF")
    @Column(name = "uf")
    @Enumerated(EnumType.STRING)
    private UF uf;
    @NotNull(message = "Informe a cidade")
    @Size(min = 1, max = 20)
    @Column(name = "cidade")
    private String cidade;
    @NotNull(message = "Informe o status")
    @Column(name = "status")
    @Enumerated
    private StatusUsuario status;
    @Size(max = 50)
    @NotNull(message = "Informe a senha")
    @Column(name = "senha")
    private String senha;
    @JoinColumn(name = "role", referencedColumnName = "role")
    @ManyToOne(optional = false)
    private Roles role;

    public Usuario() {
    }

    public Usuario(Long matricula) {
        this.matricula = matricula;
    }

    public Usuario(Long matricula, String nome, String email, String telefone, String endereco, UF uf, String cidade, StatusUsuario status) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.uf = uf;
        this.cidade = cidade;
        this.status = status;
    }
    
    public boolean isAdmin(){
    	return Permissao.ROLE_ADMIN.toString().equals(role.getRole());
    }
    
    public boolean isCoordenador(){
    	return Permissao.ROLE_COORDENADOR.toString().equals(role.getRole());
    }
    
    public boolean isProfessor(){
    	return Permissao.ROLE_PROFESSOR.toString().equals(role.getRole());
    }
    
    public boolean isAluno(){
    	return Permissao.ROLE_ALUNO.toString().equals(role.getRole());
    }
    
   
    public boolean isAcademico()
    {
    	return Permissao.ROLE_COORDENADOR.toString().equals(role.getRole()) ||
    			 Permissao.ROLE_PROFESSOR.toString().equals(role.getRole()) ||
    			 Permissao.ROLE_ALUNO.toString().equals(role.getRole());
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getStatus() {
        return status.getChave();
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }
    
    @Transient
    public StatusUsuario getStatusUsuario(){
    	return status;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles roles) {
        this.role = roles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.matricula == null && other.matricula != null) || (this.matricula != null && !this.matricula.equals(other.matricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario[ matricula=" + matricula + " ]";
    }

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}
    
}
