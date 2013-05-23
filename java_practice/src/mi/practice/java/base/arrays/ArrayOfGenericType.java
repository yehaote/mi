package mi.practice.java.base.arrays;
/**
 * 当创建一个Object[]的时候, 他可以存放任何对象, 
 * 就是不能保存基本类型.
 * 而在创建一个String[]以后, 编译期跟执行期都需要
 * 放入String
 * @author waf
 * @param <T>
 */
public class ArrayOfGenericType<T> {
	T[] array;
	@SuppressWarnings("unchecked")
	public ArrayOfGenericType(int size){
//		array = new T[size]; // 不合法
		array = (T[])new Object[size]; // unchecked
	}
	
//	public <U> U[]
//			makeArray(){
//		return new U[10];// 不能这样进行初始化
//	}
}
