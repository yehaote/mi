package mi.practice.java.base.generics;

import java.lang.reflect.Array;
/**
 * 可以传入一个Class来消除擦写带来的副作用,
 * 虽然还是要加上强制转换但是实际是可以工作的
 * @author waf
 * @param <T>
 */
public class GenericArrayWithTypeToken<T> {
	private T[] array;
	@SuppressWarnings("unchecked")
	public GenericArrayWithTypeToken(Class<T> type,int sz){
		// 使用Array.newInstance()来实例化数组, 
		// 这是一个native方法
		array=(T[]) Array.newInstance(type, sz);
	}
	public void put(int index ,T item){
		array[index]=item;
	}
	public T get(int index){
		return array[index];
	}
	public T[] rep(){
		return array;
	}
	public static void main(String[] args){
		GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<Integer>(Integer.class,10);
		for(int i=0;i<10;i++){
			gai.put(i, i);
		}
		System.out.println(gai);
		for(int i=0;i<10;i++){
			System.out.print(gai.get(i)+" ");
		}
		try{
			// 这样就可以工作了
			Integer[] ia =gai.rep();
			System.out.println(ia);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
