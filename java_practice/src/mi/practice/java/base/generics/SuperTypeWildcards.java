package mi.practice.java.base.generics;

import java.util.List;
/**
 * super在边界中代表它的父类, 
 * super只能结合?使用, 不能说<T super MyClass>
 * @author waf
 */
public class SuperTypeWildcards {
	static void writeTo(List<? super Apple> apples){
		apples.add(new Apple());
		apples.add(new Jonathan());
//		apples.add(new Fruit()); // 编译出错
	}
}
