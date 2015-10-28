/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.error;

import java.io.Serializable;

/**
 * @author jesiel
 * @Data: 2013
 */
public class Error implements Serializable{
	   private static final long serialVersionUID = 1L;
	   private int errorCode;
	   private Object[] args;

	   public Error(int errorCode, String... args)
	   {
	      this.errorCode = errorCode;
	      this.args = args;
	   }

	   public int getErrorCode()
	   {
	      return errorCode;
	   }

	   public void setErrorCode(int errorCode)
	   {
	      this.errorCode = errorCode;
	   }

	   public Object[] getArgs()
	   {
	      return args;
	   }

	   public void setArgs(Object[] args)
	   {
	      this.args = args;
	   }

}
