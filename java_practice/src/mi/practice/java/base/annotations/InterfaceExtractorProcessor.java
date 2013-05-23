package mi.practice.java.base.annotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

/**
 * 抽取接口的处理器
 * 前提:
 * 1. 定义了一个标签@ExtractInterface
 * 2. 写了一个类Multiplier使用了@ExtractInterface标签
 * 注意AnnotationProcessor需要引入java的tool.jar
 * 执行的命令:
 * apt -factory
 * mi.practice.java.base.annotations.InterfaceExtractorProcessorFactory
 * /home/waf/git/mi/java_practice/src/
 * mi/practice/java/base/annotations/Multiplier.java
 * -s ..
 */
public class InterfaceExtractorProcessor implements AnnotationProcessor {
	// 处理标签的环境
	private final AnnotationProcessorEnvironment env;
	// 获取的声明方法
	private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList<MethodDeclaration>();

	public InterfaceExtractorProcessor(AnnotationProcessorEnvironment env) {
		this.env = env;
	}

	@Override
	public void process() {
		for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()) {
			// 获取这个type(ElementType.Type)的@ExtractInterface标签
			ExtractInterface annot = typeDecl
					.getAnnotation(ExtractInterface.class);
			if (annot == null) {
				break;
			}
			// 获取声明的方法, 并且必须是public的, 但是不能是static的
			for (MethodDeclaration m : typeDecl.getMethods()) {
				if (m.getModifiers().contains(Modifier.PUBLIC)
						&& !(m.getModifiers().contains(Modifier.STATIC))) {
					interfaceMethods.add(m);
				}
			}
			// 根据获取到的方法的信息, 生成接口文件
			if (interfaceMethods.size() > 0) {
				try {
					// 创建文件, 以@ExtractInterface的value为文件名
					// Filter是可以创建新文件(多个)的PrintWriter,
					// 创建完以后还可以持续跟踪.
					PrintWriter writer = env.getFiler().createSourceFile(
							annot.value());
					// 包空间跟被处理的Type相同
					writer.println("package "
							+ typeDecl.getPackage().getQualifiedName() + ";");
					writer.println("public interface " + annot.value() + "{");
					// 根据各个方法输出相应的在接口里的声明, 返回类型, 参数类型
					for (MethodDeclaration m : interfaceMethods) {
						writer.print("  public ");
						writer.print(m.getReturnType() + " ");
						writer.print(m.getSimpleName() + " (");
						int i = 0;
						for (ParameterDeclaration parm : m.getParameters()) {
							writer.print(parm.getType() + " "
									+ parm.getSimpleName());
							if (++i < m.getParameters().size()) {
								writer.print(", ");
							}
						}
						writer.println(");");
					}
					writer.println("}");
					writer.close();
				} catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}

	}

}
