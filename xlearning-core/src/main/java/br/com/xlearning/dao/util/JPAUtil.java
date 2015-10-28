/**
 * 
 */
package br.com.xlearning.dao.util;


/**
 * Classe útil que é usada para criação de EntityManager quando ela é injetada em qualquer classe do sistema
 * 
 * 
 * @author jesiel
 *
 */
public class JPAUtil {

//	/**
//	 * Método produtor de EntityManagerFactory
//	 */
//	@Produces @RequestScoped @MySQLDatabase
//	public EntityManagerFactory criaEntityManagerFactory(){
//		return Persistence.createEntityManagerFactory("xlearning");
//	}
//	
//	/**
//	 * Método produtor de EntityManager
//	 */
//	@Produces @RequestScoped @MySQLDatabase
//	public EntityManager criaEntityManager(@MySQLDatabase EntityManagerFactory factory){
//		return factory.createEntityManager();
//	}
//	
//	/**
//	 * Método chamado automaticamente ao final do request para fechar a EntityManager
//	 */
//	public void fechaEntityManager(@MySQLDatabase @Disposes EntityManager entityManager){
//		entityManager.close();
//	}
}


