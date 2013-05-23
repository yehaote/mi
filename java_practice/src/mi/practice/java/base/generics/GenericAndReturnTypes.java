package mi.practice.java.base.generics;
//定义接口中可以返回自己的子类
interface GenericGetter<T extends GenericGetter<T>>{
	T get();
}
//不需要覆盖方法, 只需要指定泛型type就行
interface Getter extends GenericGetter<Getter>{}

/**
 * 可以对比CovariantReturnTypes
 * @author waf
 */
public class GenericAndReturnTypes {
	@SuppressWarnings({ "unused", "rawtypes" })
	void test(Getter g){
		Getter result =g.get();
		GenericGetter gg = g.get(); // Also the base type
	}
}
