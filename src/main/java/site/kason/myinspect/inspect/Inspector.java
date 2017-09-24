package site.kason.myinspect.inspect;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

/**
 *
 * @author Kason Yang
 */
public class Inspector {

  private List<Analyzer> analyzers = new LinkedList();

  public void addAnalyzer(Analyzer analyzer) {
    this.analyzers.add(analyzer);
  }

  public void inspect(String sql,@Nullable DiagnosisHandler diagnosisHandler) {
    if(diagnosisHandler==null) diagnosisHandler = DiagnosisHandler.NONE;
    for (Analyzer a : this.analyzers) {
      a.analyse(sql, diagnosisHandler);
    }
  }

}
