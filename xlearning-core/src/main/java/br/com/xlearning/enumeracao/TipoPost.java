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
public enum TipoPost
{
   ALUNOS(0, "Alunos"), PAGINA_INICIAL(1, "Página inicial"),  PROFESSOR(2, "Professores"),  
   CURSO(3, "Cursos"),  USUARIOS(4, "Usuários"), PAGINA_CURSO(5, "Página do curso"), DISCIPLINA(6 , "Disciplina");
   
   private Integer chave;
   private String valor;
   
   TipoPost(Integer chave, String valor){
      this.chave = chave;
      this.valor = valor;
   }
   
   public static TipoPost get(Integer chave){
      for(TipoPost situacao : TipoPost.values()){
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
