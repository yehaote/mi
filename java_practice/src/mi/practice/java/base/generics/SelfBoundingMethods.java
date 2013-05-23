package mi.practice.java.base.generics;
/**
 * 自包含也可以使用在函数的泛型化上
 * @author waf
 */
public class SelfBoundingMethods {
	static <T extends SelfBounded<T>>
		T f(T arg){
		return arg.set(arg).get();
	}
}
