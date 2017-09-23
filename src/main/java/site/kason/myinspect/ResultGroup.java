package site.kason.myinspect;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kason Yang
 */
public class ResultGroup {

  private final String name;

  private final List<ResultItem> items = new LinkedList();

  public ResultGroup(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public List<ResultItem> getItems() {
    return items;
  }

}
