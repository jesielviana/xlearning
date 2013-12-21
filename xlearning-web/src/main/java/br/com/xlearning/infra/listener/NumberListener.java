package br.com.xlearning.infra.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * 
 * @author jesiel
 *
 */
@WebListener
public class NumberListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("*************************** parametro para number ***************************");
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}  


}
