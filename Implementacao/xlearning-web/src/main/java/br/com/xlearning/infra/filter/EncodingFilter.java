/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.infra.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


/**
 * @author Jesiel Viana
 *
 * Date: 04/11/2013
 */
@WebFilter(urlPatterns = "/*")
public class EncodingFilter  implements Filter
{

   @Override
   public void destroy()
   {
      
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
   {
      request.setCharacterEncoding("UTF-8") ;
      chain.doFilter(request, response);
   }

   @Override
   public void init(FilterConfig arg0) throws ServletException
   {
      
   }

}
