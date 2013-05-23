package mi.practice.java.base.access;

import mi.practice.java.base.access.mypackage.MyClass;

/**
 * import以后不用使用全称进行访问
 */
public class ImportedMyClass {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MyClass myClass = new MyClass();
	}
}
