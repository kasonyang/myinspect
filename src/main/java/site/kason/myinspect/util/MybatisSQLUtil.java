package site.kason.myinspect.util;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

/**
 *
 * @author Kason Yang
 */
public class MybatisSQLUtil {

  public static String removeVar(String sql) {
    GenericTokenParser parser = new GenericTokenParser("#{", "}", new TokenHandler() {
      @Override
      public String handleToken(String content) {
        return "0";
      }
    });
    return parser.parse(sql);
  }

}
