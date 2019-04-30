package br.com.xlearning.enumeracao.status;


public enum StatusUsuario {

	INATIVO(0, "Inativo"), ATIVO(1, "Ativo");
	
	private Integer chave;
	private String valor;
	
	StatusUsuario(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static StatusUsuario get(Integer chave){
		for(StatusUsuario statusUsuario : StatusUsuario.values()){
			if(statusUsuario.getChave().equals(chave)){
				return statusUsuario;
			}
		}
		return null;
	}

	public Integer getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}

}
