package site.kason.myinspect;

import java.io.FileOutputStream;
import java.io.IOException;
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
import site.kason.myinspect.inspect.Diagnosis;
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

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if(roundEnv.processingOver()) return false;
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
    if(jdbcUrl!=null && !jdbcUrl.isEmpty()){
      inspector.addAnalyzer(new SyntaxAnalyzer(jdbcUrl, user, password));
    }
    for(ResultGroup rg :resultMap.values()){
      for(ResultItem it:rg.getItems()){
        String sql = it.getSql();
        String normalSql = MybatisSQLUtil.removeVar(sql);
        List<Diagnosis> diagnosisList = inspector.inspect(normalSql);
        for(Diagnosis d:diagnosisList){
          System.out.println(sql);
          System.out.println(d);
          System.out.println("");
        }
      }
    }
    
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
