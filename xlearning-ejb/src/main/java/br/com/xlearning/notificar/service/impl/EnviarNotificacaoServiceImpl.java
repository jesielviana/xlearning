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
import br.com.xlearning.notificacao.entidade.Notificacao;
import br.com.xlearning.notificar.service.EnviarNotificacaoService;
import br.com.xlearning.notificar.service.GeradorNotificacao;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
@Stateless
@LocalBean
@Local(EnviarNotificacaoService.class)
public class EnviarNotificacaoServiceImpl implements EnviarNotificacaoService
{

   @Override
   public void enviar(Notificacao notificacao, GeradorNotificacao geraradorNotificacao) throws PersistenceException, EJBException, EmailException
   {
      geraradorNotificacao.gerarNotificacaoEmail(notificacao);
   }

}
