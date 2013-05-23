package mi.practice.java.base.generics;
/**
 * 想对于GenericArray那种直接存放T[]的方式, 
 * 其实存Object[]然后取出的时候做下类型转换会更好,
 * 只是这样的方式也不能解决返回T[]的问题
 * @author waf
 * @param <T>
 */
public class GenericArray2<T> {
	private Object[] array;
	public GenericArray2(int sz){
		array = new Object[sz];
	}
	public void put(int index, T item){
		array[index]=item;
	}
	@SuppressWarnings("unchecked")
	public T get(int index){
		return (T) array[index];
	}
	@SuppressWarnings("unchecked")
	public T[] rep(){
		return (T[]) array;
	}
	public static void main(String[] args){
		GenericArray2<Integer> gai = new GenericArray2<Integer>(10);
		for(int i=0;i<10;i++){
			gai.put(i, i);
		}
		System.out.println(gai);
		for(int i=0;i<10;i++){
			System.out.print(gai.get(i)+" ");
		}
		try{
			//还是不能转换会报错ClassCastException
			Integer[] ia =gai.rep();
			System.out.println(ia);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
