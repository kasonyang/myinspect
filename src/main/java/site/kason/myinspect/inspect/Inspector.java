package site.kason.myinspect.inspect;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kason Yang
 */
public class Inspector {

  private List<Analyzer> analyzers = new LinkedList();

  public void addAnalyzer(Analyzer analyzer) {
    this.analyzers.add(analyzer);
  }

  public List<Diagnosis> inspect(String sql) {
    List<Diagnosis> results = new LinkedList();
    for (Analyzer a : this.analyzers) {
      results.addAll(a.analyse(sql));
    }
    return results;
  }

}
