package mi.practice.java.base.generics;
/**
 * 使用泛型可以对函数的返回值也得到泛型的效果
 * @author waf
 * @param <T>
 */
public class ReturnGenericType <T extends HasF>{
	private T obj;
	public ReturnGenericType(T x){
		obj=x;
	}
	public T get(){
		return obj;
	}
}
