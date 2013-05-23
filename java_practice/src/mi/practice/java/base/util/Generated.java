package mi.practice.java.base.util;
/**
 * 注意这个方法的新建数组不能创建基本类型的数组
 * @author waf
 *
 */
public class Generated {
	// Fill a existing array;
	public static <T> T[]
			array(T[] a, Generator<T> gen){
		return new CollectionData<T>(gen, a.length).toArray(a);
	}
	
	// Create a new array:
	@SuppressWarnings("unchecked")
	public static <T> T[]
			array(Class<T> type, Generator<T> gen, int size){
		// native method
		T[] a = (T[]) java.lang.reflect.Array.newInstance(type, size);
		return new CollectionData<T>(gen, size).toArray(a);
	}
}
