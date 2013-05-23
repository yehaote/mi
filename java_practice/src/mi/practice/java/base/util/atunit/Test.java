package mi.practice.java.base.util.atunit;

import java.lang.annotation.*;

/**
 * 定义个@Test annotation
 * 定义标签跟定义接口差不多
 * 实际上编译出来的class文件跟interface差不多
 * Compiled from "Test.java"
 * public interface mi.practice.java.base.util.atunit.Test extends
 * java.lang.annotation.Annotation{}
 */
@Target(ElementType.METHOD)
// 作用在方法上
@Retention(RetentionPolicy.RUNTIME)
// 保留的地方, SOURCE, CLASS, RUNTIME
public @interface Test {
}
