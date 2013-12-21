/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.enumeracao;


/**
 * @author Jesiel Viana
 *
 * Date: 24/10/2013
 */
public enum StatusAprovacao
{
   NAO_CURSOU(0, "Não cursou"), APROVADO(1, "Aprovado"),  REPROVADO(2, "Reprovado");
   
   private Integer chave;
   private String valor;
   
   StatusAprovacao(Integer chave, String valor){
      this.chave = chave;
      this.valor = valor;
   }
   
   public static StatusAprovacao get(Integer chave){
      for(StatusAprovacao situacao : StatusAprovacao.values()){
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
