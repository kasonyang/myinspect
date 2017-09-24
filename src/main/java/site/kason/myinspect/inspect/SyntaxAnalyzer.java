package site.kason.myinspect.inspect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

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
      connection.prepareStatement(explainSql).executeQuery();
    } catch (SQLException ex) {
      Diagnosis dn = new Diagnosis(Diagnosis.Kind.ERROR, ex.getMessage(), null);
      diagnosisHandler.handleDiagnosis(dn);
    }
  }

}
