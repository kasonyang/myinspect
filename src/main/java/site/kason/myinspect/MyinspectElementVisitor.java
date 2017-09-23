package site.kason.myinspect;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.AbstractElementVisitor8;
import kamons.collection.MapList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author Kason Yang
 */
public class MyinspectElementVisitor extends AbstractElementVisitor8<Object, Object> {

  private Result result = new Result();

  @Override
  public Object visitPackage(PackageElement e, Object p) {
    return null;
  }

  @Override
  public Object visitType(TypeElement e, Object p) {
    for (Element ee : e.getEnclosedElements()) {
      if (ee instanceof ExecutableElement) {
        this.handleExecutableElement(e, (ExecutableElement) ee);
      }
    }
    return null;
  }

  @Override
  public Object visitVariable(VariableElement e, Object p) {
    return null;
  }

  @Override
  public Object visitExecutable(ExecutableElement e, Object p) {

    return null;
  }

  @Override
  public Object visitTypeParameter(TypeParameterElement e, Object p) {
    return null;
  }

  private void handleExecutableElement(TypeElement te, ExecutableElement ee) {
    Select select = ee.getAnnotation(Select.class);
    Update update = ee.getAnnotation(Update.class);
    Delete delete = ee.getAnnotation(Delete.class);
    Insert insert = ee.getAnnotation(Insert.class);
    String cls = te.getQualifiedName().toString();
    String md = ee.toString();
    if (select != null) {
      result.put(cls, md, select.value());
    }
    if (update != null) {
      result.put(cls, md, update.value());
    }
    if (delete != null) {
      result.put(cls, md, delete.value());
    }
    if (insert != null) {
      result.put(cls, md, insert.value());
    }
  }

  public Result getResult() {
    return result;
  }

}
