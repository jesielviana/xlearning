package br.com.xlearning.enumeracao.status;

public enum StatusPost {

	DESABILITADO(0, "Desabilitado"), HABILITADO(1, "Habilitado");
	
	private Integer chave;
	private String valor;
	
	StatusPost(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static StatusPost get(Integer chave){
		for(StatusPost enumStatus : StatusPost.values()){
			if(enumStatus.getChave().equals(chave)){
				return enumStatus;
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
