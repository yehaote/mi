package mi.practice.java.base.arrays;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

/**
 * 当泛型跟数组一起使用的时候会有一些问题, 
 * 因为数组是固定类型的,
 * 而泛型到擦写才会生成具体的类型
 * @author waf
 * @param <T>
 */
class ClassParameter<T>{
	public T[] f(T[] arg){
		return arg;
	}
}

class MethodParameter{
	public static <T>
		T[] f(T[] arg){
		return arg;
	}
}
public class ParameterizedArrayType {
	@SuppressWarnings({ "unused", "unchecked" })
	public static void main(String[] args){
		List<Integer>[] intLists; // 虽然这样可以定义, 其实是不能实例化的
//		List<Integer>[] intLists = new ArrayList<Integer>[10]; // 不能编译
		intLists = ((List<Integer>[])new ArrayList[3]);//但你可以实例化一个, 然后转换它
		
		Integer[] ints = {1,2,3,4,5};
		Double[] doubles={1.1, 2.2, 3.3, 4.4, 5.5};
		Integer[] ints2 = new ClassParameter<Integer>().f(ints);
		Double[] doubles2= new ClassParameter<Double>().f(doubles);
		ints2= MethodParameter.f(ints);
		doubles=MethodParameter.f(doubles);
	}
}
