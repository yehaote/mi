package mi.practice.java.base.generics;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 跟OrdinaryArguments相比, 
 * 自包含的时候的参数跟返回值的变化是动态的, 
 * 而且至始至终只有一个方法存在, 而不是重载
 * @author waf
 * @param <T>
 */
interface SelfBoundSetter<T extends SelfBoundSetter<T>>{
	void set(T arg);
}
interface Setter extends SelfBoundSetter<Setter>{}

public class SelfBoundingAndCovariantArguments {
	@SuppressWarnings("rawtypes")
	void testA(Setter st1, Setter st2, SelfBoundSetter sbs){
		st1.set(st2);
//		st1.set(sbs); // 不能编译, 因为这个时候是指定type为Setter
	}
	
	public static void main(String[] args){
		Method[] methods = Setter.class.getMethods();
		for(Method method : methods){
			System.out.print(method.getName()+" ");
			Type[] parameters=method.getGenericParameterTypes();
			for(Type parameter: parameters){
				System.out.print(parameter.getClass().getSimpleName()+" ");
			}
			System.out.println();
		}
	}
}
