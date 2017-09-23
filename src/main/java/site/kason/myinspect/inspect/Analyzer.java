package site.kason.myinspect.inspect;

import java.util.List;

/**
 *
 * @author Kason Yang
 */
public interface Analyzer {

  public List<Diagnosis> analyse(String sql);

}
