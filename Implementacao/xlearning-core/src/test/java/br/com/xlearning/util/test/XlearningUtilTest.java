/**
 * TCC BSI 2013
 * xlearning.com.br
 */
package br.com.xlearning.util.test;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import br.com.xlearning.util.XlearningUtil;


/**
 * @author Jesiel Viana
 *
 * Date: 04/11/2013
 */
@RunWith(JUnit4.class)
public class XlearningUtilTest
{

   @Test
   public void formataDataTest()
   {
     String data = XlearningUtil.formataData(new Date(), "dd/MM/yyyy");
     Assert.assertEquals("04/11/2013", data);
   }
}
