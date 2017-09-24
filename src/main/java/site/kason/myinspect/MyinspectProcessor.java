package site.kason.myinspect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import site.kason.myinspect.inspect.Inspector;
import site.kason.myinspect.inspect.SyntaxAnalyzer;
import site.kason.myinspect.util.MybatisSQLUtil;

/**
 *
 * @author Kason Yang
 */
@SupportedAnnotationTypes({"org.apache.ibatis.annotations.Insert", "org.apache.ibatis.annotations.Update", "org.apache.ibatis.annotations.Select", "org.apache.ibatis.annotations.Delete"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyinspectProcessor extends AbstractProcessor {

  private int diagnosisCount;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      return false;
    }
    MyinspectElementVisitor srev = new MyinspectElementVisitor();
    for (Element e : roundEnv.getRootElements()) {
      srev.visit(e);
    }
    Result result = srev.getResult();
    Map<String, ResultGroup> resultMap = result.getResults();
    Inspector inspector = new Inspector();
    String jdbcUrl = System.getProperty("myinspect.db.url");
    String user = System.getProperty("myinspect.db.user");
    String password = System.getProperty("myinspect.db.password");
    String driver = System.getProperty("myinspect.db.driver");
    if (driver != null && !driver.isEmpty()) {
      try {
        Class<?> clazz = Class.forName(driver);
        Driver driverObj = (Driver) clazz.newInstance();
        DriverManager.registerDriver(driverObj);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    List<String> analyzers = new LinkedList();
    if (jdbcUrl != null && !jdbcUrl.isEmpty()) {
      inspector.addAnalyzer(new SyntaxAnalyzer(jdbcUrl, user, password));
      analyzers.add(SyntaxAnalyzer.class.getSimpleName());
    }
    System.out.println("myinspect:Using inspect Analyzers:" + analyzers);
    int inspectedCount = 0;
    diagnosisCount = 0;
    for (ResultGroup rg : resultMap.values()) {
      for (ResultItem it : rg.getItems()) {
        String sql = it.getSql();
        String normalSql = MybatisSQLUtil.removeVar(sql);
        inspector.inspect(normalSql, (d) -> {
          System.out.println("");
          System.out.println(sql);
          System.out.println(d);
          diagnosisCount++;
        });
        inspectedCount++;
      }
    }
    System.out.format("myinspect:%d sql inspected. %d diagnosis provided.\n", inspectedCount, diagnosisCount);
    String outFile = System.getProperty("myinspect.outfile");
    if (outFile != null && !outFile.isEmpty()) {
      try {
        String out = result.render();
        //System.out.println(out);
        System.out.println(out.length());
        byte[] bytes = out.getBytes("utf-8");
        //System.out.println(bytes.length);
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
          fos.write(bytes);
          //FileUtils.writeStringToFile(new File(outFile), out, "utf-8");
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    return false;
  }

}
