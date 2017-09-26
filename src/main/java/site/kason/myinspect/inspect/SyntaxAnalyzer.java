package site.kason.myinspect.inspect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kason Yang
 */
public class SyntaxAnalyzer implements Analyzer {

  private final Connection connection;

  public SyntaxAnalyzer(String jdbcUrl, String user, String password) {
    try {
      connection = DriverManager.getConnection(jdbcUrl, user, password);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void analyse(String sql,DiagnosisHandler diagnosisHandler) {
    String explainSql = "explain " + sql;
    try {
      ResultSet result = connection.prepareStatement(explainSql).executeQuery();
      while(result.next()){
        String extra = result.getString("Extra");
        if(extra==null) extra = "";
        extra = extra.toLowerCase();
        boolean usingWhere = extra.contains("using where");
        if( !usingWhere && (isDeleteSql(sql)||isUpdateSql(sql))){
          diagnosisHandler.handleDiagnosis(new Diagnosis(Diagnosis.Kind.WARNING, "delete/update table without where condition", "add where condition"));
        }
        if(extra.contains("using filesort")){
          diagnosisHandler.handleDiagnosis(new Diagnosis(Diagnosis.Kind.NOTE, "Using file sort", "optimize your sql"));
        }
      }
    } catch (SQLException ex) {
      Diagnosis dn = new Diagnosis(Diagnosis.Kind.ERROR, ex.getMessage(), null);
      diagnosisHandler.handleDiagnosis(dn);
    }
  }
  
  private boolean isDeleteSql(String sql){
    return sql.trim().toLowerCase().startsWith("delete ");
  }
  
  private boolean isUpdateSql(String sql){
    return sql.trim().toLowerCase().startsWith("update");
  }

}
