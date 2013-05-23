package mi.practice.java.base.annotations;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
/**
 * 在使用apt的时候需要指定一个AnnotationProcessorFactory
 */
public class InterfaceExtractorProcessorFactory implements
		AnnotationProcessorFactory {
	@Override
	public AnnotationProcessor getProcessorFor(
			Set<AnnotationTypeDeclaration> atds,
			AnnotationProcessorEnvironment env) {
		return new InterfaceExtractorProcessor(env);
	}

	@Override
	public Collection<String> supportedAnnotationTypes() {
		// 生成单一元素的Collection
		return Collections
				.singleton("mi.practice.java.base.annotations.ExtractInterface");
	}

	@Override
	public Collection<String> supportedOptions() {
		// 返回一个空的集合
		return Collections.emptySet();
	}

}
