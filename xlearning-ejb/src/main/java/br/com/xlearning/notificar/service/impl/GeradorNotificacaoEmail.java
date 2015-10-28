/**
 * TCC BSI 2013
 * xlearning.com.br
 */

package br.com.xlearning.notificar.service.impl;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import br.com.xlearning.notificacao.entidade.Notificacao;
import br.com.xlearning.notificar.service.GeradorNotificacao;
import br.com.xlearning.usuario.entidade.Usuario;

/**
 * @author Jesiel Viana Date: 18/10/2013
 */
@Stateless
@LocalBean
@Local(GeradorNotificacao.class)
public class GeradorNotificacaoEmail implements GeradorNotificacao
{

   @SuppressWarnings("deprecation")
   @Override
   public void gerarNotificacaoEmail(Notificacao notificacao) throws PersistenceException, EJBException, EmailException
   {

      HtmlEmail email = new HtmlEmail();
      email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
      for(Usuario destinatario : notificacao.getDestinatarios())
      {
         email.addTo(destinatario.getEmail().toLowerCase().trim(), destinatario.getNome()); // destinatário
      }
      email.setFrom("xlearning.com.br@gmail.com", "Xlearning"); // remetente
      email.setSubject(notificacao.getAssunto()); // assunto
      email.setHtmlMsg(notificacao.getMensagem()); // mensagem

      email.setSSL(true);
      email.setTLS(true);
      email.setSmtpPort(465);
      email.addCc(notificacao.getUsuario().getEmail());//copia
      email.setAuthentication("xlearning.com.br@gmail.com", "xlearning123"); // para testar coloque seu email do gmail e senha.

      email.send(); // envia o e-mail

   }

}
