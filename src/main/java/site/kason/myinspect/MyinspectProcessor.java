package site.kason.myinspect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

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
    
    String outFile = System.getProperty("myinspect.outfile");
    if (outFile != null && !outFile.isEmpty()) {
      try {
        String out = srev.getResult().render();
        //System.out.println(out);
        System.out.println(out.length());
        byte[] bytes = out.getBytes("utf-8");
        System.out.println(bytes.length);
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
          fos.write(out.getBytes("utf-8"));
          //FileUtils.writeStringToFile(new File(outFile), out, "utf-8");
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    return false;
  }

}
