package mi.practice.java.base.access;

/**
 * 限定访问的MyClass类
 * 这个Class在包中
 * 不使用import的话,我们要指定全陈来访问这个类
 */
public class QualifiedMyClass {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		mi.practice.java.base.access.mypackage.MyClass myClass = new mi.practice.java.base.access.mypackage.MyClass();
	}
}
