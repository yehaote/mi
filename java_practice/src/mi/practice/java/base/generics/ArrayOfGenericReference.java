package mi.practice.java.base.generics;
/**
 * 在很多时候可以指定泛型的类型再生成一个类进行调用, 
 * 这样会让编译器变得比较Happy
 * @author waf
 * @param <T>
 */
class Generic<T>{}

public class ArrayOfGenericReference {
	static Generic<Integer>[] gia;
}
