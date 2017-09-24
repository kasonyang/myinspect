package site.kason.myinspect.inspect;

/**
 *
 * @author Kason Yang
 */
public interface DiagnosisHandler {
  
  DiagnosisHandler NONE = new DiagnosisHandler() {
    @Override
    public void handleDiagnosis(Diagnosis diagnosis) {
      //do nothing
    }
  };
  
  void handleDiagnosis(Diagnosis diagnosis);
  
}
