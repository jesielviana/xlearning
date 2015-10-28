/**
 * 
 */
package br.com.xlearning.mbean.navegacao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.xlearning.mbean.infra.PageMB;
import br.com.xlearning.mbean.util.ConstantsMB;

/**
 * @author jesiel
 * 
 */

@ManagedBean
@RequestScoped
public class NavigationMB extends PageMB {

	private static final long serialVersionUID = 1L;
	public static String BEAN_NAVIGATION = "NavigationMB";
	private static final String[] BEANS_REMOVER = { ConstantsMB.BEAN_ALUNO_MB,
			ConstantsMB.BEAN_PROESSOR_MB, ConstantsMB.BEAN_CURSO_MB,
			ConstantsMB.BEAN_DISCIPLINA_MB, ConstantsMB.BEAN_TURMA_MB, ConstantsMB.BEAN_LOGIN_MB, 
			ConstantsMB.BEAN_ADM_MB, ConstantsMB.BEAN_COORDENADOR_MB, ConstantsMB.BEAN_CONTEUDO_MB,
			ConstantsMB.BEAN_ALUNO_ACADEMICO_MB, ConstantsMB.BEAN_QUESTIONARIO_ALUNO, 
			ConstantsMB.BEAN_QUESTIONARIO, ConstantsMB.BEAN_POST, ConstantsMB.BEAN_PROFESSOR_ACADEMICO};

	public String paginaInicia() {
		limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL;
	}
	
	public String paginaIniciaAdministrativo(){
		limparSessao();
		return ConstantsNavigation.PAGINA_INICIAL_ADMINISTRATIVO;
	}
	
	public void limparSessao() {
		for (String beanRemover : BEANS_REMOVER) {
			removeBean(beanRemover);
		}
		this.getExternalContext().getApplicationMap().put("mudouBean", true);
	}

}
