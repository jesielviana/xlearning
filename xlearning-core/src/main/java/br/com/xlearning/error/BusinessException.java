package br.com.xlearning.error;



import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * Classe projetada para sobreposi��o de obten��o de arquivo de proridades, onde s�o armazendas as descri��es dos erros a serem
 * apresentados.
 * 
 * @author marcio.amaral - M�rcio Ribeiro Gurgel do Amaral
 */
public class BusinessException extends Exception
{

   private static final long serialVersionUID = 1L;
   private static final String ERROR_CODE_PROPERTIES_LOCATION = "ErrorCode.properties";
   private Object[] args;
   private static Map<Integer, String> mensagens;
   private List<String> mensagensErro = new ArrayList<String>();
   private List<Error> erros = new ArrayList<Error>();
   private transient Logger logger = Logger.getLogger(BusinessException.class);
   private int errorCode;

   /**
    * Constr�i inst�ncia padr�o com base em error code. <br>
    * <b>Aten��o:</b> O "errorCode" deve estar registrado na classe {@link ErrorCode}
    * 
    * @param errorCode
    */
   public BusinessException(int errorCode, Object... args)
   {
      super();
      this.args = args;
      inicializaMensagens();
   }
   
   public BusinessException(int errorCode)
   {
      super();
      this.errorCode = errorCode;
      inicializaMensagens();
   }

   public BusinessException(List<Error> erros)
   {
      super();
      inicializaMensagens();
      this.erros = erros;
   }

   /**
    * Inicializa o mapa de mensagens lendo do arquivo de properties e armazenando na static Map<Integer, String> mensagens;
    */
   private void inicializaMensagens()
   {
      if (mensagens == null || mensagens.isEmpty())
      {
         mensagens = new HashMap<Integer, String>();
         try
         {
            Properties propriedadesEspecificas = new Properties();
            propriedadesEspecificas.load(new ClassPathResource(ERROR_CODE_PROPERTIES_LOCATION).getInputStream());

            for (Map.Entry<Object, Object> currentEntry : propriedadesEspecificas.entrySet())
            {
               String keyCandidate = String.valueOf(currentEntry.getKey().toString());
               if(isValid(keyCandidate))
               {
                  mensagens.put(Integer.parseInt(keyCandidate), String.valueOf(currentEntry.getValue()));
               }
            }
         }
         catch (IOException e)
         {
            // A IOException n�o deve subir
            logger.error("Erro ao tentar carregar arquivo de properties.", e);
         }
      }
   }
	public static boolean isValid(String candidatoNumero)
	{
		return candidatoNumero != null && !isEmpty(candidatoNumero) && onlyNumber(candidatoNumero);
	}
	
	  public static boolean isEmpty(String value)
	   {
		   return value.length() == 0;
	   }
	  
		public static boolean onlyNumber(String value)
		{
			 for(int i = 0; i < value.length(); i++) 
		      {
		         char charAt = value.charAt(i);
		         if(!Character.isDigit(charAt)) 
		         {
		            return false;
		         }
		      }
		      return true;
		}
		

   /**
    * Sobrep�e mensagem da exce��o
    */
   public String getMessage()
   {
      if (isMensagensInicializadas())
      {
         return trataMensagemNegocio();
      }
      else
      {
         return super.getMessage();
      }
   }

   /**
    * Verifica se a lista do arquivo properties ERROR_CODE_PROPERTIES_LOCATION foi carregada com sucesso
    */
   private boolean isMensagensInicializadas()
   {
      return mensagens != null && !mensagens.isEmpty();
   }

   /**
    * Trata as mensagens simples (�nica) e m�ltipla
    */
   private String trataMensagemNegocio()
   {
      if (isMultipla())
      {
         return trataMensagemMultipla();
      }
      else
      {
         return formataMensagem(getErrorCode(), args);
      }
   }

   private String trataMensagemMultipla()
   {
      StringBuffer listaMensagens = new StringBuffer();

      for (Error error : getErros())
      {
         listaMensagens.append(formataMensagem(error.getErrorCode(), error.getArgs()));
      }

      return listaMensagens.toString();
   }

   /**
    * @TODO Verifica se existe um errorCode dentro da lista de errors
    * @param errorCode
    * @return true se existe o errorCode na lista de errors
    */
   public boolean temErroCode(Integer errorCode)
   {
      boolean temErrorCode = false;
      for (Error error : getErros())
      {
         if (error.getErrorCode() == errorCode)
         {
            temErrorCode = true;
            break;
         }
      }

      return temErrorCode;
   }

   /**
    * @TODO Formata um Erro
    * @param errorCode
    * @param parametros
    * @return mensagem de erro formatada
    */
   private String formataMensagem(Integer errorCode, Object... parametros)
   {
      String mensagemFormatada = new String();

      StringBuffer stringBuffer = new StringBuffer();
      String mensagemErro = mensagens.get(errorCode);
      stringBuffer.append((mensagemErro != null) ? mensagemErro + "\n" : "");

      if (super.getMessage() != null && !super.getMessage().isEmpty())
      {
         stringBuffer.append("\t" + super.getMessage().toString());
      }

      /** Apresenta argumentos din�micos aplic�veis ao erro **/
      if (parametros != null && parametros.length > 0)
      {
         mensagemFormatada = MessageFormat.format(stringBuffer.toString(), parametros);
      }
      else
      {
         mensagemFormatada = stringBuffer.toString();
      }

      return mensagemFormatada;
   }

   public boolean isMultipla()
   {
      return !getErros().isEmpty();
   }

   public List<Error> getErros()
   {
      return erros;
   }

   public void setErros(List<Error> erros)
   {
      this.erros = erros;
   }

   public List<String> getMensagensErro()
   {
      for (Error error : getErros())
      {
         mensagensErro.add(formataMensagem(error.getErrorCode(), error.getArgs()));
      }

      return mensagensErro;
   }

   public void setMensagensErro(List<String> mensagensErro)
   {
      this.mensagensErro = mensagensErro;
   }

public int getErrorCode() {
	return errorCode;
}

public void setErrorCode(int erroCode) {
	this.errorCode = erroCode;
}
}
