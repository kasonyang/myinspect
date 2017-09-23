package test.site.kason.myinspect.util;

import org.junit.Test;
import static org.junit.Assert.*;
import site.kason.myinspect.util.MybatisSQLUtil;

/**
 *
 * @author Kason Yang
 */
public class MybatisSQLUtilTest {
  
  public MybatisSQLUtilTest() {
  }
  
  @Test
  public void test(){
    String sql = "key=#{key}";
    assertEquals("key=null", MybatisSQLUtil.removeVar(sql));
  }
  
}
