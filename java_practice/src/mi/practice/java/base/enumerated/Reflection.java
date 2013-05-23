package mi.practice.java.base.enumerated;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;
//import mi.practice.java.base.util.OSExecute;
/**
 * 可以发现enum在编译完以后会比Enum多一个values()的方法,
 * 这个方法在Enum中并没有, 是编译器生成的.
 * 编译器还会为enum生成一个新的valueOf()的方法,
 * Enum的valueOf()需要两个参数, 而添加的valueOf()仅仅需要一个参数.
 * enum定义生成的类是final的不能被继承
 */
enum Explore {
	HERE, THERE
}

public class Reflection {
	public static Set<String> analyze(Class<?> enumClass) {
		System.out.println("----- Analyzing " + enumClass + " -----");
		System.out.println("Interfaces:");
		for (Type t : enumClass.getGenericInterfaces()) {
			System.out.println(t);
		}
		System.out.println("Base: " + enumClass.getSuperclass());
		System.out.println("Methods: ");
		Set<String> methods = new TreeSet<String>();
		for (Method m : enumClass.getMethods()) {
			methods.add(m.getName());
		}
		System.out.println(methods);
		return methods;
	}

	public static void main(String[] args) {
		Set<String> exploreMethods = analyze(Explore.class);
		Set<String> enumMethods = analyze(Enum.class);
		System.out.println("Explore.containsAll(Enum)? "
				+ exploreMethods.containsAll(enumMethods));
		System.out.println("Explore.removeAll(Enum): ");
		exploreMethods.removeAll(enumMethods);
		System.out.println(exploreMethods);

//		OSExecute.command("javap Explore");
	}
}
