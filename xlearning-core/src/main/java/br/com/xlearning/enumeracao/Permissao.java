package br.com.xlearning.enumeracao;

/**
 * 
 * @author jesiel
 * @Data: 2013
 */
public enum Permissao {
	
	ROLE_ADMIN("Usuário administrativo"), ROLE_PROFESSOR("Usuário Professor"), ROLE_COORDENADOR("Usuário Coordenador"), 
	ROLE_ALUNO("Usuário aluno"), ROLE_SUPER("Usuário super admin");
	
	private String descricao;
	
	private Permissao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Permissao get(String descricao){
		for(Permissao permissao : Permissao.values()){
			if(permissao.getDescricao().equals(descricao)){
				return permissao;
			}
		}
		return null;
		
	}
}