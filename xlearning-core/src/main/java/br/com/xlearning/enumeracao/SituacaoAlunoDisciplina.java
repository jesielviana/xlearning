/**
 * Xlearning 2013
 */
package br.com.xlearning.enumeracao;


/**
 * @author Jesiel Viana
 * Date 21/09/2013
 */
public enum SituacaoAlunoDisciplina {
	
	A_CURSAR(0, "A cursar"), CURSANDO(1, "Cursando"),  CURSADA(2, "Cursada");
	
	private Integer chave;
	private String valor;
	
	SituacaoAlunoDisciplina(Integer chave, String valor){
		this.chave = chave;
		this.valor = valor;
	}
	
	public static SituacaoAlunoDisciplina get(Integer chave){
		for(SituacaoAlunoDisciplina situacao : SituacaoAlunoDisciplina.values()){
			if(situacao.getChave().equals(chave)){
				return situacao;
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
