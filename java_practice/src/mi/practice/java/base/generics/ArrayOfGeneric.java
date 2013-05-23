package mi.practice.java.base.generics;
/**
 * 问题是数组是根据实际的类型来进行分配的,
 * 所以我们不能实例化Object[]数组转换成Generic<Integer>[],
 * 只能实例化出擦写后的类型Generic的数组, 
 * 然后转换成Generic<Integer>数组
 * @author waf
 */
public class ArrayOfGeneric {
	static final int SIZE=100;
	static Generic<Integer>[] gia;
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		// 虽说可以这么写,编译的时候不会报错, 但是实际执行的时候, 会报ClassCastException
		//gia=(Generic<Integer>[]) new Object[SIZE];
		// 因为执行的时候是rawtype, 因为擦写的关系
		//去掉<Integer>进行实例化, 然后再转成Generic<Integer>[]
		gia = (Generic<Integer>[])new Generic[SIZE];
		System.out.println(gia.getClass().getSimpleName());
		gia[0] = new Generic<Integer>();
//		gia[1] = new Object();//编译出错
//		gia[2] = new Generic<Double>();//编译出错
	}
}
