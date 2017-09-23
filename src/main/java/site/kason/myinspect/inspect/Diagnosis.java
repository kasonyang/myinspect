package site.kason.myinspect.inspect;

import javax.annotation.Nullable;

/**
 *
 * @author Kason Yang
 */
public class Diagnosis {

  public static enum Kind {
    ERROR, WARNING
  }

  private Kind kind;

  private String message;

  private String suggestion;

  public Diagnosis(Kind kind, String message, @Nullable String suggestion) {
    this.kind = kind;
    this.message = message;
    this.suggestion = suggestion;
  }

  public Kind getKind() {
    return kind;
  }

  public String getMessage() {
    return message;
  }

  @Nullable
  public String getSuggestion() {
    return suggestion;
  }

  @Override
  public String toString() {
    return String.format("%s:%s", kind, message);
  }

}
