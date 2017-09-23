package site.kason.myinspect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import site.kason.tempera.engine.Configuration;
import site.kason.tempera.engine.Engine;
import site.kason.tempera.engine.Template;
import site.kason.tempera.loader.ClasspathTemplateLoader;

/**
 *
 * @author Kason Yang
 */
public class Result {

  private final Map<String,ResultGroup> resultGroups = new HashMap();

  public void put(String type, String method, String sql) {
    ResultGroup group = resultGroups.get(type);
    if(group==null){
      group = new ResultGroup(type);
      resultGroups.put(type, group);
    }
    group.getItems().add(new ResultItem(type, method, sql));
  }

  public void put(String type, String method, String[] sql) {
    for (String s : sql) {
      put(type, method, s);
    }
  }

  public String render() {
    List<ResultGroup> groups = new ArrayList(resultGroups.values());
    Configuration conf  = new Configuration();
    conf.setTemplateLoader(new ClasspathTemplateLoader(new String[]{".tpr"}));
    Engine engine = new Engine(conf);
    try {
      Template tpl = engine.compile("site.kason.myinspect.sqlsmd");
      Map<String,Object> data = new HashMap();
      data.put("groups", groups);
      return tpl.render(data);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

}
