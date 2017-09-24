package site.kason.myinspect.inspect;

import java.util.List;
import javax.annotation.Nullable;

/**
 *
 * @author Kason Yang
 */
public interface Analyzer {

  public void analyse(String sql,@Nullable DiagnosisHandler diagnosisHandler);

}
