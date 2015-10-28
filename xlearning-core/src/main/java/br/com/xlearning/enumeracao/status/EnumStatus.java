package br.com.xlearning.enumeracao.status;

public enum EnumStatus {

	INATIVO(0, "Inativo"), ATIVO(1, "Ativo");
	
	private Integer chave;
	private String valor;
	
	EnumStatus(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static EnumStatus get(Integer chave){
		for(EnumStatus enumStatus : EnumStatus.values()){
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
