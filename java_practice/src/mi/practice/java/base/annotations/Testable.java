package mi.practice.java.base.annotations;

import mi.practice.java.base.util.atunit.Test;

/**
 * 使用一个自己定义的annotation
 */
public class Testable {
	public void execute() {
		System.out.println("Executing..");
	}

	@Test // 在方法前加上标签
	void testExecute() {
		execute();
	}
}
