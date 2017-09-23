package site.kason.myinspect;

/**
 *
 * @author Kason Yang
 */
public class ResultItem {

  private String type, method, sql;

  public ResultItem(String type, String method, String sql) {
    this.type = type;
    this.method = method;
    this.sql = sql;
  }

  public String getType() {
    return type;
  }

  public String getMethod() {
    return method;
  }

  public String getSql() {
    return sql;
  }

}
