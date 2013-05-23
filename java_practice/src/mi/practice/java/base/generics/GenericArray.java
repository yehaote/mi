package mi.practice.java.base.generics;

public class GenericArray<T> {
	private T[] array;
	@SuppressWarnings("unchecked")
	public GenericArray(int sz){
		//不能直接使用T实例化
//		array = new T[sz];
		//所以只能实例化成Object数组然后再转换
		array= (T[])new Object[sz];
	}
	public void put(int index, T item){
		array[index]=item;
	}
	public T get(int index){
		return array[index];
	}
	public T[] rep(){
		return array;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		GenericArray<Integer> gai = new GenericArray<Integer>(10);
		//不能这么转换, 因为实例化的时候是Object[]
		Integer[] ia =gai.rep();
		Object[] a= gai.rep();
		System.out.println(a);
	}
}
