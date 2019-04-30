/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.notificar.service;

import javax.ejb.EJBException;
import javax.persistence.PersistenceException;
import org.apache.commons.mail.EmailException;
import br.com.xlearning.notificacao.entidade.Notificacao;


/**
 * @author Jesiel Viana
 *
 * Date: 18/10/2013
 */
public interface EnviarNotificacaoService
{

   public void enviar(Notificacao notificacao, GeradorNotificacao geraradorNotificacao) throws PersistenceException, EJBException, EmailException;
}
