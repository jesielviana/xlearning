package br.com.xlearning.enumeracao.status;

public enum StatusQuestionarioAluno {

	A_FAZER(0, "A fazer"), EM_ANDAMENTO(1, "Em andamento"), CONCLUIDO(2, "Concluido");
	
	private Integer chave;
	private String valor;
	
	StatusQuestionarioAluno(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static StatusQuestionarioAluno get(Integer chave){
		for(StatusQuestionarioAluno enumStatus : StatusQuestionarioAluno.values()){
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
