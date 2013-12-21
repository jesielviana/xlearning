/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.mbean.usuario;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;


import br.com.xlearning.enumeracao.UF;
import br.com.xlearning.enumeracao.status.StatusUsuario;
import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.navegacao.NavigationMB;
import br.com.xlearning.usuario.entidade.Usuario;
import br.com.xlearning.usuario.service.impl.RoleServiceImpl;
import br.com.xlearning.util.XlearningUtil;

/**
 * @author jesiel
 * @Data: 2013
 */
public abstract class UsuarioMB extends PageMB{
	
	private static final long serialVersionUID = 1L;
	@EJB
	private RoleServiceImpl roleService;
	private Integer statusUsuario;
	private String uf;
	private String senha;
	@Inject
	private NavigationMB navegacaoMbean;
	
	public UsuarioMB() {
	}
	
	public List<UF> getUfItens() {
		return Arrays.asList(UF.values());
	}
	
	public List<StatusUsuario> getStatusItens() {
		return Arrays.asList(StatusUsuario.values());
	}
	
	public void recuperaUFeSatatus(Usuario usuario){
		setUf(usuario.getUf().getValor());
		setStatusUsuario(usuario.getStatus());
	}
	
	public RoleServiceImpl getRoleService() {
		return roleService;
	}
	public Integer getStatusUsuario() {
		return statusUsuario;
	}
	public String getUf() {
		return uf;
	}
	public NavigationMB getNavegacaoMbean() {
		return navegacaoMbean;
	}
	public void setRoleService(RoleServiceImpl roleService) {
		this.roleService = roleService;
	}
	public void setStatusUsuario(Integer statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public void setNavegacaoMbean(NavigationMB navegacaoMbean) {
		this.navegacaoMbean = navegacaoMbean;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	protected boolean validaCPF(Usuario usuario)
	{
		usuario.setCpf(removeMascaraCPF(usuario.getCpf()));
		return XlearningUtil.isCPFValido(usuario.getCpf());
	}
	
	private String removeMascaraCPF(String cpf)
	{
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		cpf = cpf.trim();
		return cpf;
	}

}
