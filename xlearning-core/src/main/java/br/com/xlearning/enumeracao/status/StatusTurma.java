package br.com.xlearning.enumeracao.status;

public enum StatusTurma {

	ABERTA(0, "Aberta"), CONCLUIDA(1, "Concluida"),  CANCELADA(2, "Cancelada");
	
	private Integer chave;
	private String valor;
	
	StatusTurma(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static StatusTurma get(Integer chave){
		for(StatusTurma statusTurma : StatusTurma.values()){
			if(statusTurma.getChave().equals(chave)){
				return statusTurma;
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
