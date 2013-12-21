/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.enumeracao;


/**
 * @author Jesiel Viana
 *
 * Date: 08/10/2013
 */
public enum TipoConteudo
{
   PDF(1, "pdf"), VIDEO(2, "Video"),  OUTROS(3, "Outros");
   
   private Integer chave;
   private String descricao;
   
   private TipoConteudo(Integer chave, String descricao) {
      this.chave = chave;
      this.descricao = descricao;
   }
   
   public Integer getChave() {
      return chave;
   }
   
   public String getDescricao()
   {
   	return descricao;
   }
   public static TipoConteudo get(Integer chave){
      for(TipoConteudo tipoConteudo : TipoConteudo.values()){
         if(tipoConteudo.getChave().equals(chave)){
            return tipoConteudo;
         }
      }
      return null;
      
   }
}
