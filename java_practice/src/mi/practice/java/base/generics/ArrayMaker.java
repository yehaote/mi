package mi.practice.java.base.generics;

import java.lang.reflect.Array;
import java.util.Arrays;
/**
 * 即使kind已经被保存了,
 * 擦写的缘故, 仅仅识别保存了一个Class, 而不是一个类型参数
 * @author waf
 * @param <T>
 */
public class ArrayMaker<T> {
	private Class<T> kind;
	public ArrayMaker(Class<T> kind){
		this.kind=kind;
	}
	@SuppressWarnings("unchecked")
	T[] create(int size){
		//在泛型的时候推荐的创建数组的方式
		return (T[]) Array.newInstance(kind, size);
	}
	public static void main(String[] args){
		ArrayMaker<String> stringMaker=new ArrayMaker<String>(String.class);
		String[] stringArray=stringMaker.create(9);
		System.out.println(Arrays.toString(stringArray));
	}
}
